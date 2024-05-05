package com.example.buensaborback.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@ToString
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "telefono", "email", "usuario" }) })
public class Cliente extends Base{

    @NotBlank(message = "El nombre es requerido")
    private String nombre;
    @NotBlank(message = "El apellido es requerido")
    private String apellido;
    private String telefono;
    //private LocalDate fechaNacimiento;

    @NotBlank(message = "El email es requerido")
    @Column(unique=true)
    private String email;

    @NotNull(message = "El usuario es requerido")
    @OneToOne
    private Usuario usuario;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "cliente")
    private List<Pedido> pedidos;

    @OneToOne
    private Imagen imagen = new Imagen("https://cdn.icon-icons.com/icons2/2483/PNG/512/user_icon_149851.png");

    @ManyToMany
    @JoinTable(
            name = "cliente_domicilio",
            joinColumns = {@JoinColumn(name = "cliente_id")},
            inverseJoinColumns = {@JoinColumn(name = "domicilio_id")}
    )
    //SE AGREGA EL BUILDER.DEFAULT PARA QUE BUILDER NO SOBREESCRIBA LA INICIALIZACION DE LA LISTA
    private List<Domicilio> domicilios;

    public Cliente(Usuario usuario, Imagen imagen, String email, String nombre, String apellido, String telefono) {
        this.usuario = usuario;
        this.imagen = imagen;
        this.email = email;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.domicilios = new ArrayList<>();
        this.pedidos = new ArrayList<>();
    }
}
