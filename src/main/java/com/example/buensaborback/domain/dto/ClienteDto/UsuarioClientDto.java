package com.example.buensaborback.domain.dto.ClienteDto;

import com.example.buensaborback.domain.dto.ArticuloManufacturado.ArticuloManufacturadoDetalleDto;
import com.example.buensaborback.domain.dto.BaseDto;
import com.example.buensaborback.domain.dto.DomicilioDto;
import com.example.buensaborback.domain.enums.Rol;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UsuarioClientDto extends BaseDto {
    String username;
    String email;
}
