package com.example.buensaborback.entities;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
//@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "descripcion" }) })
public class ArticuloManufacturado extends Articulo {

    @NotBlank(message = "La descripcion es requerida")
    private String descripcion;

    @NotNull(message = "El tiempoEstimadoMinutos es requerido")
    private Integer tiempoEstimadoMinutos;
    private String preparacion;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "articuloManufacturado")
    @JsonIgnoreProperties("articuloManufacturado")
    private List<ArticuloManufacturadoDetalle> articuloManufacturadoDetalles;

    @Builder
    public ArticuloManufacturado(String denominacion, String descripcion, UnidadMedida unidadMedida, Double precioVenta, Integer tiempoEstimadoMinutos, String preparacion, Categoria categoria, Set<ImagenArticulo> imagenes) {
        super(denominacion, precioVenta, unidadMedida, categoria, imagenes);
        this.descripcion = descripcion;
        this.tiempoEstimadoMinutos = tiempoEstimadoMinutos;
        this.preparacion = preparacion;
        this.articuloManufacturadoDetalles = new ArrayList<>();
    }
}