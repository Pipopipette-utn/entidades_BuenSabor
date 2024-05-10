package com.example.buensaborback.entities.dtos;

import com.example.buensaborback.entities.enums.Estado;
import com.example.buensaborback.entities.enums.FormaPago;
import com.example.buensaborback.entities.enums.TipoEnvio;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PedidoDto extends BaseDto{
    private LocalTime horaEstimadaFinalizacion;
    private Double total;
    private Double totalCosto;
    private Estado estado;
    private TipoEnvio tipoEnvio;
    private FormaPago formaPago;
    private LocalDate fechaPedido;
    private SucursalDto sucursal;
    private DomicilioDto domicilio;
    private ClienteDto cliente;
    private FacturaDto factura;
    private Set<DetallePedidoDto> detallePedidos = new HashSet<>();
    private Long domicilioId; //Usado en el create
    private Long sucursalId; //Usado en el create
}
