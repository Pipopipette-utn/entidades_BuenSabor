package com.example.buensaborback.entities.dtos;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class EmpresaDto extends BaseDto{
    private String nombre;
    private String razonSocial;
    private Integer cuil;
    private Set<SucursalDto> sucursales = new HashSet<>();
}
