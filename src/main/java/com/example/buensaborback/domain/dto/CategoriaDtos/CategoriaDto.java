package com.example.buensaborback.domain.dto.CategoriaDtos;

import com.example.buensaborback.domain.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoriaDto extends BaseDto {
    private String denominacion;
    private boolean esInsumo;
    private boolean esParaVender;
}
