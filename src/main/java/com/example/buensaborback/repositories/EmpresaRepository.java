package com.example.buensaborback.repositories;

import com.example.buensaborback.entities.Empresa;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaRepository extends BaseRepository<Empresa,Long> {
}
