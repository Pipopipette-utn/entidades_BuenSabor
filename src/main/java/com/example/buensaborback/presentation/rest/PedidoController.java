package com.example.buensaborback.presentation.rest;

import com.example.buensaborback.business.facade.Imp.PedidoFacadeImp;
import com.example.buensaborback.domain.dto.PedidoDtos.PedidoDto;
import com.example.buensaborback.domain.entities.Pedido;
import com.example.buensaborback.presentation.rest.Base.BaseControllerImp;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pedidos")
@CrossOrigin("*")
public class PedidoController extends BaseControllerImp<Pedido, PedidoDto,PedidoDto,Long, PedidoFacadeImp> {
    public PedidoController(PedidoFacadeImp facade) {
        super(facade);
    }
}
