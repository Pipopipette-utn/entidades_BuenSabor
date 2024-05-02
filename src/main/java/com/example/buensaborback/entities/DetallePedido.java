package com.example.buensaborback.entities;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@ToString
@Builder
public class DetallePedido extends Base{

    private Integer cantidad;
    private Double subTotal;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "articulo_id")
    private Articulo articulo;

    public DetallePedido(Articulo pizzaMuzarella, int cantidad, double subTotal) {
        this.articulo = pizzaMuzarella;
        this.cantidad = cantidad;
        this.subTotal = subTotal;
    }
}
