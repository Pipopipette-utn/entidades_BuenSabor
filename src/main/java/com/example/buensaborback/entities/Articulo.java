package com.example.buensaborback.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Articulo extends Base{

    protected String denominacion;
    protected Double precioVenta;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "articulo")
    @JsonIgnoreProperties("articulo")
    private List<Imagen> imagenes;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    @JsonIgnoreProperties("articulos")
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "unidad_medida_id")
    private UnidadMedida unidadMedida;

    public Articulo(String denominacion,  UnidadMedida unidadMedida){
        this.denominacion = denominacion;
        this.unidadMedida = unidadMedida;
        this.imagenes = new ArrayList<>();
    }

    public Articulo(String denominacion, Double precioVenta, UnidadMedida unidadMedida){
        this.denominacion = denominacion;
        this.precioVenta = precioVenta;
        this.unidadMedida = unidadMedida;
        this.imagenes = new ArrayList<>();
    }

    public Articulo(String denominacion, Double precioVenta, UnidadMedida unidadMedida, Categoria categoria, ArrayList<Imagen> imagenes){
        this.denominacion = denominacion;
        this.precioVenta = precioVenta;
        this.unidadMedida = unidadMedida;
        this.categoria = categoria;
        this.imagenes = imagenes;
    }

}
