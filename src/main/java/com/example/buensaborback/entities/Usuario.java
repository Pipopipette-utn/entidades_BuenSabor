package com.example.buensaborback.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@ToString
@Builder
public class Usuario extends Base{

    @Column(unique=true)
    private String auth0Id;
    @Column(unique=true)
    private String username;

}
