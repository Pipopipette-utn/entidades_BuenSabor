package com.example.buensaborback.entities.dtos;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class LocalidadDto extends BaseDto{
    private String nombre;
    private ProvinciaDto provincia;
}
