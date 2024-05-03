package com.example.buensaborback.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "nombre", "domicilio"}) })
public class Sucursal extends Base{

    @NotBlank(message = "El nombre es requerido")
    private String nombre;

    private LocalTime horarioApertura;
    private LocalTime horarioCierre;

    @ManyToOne
    @JoinColumn(name="empresa_id")
    @JsonIgnoreProperties("sucursales")
    private Empresa empresa;

    @OneToOne(cascade = CascadeType.ALL)
    private Domicilio domicilio;

    @ManyToMany
    //SE AGREGA EL JOIN TABLE PARA QUE JPA CREE LA TABLA INTERMEDIA EN UNA RELACION MANY TO MANY
    @JoinTable(name = "sucursal_categoria",
            joinColumns = @JoinColumn(name = "sucursal_id"),
            inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    private List<Categoria> categorias;

    @ManyToMany
    @JoinTable(
            name = "sucursal_promocion",
            joinColumns = {@JoinColumn(name = "sucursal_id")},
            inverseJoinColumns = {@JoinColumn(name = "promocion_id")}
    )
    private List<Promocion> promociones;

    public Sucursal(Empresa empresa, String nombre, LocalTime horarioApertura, LocalTime horarioCierre){
        this.empresa = empresa;
        this.nombre = nombre;
        this.horarioApertura = horarioApertura;
        this.horarioCierre = horarioCierre;
        this.categorias = new ArrayList<>();
        this.promociones = new ArrayList<>();
    }


}
