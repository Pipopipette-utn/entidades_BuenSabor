package com.example.buensaborback.entities.dtos;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UnidadMedidaDto extends BaseDto{
    private Long id;
    private Boolean baja;
    private String denominacion;
}
