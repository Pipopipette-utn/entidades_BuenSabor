package com.example.buensaborback.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@ToString
public class ArticuloInsumo extends Articulo {

    private Double precioCompra;

    // Método que calcule el stockActual
    private Integer stockActual;
    private Integer stockMaximo;

    @NotNull(message = "El stockMinimo es requerido")
    private Integer stockMinimo;

    @NotNull(message = "El esParaElaborar es requerido")
    private Boolean esParaElaborar;

    public ArticuloInsumo(String denominacion, UnidadMedida unidadMedida, Boolean esParaElaborar, Integer stockMinimo, Integer stockMaximo, Integer stockActual, Double precioCompra) {
        super(denominacion, unidadMedida);
        this.precioCompra = precioCompra;
        this.stockMinimo = stockMinimo;
        this.stockMaximo = stockMaximo;
        this.stockActual = stockActual;
        this.esParaElaborar = esParaElaborar;
    }

    //Este es para los articulos que estan a la venta
    public ArticuloInsumo(String denominacion, UnidadMedida unidadMedida, Integer stockMinimo, Integer stockMaximo, Integer stockActual, Double precioCompra, Double precioVenta, Categoria categoria, ArrayList<Imagen> imagenes) {
        super(denominacion, precioVenta,unidadMedida, categoria, imagenes);
        this.precioCompra = precioCompra;
        this.stockMinimo = stockMinimo;
        this.stockMaximo = stockMaximo;
        this.stockActual = stockActual;
        this.esParaElaborar = false;
    }

}
