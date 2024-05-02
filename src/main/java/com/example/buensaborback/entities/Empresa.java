package com.example.buensaborback.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
public class Empresa extends Base{

    private String nombre;
    private String razonSocial;
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
