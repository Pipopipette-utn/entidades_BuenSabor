package com.example.buensaborback.domain.dto.PedidoDtos;

import com.example.buensaborback.domain.dto.BaseDto;
import com.example.buensaborback.domain.dto.DomicilioDto;
import com.example.buensaborback.domain.dto.EmpleadoDto;
import com.example.buensaborback.domain.dto.SucursalDtos.SucursalShortDto;
import com.example.buensaborback.domain.enums.Estado;
import com.example.buensaborback.domain.enums.FormaPago;
import com.example.buensaborback.domain.enums.TipoEnvio;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PedidoDto extends BaseDto {
    private Double total;
    private TipoEnvio tipoEnvio;
    private FormaPago formaPago;

    // private DomicilioDto domicilio;
    private SucursalShortDto sucursal;
    // Factura, cliente
    private Set<PedidoDetalleDto> detallePedidos = new HashSet<>();
    //private EmpleadoDto empleado;
}
