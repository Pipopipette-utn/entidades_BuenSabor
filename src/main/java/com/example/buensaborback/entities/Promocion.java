package com.example.buensaborback.entities;

import com.example.buensaborback.entities.enums.TipoPromocion;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
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
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "denominacion" }) })
public class Promocion extends Base {

    @NotBlank(message = "La denominacion es requerida")
    private String denominacion;

    private LocalDate fechaDesde = LocalDate.now();

    @NotNull(message = "La fechaHasta es requerida")
    private LocalDate fechaHasta;

    private LocalTime horaDesde = LocalTime.now();

    private LocalTime horaHasta = LocalTime.now();
    private String descripcionDescuento;

    @NotNull(message = "El precioPromocional es requerido")
    private Double precioPromocional;
    private TipoPromocion tipoPromocion;

    @ManyToMany
    //SE AGREGA EL JOIN TABLE PARA QUE JPA CREE LA TABLA INTERMEDIA EN UNA RELACION MANY TO MANY
    @JoinTable(name = "promocion_articulo",
            joinColumns = @JoinColumn(name = "promocion_id"),
            inverseJoinColumns = @JoinColumn(name = "articulo_id"))
    private List<Articulo> articulos;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "promocion")
    private List<Imagen> imagenes;

    public Promocion(String denominacion, LocalDate fechaDesde, LocalDate fechaHasta, LocalTime horaDesde, LocalTime horaHasta, String descripcionDescuento, double precioPromocional, TipoPromocion tipoPromocion) {
        this.denominacion = denominacion;
        this.fechaDesde = fechaDesde;
        this.fechaHasta = fechaHasta;
        this.horaDesde = horaDesde;
        this.horaHasta = horaHasta;
        this.descripcionDescuento = descripcionDescuento;
        this.precioPromocional = precioPromocional;
        this.tipoPromocion = tipoPromocion;
        this.articulos = new ArrayList<>();
    }
}
