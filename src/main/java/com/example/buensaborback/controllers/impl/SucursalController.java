package com.example.buensaborback.controllers.impl;


import com.example.buensaborback.controllers.ISucursalController;
import com.example.buensaborback.services.impl.SucursalServiceImpl;
import com.example.buensaborback.entities.Sucursal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path= "sucursales")
public class SucursalController extends BaseControllerImpl<Sucursal, SucursalServiceImpl> implements ISucursalController {
    public SucursalController(SucursalServiceImpl servicio) {
        super(servicio);
    }
}
