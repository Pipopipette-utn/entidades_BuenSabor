package com.example.buensaborback.business.service.Imp;

import com.example.buensaborback.business.service.Base.BaseServiceImp;
import com.example.buensaborback.business.service.PromocionService;
import com.example.buensaborback.domain.dto.SucursalDtos.SucursalShortDto;
import com.example.buensaborback.domain.entities.*;
import com.example.buensaborback.repositories.*;
import com.example.buensaborback.utils.PublicIdService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PromocionServiceImp extends BaseServiceImp<Promocion,Long> implements PromocionService {

    @Autowired
    ArticuloRepository articuloRepository;

    @Autowired
    ArticuloInsumoRepository articuloInsumoRepository;

    @Autowired
    ArticuloManufacturadoRepository articuloManufacturadoRepository;

    @Autowired
    PromocionDetalleRepository promocionDetalleRepository;

    @Autowired
    PromocionRepository promocionRepository;

    @Autowired
    SucursalRepository sucursalRepository;

    @Autowired
    ImagenPromocionServiceImpl imagenPromocionService;

    @Autowired
    PublicIdService publicIdService;

    @Transactional
    public List<Promocion> create(Promocion request, Set<Sucursal> sucursales) {
        List<Promocion> promocionesCreadas = new ArrayList<>();

        for (Sucursal sucursal : sucursales) {
            Sucursal sucursalBd = sucursalRepository.findById(sucursal.getId())
                    .orElseThrow(() -> new RuntimeException("La sucursal con id " + sucursal.getId() + " no se ha encontrado"));
            Promocion nuevaPromocion = createPromocion(request, sucursalBd);
            promocionesCreadas.add(nuevaPromocion);
        }
        return promocionRepository.saveAll(promocionesCreadas);
    }

    @Transactional
    @Override
    public Promocion update(Promocion request, Long id) {
        Promocion promocionExistente = promocionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("La promoción con id " + id + " no se ha encontrado"));

        updateImagenes(request, promocionExistente);

        // Eliminar detalles antiguos
        promocionDetalleRepository.deleteAll(promocionExistente.getPromocionDetalles());

        Set<PromocionDetalle> nuevosDetalles = request.getPromocionDetalles();
        if (nuevosDetalles == null || nuevosDetalles.isEmpty()) {
            throw new RuntimeException("La promoción debe tener al menos un detalle.");
        }

        Set<PromocionDetalle> detallesActualizados = new HashSet<>();
        for (PromocionDetalle detalle : nuevosDetalles) {
            Articulo articulo = articuloInsumoRepository.findById(detalle.getArticulo().getId())
                    .orElseThrow(() -> new RuntimeException("El artículo con id " + detalle.getArticulo().getId() + " no se ha encontrado"));
            PromocionDetalle nuevoDetalle = new PromocionDetalle();
            nuevoDetalle.setCantidad(detalle.getCantidad());
            nuevoDetalle.setArticulo(articulo);
            detallesActualizados.add(nuevoDetalle);
        }

        request.setPromocionDetalles(detallesActualizados);

        return promocionRepository.save(request);
    }

    @Transactional
    public List<Promocion> duplicateInOtherSucursales(Long id, Set<SucursalShortDto> sucursales) {
        Promocion promocionExistente = promocionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Promoción no encontrada: { id: " + id + " }"));

        List<Promocion> promocionesDuplicadas = new ArrayList<>();

        for (SucursalShortDto sucursalDto : sucursales) {
            Sucursal sucursalBd = sucursalRepository.findById(sucursalDto.getId())
                    .orElseThrow(() -> new RuntimeException("La sucursal con id " + sucursalDto.getId() + " no se ha encontrado"));
            Promocion nuevaPromocion = createPromocion(promocionExistente, sucursalBd);
            promocionesDuplicadas.add(nuevaPromocion);
        }

        return promocionRepository.saveAll(promocionesDuplicadas);
    }

    @Override
    public Page<Promocion> findBySucursal_Id(Long sucursalId, Pageable pageable) {
        return promocionRepository.findBySucursal_Id(sucursalId, pageable);
    }

    private Promocion createPromocion(Promocion request, Sucursal sucursal) {
        Promocion nuevaPromocion = new Promocion();
        nuevaPromocion.setSucursal(request.getSucursal());
        nuevaPromocion.setTipoPromocion(request.getTipoPromocion());
        nuevaPromocion.setPrecioPromocional(request.getPrecioPromocional());
        nuevaPromocion.setDenominacion(request.getDenominacion());
        nuevaPromocion.setFechaDesde(request.getFechaDesde());
        nuevaPromocion.setFechaHasta(request.getFechaHasta());
        nuevaPromocion.setHoraDesde(request.getHoraDesde());
        nuevaPromocion.setHoraHasta(request.getHoraHasta());
        nuevaPromocion.setDescripcionDescuento(request.getDescripcionDescuento());

        nuevaPromocion.setImagenes(new HashSet<>(request.getImagenes()));

        Set<PromocionDetalle> detalles = request.getPromocionDetalles();
        Set<PromocionDetalle> nuevosDetalles = new HashSet<>();

        if (detalles != null && !detalles.isEmpty()) {
            for (PromocionDetalle detalle : detalles) {
                Articulo articuloExistente = articuloRepository.findById(detalle.getArticulo().getId()).orElseThrow(() -> new RuntimeException("Uno de los artículos enviados no es válido."));
                if (articuloExistente instanceof ArticuloInsumo)
                     articuloExistente = articuloInsumoRepository.findBySucursal_IdAndDenominacionContainingIgnoreCase(sucursal.getId(), detalle.getArticulo().getDenominacion());
                else if (articuloExistente instanceof ArticuloManufacturado)
                    articuloExistente = articuloManufacturadoRepository.findBySucursal_IdAndDenominacionContainingIgnoreCase(sucursal.getId(), detalle.getArticulo().getDenominacion());
                else
                    throw new RuntimeException("El artículo " + detalle.getArticulo().getDenominacion() + " no se ha encontrado en la sucursal " + sucursal.getNombre());

                if (articuloExistente == null) {
                    throw new RuntimeException("El artículo " + detalle.getArticulo().getDenominacion() + " no se ha encontrado en la sucursal " + sucursal.getNombre());
                }
                PromocionDetalle nuevoDetalle = new PromocionDetalle();
                nuevoDetalle.setCantidad(detalle.getCantidad());
                nuevoDetalle.setArticulo(articuloExistente);
                nuevosDetalles.add(nuevoDetalle);
            }
            nuevaPromocion.setPromocionDetalles(nuevosDetalles);
        } else {
            throw new RuntimeException("La promoción debe tener al menos un detalle.");
        }

        nuevaPromocion.setSucursal(sucursal);
        return nuevaPromocion;
    }

    private void updateImagenes(Promocion request, Promocion promocionExistente) {
        Set<ImagenPromocion> imagenes = request.getImagenes();
        Set<ImagenPromocion> imagenesEliminadas = new HashSet<>(promocionExistente.getImagenes());
        imagenesEliminadas.removeAll(imagenes);

        for (ImagenPromocion imagen : imagenesEliminadas) {
            imagenPromocionService.deleteImage(publicIdService.obtenerPublicId(imagen.getUrl()), imagen.getId());
        }

        promocionExistente.setImagenes(imagenes);
    }

    public Page<Promocion> getPromocionesByNombre(Pageable pageable, Long idSucursal, String denominacion) {
        return promocionRepository.findBySucursal_IdAndDenominacionContainingIgnoreCase(idSucursal, denominacion, pageable);
    }
}

