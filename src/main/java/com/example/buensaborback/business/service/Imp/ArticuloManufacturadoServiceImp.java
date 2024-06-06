package com.example.buensaborback.business.service.Imp;

import com.example.buensaborback.business.service.ArticuloManufacturadoService;
import com.example.buensaborback.business.service.Base.BaseServiceImp;
import com.example.buensaborback.domain.entities.*;
import com.example.buensaborback.repositories.*;
import com.example.buensaborback.utils.PublicIdService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ArticuloManufacturadoServiceImp extends BaseServiceImp<ArticuloManufacturado,Long> implements ArticuloManufacturadoService {

    @Autowired
    ArticuloManufacturadoRepository articuloManufacturadoRepository;

    @Autowired
    ImagenArticuloServiceImpl imagenArticuloService;

    @Autowired
    ArticuloManufacturadoDetalleRepository articuloManufacturadoDetalleRepository;

    @Autowired
    ArticuloInsumoRepository articuloInsumoRepository;

    @Autowired
    CategoriaRepository categoriaRepository;

    @Autowired
    PublicIdService publicIdService;

    @Autowired
    SucursalRepository sucursalRepository;

    @Override
    @Transactional
    public List<ArticuloManufacturado> create(ArticuloManufacturado request, Set<Sucursal> sucursales) {
        List<ArticuloManufacturado> articulosCreados = new ArrayList<>();

        for (Sucursal sucursal: sucursales) {
            ArticuloManufacturado nuevoArticulo = new ArticuloManufacturado();
            nuevoArticulo.setDenominacion(request.getDenominacion());
            nuevoArticulo.setPrecioVenta(request.getPrecioVenta());
            nuevoArticulo.setUnidadMedida(request.getUnidadMedida());
            nuevoArticulo.setTiempoEstimadoMinutos(request.getTiempoEstimadoMinutos());
            nuevoArticulo.setPreparacion(request.getPreparacion());


            Set<ArticuloManufacturadoDetalle> detalles = request.getArticuloManufacturadoDetalles();
            Set<ArticuloManufacturadoDetalle> detallesPersistidos = new HashSet<>();

            if (detalles != null && !detalles.isEmpty()) {
                for (ArticuloManufacturadoDetalle detalle : detalles) {
                    ArticuloInsumo insumo = detalle.getArticulo();
                    if (insumo == null || insumo.getId() == null) {
                        throw new RuntimeException("El insumo del detalle no puede ser nulo");
                    }
                    insumo = articuloInsumoRepository.findById(detalle.getArticulo().getId())
                            .orElseThrow(() -> new RuntimeException("El insumo con id " + detalle.getArticulo().getId() + " no se ha encontrado"));
                    detalle.setArticulo(insumo);
                    ArticuloManufacturadoDetalle savedDetalle = articuloManufacturadoDetalleRepository.save(detalle);
                    detallesPersistidos.add(savedDetalle);
                }
                nuevoArticulo.setArticuloManufacturadoDetalles(detallesPersistidos);
            }

            if (request.getCategoria() != null) {
                Categoria categoria = categoriaRepository.getById(request.getCategoria().getId());
                if (categoria == null) {
                    throw new RuntimeException("La categoría con id: " + request.getCategoria().getId() + " no existe");
                }
                if (categoria.isEsInsumo()) {
                    throw new RuntimeException("La categoría con id: " + request.getCategoria().getId() + " no pertenece a una categoría de insumos manufacturados");
                }

                nuevoArticulo.setCategoria(categoria);
            }

            Sucursal sucursalBd = sucursalRepository.getById(sucursal.getId());
            if (sucursalBd == null) {
                throw new RuntimeException("La sucursal con id " + sucursal.getId() + " no se ha encontrado");
            }
            nuevoArticulo.setSucursal(sucursalBd);
            // Guardar el artículo en la base de datos
            ArticuloManufacturado articuloGuardado = articuloManufacturadoRepository.save(nuevoArticulo);
            articulosCreados.add(articuloGuardado);
        }
        return articulosCreados;
    }

    @Override
    @Transactional
    public ArticuloManufacturado update(ArticuloManufacturado request, Long id) {

        ArticuloManufacturado articulo = articuloManufacturadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El articulo manufacturado con id " + id + " no se ha encontrado"));

        Set<ImagenArticulo> imagenes = request.getImagenes();
        Set<ImagenArticulo> imagenesEliminadas = articulo.getImagenes();
        Iterator<ImagenArticulo> iterator = imagenesEliminadas.iterator();
        while (iterator.hasNext()) {
            ImagenArticulo imagenEliminada = iterator.next();
            for (ImagenArticulo imagen : imagenes) {
                if (imagen.getId().equals(imagenEliminada.getId())) {
                    iterator.remove();
                    break;
                }
            }
        }

        for (ImagenArticulo imagen: imagenesEliminadas) {
            imagenArticuloService.deleteImage(publicIdService.obtenerPublicId(imagen.getUrl()), imagen.getId());
        }
        request.getImagenes().removeAll(imagenesEliminadas);

        Set<ArticuloManufacturadoDetalle> detalles = request.getArticuloManufacturadoDetalles();
        Set<ArticuloManufacturadoDetalle> detallesPersistidos = new HashSet<>();

        Set<ArticuloManufacturadoDetalle> detallesEliminados = articulo.getArticuloManufacturadoDetalles();
        detallesEliminados.removeAll(detalles);
        articuloManufacturadoDetalleRepository.deleteAll(detallesEliminados);

        if (!detalles.isEmpty()) {
            for (ArticuloManufacturadoDetalle detalle : detalles) {
                ArticuloInsumo insumo = detalle.getArticulo();
                if (insumo == null || insumo.getId() == null) {
                    throw new RuntimeException("El insumo del detalle no puede ser nulo");
                }
                insumo = articuloInsumoRepository.findById(detalle.getArticulo().getId())
                        .orElseThrow(() -> new RuntimeException("El insumo con id " + detalle.getArticulo().getId() + " no se ha encontrado"));
                ArticuloManufacturadoDetalle savedDetalle = articuloManufacturadoDetalleRepository.save(detalle);
                detallesPersistidos.add(savedDetalle);
            }
            articulo.setArticuloManufacturadoDetalles(detallesPersistidos);
        }

        if (!detallesPersistidos.isEmpty()) {
            request.setArticuloManufacturadoDetalles(detallesPersistidos);
        }

        if (request.getCategoria() != null) {
            Categoria categoria = categoriaRepository.getById(request.getCategoria().getId());
            if (categoria == null ) {
                throw new RuntimeException("La categoría con id: " + request.getCategoria().getId() + " no existe");
            }
            if (categoria.isEsInsumo()) {
                throw new RuntimeException("La categoría con id: " + request.getCategoria().getId() + " no pertenece a una categoría de insumos manufacturados");
            }

            request.setCategoria(categoria);
        }

        return articuloManufacturadoRepository.save(request);
    }

    @Override
    public void deleteById(Long id) {
        ArticuloManufacturado producto = articuloManufacturadoRepository.getById(id);
        if (producto == null) {
            throw new RuntimeException("Producto no encontrado: { id: " + id + " }");
        }
        articuloManufacturadoRepository.delete(producto);
    }

    @Override
    public Page<ArticuloManufacturado> buscarPorCategoriaYNombre(Pageable pageable, Long idSucursal, Long categoriaId, String nombre) {
        return articuloManufacturadoRepository.findBySucursal_IdAndCategoria_IdAndDenominacionContainingIgnoreCase(idSucursal, categoriaId, nombre, pageable);
    }

    @Override
    public Page<ArticuloManufacturado> getArticulosByCategoria(Pageable pageable, Long idSucursal, Long categoriaId) {
        return articuloManufacturadoRepository.findBySucursal_IdAndCategoria_Id(idSucursal, categoriaId, pageable);
    }

    @Override
    public Page<ArticuloManufacturado> getArticulosByNombre(Pageable pageable, Long idSucursal, String nombre) {
        return articuloManufacturadoRepository.findBySucursal_IdAndDenominacionContainingIgnoreCase(idSucursal, nombre, pageable);
    }
    @Override
    public Page<ArticuloManufacturado> findBySucursal(Long sucursalId, Pageable pageable) {
        return articuloManufacturadoRepository.findBySucursal_Id(sucursalId, pageable);
    }

    @Override
    public List<ArticuloManufacturado> findBySucursal(Long sucursalId ) {
        return articuloManufacturadoRepository.findBySucursal_IdAndBajaFalse(sucursalId);
    }
}
