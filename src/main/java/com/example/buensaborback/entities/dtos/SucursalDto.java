package com.example.buensaborback.entities.dtos;

import lombok.*;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SucursalDto extends BaseDto{
    private String nombre;
    private LocalTime horarioApertura;
    private LocalTime horarioCierre;
    private EmpresaDto empresa;
    private DomicilioDto domicilio;
    private Set<CategoriaDto> categorias = new HashSet<>();
    private Set<PromocionDto> promociones = new HashSet<>();
}
