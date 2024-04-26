package com.example.buensaborback.controllers.impl;


import com.example.buensaborback.controllers.IProvinciaController;
import com.example.buensaborback.services.impl.ProvinciaServiceImpl;
import com.example.buensaborback.entities.Provincia;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path= "provincias")
public class ProvinciaController extends BaseControllerImpl<Provincia, ProvinciaServiceImpl> implements IProvinciaController {
    public ProvinciaController(ProvinciaServiceImpl servicio) {
        super(servicio);
    }
}
