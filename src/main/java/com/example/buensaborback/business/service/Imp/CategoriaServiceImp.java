package com.example.buensaborback.business.service.Imp;

import com.example.buensaborback.business.service.Base.BaseServiceImp;
import com.example.buensaborback.business.service.CategoriaService;
import com.example.buensaborback.business.service.DomicilioService;
import com.example.buensaborback.domain.entities.Categoria;
import com.example.buensaborback.domain.entities.Domicilio;
import com.example.buensaborback.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoriaServiceImp extends BaseServiceImp<Categoria,Long> implements CategoriaService {

    @Autowired
    CategoriaRepository categoriaRepository;

    @Override
    public Page<Categoria> findByEsInsumoTrue(Pageable pageable) {
        return categoriaRepository.findByEsInsumoTrue(pageable);
    }

    @Override
    public Page<Categoria> findByEsInsumoFalse(Pageable pageable) {
        return categoriaRepository.findByEsInsumoFalse(pageable);
    }
}
