package com.example.buensaborback.controllers.impl;


import com.example.buensaborback.controllers.IDetallePedidoController;
import com.example.buensaborback.services.impl.DetallePedidoServiceImpl;
import com.example.buensaborback.entities.DetallePedido;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path= "detallesPedidos")
public class DetallePedidoController extends BaseControllerImpl<DetallePedido, DetallePedidoServiceImpl> implements IDetallePedidoController {
    public DetallePedidoController(DetallePedidoServiceImpl servicio) {
        super(servicio);
    }
}
