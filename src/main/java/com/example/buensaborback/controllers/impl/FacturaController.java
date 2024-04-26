package com.example.buensaborback.controllers.impl;


import com.example.buensaborback.controllers.IFacturaController;
import com.example.buensaborback.services.impl.FacturaServiceImpl;
import com.example.buensaborback.entities.Factura;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path= "facturas")
public class FacturaController extends BaseControllerImpl<Factura, FacturaServiceImpl> implements IFacturaController {
    public FacturaController(FacturaServiceImpl servicio) {
        super(servicio);
    }
}
