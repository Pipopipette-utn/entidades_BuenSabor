package com.example.buensaborback.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@ToString
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "denominacion" }) })
public class Categoria extends Base{

    @NotBlank(message = "La denominacion es requerida")
    private String denominacion;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "categoria")
    @JsonIgnoreProperties("categoria")
    private List<Articulo> articulos;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "categoriaPadre")
    @JsonIgnoreProperties("categoriaPadre")
    private List<Categoria> subCategorias;

    @ManyToOne
    @JoinColumn(name = "categoria_padre_id")
    @JsonIgnoreProperties("subCategorias")
    private Categoria categoriaPadre;

    public Categoria(String denominacion, Categoria categoriaPadre){
        this.denominacion = denominacion;
        this.categoriaPadre = categoriaPadre;
    }

    public Categoria(String denominacion){
        this.denominacion = denominacion;
        this.subCategorias = new ArrayList<>();
    }

}
