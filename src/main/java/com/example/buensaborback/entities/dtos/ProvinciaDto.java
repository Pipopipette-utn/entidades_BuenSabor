package com.example.buensaborback.entities.dtos;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProvinciaDto extends BaseDto{

    private String nombre;
    private PaisDto pais;

}
