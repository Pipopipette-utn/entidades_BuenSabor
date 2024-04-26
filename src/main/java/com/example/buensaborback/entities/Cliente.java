package com.example.buensaborback.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@ToString
public class Cliente extends Base{

    private String nombre;
    private String apellido;
    private String telefono;
    //private LocalDate fechaNacimiento;

    @Column(unique=true)
    private String email;

    @OneToOne
    private Usuario usuario;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "cliente")
    private List<Pedido> pedidos;

    @OneToOne
    private Imagen imagen;

    @ManyToMany
    @JoinTable(
            name = "cliente_domicilio",
            joinColumns = {@JoinColumn(name = "cliente_id")},
            inverseJoinColumns = {@JoinColumn(name = "domicilio_id")}
    )
    //SE AGREGA EL BUILDER.DEFAULT PARA QUE BUILDER NO SOBREESCRIBA LA INICIALIZACION DE LA LISTA
    private List<Domicilio> domicilios;

}
