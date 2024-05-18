package com.example.buensaborback.presentation.rest;

import com.example.buensaborback.business.facade.Imp.ArticuloManufacturadoFacadeImp;
import com.example.buensaborback.domain.dto.ArticuloManufacturadoDto;
import com.example.buensaborback.domain.entities.ArticuloManufacturado;
import com.example.buensaborback.presentation.rest.Base.BaseControllerImp;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/articulosManufacturados")
@CrossOrigin("*")
public class ArticuloManufacturadoController extends BaseControllerImp<ArticuloManufacturado, ArticuloManufacturadoDto,ArticuloManufacturadoDto,Long, ArticuloManufacturadoFacadeImp> {

    public ArticuloManufacturadoController(ArticuloManufacturadoFacadeImp facade) {
        super(facade);
    }
}
