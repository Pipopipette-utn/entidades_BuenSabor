package com.example.buensaborback.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class Domicilio extends Base{

    @NotBlank(message = "La calle es requerida")
    private String calle;
    private Integer numero;

    @NotNull(message = "El cp es requerido")
    private Integer cp;
    private Integer piso;
    private Integer nroDpto;

    @NotNull(message = "La localidad es requerida")
    @ManyToOne
    @JoinColumn(name = "localidad_id")
    private Localidad localidad;

    public Domicilio(String calle, Integer numero, Integer cp, Localidad localidad) {
        this.calle = calle;
        this.numero = numero;
        this.cp = cp;
        this.localidad = localidad;
    }

}
