package com.example.buensaborback.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@SuperBuilder
@Audited
public class Empresa extends Base{

    private String nombre;
    private String razonSocial;
    @Column(unique = true)
    private Long cuil;

    @OneToOne
    @NotAudited
    private ImagenEmpresa imagenEmpresa;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "empresa")
    @Builder.Default
    @NotAudited
    private Set<Sucursal> sucursales = new HashSet<>();

}
