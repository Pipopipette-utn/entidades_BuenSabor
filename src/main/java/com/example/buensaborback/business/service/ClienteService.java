package com.example.buensaborback.business.service;

import com.example.buensaborback.business.service.Base.BaseService;
import com.example.buensaborback.domain.entities.Cliente;
import com.example.buensaborback.domain.entities.Empleado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClienteService extends BaseService<Cliente,Long> {

    Cliente login(Cliente request, String email) throws Exception;
}
