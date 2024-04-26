package com.example.buensaborback.controllers.impl;

import com.example.buensaborback.entities.ArticuloManufacturadoDetalle;
import com.example.buensaborback.services.impl.ArticuloManufacturadoDetalleServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path= "articulosManufacturadosDetalles")
public class ArticuloManufacturadoDetalleController extends BaseControllerImpl<ArticuloManufacturadoDetalle, ArticuloManufacturadoDetalleServiceImpl> {
    public ArticuloManufacturadoDetalleController(ArticuloManufacturadoDetalleServiceImpl servicio) {
        super(servicio);
    }
}
