package com.example.buensaborback.business.service.Imp;

import com.example.buensaborback.business.service.ArticuloManufacturadoService;
import com.example.buensaborback.business.service.Base.BaseServiceImp;
import com.example.buensaborback.domain.entities.*;
import com.example.buensaborback.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class ArticuloManufacturadoServiceImp extends BaseServiceImp<ArticuloManufacturado,Long> implements ArticuloManufacturadoService {

    @Autowired
    ArticuloManufacturadoRepository articuloManufacturadoRepository;

    @Autowired
    ImagenArticuloRepository imagenArticuloRepository;

    @Autowired
    ArticuloManufacturadoDetalleRepository articuloManufacturadoDetalleRepository;

    @Autowired
    ArticuloInsumoRepository articuloInsumoRepository;

    @Autowired
    CategoriaRepository categoriaRepository;

    @Override
    @Transactional
    public ArticuloManufacturado create(ArticuloManufacturado request) {
        Set<ImagenArticulo> imagenes = request.getImagenes();
        Set<ImagenArticulo> imagenesPersistidas = new HashSet<>();

        if (!imagenes.isEmpty()) {
            for (ImagenArticulo imagen : imagenes) {
                if (imagen.getId() != null) {
                    Optional<ImagenArticulo> imagenBd = imagenArticuloRepository.findById(imagen.getId());
                    imagenBd.ifPresent(imagenesPersistidas::add);
                } else {
                    imagen.setBaja(false);
                    ImagenArticulo savedImagen = imagenArticuloRepository.save(imagen);
                    imagenesPersistidas.add(savedImagen);
                }
            }
            request.setImagenes(imagenesPersistidas);
        }

        Set<ArticuloManufacturadoDetalle> detalles = request.getArticuloManufacturadoDetalles();
        Set<ArticuloManufacturadoDetalle> detallesPersistidos = new HashSet<>();

        if (detalles != null && !detalles.isEmpty()) {
            for (ArticuloManufacturadoDetalle detalle : detalles) {
                ArticuloInsumo insumo = detalle.getArticuloInsumo();
                if (insumo == null || insumo.getId() == null) {
                    throw new RuntimeException("El insumo del detalle no puede ser nulo");
                }
                 insumo = articuloInsumoRepository.findById(detalle.getArticuloInsumo().getId())
                        .orElseThrow(() -> new RuntimeException("El insumo con id " + detalle.getArticuloInsumo().getId() + " no se ha encontrado"));
                detalle.setArticuloInsumo(insumo);
                ArticuloManufacturadoDetalle savedDetalle = articuloManufacturadoDetalleRepository.save(detalle);
                detallesPersistidos.add(savedDetalle);
            }
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
    @Transactional
    public ArticuloManufacturado update(ArticuloManufacturado request, Long id) {
        ArticuloManufacturado articulo = articuloManufacturadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El articulo manufacturado con id " + id + " no se ha encontrado"));

        Set<ImagenArticulo> imagenes = request.getImagenes();
        Set<ImagenArticulo> imagenesPersistidas = new HashSet<>();

        if (imagenes != null && !imagenes.isEmpty()) {
            for (ImagenArticulo imagen : imagenes) {
                if (imagen.getId() != null) {
                    ImagenArticulo imagenBd = imagenArticuloRepository.findById(imagen.getId())
                            .orElseThrow(() -> new RuntimeException("La imagen con id " + imagen.getId() + " no se ha encontrado"));
                    imagenesPersistidas.add(imagenBd);
                } else {
                    ImagenArticulo savedImagen = imagenArticuloRepository.save(imagen);
                    imagenesPersistidas.add(savedImagen);
                }
            }
            articulo.setImagenes(imagenesPersistidas);
        }

        if (!imagenesPersistidas.isEmpty()) {
            request.setImagenes(imagenesPersistidas);
        }

        Set<ArticuloManufacturadoDetalle> detalles = request.getArticuloManufacturadoDetalles();
        Set<ArticuloManufacturadoDetalle> detallesPersistidos = new HashSet<>();

        if (detalles != null && !detalles.isEmpty()) {
            for (ArticuloManufacturadoDetalle detalle : detalles) {
                ArticuloInsumo insumo = detalle.getArticuloInsumo();
                if (insumo == null || insumo.getId() == null) {
                    throw new RuntimeException("El insumo del detalle no puede ser nulo");
                }
                 insumo = articuloInsumoRepository.findById(detalle.getArticuloInsumo().getId())
                        .orElseThrow(() -> new RuntimeException("El insumo con id " + detalle.getArticuloInsumo().getId() + " no se ha encontrado"));
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
}
