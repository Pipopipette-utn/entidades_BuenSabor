package com.example.buensaborback.controllers.impl;


import com.example.buensaborback.controllers.IDomicilioController;
import com.example.buensaborback.services.impl.DomicilioServiceImpl;
import com.example.buensaborback.entities.Domicilio;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path= "domicilios")
public class DomicilioController extends BaseControllerImpl<Domicilio, DomicilioServiceImpl> implements IDomicilioController {
    public DomicilioController(DomicilioServiceImpl servicio) {
        super(servicio);
    }
}
