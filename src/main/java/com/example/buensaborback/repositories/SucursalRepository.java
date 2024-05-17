package com.example.buensaborback.repositories;

import com.example.buensaborback.domain.entities.Sucursal;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface SucursalRepository extends BaseRepository<Sucursal,Long> {
    @Query("SELECT s FROM Sucursal s LEFT JOIN FETCH s.promociones WHERE s.id = :id")
    Sucursal findWithPromocionesById(@Param("id") Long id);

}
