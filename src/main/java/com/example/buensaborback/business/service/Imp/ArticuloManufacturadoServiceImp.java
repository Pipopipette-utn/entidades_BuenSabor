package com.example.buensaborback.business.service.Imp;

import com.example.buensaborback.business.mapper.ArticuloManufacturadoMapper;
import com.example.buensaborback.business.service.ArticuloManufacturadoService;
import com.example.buensaborback.business.service.Base.BaseServiceImp;
import com.example.buensaborback.business.service.ImagenArticuloService;
import com.example.buensaborback.domain.dto.ArticuloManufacturadoDto;
import com.example.buensaborback.domain.entities.*;
import com.example.buensaborback.repositories.*;
import com.example.buensaborback.utils.PublicIdService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ArticuloManufacturadoServiceImp extends BaseServiceImp<ArticuloManufacturado,Long> implements ArticuloManufacturadoService {

    @Autowired
    ArticuloManufacturadoRepository articuloManufacturadoRepository;

    @Autowired
    ImagenArticuloServiceImpl imagenArticuloService;

    @Autowired
    ArticuloManufacturadoMapper articuloManufacturadoMapper;

    @Autowired
    ArticuloManufacturadoDetalleRepository articuloManufacturadoDetalleRepository;

    @Autowired
    ArticuloInsumoRepository articuloInsumoRepository;

    @Autowired
    CategoriaRepository categoriaRepository;

    @Autowired
    PublicIdService publicIdService;

    @Override
    @Transactional
    public ArticuloManufacturado create(ArticuloManufacturado request) {

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

        /*if (request.getArchivos() != null) {
            imagenArticuloService.uploadImages(request.getArchivos(), id);
        }*/

        return articuloManufacturadoRepository.save(request);
    }
}
