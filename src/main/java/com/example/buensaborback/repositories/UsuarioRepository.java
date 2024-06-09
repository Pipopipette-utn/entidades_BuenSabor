package com.example.buensaborback.repositories;

import com.example.buensaborback.domain.entities.Usuario;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends BaseRepository<Usuario ,Long> {

    Usuario findByAuth0Id(String auth0Id);
}
