package com.example.buensaborback.controllers.impl;


import com.example.buensaborback.controllers.IArticuloInsumoController;
import com.example.buensaborback.services.impl.ArticuloInsumoServiceImpl;
import com.example.buensaborback.entities.ArticuloInsumo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path= "articulosInsumos")
public class ArticuloInsumoController extends BaseControllerImpl<ArticuloInsumo, ArticuloInsumoServiceImpl> implements IArticuloInsumoController {
    public ArticuloInsumoController(ArticuloInsumoServiceImpl servicio) {
        super(servicio);
    }
}
