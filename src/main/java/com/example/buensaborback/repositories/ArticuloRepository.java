package com.example.buensaborback.repositories;


import com.example.buensaborback.domain.entities.Articulo;
import com.example.buensaborback.domain.entities.UnidadMedida;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticuloRepository extends BaseRepository<Articulo, Long> {
    List<Articulo> getByUnidadMedida(UnidadMedida unidadMedida);
}
