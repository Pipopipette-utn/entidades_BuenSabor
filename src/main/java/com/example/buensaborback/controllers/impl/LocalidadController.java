package com.example.buensaborback.controllers.impl;


import com.example.buensaborback.controllers.ILocalidadController;
import com.example.buensaborback.services.impl.LocalidadServiceImpl;
import com.example.buensaborback.entities.Localidad;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path= "localidades")
public class LocalidadController extends BaseControllerImpl<Localidad, LocalidadServiceImpl> implements ILocalidadController {
    public LocalidadController(LocalidadServiceImpl servicio) {
        super(servicio);
    }
}
