package com.example.buensaborback.controllers.impl;


import com.example.buensaborback.controllers.IClienteController;
import com.example.buensaborback.services.impl.ClienteServiceImpl;
import com.example.buensaborback.entities.Cliente;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path= "clientes")
public class ClienteController extends BaseControllerImpl<Cliente, ClienteServiceImpl> implements IClienteController {
    public ClienteController(ClienteServiceImpl servicio) {
        super(servicio);
    }
}
