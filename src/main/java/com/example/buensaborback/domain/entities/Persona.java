package com.example.buensaborback.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Setter
@SuperBuilder
@Inheritance(strategy = InheritanceType.JOINED)
@Audited
public class Persona extends Base {
    private String nombre;
    private String apellido;
    private String telefono;
    private LocalDate fechaNacimiento;

    @OneToOne
    @NotAudited
    private Usuario usuario;

    @OneToOne
    @NotAudited
    private ImagenPersona imagenPersona;

}
