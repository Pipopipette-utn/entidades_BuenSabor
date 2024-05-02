package com.example.buensaborback.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@ToString
public class ArticuloInsumo extends Articulo {

    private Double precioCompra;
    private Integer stockActual;
    private Integer stockMaximo;
    private Integer stockMinimo;
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
