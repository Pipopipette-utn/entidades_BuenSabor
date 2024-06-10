package com.example.buensaborback.repositories;

import com.example.buensaborback.domain.entities.Cliente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends BaseRepository<Cliente,Long> {
    @Query("SELECT c FROM Cliente c WHERE c.usuario.email = :email")
    Cliente findByEmail(@Param("email") String email);

}
