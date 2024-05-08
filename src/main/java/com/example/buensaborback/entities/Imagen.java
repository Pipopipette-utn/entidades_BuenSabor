package com.example.buensaborback.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@ToString
@Builder
public class Imagen extends Base{

    @Builder.Default
    private String url = "https://cdn.icon-icons.com/icons2/1874/PNG/512/iconfinder-hamburgerburgerdrinksoftfastfoodfastfood-4306475_119909.png";

    @ManyToOne
    @JoinColumn(name = "articulo_id")
    @JsonIgnoreProperties("imagenes")
    private Articulo articulo;

    @ManyToOne
    @JoinColumn(name = "promocion_id")
    @JsonIgnoreProperties("imagenes")
    private Promocion promocion;

    public Imagen(String url){
        this.url = url;
    }
    
}
