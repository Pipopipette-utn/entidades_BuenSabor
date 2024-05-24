package com.example.buensaborback.domain.dto.PromocionDtos;

import com.example.buensaborback.domain.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ImagenPromocionDto extends BaseDto {
    private String name;
    private String url;
}
