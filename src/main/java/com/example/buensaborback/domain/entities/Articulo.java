package com.example.buensaborback.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;

import java.util.HashSet;
import java.util.Set;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Setter
@SuperBuilder
@Inheritance(strategy = InheritanceType.JOINED)
@Audited
public class Articulo extends Base {

    protected String denominacion;
    protected Double precioVenta;

    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @ToString.Exclude
    //SE AGREGA EL JOIN TABLE PARA QUE JPA CREE LA TABLA INTERMEDIA
    // EN UNA RELACION MANY TO MANY
    @JoinTable(name = "articulo_imagen",
            joinColumns = @JoinColumn(name = "articulo_id"),
            inverseJoinColumns = @JoinColumn(name = "imagen_id"))
    //SE AGREGA EL BUILDER.DEFAULT PARA QUE BUILDER NO SOBREESCRIBA LA INICIALIZACION DE LA LISTA
    @Builder.Default
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    protected Set<ImagenArticulo> imagenes = new HashSet<>();

    @ManyToOne
    @NotAudited
    protected UnidadMedida unidadMedida;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    @JsonIgnoreProperties("articulos")
    @NotAudited
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "sucursal_id")
    @JsonIgnoreProperties("articulos")
    @NotAudited
    private Sucursal sucursal;

    public Articulo(String denominacion, Double precioVenta, UnidadMedida unidadMedida) {
        this.denominacion = denominacion;
        this.precioVenta = precioVenta;
        this.unidadMedida = unidadMedida;
    }
}

