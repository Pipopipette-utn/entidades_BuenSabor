package com.example.buensaborback.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@ToString
@Builder
public class ImagenArticulo extends Base{

    @Builder.Default
    private String url = "https://cdn.icon-icons.com/icons2/1874/PNG/512/iconfinder-hamburgerburgerdrinksoftfastfoodfastfood-4306475_119909.png";

    public ImagenArticulo(String url){
        this.url = url;
    }
    
}
