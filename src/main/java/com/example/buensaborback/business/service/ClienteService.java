package com.example.buensaborback.business.service;

import com.example.buensaborback.business.service.Base.BaseService;
import com.example.buensaborback.domain.entities.Cliente;
import com.example.buensaborback.domain.entities.Empleado;
import com.example.buensaborback.domain.entities.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface ClienteService extends BaseService<Cliente,Long> {

    Cliente login(Cliente request, String email) throws Exception;
    void removeDomicilio(Long clienteId, Long domicilioId) throws Exception;
    Set<Pedido> getAllPedidos(Long clienteId) throws Exception;

}
