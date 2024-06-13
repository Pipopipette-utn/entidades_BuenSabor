package com.example.buensaborback.business.service;

import com.example.buensaborback.business.service.Base.BaseService;
import com.example.buensaborback.domain.entities.Empleado;
import com.example.buensaborback.domain.entities.Usuario;

public interface UsuarioService extends BaseService<Usuario,Long> {
    Usuario findByEmail (String email);

}
