package com.example.buensaborback.domain.dto.CategoriaDtos;

import com.example.buensaborback.domain.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoriaHomeDto extends BaseDto {
    private String denominacion;
    private boolean esParaVender;

    private boolean esInsumo;
}
