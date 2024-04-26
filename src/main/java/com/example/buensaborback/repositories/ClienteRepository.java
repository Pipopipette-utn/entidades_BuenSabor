package com.example.buensaborback.repositories;

import com.example.buensaborback.entities.Cliente;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends BaseRepository<Cliente,Long> {
}
