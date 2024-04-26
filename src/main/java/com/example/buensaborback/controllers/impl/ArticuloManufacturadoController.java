package com.example.buensaborback.controllers.impl;

import com.example.buensaborback.services.impl.ArticuloManufacturadoServiceImpl;
import com.example.buensaborback.controllers.IArticuloManufacturadoController;
import com.example.buensaborback.entities.ArticuloManufacturado;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path= "articulosManufacturados")
public class ArticuloManufacturadoController extends BaseControllerImpl<ArticuloManufacturado, ArticuloManufacturadoServiceImpl> implements IArticuloManufacturadoController {
    public ArticuloManufacturadoController(ArticuloManufacturadoServiceImpl servicio) {
        super(servicio);
    }
}
