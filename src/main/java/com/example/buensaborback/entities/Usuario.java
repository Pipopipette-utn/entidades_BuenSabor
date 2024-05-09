package com.example.buensaborback.entities;

import com.example.buensaborback.entities.enums.Rol;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@ToString
@Builder
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "auth0Id", "username" }) })
public class Usuario extends Base{

    private String auth0Id;
    private String username;
    private Rol rol;

}
