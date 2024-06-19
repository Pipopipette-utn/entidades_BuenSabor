package com.example.buensaborback.business.service.Imp;

import com.example.buensaborback.business.service.Base.BaseServiceImp;
import com.example.buensaborback.business.service.EmailService;
import com.example.buensaborback.business.service.FacturaService;
import com.example.buensaborback.business.service.PedidoService;
import com.example.buensaborback.config.email.EmailDto;
import com.example.buensaborback.domain.entities.*;
import com.example.buensaborback.domain.enums.Estado;
import com.example.buensaborback.domain.enums.TipoEnvio;
import com.example.buensaborback.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PedidoServiceImp extends BaseServiceImp<Pedido,Long> implements PedidoService {
    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    ArticuloRepository articuloRepository;

    @Autowired
    SucursalRepository sucursalRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    DomicilioRepository domicilioRepository;

    @Autowired
    FacturaService facturaService;

    @Autowired
    EmailService emailService;

    @Override
    @Transactional
    public Pedido create(Pedido request) throws Exception {
        Optional<Cliente> clienteOptional = clienteRepository.findById(request.getCliente().getId());
        if (clienteOptional.isEmpty()) {
            throw new RuntimeException("El cliente con el id " + request.getCliente().getId() + " no se ha encontrado");
        }

        if (request.getTipoEnvio().equals(TipoEnvio.DELIVERY)) {
            if (request.getDomicilio() == null) {
                throw new RuntimeException("Debe proporcionar un domicilio para envío delivery");
            }
            Optional<Domicilio> domicilio = domicilioRepository.findById(request.getDomicilio().getId());
            if (domicilio.isEmpty()) {
                throw new RuntimeException("El domicilio con el id " + request.getDomicilio().getId() + " no se ha encontrado");
            }
            Cliente cliente = clienteOptional.get();
            if (!cliente.getDomicilios().contains(domicilio.get())) {
                throw new RuntimeException("El domicilio con el id " + request.getDomicilio().getId() + " no coincide con el cliente " + cliente.getId());
            }
        }


        Set<DetallePedido> detalles = request.getDetallePedidos(); // Guardar los detalles del body en un set
        Set<DetallePedido> detallesPersistidos = new HashSet<>(); // Inicializar un set que contendrá los detalles que pasen las validaciones
        TipoEnvio tipoEnvio = request.getTipoEnvio();

        int tiempoEnvio = 0;
        if (tipoEnvio.equals(TipoEnvio.DELIVERY)) {
            tiempoEnvio = 10;
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

        if (detalles != null && !detalles.isEmpty()) {
            double costoTotal = 0;
            LocalTime horaActual = LocalTime.now();
            LocalTime horaEstimadaFinalizacion = horaActual;
            //Iterar los detalles
            for (DetallePedido detalle : detalles) {
                Articulo articulo = detalle.getArticulo(); // Obtener el artículo presente en el detalle
                if (articulo == null || articulo.getId() == null) {
                    throw new RuntimeException("El artículo del detalle no puede ser nulo.");
                }
                // Validar que el articulo exista
                articulo = articuloRepository.findById(detalle.getArticulo().getId())
                        .orElseThrow(() -> new RuntimeException("El artículo con id " + detalle.getArticulo().getId() + " no se ha encontrado."));
                detalle.setArticulo(articulo);
                //DetallePedido savedDetalle = detallePedidoRepository.save(detalle); // Guardar los detalles en la bd

                costoTotal += calcularTotalCosto(articulo, detalle.getCantidad()); // Calcular costo total por cada iteración de detalle
                descontarStock(articulo, detalle.getCantidad()); // Descontar el stock por cada iteración de detalle
                if (articulo instanceof ArticuloManufacturado)
                    horaEstimadaFinalizacion = horaEstimadaFinalizacion.plusMinutes((long) ((ArticuloManufacturado) articulo).getTiempoEstimadoMinutos() * detalle.getCantidad());

                detallesPersistidos.add(detalle);
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

        request.setSucursal(sucursal); // Asignar la sucursal al pedido

        request.setEstado(Estado.PENDIENTE); //Asignar el estado inicial

        request.setFechaPedido(LocalDate.now()); //Asignar la fecha

        return pedidoRepository.save(request); // Guardar el nuevo pedido
    }

    @Transactional
    public void descontarStock(Articulo articulo, int cantidad) throws Exception{
        if (articulo instanceof ArticuloInsumo){
            // Si el articulo es un insumo
            Double stockDescontado = ((ArticuloInsumo) articulo).getStockActual() - cantidad; // Descontar cantidad a stock actual
            // Validar que el stock actual no supere el mínimo
            if (stockDescontado <= ((ArticuloInsumo) articulo).getStockMinimo()) {
                throw new RuntimeException("El insumo con id " + articulo.getId() + " alcanzó el stock mínimo");
            }
            ((ArticuloInsumo) articulo).setStockActual(stockDescontado); // Asignarle al insumo el stock descontado
        }else if(articulo instanceof ArticuloManufacturado){
            // Obtener los detalles del manufacturado
            Set<ArticuloManufacturadoDetalle> detalles = ((ArticuloManufacturado) articulo).getArticuloManufacturadoDetalles();
            if (detalles != null && !detalles.isEmpty()) {
                for (ArticuloManufacturadoDetalle detalle : detalles) { // Recorrer los detalles
                    ArticuloInsumo insumo = detalle.getArticulo();
                    Double cantidadInsumo = detalle.getCantidad() * cantidad; // Multiplicar la cantidad necesaria de insumo por la cantidad de manufacturados del pedido
                    double stockDescontado = insumo.getStockActual() - cantidadInsumo; // Descontar el stock actual
                    if (stockDescontado <= insumo.getStockMinimo()) {
                        throw new RuntimeException("El insumo con id " + insumo.getId() + " presente en el artículo "
                                + articulo.getDenominacion() + " (id " + articulo.getId() + ") alcanzó el stock mínimo");
                    }
                    insumo.setStockActual(stockDescontado); // Asignarle al insumo el stock descontado
                }
            }
        }else{
            throw new RuntimeException("Artículo con id " + articulo.getId() + " no encontrado");
        }

    }

    public Double calcularTotalCosto(Articulo articulo, Integer cantidad) throws Exception {
        if (articulo instanceof ArticuloInsumo){
            // Si el artículo es un insumo, multiplicar el precio del insumo por la cantidad
            if (((ArticuloInsumo) articulo).getPrecioCompra() == null) {
                throw new RuntimeException("El precio compra del insumo con id " + articulo.getId() + " es nulo");
            }
            return ((ArticuloInsumo) articulo).getPrecioCompra() * cantidad;

        }else if(articulo instanceof ArticuloManufacturado){
            Set<ArticuloManufacturadoDetalle> detalles = ((ArticuloManufacturado) articulo).getArticuloManufacturadoDetalles();
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
        }else{
            throw new Exception("El artículo con id " + articulo.getId() + " no es de ningún tipo.");
        };

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
        pedidoRepository.save(pedido);
    }

    @Override
    public Pedido cambiarEstado(Pedido request, Long id) throws Exception {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El pedido con id " + id + " no se ha encontrado"));

        // Si el pedido está en proceso no se puede cancelar
        if (request.getEstado() == Estado.CANCELADO && pedido.getEstado() != Estado.PENDIENTE) {
            throw new RuntimeException("El pedido no se puede cancelar porque está en proceso");
        }

        // Si el pedido se cancela, restaurar stock
        if (request.getEstado() == Estado.CANCELADO){
            for(DetallePedido detalle: pedido.getDetallePedidos()){
                Articulo articulo = articuloRepository.findById(detalle.getArticulo().getId()).orElseThrow(() -> new RuntimeException("El artículo con id " + detalle.getArticulo().getId() + " no se ha encontrado."));
                devolverStock(articulo, detalle.getCantidad());
            }
        }

        // Si el pedido es aprobado, envíar factura
        if (request.getEstado() == Estado.PREPARACION) {
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                // Crear un nuevo documento
                facturaService.crearFacturaPdf(id, outputStream);

                // Configurar detalles del correo
                EmailDto emailDto = new EmailDto();
                emailDto.setDestinatario(pedido.getCliente().getUsuario().getEmail());
                emailDto.setAsunto("Factura de su pedido #" + id);
                emailDto.setMensaje("¡Gracias por tu compra " + pedido.getCliente().getNombre() + " 🍕🍟🍔🥐! " +
                        "A continuación encontrarás la factura.");

                // Enviar el correo con la factura adjunta
                emailService.enviarEmail(emailDto, outputStream);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Error al generar o enviar la factura", e);
            }
        }

        pedido.setEstado(request.getEstado());
        return pedidoRepository.save(pedido);
    }

    public void devolverStock(Articulo articulo, int cantidad) throws Exception{
        if (articulo instanceof ArticuloInsumo){
            // Si el articulo es un insumo
            Double stockAumentado = ((ArticuloInsumo) articulo).getStockActual() + cantidad; // Aumentar cantidad a stock actual
            ((ArticuloInsumo) articulo).setStockActual(stockAumentado); // Asignarle al insumo el stock descontado
        }else if(articulo instanceof ArticuloManufacturado){
            // Obtener los detalles del manufacturado
            Set<ArticuloManufacturadoDetalle> detalles = ((ArticuloManufacturado) articulo).getArticuloManufacturadoDetalles();
            if (detalles != null && !detalles.isEmpty()) {
                for (ArticuloManufacturadoDetalle detalle : detalles) { // Recorrer los detalles
                    ArticuloInsumo insumo = detalle.getArticulo();
                    Double cantidadInsumo = detalle.getCantidad() * cantidad; // Multiplicar la cantidad necesaria de insumo por la cantidad de manufacturados del pedido
                    double stockAumentado = insumo.getStockActual() + cantidadInsumo; // Aumentar el stock actual
                    insumo.setStockActual(stockAumentado); // Asignarle al insumo el stock descontado
                }
            }
        }else{
            throw new RuntimeException("Artículo con id " + articulo.getId() + " no encontrado");
        }

    }

    @Override
    public Page<Pedido> findBySucursalAndEstado(Long sucursalId, Estado estado, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "fechaPedido"));
        if (estado != null) {
            return pedidoRepository.findBySucursal_IdAndEstado(sucursalId, estado, pageable);
        } else {
            return pedidoRepository.findBySucursal_Id(sucursalId, pageable);
        }
    }

    // REPORTES -------------------------------------------------------------------------------------
    public List<Object[]> findRankingComidasMasPedidas(LocalDate fecha1, LocalDate fecha2, Long sucursalId) throws SQLException {
        return pedidoRepository.findRankingComidasMasPedidas(fecha1, fecha2, sucursalId);
    }

    public List<Object[]> calcularTotalRecaudado(LocalDate fecha1, LocalDate fecha2, Long sucursalId) throws SQLException {
        return pedidoRepository.calcularTotalRecaudado(fecha1, fecha2, sucursalId);
    }

    public List<Object[]> calcularTotalRecaudadoPorMes(LocalDate fecha1, LocalDate fecha2, Long sucursalId) throws SQLException {
        return pedidoRepository.calcularTotalRecaudadoPorMes(fecha1, fecha2, sucursalId);
    }

    public List<Object[]> findClientePedidos(LocalDate fechaInicio, LocalDate fechaFin, Long sucursalId) throws SQLException {
        return pedidoRepository.findClientePedidos(fechaInicio, fechaFin, sucursalId);
    }

    public List<Object[]> calcularGanancia(LocalDate fecha1, LocalDate fecha2, Long sucursalId) throws SQLException {
        return pedidoRepository.calcularGanancia(fecha1, fecha2, sucursalId);
    }

    public List<Object[]> calcularGananciaPorMes(LocalDate fecha1, LocalDate fecha2, Long sucursalId) throws SQLException {
        return pedidoRepository.calcularGananciaPorMes(fecha1, fecha2, sucursalId);
    }
}
