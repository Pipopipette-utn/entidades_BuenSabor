package com.example.buensaborback.domain.dto.SucursalDtos;

import com.example.buensaborback.domain.dto.BaseDto;
import com.example.buensaborback.domain.dto.EmpresaDtos.EmpresaDto;
import com.example.buensaborback.domain.dto.EmpresaDtos.EmpresaShortDto;

public class SucursalPedidoDto extends BaseDto {
    private String nombre;
    private ImagenSucursalDto imagenSucursal;
    private EmpresaShortDto empresa;
}
