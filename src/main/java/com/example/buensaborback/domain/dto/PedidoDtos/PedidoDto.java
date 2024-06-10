package com.example.buensaborback.domain.dto.PedidoDtos;

import com.example.buensaborback.domain.dto.BaseDto;
import com.example.buensaborback.domain.dto.ClienteDto.ClienteShortDto;
import com.example.buensaborback.domain.dto.DomicilioShortDto;
import com.example.buensaborback.domain.dto.SucursalDtos.SucursalShortDto;
import com.example.buensaborback.domain.enums.FormaPago;
import com.example.buensaborback.domain.enums.TipoEnvio;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    private DomicilioShortDto domicilio;
    private SucursalShortDto sucursal;
    private ClienteShortDto cliente;
    // Factura
    private Set<PedidoDetalleDto> detallePedidos = new HashSet<>();
    //private EmpleadoDto empleado;
}
