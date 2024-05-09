package com.example.buensaborback.entities.dtos;

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
public class EmpresaDto extends BaseDto{
    private String nombre;
    private String razonSocial;
    private Integer cuil;
    private Set<SucursalDto> sucursales = new HashSet<>();
}
