package com.example.buensaborback.domain.dto.EmpresaDtos;

import com.example.buensaborback.domain.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmpresaShortDto extends BaseDto {
    private String nombre;
    private String logo;
}
