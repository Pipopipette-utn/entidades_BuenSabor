package com.example.buensaborback.business.service.Imp;

import com.example.buensaborback.business.service.Base.BaseServiceImp;
import com.example.buensaborback.business.service.PedidoService;
import com.example.buensaborback.domain.entities.*;
import com.example.buensaborback.domain.enums.Estado;
import com.example.buensaborback.domain.enums.TipoEnvio;
import com.example.buensaborback.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Optional;
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
        TipoEnvio tipoEnvio = request.getTipoEnvio();

        int tiempoEnvio = 0;
        if (tipoEnvio.equals(TipoEnvio.DELIVERY)) {
            tiempoEnvio = 10;
        }

        if (detalles != null && !detalles.isEmpty()) {
            double costoTotal = 0;
            LocalTime horaActual = LocalTime.now();
            LocalTime horaEstimadaFinalizacion = horaActual;
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
                descontarStock(articulo.getId(), detalle.getCantidad()); // Descontar el stock por cada iteración de detalle
                horaEstimadaFinalizacion = horaEstimadaFinalizacion.plusMinutes(calcularHoraFinalizacion(articulo.getId(), detalle.getCantidad()));

                detallesPersistidos.add(savedDetalle);
            }
            request.setTotalCosto(costoTotal); // Asignarle el total costo al pedido
            request.setDetallePedidos(detallesPersistidos); // Después de la iteración, asignarle todos los detalles al pedido
            LocalTime finalizaA = horaEstimadaFinalizacion.plusMinutes(tiempoEnvio);// Sumarle el tiempo de envío
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm"); // Declara formato de hora
            String horaFormateada = finalizaA.format(formatter); // Formatear hora
            request.setHoraEstimadaFinalizacion(LocalTime.parse(horaFormateada)); // Asignar la hora estimada de finalización formateada al pedido
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

    public Integer calcularHoraFinalizacion (Long idArticulo, int cantidad) {

        Optional<ArticuloInsumo> optionalInsumo = articuloInsumoRepository.findById(idArticulo);
        if (optionalInsumo.isPresent()) {
            return 0;
        }
        Optional<ArticuloManufacturado> optionalManufacturado = articuloManufacturadoRepository.findById(idArticulo);

        if (optionalManufacturado.isPresent()) {
            ArticuloManufacturado manufacturado = optionalManufacturado.get();
            int tiempoEstimado = manufacturado.getTiempoEstimadoMinutos() * cantidad;
            return tiempoEstimado;
        }

        return  0;
    }

    @Transactional
    public void descontarStock(Long idArticulo, int cantidad) {
        // Buscar ArticuloInsumo por ID
        Optional<ArticuloInsumo> optionalInsumo = articuloInsumoRepository.findById(idArticulo);

        // Si el articulo es un insumo
        if (optionalInsumo.isPresent()) {
            ArticuloInsumo insumo = optionalInsumo.get();
            Double stockDescontado = insumo.getStockActual() - cantidad; // Descontar cantidad a stock actual
            // Validar que el stock actual no supere el mínimo
            if (stockDescontado <= insumo.getStockMinimo()) {
                throw new RuntimeException("El insumo con id " + insumo.getId() + " alcanzó el stock mínimo");
            }
            insumo.setStockActual(stockDescontado); // Asignarle al insumo, el stock descontado
            articuloInsumoRepository.save(insumo); // Guardar cambios
        } else {
            // Si no es insumo, es manufacturado
            Optional<ArticuloManufacturado> optionalManufacturado = articuloManufacturadoRepository.findById(idArticulo);

            if (optionalManufacturado.isPresent()) {
                ArticuloManufacturado manufacturado = optionalManufacturado.get();
                // Obtener los detalles del manufacturado
                Set<ArticuloManufacturadoDetalle> detalles = manufacturado.getArticuloManufacturadoDetalles();

                if (detalles != null && !detalles.isEmpty()) {
                    for (ArticuloManufacturadoDetalle detalle : detalles) { // Recorrer los detalles
                        ArticuloInsumo insumo = detalle.getArticulo();
                        Double cantidadInsumo = detalle.getCantidad() * cantidad; // Multiplicar la cantidad necesaria de insumo por la cantidad de manufacturados del pedido
                        double stockDescontado = insumo.getStockActual() - cantidadInsumo; // Descontar el stock actual
                        if (stockDescontado <= insumo.getStockMinimo()) {
                            throw new RuntimeException("El insumo con id " + insumo.getId() + " presente en el artículo "
                                    + manufacturado.getDenominacion() + " (id " + manufacturado.getId() + ") alcanzó el stock mínimo");
                        }
                        insumo.setStockActual(stockDescontado); // Asignarle al insumo, el stock descontado
                        articuloInsumoRepository.save(insumo); // Guardar cambios
                    }
                }
            } else { // Por si no encuentra el artículo
                throw new RuntimeException("Artículo con id " + idArticulo + " no encontrado");
            }
        }
    }

    public Double calcularTotalCosto(Long idArticulo, Integer cantidad) {
        // Buscar ArticuloInsumo por ID
        Optional<ArticuloInsumo> optionalInsumo = articuloInsumoRepository.findById(idArticulo);

        // Si el artículo es un insumo, multiplicar el precio del insumo por la cantidad
        if (optionalInsumo.isPresent()) {
            ArticuloInsumo insumo = optionalInsumo.get();
            if (insumo.getPrecioCompra() == null) {
                throw new RuntimeException("El precio compra del insumo con id " + insumo.getId() + " es nulo");
            }
            return insumo.getPrecioCompra() * cantidad;
        }

        // Buscar ArticuloManufacturado por ID
        Optional<ArticuloManufacturado> optionalManufacturado = articuloManufacturadoRepository.findById(idArticulo);

        // Si el artículo es un manufacturado, obtener sus detalles
        if (optionalManufacturado.isPresent()) {
            ArticuloManufacturado manufacturado = optionalManufacturado.get();
            Set<ArticuloManufacturadoDetalle> detalles = manufacturado.getArticuloManufacturadoDetalles();

            if (detalles != null && !detalles.isEmpty()) {
                double totalCosto = 0;
                for (ArticuloManufacturadoDetalle detalle : detalles) { // Recorrer los detalles
                    Double precioCompraInsumo = detalle.getArticulo().getPrecioCompra(); // Obtener el precioCompra del insumo presente en el detalle
                    if (precioCompraInsumo == null) {
                        throw new RuntimeException("El precio compra del insumo con id " + detalle.getArticulo().getId() + " es nulo");
                    }
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
        // Busca el pedido por is
        Pedido pedido = pedidoRepository.getById(id);
        // Si el pedido no existe
        if (pedido == null) {
            throw new RuntimeException("El pedido con id " + id + " no se ha encontrado");
        }

        // Si el pedido está en preparación o un estado superior
        if (pedido.getEstado() != Estado.PENDIENTE) {
            throw new RuntimeException("El pedido no se puede eliminar porque está en proceso");
        }

        pedido.setEstado(Estado.CANCELADO);
        pedidoRepository.delete(pedido);
    }

    @Override
    public Pedido cambiarEstado(Pedido request, Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El pedido con id " + id + " no se ha encontrado"));

        pedido.setEstado(request.getEstado());
        return pedidoRepository.save(request);
    }

    @Override
    public Page<Pedido> findBySucursal(Long sucursalId, Pageable pageable) {

        return pedidoRepository.findBySucursal_Id(sucursalId, pageable);
    }
}
