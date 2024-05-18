package com.example.buensaborback.presentation.rest;

import com.example.buensaborback.business.facade.Imp.CategoriaFacadeImp;
import com.example.buensaborback.domain.dto.CategoriaDtos.CategoriaPostDto;
import com.example.buensaborback.domain.dto.CategoriaDtos.CategoriaGetDto;
import com.example.buensaborback.domain.entities.Categoria;
import com.example.buensaborback.presentation.rest.Base.BaseControllerImp;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categorias")
@CrossOrigin("*")
public class CategoriaController extends BaseControllerImp<Categoria, CategoriaPostDto, CategoriaGetDto, Long, CategoriaFacadeImp> {

    public CategoriaController(CategoriaFacadeImp facade) {
        super(facade);
    }
}
