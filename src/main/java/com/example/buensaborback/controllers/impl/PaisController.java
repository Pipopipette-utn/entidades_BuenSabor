package com.example.buensaborback.controllers.impl;


import com.example.buensaborback.controllers.IPaisController;
import com.example.buensaborback.services.impl.PaisServiceImpl;
import com.example.buensaborback.entities.Pais;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path= "paises")
public class PaisController extends BaseControllerImpl<Pais, PaisServiceImpl> implements IPaisController {
    public PaisController(PaisServiceImpl servicio) {
        super(servicio);
    }
}
