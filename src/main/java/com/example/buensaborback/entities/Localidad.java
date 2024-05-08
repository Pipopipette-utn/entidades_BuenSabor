package com.example.buensaborback.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@ToString
@Builder
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "nombre" }) })
public class Localidad extends Base{

    @NotBlank(message = "El nombre es requerido")
    private String nombre;

    @NotNull(message = "La provincia es requerida")
    @ManyToOne
    @JoinColumn(name = "provincia_id")
    private Provincia provincia;

}
