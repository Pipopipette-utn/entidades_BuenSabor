package com.example.buensaborback.entities;

import com.example.buensaborback.entities.enums.Estado;
import com.example.buensaborback.entities.enums.FormaPago;
import com.example.buensaborback.entities.enums.TipoEnvio;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "factura" }) })
public class Pedido extends Base{

    @NotBlank(message = "La horaEstimadaFinalizacion es requerida")
    private LocalTime horaEstimadaFinalizacion;

    // Crear un método que calcule el total
    private Double total;
    private Double totalCosto;

    @Builder.Default
    private Estado estado = Estado.Pendiente;
    private TipoEnvio tipoEnvio;
    private FormaPago formaPago;

    @Builder.Default
    private LocalDate fechaPedido = LocalDate.now();

    @NotBlank(message = "La sucursal es requerida")
    @ManyToOne
    @JoinColumn(name = "sucursal_id")
    private Sucursal sucursal;

    @ManyToOne
    @JoinColumn(name = "domicilio_id")
    private Domicilio domicilio;

    @NotBlank(message = "El cliente es requerido")
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @OneToOne
    private Factura factura;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "pedido")
    private List<DetallePedido> detallePedidos;

    public Pedido(LocalTime horaEstimadaFinalizacion, double total, double totalCosto, Estado estado, FormaPago formaPago, TipoEnvio tipoEnvio, Sucursal sucursal, Domicilio domicilio) {
        this.horaEstimadaFinalizacion = horaEstimadaFinalizacion;
        this.total = total;
        this.totalCosto = totalCosto;
        this.estado = estado;
        this.formaPago = formaPago;
        this.tipoEnvio = tipoEnvio;
        this.sucursal = sucursal;
        this.domicilio = domicilio;
        this.detallePedidos = new ArrayList<>();
    }
}
