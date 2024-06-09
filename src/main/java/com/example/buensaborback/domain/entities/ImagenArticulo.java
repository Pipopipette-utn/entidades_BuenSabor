package com.example.buensaborback.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@SuperBuilder
public class ImagenArticulo extends Base{

    private String name;
    private String url;

    @ManyToMany(mappedBy = "imagenes")
    @Builder.Default
    private Set<Articulo> articulos = new HashSet<>();

}
