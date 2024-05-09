package com.example.buensaborback.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@ToString
@Builder
public class DetallePedido extends Base{

    @Builder.Default
    private Integer cantidad = 1;

    // Método que calcule el subTotal dependiendo del precioVenta de Articulo y la cantidad
    private Double subTotal;

    @NotNull(message = "El pedido es requerido")
    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @NotNull(message = "El articulo es requerido")
    @ManyToOne
    @JoinColumn(name = "articulo_id")
    private Articulo articulo;

    public DetallePedido(Articulo pizzaMuzarella, int cantidad, double subTotal, Pedido pedido) {
        this.articulo = pizzaMuzarella;
        this.cantidad = cantidad;
        this.subTotal = subTotal;
        this.pedido = pedido;
    }
}
