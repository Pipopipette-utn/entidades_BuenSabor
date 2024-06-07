package com.example.buensaborback.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@SuperBuilder
@Audited
public class ArticuloManufacturadoDetalle extends Base{
    private Double cantidad;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "articulo_insumo_id")
    @NotAudited
    private ArticuloInsumo articulo;
}
