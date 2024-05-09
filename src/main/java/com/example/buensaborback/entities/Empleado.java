    package com.example.buensaborback.entities;


    import jakarta.persistence.*;
    import lombok.*;

    import java.util.HashSet;
    import java.util.Set;

    @Entity
    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    @Getter
    @Builder
    public class Empleado extends Persona {

        @OneToMany(mappedBy = "empleado", cascade = CascadeType.REFRESH, orphanRemoval = true)
        @ToString.Exclude
        @Builder.Default
        private Set<Pedido> pedidos= new HashSet<>();

        @ManyToOne
        @ToString.Exclude
        @JoinColumn(name = "sucursal_id")
        private Sucursal sucursal;

        public Empleado(String nombre, String apellido, String telefono, String email, Usuario usuario,  Sucursal sucursal) {
            super(nombre, apellido, telefono, email, usuario);
            this.sucursal = sucursal;
        }
    }
