package com.example.buensaborback.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "nombre", "cuil" }) })
public class Empresa extends Base{

    @NotBlank(message = "El nombre es requerido")
    private String nombre;
    @NotBlank(message = "La razonSocial es requerida")
    private String razonSocial;
    @NotBlank(message = "El cuil es requerido")
    private Integer cuil;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "empresa")
    @JsonIgnoreProperties("empresa")
    private List<Sucursal> sucursales;

    public Empresa(String nombre, Integer cuil, String razonSocial){
        this.nombre = nombre;
        this.cuil = cuil;
        this.razonSocial = razonSocial;
        this.sucursales = new ArrayList<>();
    }

}
