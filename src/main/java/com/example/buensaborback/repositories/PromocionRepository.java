package com.example.buensaborback.repositories;

import com.example.buensaborback.domain.entities.Promocion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface PromocionRepository extends BaseRepository<Promocion,Long>{
    Page<Promocion> findBySucursal_Id(Long sucursalId, Pageable pageable);
    Page<Promocion> findBySucursal_IdAndDenominacionContainingIgnoreCase(Long sucursalId, String denominacion, Pageable pageable);

}
