package com.example.buensaborback.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.NotAudited;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Setter
@Getter
@ToString
@SuperBuilder
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "telefono", "email", "usuario" }) })
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Persona extends Base {

    @NotBlank(message = "El nombre es requerido")
    private String nombre;
    @NotBlank(message = "El apellido es requerido")
    protected String apellido;
    protected String telefono;
    protected String email;

    @OneToOne
    protected Usuario usuario;

    @OneToOne
    @NotAudited
    protected ImagenPersona imagenPersona;

    @ManyToMany
    //SE AGREGA EL JOIN TABLE PARA QUE JPA CREE LA TABLA INTERMEDIA EN UNA RELACION MANY TO MANY
    @JoinTable(name = "cliente_domicilio",
            joinColumns = @JoinColumn(name = "cliente_id"),
            inverseJoinColumns = @JoinColumn(name = "domicilio_id"))
    //SE AGREGA EL BUILDER.DEFAULT PARA QUE BUILDER NO SOBREESCRIBA LA INICIALIZACION DE LA LISTA
    @Builder.Default
    protected Set<Domicilio> domicilios = new HashSet<>();

    public Persona(String nombre, String apellido, String telefono, String email, Usuario usuario, ImagenPersona imagenPersona) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.email = email;
        this.usuario = usuario;
        this.imagenPersona = imagenPersona;
    }

    public Persona(String nombre, String apellido, String telefono, String email, Usuario usuario) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.email = email;
        this.usuario = usuario;
    }

    public Persona(String nombre, String apellido, String telefono, String email, Usuario usuario, ImagenPersona imagenPersona, Set<Domicilio> domicilios) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.email = email;
        this.usuario = usuario;
        this.imagenPersona = imagenPersona;
        this.domicilios = domicilios;
    }

}
