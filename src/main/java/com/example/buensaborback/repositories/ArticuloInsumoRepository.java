package com.example.buensaborback.repositories;


import com.example.buensaborback.domain.entities.ArticuloInsumo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticuloInsumoRepository extends BaseRepository<ArticuloInsumo,Long> {
    Page<ArticuloInsumo> findByEsParaElaborarTrue(Pageable pageable);
    Page<ArticuloInsumo> findByEsParaElaborarFalse(Pageable pageable);
    Page<ArticuloInsumo> findBySucursal_IdAndCategoria_IdAndDenominacionContainingIgnoreCase(Long sucursalId, Long categoriaId, String denominacion, Pageable pageable);
    Page<ArticuloInsumo> findBySucursal_IdAndCategoria_Id(Long sucursalId, Long categoriaId, Pageable pageable);
    Page<ArticuloInsumo> findBySucursal_IdAndDenominacionContainingIgnoreCase(Long sucursalId, String denominacion, Pageable pageable);
    Page<ArticuloInsumo> findBySucursal_Id(Long sucursalId, Pageable pageable);
    Page<ArticuloInsumo> findBySucursal_IdAndEsParaElaborarFalseAndBajaFalse(Long sucursalId, Pageable pageable);

}
