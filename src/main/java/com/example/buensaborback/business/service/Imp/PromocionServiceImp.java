package com.example.buensaborback.business.service.Imp;

import com.example.buensaborback.business.service.Base.BaseServiceImp;
import com.example.buensaborback.business.service.PromocionService;
import com.example.buensaborback.domain.dto.SucursalDtos.SucursalShortDto;
import com.example.buensaborback.domain.entities.*;
import com.example.buensaborback.repositories.ArticuloRepository;
import com.example.buensaborback.repositories.PromocionDetalleRepository;
import com.example.buensaborback.repositories.PromocionRepository;
import com.example.buensaborback.repositories.SucursalRepository;
import com.example.buensaborback.utils.PublicIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class PromocionServiceImp extends BaseServiceImp<Promocion,Long> implements PromocionService {

    @Autowired
    ArticuloRepository articuloRepository;

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

    @Override
    public Promocion create(Promocion request) {
        Set<PromocionDetalle> detalles = request.getPromocionDetalles();
        Set<PromocionDetalle> detallesPersistidos = new HashSet<>();

        Set<Sucursal> sucursales = request.getSucursales();
        Set<Sucursal> sucursalesPersistidas = new HashSet<>();

        if (detalles != null && !detalles.isEmpty()) {
            for (PromocionDetalle detalle : detalles) {
                System.out.println(detalle.getCantidad());
                System.out.println(detalle.getArticulo().getDenominacion());
                Articulo articulo = detalle.getArticulo();
                if (articulo == null || articulo.getId() == null) {
                    throw new RuntimeException("El articulo del detalle no puede ser nulo");
                }
                articulo = articuloRepository.findById(detalle.getArticulo().getId())
                        .orElseThrow(() -> new RuntimeException("El aticulo con id " + detalle.getArticulo().getId() + " no se ha encontrado"));
                detalle.setArticulo(articulo);
                PromocionDetalle savedDetalle = promocionDetalleRepository.save(detalle);
                detallesPersistidos.add(savedDetalle);
            }
            request.setPromocionDetalles(detallesPersistidos);
        }

        // Verificar y asociar sucursales existentes
        if (sucursales != null && !sucursales.isEmpty()) {
            for (Sucursal sucursal : sucursales) {
                Sucursal sucursalBd = sucursalRepository.findById(sucursal.getId())
                        .orElseThrow(() -> new RuntimeException("La sucursal con id " + sucursal.getId() + " no se ha encontrado"));
                sucursalBd.getPromociones().add(request);
                sucursalesPersistidas.add(sucursalBd);
            }
            request.setSucursales(sucursalesPersistidas);
        }


        return promocionRepository.save(request);
    }

    @Override
    public Promocion update(Promocion request, Long id) {
        Promocion promocion = promocionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("La promoción con id " + id + " no se ha encontrado"));

        Set<ImagenPromocion> imagenes = request.getImagenes();

        Set<ImagenPromocion> imagenesEliminadas = promocion.getImagenes();
        imagenesEliminadas.removeAll(imagenes);
        for (ImagenPromocion imagen: imagenesEliminadas) {
            imagenPromocionService.deleteImage(publicIdService.obtenerPublicId(imagen.getUrl()), imagen.getId());
        }

        Set<PromocionDetalle> detalles = request.getPromocionDetalles();
        Set<PromocionDetalle> detallesPersistidos = new HashSet<>();

        Set<PromocionDetalle> detallesEliminados = promocion.getPromocionDetalles();
        detallesEliminados.removeAll(detalles);
        promocionDetalleRepository.deleteAll(detallesEliminados);

        if (detalles != null && !detalles.isEmpty()) {
            for (PromocionDetalle detalle : detalles) {
                Articulo articulo = detalle.getArticulo();
                if (articulo == null || articulo.getId() == null) {
                    throw new RuntimeException("El articulo del detalle no puede ser nulo");
                }
                articulo = articuloRepository.findById(detalle.getArticulo().getId())
                        .orElseThrow(() -> new RuntimeException("El aticulo con id " + detalle.getArticulo().getId() + " no se ha encontrado"));
                detalle.setArticulo(articulo);
                PromocionDetalle savedDetalle = promocionDetalleRepository.save(detalle);
                detallesPersistidos.add(savedDetalle);
            }
            request.setPromocionDetalles(detallesPersistidos);
        }

        if (!detallesPersistidos.isEmpty()) {
            request.setPromocionDetalles(detallesPersistidos);
        }

        Set<Sucursal> sucursales = request.getSucursales();
        Set<Sucursal> sucursalesPersistidas = new HashSet<>();

        if (sucursales != null && !sucursales.isEmpty()) {
            for (Sucursal sucursal : sucursales) {
                Sucursal sucursalBd = sucursalRepository.findById(sucursal.getId())
                        .orElseThrow(() -> new RuntimeException("La sucursal con id " + sucursal.getId() + " no se ha encontrado"));
                sucursalBd.getPromociones().add(promocion);
                sucursalesPersistidas.add(sucursalBd);
            }
            promocion.setSucursales(sucursalesPersistidas);
        }

        return super.update(request, id);
    }

    @Override
    public void deleteInSucursales(Long id, Long idSucursal) {
        Promocion promocionExistente = promocionRepository.getById(id);
        if (promocionExistente == null) {
            throw new RuntimeException("La promoción con el id " + id + " no se ha encontrado");
        }

        Sucursal sucursal = sucursalRepository.getById(idSucursal);
        if (sucursal == null) {
            throw new RuntimeException("La sucursal con el id " + id + " no se ha encontrado");
        }

        sucursal.getPromociones().remove(promocionExistente);
        promocionExistente.getSucursales().remove(sucursal);

        promocionRepository.save(promocionExistente);
    }
}

