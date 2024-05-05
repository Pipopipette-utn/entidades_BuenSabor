package com.example.buensaborback.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
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
public class Pais extends Base{

    @NotBlank(message = "El nombre es requerido")
    private String nombre;

}
