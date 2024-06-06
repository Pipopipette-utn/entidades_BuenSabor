package com.example.buensaborback.domain.dto.PedidoDtos;

import com.example.buensaborback.domain.dto.BaseDto;
import com.example.buensaborback.domain.enums.Estado;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PedidoEstadoDto extends BaseDto {
    Estado estado;
}
