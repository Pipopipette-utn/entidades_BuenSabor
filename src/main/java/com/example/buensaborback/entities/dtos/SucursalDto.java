package com.example.buensaborback.entities.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SucursalDto extends BaseDto{
    private String nombre;
    private LocalTime horarioApertura;
    private LocalTime horarioCierre;
    private EmpresaDto empresa;
    private DomicilioDto domicilio;
    private Set<CategoriaDto> categorias = new HashSet<>();
    private Set<PromocionDto> promociones = new HashSet<>();
}
