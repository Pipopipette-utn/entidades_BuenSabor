package com.example.buensaborback.business.service.Imp;

import com.example.buensaborback.business.service.Base.BaseServiceImp;
import com.example.buensaborback.business.service.PedidoService;
import com.example.buensaborback.domain.entities.*;
import com.example.buensaborback.domain.enums.Estado;
import com.example.buensaborback.repositories.ArticuloRepository;
import com.example.buensaborback.repositories.DetallePedidoRepository;
import com.example.buensaborback.repositories.PedidoRepository;
import com.example.buensaborback.repositories.SucursalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class PedidoServiceImp extends BaseServiceImp<Pedido,Long> implements PedidoService {
    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    DetallePedidoRepository detallePedidoRepository;

    @Autowired
    ArticuloRepository articuloRepository;

    @Autowired
    SucursalRepository sucursalRepository;

    @Override
    public Pedido create(Pedido request) {
        // Asignar detalles al pedido
        Set<DetallePedido> detalles = request.getDetallePedidos();
        Set<DetallePedido> detallesPersistidos = new HashSet<>();

        if (detalles != null && !detalles.isEmpty()) {
            for (DetallePedido detalle : detalles) {
                // Obtener el articulo presente en el detalle
                Articulo articulo = detalle.getArticulo();
                if (articulo == null || articulo.getId() == null) {
                    throw new RuntimeException("El articulo del detalle no puede ser nulo");
                }
                // Validar que el articulo exista
                articulo = articuloRepository.findById(detalle.getArticulo().getId())
                        .orElseThrow(() -> new RuntimeException("El articulo con id " + detalle.getArticulo().getId() + " no se ha encontrado"));
                detalle.setArticulo(articulo);
                // Guardar los detalles en la bd
                DetallePedido savedDetalle = detallePedidoRepository.save(detalle);
                detallesPersistidos.add(savedDetalle);
            }
            // Añadir el detalle al pedido
            request.setDetallePedidos(detallesPersistidos);
        }

        // Validar que se haya pasado una sucursal en el body
        if (request.getSucursal() == null) {
            throw new RuntimeException("No se ha asignado una sucursal al pedido");
        }

        Sucursal sucursal = sucursalRepository.getById(request.getSucursal().getId());
        // Validar que la sucursal existe
        if (sucursal == null) {
            throw new RuntimeException("La sucursal con id " + request.getSucursal().getId() + " no se ha encontrado");
        }

        // Asignar la sucursal al pedido
        request.setSucursal(sucursal);

        //Asignar el estado inicial
        request.setEstado(Estado.PENDIENTE);

        // Guardar el nuevo pedido
        return pedidoRepository.save(request);
    }

    public LocalTime calcularHoraEstimadaFinalizacion() {
        return LocalTime.parse("23:45:00");
    }
}
