package com.example.buensaborback.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@ToString
@SuperBuilder
public class ArticuloInsumo extends Articulo {

    private Double precioCompra;

    // Método que calcule el stockActual
    private Integer stockActual;
    private Integer stockMaximo;

    @NotNull(message = "El stockMinimo es requerido")
    private Integer stockMinimo;

    @NotNull(message = "El esParaElaborar es requerido")
    private Boolean esParaElaborar;

    public ArticuloInsumo(String denominacion, UnidadMedida unidadMedida, Double precioCompra, Integer stockActual, Integer stockMaximo, Integer stockMinimo, Boolean esParaElaborar) {
        super(denominacion, unidadMedida);

        this.precioCompra = precioCompra;
        this.stockActual = stockActual;
        this.stockMaximo = stockMaximo;
        this.stockMinimo = stockMinimo;
        this.esParaElaborar = esParaElaborar;
    }

    public ArticuloInsumo(String denominacion, Double precioVenta, UnidadMedida unidadMedida, Categoria categoria, Set<ImagenArticulo> imagenes, Double precioCompra, Integer stockActual, Integer stockMaximo, Integer stockMinimo, Boolean esParaElaborar) {
        super(denominacion, precioVenta, unidadMedida, categoria, imagenes);
        this.precioCompra = precioCompra;
        this.stockActual = stockActual;
        this.stockMaximo = stockMaximo;
        this.stockMinimo = stockMinimo;
        this.esParaElaborar = esParaElaborar;
    }

    public ArticuloInsumo(String denominacion, UnidadMedida unidadMedida, Set<ImagenArticulo> imagenes, Double precioCompra, Integer stockActual, Integer stockMaximo, Integer stockMinimo, Boolean esParaElaborar) {
        super(denominacion, unidadMedida, imagenes);
        this.precioCompra = precioCompra;
        this.stockActual = stockActual;
        this.stockMaximo = stockMaximo;
        this.stockMinimo = stockMinimo;
        this.esParaElaborar = esParaElaborar;
    }
}
