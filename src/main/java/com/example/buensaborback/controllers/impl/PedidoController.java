package com.example.buensaborback.controllers.impl;


import com.example.buensaborback.controllers.IPedidoController;
import com.example.buensaborback.services.impl.PedidoServiceImpl;
import com.example.buensaborback.entities.Pedido;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path= "pedidos")
public class PedidoController extends BaseControllerImpl<Pedido, PedidoServiceImpl> implements IPedidoController {
    public PedidoController(PedidoServiceImpl servicio) {
        super(servicio);
    }
}
