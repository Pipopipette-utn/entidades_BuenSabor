package com.example.buensaborback.controllers.impl;


import com.example.buensaborback.controllers.IUnidadMedidaController;
import com.example.buensaborback.services.impl.UnidadMedidaServiceImpl;
import com.example.buensaborback.entities.UnidadMedida;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path= "unidadesMedidas")
public class UnidadMedidaController extends BaseControllerImpl<UnidadMedida, UnidadMedidaServiceImpl> implements IUnidadMedidaController {
    public UnidadMedidaController(UnidadMedidaServiceImpl servicio) {
        super(servicio);
    }
}
