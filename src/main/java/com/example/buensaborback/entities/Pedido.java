package com.example.buensaborback.entities;

import com.example.buensaborback.entities.enums.Estado;
import com.example.buensaborback.entities.enums.FormaPago;
import com.example.buensaborback.entities.enums.TipoEnvio;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@ToString
@Builder
public class Pedido extends Base{

    private LocalTime horaEstimadaFinalizacion;
    private Double total;
    private Double totalCosto;
    private Estado estado;
    private TipoEnvio tipoEnvio;
    private FormaPago formaPago;
    private LocalDate fechaPedido;

    @ManyToOne
    @JoinColumn(name = "sucursal_id")
    private Sucursal sucursal;

    @ManyToOne
    @JoinColumn(name = "domicilio_id")
    private Domicilio domicilio;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @OneToOne
    private Factura factura;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "pedido")
    private List<DetallePedido> detallePedidos;
}