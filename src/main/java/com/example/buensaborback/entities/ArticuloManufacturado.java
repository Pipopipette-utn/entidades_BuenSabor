package com.example.buensaborback.entities;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class ArticuloManufacturado extends Articulo {

    private String descripcion;
    private Integer tiempoEstimadoMinutos;
    private String preparacion;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "articuloManufacturado")
    @JsonIgnoreProperties("articuloManufacturado")
    private List<ArticuloManufacturadoDetalle> articuloManufacturadoDetalles;

    @Builder
    public ArticuloManufacturado(String denominacion, String descripcion, UnidadMedida unidadMedida, Double precioVenta, Integer tiempoEstimadoMinutos, String preparacion, Categoria categoria, ArrayList<Imagen> imagenes) {
        super(denominacion, precioVenta, unidadMedida, categoria, imagenes);
        this.descripcion = descripcion;
        this.tiempoEstimadoMinutos = tiempoEstimadoMinutos;
        this.preparacion = preparacion;
        this.articuloManufacturadoDetalles = new ArrayList<>();
    }
}