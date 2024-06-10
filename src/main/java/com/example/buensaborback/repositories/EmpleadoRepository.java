package com.example.buensaborback.repositories;

import com.example.buensaborback.domain.entities.Empleado;
import com.example.buensaborback.domain.entities.Promocion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpleadoRepository extends BaseRepository<Empleado, Long>{
    @Query("SELECT e FROM Empleado e WHERE e.usuario.email = :email")
    Empleado findByEmail(@Param("email") String email);
    Page<Empleado> findBySucursal_Id(Long sucursalId, Pageable pageable);
}
