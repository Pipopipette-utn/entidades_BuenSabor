package com.example.buensaborback.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.envers.NotAudited;

import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "denominacion" }) })
@Inheritance(strategy = InheritanceType.JOINED)
public class Articulo extends Base{

    @NotBlank(message = "La denominacion es requerida")
    protected String denominacion;

    protected Double precioVenta;

    @OneToMany
    //SE AGREGA EL JOIN COLUMN PARA QUE JPA NO CREE LA TABLA INTERMEDIA EN UNA RELACION ONE TO MANY
    //DE ESTA MANERA PONE EL FOREIGN KEY 'cliente_id' EN LA TABLA DE LOS MANY
    @JoinColumn(name = "articulo_id")
    //SE AGREGA EL BUILDER.DEFAULT PARA QUE BUILDER NO SOBREESCRIBA LA INICIALIZACION DE LA LISTA
    @Builder.Default
    @NotAudited
    protected Set<ImagenArticulo> imagenes = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    @JsonIgnoreProperties("articulos")
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "unidad_medida_id")
    private UnidadMedida unidadMedida;

    public Articulo() {
    }

    public Articulo(String denominacion, UnidadMedida unidadMedida, Set<ImagenArticulo> imagenes){
        this.denominacion = denominacion;
        this.unidadMedida = unidadMedida;
        this.imagenes = imagenes;
    }

    public Articulo(String denominacion,  UnidadMedida unidadMedida){
        this.denominacion = denominacion;
        this.unidadMedida = unidadMedida;
    }

    public Articulo(String denominacion, Double precioVenta, UnidadMedida unidadMedida, Categoria categoria, Set<ImagenArticulo> imagenes){
        this.denominacion = denominacion;
        this.precioVenta = precioVenta;
        this.unidadMedida = unidadMedida;
        this.categoria = categoria;
        this.imagenes = imagenes;
    }

}
