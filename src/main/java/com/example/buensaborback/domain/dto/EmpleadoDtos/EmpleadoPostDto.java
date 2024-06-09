package com.example.buensaborback.domain.dto.EmpleadoDtos;

import com.example.buensaborback.domain.dto.PersonaDto;
import com.example.buensaborback.domain.dto.SucursalDtos.SucursalShortDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmpleadoPostDto extends PersonaDto {
    SucursalShortDto sucursal;
}
