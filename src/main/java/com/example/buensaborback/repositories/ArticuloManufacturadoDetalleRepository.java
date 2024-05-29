package com.example.buensaborback.repositories;

import com.example.buensaborback.domain.entities.ArticuloInsumo;
import com.example.buensaborback.domain.entities.ArticuloManufacturadoDetalle;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticuloManufacturadoDetalleRepository extends BaseRepository<ArticuloManufacturadoDetalle,Long> {
    List<ArticuloManufacturadoDetalle> getByArticulo(ArticuloInsumo articuloInsumo);

    @Override
    @Transactional
    default void deleteAll(Iterable<? extends ArticuloManufacturadoDetalle> entities) {
        entities.forEach(entity -> {
            entity.setBaja(true);
            save(entity);
        });
    }
}
