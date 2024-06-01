package com.example.buensaborback.business.service.Imp;

import com.example.buensaborback.business.service.Base.BaseServiceImp;
import com.example.buensaborback.business.service.PedidoService;
import com.example.buensaborback.domain.entities.*;
import com.example.buensaborback.domain.enums.Estado;
import com.example.buensaborback.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    @Autowired
    ArticuloInsumoRepository articuloInsumoRepository;

    @Autowired
    ArticuloManufacturadoRepository articuloManufacturadoRepository;

    @Override
    public Pedido create(Pedido request) {
        Set<DetallePedido> detalles = request.getDetallePedidos(); // Guardar los detalles del body en un set
        Set<DetallePedido> detallesPersistidos = new HashSet<>(); // Inicializar un set que contendrá los detalles que pasen las validaciones

        if (detalles != null && !detalles.isEmpty()) {
            double costoTotal = 0;
            //Iterar los detalles
            for (DetallePedido detalle : detalles) {
                Articulo articulo = detalle.getArticulo(); // Obtener el artículo presente en el detalle
                if (articulo == null || articulo.getId() == null) {
                    throw new RuntimeException("El artículo del detalle no puede ser nulo");
                }
                // Validar que el articulo exista
                articulo = articuloRepository.findById(detalle.getArticulo().getId())
                        .orElseThrow(() -> new RuntimeException("El artículo con id " + detalle.getArticulo().getId() + " no se ha encontrado"));
                detalle.setArticulo(articulo);
                DetallePedido savedDetalle = detallePedidoRepository.save(detalle); // Guardar los detalles en la bd
                costoTotal += calcularTotalCosto(articulo.getId(), detalle.getCantidad()); // Calcular costo total por cada iteración de detalle

                detallesPersistidos.add(savedDetalle);
            }
            request.setTotalCosto(costoTotal);
            request.setDetallePedidos(detallesPersistidos); // Después de la iteración, asignarle todos los detalles al pedido
        } else {
            throw new IllegalArgumentException("El pedido debe contener al menos un detalle");
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

        request.setSucursal(sucursal); // Asignar la sucursal al pedido

        request.setEstado(Estado.PENDIENTE); //Asignar el estado inicial

        request.setFechaPedido(LocalDate.now()); //Asignar la fecha

        return pedidoRepository.save(request); // Guardar el nuevo pedido
    }

    public Double calcularTotalCosto(Long idArticulo, Integer cantidad) {

        ArticuloInsumo insumo = articuloInsumoRepository.getById(idArticulo);
        // Si el artículo es un insumo, multiplicar el precio del insumo por la cantidad
        if (insumo != null) {
            return insumo.getPrecioCompra() * cantidad;
        }

        ArticuloManufacturado manufacturado = articuloManufacturadoRepository.getById(idArticulo);

        // Si el artículo es un manufacturado, obtener sus detalles
        if (manufacturado != null) {
            Set<ArticuloManufacturadoDetalle> detalles = manufacturado.getArticuloManufacturadoDetalles();
            if (detalles != null && !detalles.isEmpty()) {
                double totalCosto = 0;
                for (ArticuloManufacturadoDetalle detalle : detalles) { // Recorrer los detalles
                    double precioCompraInsumo = detalle.getArticuloInsumo().getPrecioCompra(); // Obtener el precioCompra del insumo presente en el detalle
                    double cantidadInsumo = detalle.getCantidad(); // Obtener la cantidad del insumo presente en el detalle
                    totalCosto += (precioCompraInsumo * cantidadInsumo);
                }
                return totalCosto * cantidad; // Multiplicar por la cantidad de artículos manufacturados
            }
        }

        return 0.0; // Si no se encuentra el artículo, devuelve 0.0
    }

    @Override
    public Pedido update(Pedido request, Long id) {
        if (id != null || request != null) {
            throw new RuntimeException("El pedido no se puede editar, si desea realizar un cambio, elimine el pedido y vuelva a crearlo");
        }
        return pedidoRepository.save(request);
    }

    @Override
    public void deleteById(Long id) {
        Pedido pedido = pedidoRepository.getById(id);
        if (pedido == null) {
            throw new RuntimeException("El pedido con id " + id + " no se ha encontrado");
        }

        if (pedido.getEstado() != Estado.PENDIENTE) {
            throw new RuntimeException("El pedido no se puede eliminar porque su estado es distinto a pendiente");
        }

        pedidoRepository.delete(pedido);
    }
}
