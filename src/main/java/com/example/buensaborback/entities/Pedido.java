package com.example.buensaborback.entities;

import com.example.buensaborback.entities.enums.Estado;
import com.example.buensaborback.entities.enums.FormaPago;
import com.example.buensaborback.entities.enums.TipoEnvio;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "factura" }) })
public class Pedido extends Base{

    @NotNull(message = "La horaEstimadaFinalizacion es requerida")
    private LocalTime horaEstimadaFinalizacion;

    // Crear un método que calcule el total
    private Double total;
    private Double totalCosto;

    @Builder.Default
    private Estado estado = Estado.Pendiente;
    private TipoEnvio tipoEnvio;

    @NotNull(message = "formaPago es requerida")
    private FormaPago formaPago;

    @Builder.Default
    private LocalDate fechaPedido = LocalDate.now();

    @NotNull(message = "La sucursal es requerida")
    @ManyToOne
    @JoinColumn(name = "sucursal_id")
    private Sucursal sucursal;

    @ManyToOne
    @JoinColumn(name = "domicilio_id")
    private Domicilio domicilio;

    @NotNull(message = "El cliente es requerido")
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @OneToOne
    private Factura factura;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "pedido")
    private List<DetallePedido> detallePedidos;

    @ManyToOne
    @JoinColumn(name = "empleado_id")
    private Empleado empleado;

    public Pedido(LocalTime horaEstimadaFinalizacion, double total, double totalCosto, Estado estado, FormaPago formaPago, TipoEnvio tipoEnvio, Sucursal sucursal, Domicilio domicilio, Cliente cliente) {
        this.horaEstimadaFinalizacion = horaEstimadaFinalizacion;
        this.total = total;
        this.totalCosto = totalCosto;
        this.estado = estado;
        this.formaPago = formaPago;
        this.tipoEnvio = tipoEnvio;
        this.sucursal = sucursal;
        this.domicilio = domicilio;
        this.detallePedidos = new ArrayList<>();
        this.cliente = cliente;

    }

    public Pedido(LocalTime horaEstimadaFinalizacion, double total, double totalCosto, Estado estado, FormaPago formaPago, TipoEnvio tipoEnvio, Sucursal sucursal, Domicilio domicilio, Cliente cliente, Empleado empleado) {
        this.horaEstimadaFinalizacion = horaEstimadaFinalizacion;
        this.total = total;
        this.totalCosto = totalCosto;
        this.estado = estado;
        this.formaPago = formaPago;
        this.tipoEnvio = tipoEnvio;
        this.sucursal = sucursal;
        this.domicilio = domicilio;
        this.detallePedidos = new ArrayList<>();
        this.cliente = cliente;
        this.empleado = empleado;
    }

}