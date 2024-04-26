package com.example.buensaborback.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
public class Sucursal extends Base{

    private String nombre;
    private LocalTime horarioApertura;
    private LocalTime horarioCierre;

    @ManyToOne
    @JoinColumn(name="empresa_id")
    @JsonIgnoreProperties("sucursales")
    private Empresa empresa;

    @OneToOne
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


}
