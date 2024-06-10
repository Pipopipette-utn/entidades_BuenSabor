package com.example.buensaborback.domain.dto.ClienteDto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteLoginDto {
    String email;
    String clave;
}
