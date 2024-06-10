package com.example.buensaborback.domain.dto.ClienteDto;

import com.example.buensaborback.domain.dto.DomicilioDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDto extends PersonaClientDto {
    Set<DomicilioDto> domicilios = new HashSet<>();
    String clave;
}
