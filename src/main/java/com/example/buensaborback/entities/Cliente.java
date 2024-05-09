package com.example.buensaborback.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@ToString
public class Cliente extends Persona {

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "cliente")
    private List<Pedido> pedidos;

    public Cliente(String nombre, String apellido, String telefono, String email, Usuario usuario, ImagenPersona imagenPersona) {
        super(nombre, apellido, telefono, email, usuario, imagenPersona );
    }

    public Cliente(String nombre, String apellido, String telefono, String email, Usuario usuario, ImagenPersona imagenPersona, Set<Domicilio> domicilios) {
        super(nombre, apellido, telefono, email, usuario, imagenPersona, domicilios );
    }
    public Cliente(String nombre, String apellido, String telefono, String email, Usuario usuario, ImagenPersona imagenPersona, Set<Domicilio> domicilios, List<Pedido> pedidos) {
        super(nombre, apellido, telefono, email, usuario, imagenPersona, domicilios);
        this.pedidos = pedidos;
    }
}
