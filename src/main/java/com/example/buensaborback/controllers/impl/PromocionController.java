package com.example.buensaborback.controllers.impl;


import com.example.buensaborback.controllers.IPromocionController;
import com.example.buensaborback.services.impl.PromocionServiceImpl;
import com.example.buensaborback.entities.Promocion;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path= "promociones")
public class PromocionController extends BaseControllerImpl<Promocion, PromocionServiceImpl> implements IPromocionController {
    public PromocionController(PromocionServiceImpl servicio) {
        super(servicio);
    }
}
