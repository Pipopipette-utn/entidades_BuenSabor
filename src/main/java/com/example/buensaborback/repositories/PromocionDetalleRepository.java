package com.example.buensaborback.repositories;

import com.example.buensaborback.domain.entities.ArticuloManufacturadoDetalle;
import com.example.buensaborback.domain.entities.PromocionDetalle;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
public interface PromocionDetalleRepository extends BaseRepository<PromocionDetalle,Long>{
    @Override
    @Transactional
    default void deleteAll(Iterable<? extends PromocionDetalle> entities) {
        entities.forEach(entity -> {
            entity.setBaja(true);
            save(entity);
        });
    }
}
