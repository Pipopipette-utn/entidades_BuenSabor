package com.example.buensaborback.domain.dto.PedidoDtos;

import com.example.buensaborback.domain.dto.BaseDto;
import com.example.buensaborback.domain.dto.SucursalDtos.SucursalPedidoDto;
import com.example.buensaborback.domain.dto.SucursalDtos.SucursalShortDto;
import com.example.buensaborback.domain.enums.Estado;
import com.example.buensaborback.domain.enums.TipoEnvio;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoGetClienteDto extends BaseDto {
    private Double total;
    private TipoEnvio tipoEnvio;
    private LocalDate fechaPedido;
    private Estado estado;
    private Set<PedidoDetalleDto> detallePedidos = new HashSet<>();
    private SucursalPedidoDto sucursal;

}
