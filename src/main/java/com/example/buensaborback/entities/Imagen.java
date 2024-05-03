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

    @NotBlank(message = "La url es requerida")
    private String url;

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
