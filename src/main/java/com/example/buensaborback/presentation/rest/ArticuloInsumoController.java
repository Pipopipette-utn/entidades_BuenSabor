package com.example.buensaborback.presentation.rest;

import com.example.buensaborback.business.facade.Imp.ArticuloInsumoFacadeImp;
import com.example.buensaborback.domain.dto.ArticuloInsumoDto;
import com.example.buensaborback.domain.entities.ArticuloInsumo;
import com.example.buensaborback.presentation.rest.Base.BaseControllerImp;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/articulosInsumos")
@CrossOrigin("*")
public class ArticuloInsumoController extends BaseControllerImp<ArticuloInsumo, ArticuloInsumoDto,Long, ArticuloInsumoFacadeImp> {

    public ArticuloInsumoController(ArticuloInsumoFacadeImp facade) {
        super(facade);
    }
}
