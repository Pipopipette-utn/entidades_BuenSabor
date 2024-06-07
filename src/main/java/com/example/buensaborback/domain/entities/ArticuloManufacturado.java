package com.example.buensaborback.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;

import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@SuperBuilder
@Audited
public class ArticuloManufacturado  extends Articulo{

    private String descripcion;
    private Integer tiempoEstimadoMinutos;
    private String preparacion;


    @OneToMany
    @JoinColumn(name = "articuloManufacturado_id")
    @NotAudited
    @Builder.Default
    private Set<ArticuloManufacturadoDetalle> articuloManufacturadoDetalles = new HashSet<>();


}
