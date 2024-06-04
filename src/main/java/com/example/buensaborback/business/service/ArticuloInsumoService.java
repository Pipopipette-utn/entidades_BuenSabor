package com.example.buensaborback.business.service;

import com.example.buensaborback.business.service.Base.BaseService;
import com.example.buensaborback.domain.entities.ArticuloInsumo;
import com.example.buensaborback.domain.entities.Sucursal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface ArticuloInsumoService extends BaseService<ArticuloInsumo,Long> {
    Page<ArticuloInsumo> findByEsParaElaborarTrue(Pageable pageable);
    Page<ArticuloInsumo> findByEsParaElaborarFalse(Pageable pageable);
    List<ArticuloInsumo> create(ArticuloInsumo request, Set<Sucursal> sucursales);

}
