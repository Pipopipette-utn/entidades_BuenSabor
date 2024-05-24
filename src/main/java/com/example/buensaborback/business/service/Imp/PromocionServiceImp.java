package com.example.buensaborback.business.service.Imp;

import com.example.buensaborback.business.service.Base.BaseServiceImp;
import com.example.buensaborback.business.service.PromocionService;
import com.example.buensaborback.domain.entities.*;
import com.example.buensaborback.repositories.ArticuloRepository;
import com.example.buensaborback.repositories.PromocionDetalleRepository;
import com.example.buensaborback.repositories.PromocionRepository;
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

    @Override
    public Promocion create(Promocion request) {
        Set<PromocionDetalle> detalles = request.getPromocionDetalles();
        Set<PromocionDetalle> detallesPersistidos = new HashSet<>();

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

        return promocionRepository.save(request);
    }

    @Override
    public Promocion update(Promocion request, Long id) {
        Promocion promocion = promocionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("La promoción con id " + id + " no se ha encontrado"));

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

        return super.update(request, id);
    }
}

