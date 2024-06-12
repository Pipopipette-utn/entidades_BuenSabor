package com.example.buensaborback.business.service.Imp;

import com.example.buensaborback.business.service.Base.BaseServiceImp;
import com.example.buensaborback.business.service.UnidadMedidaService;
import com.example.buensaborback.business.service.UsuarioService;
import com.example.buensaborback.domain.entities.Articulo;
import com.example.buensaborback.domain.entities.Empleado;
import com.example.buensaborback.domain.entities.UnidadMedida;
import com.example.buensaborback.domain.entities.Usuario;
import com.example.buensaborback.repositories.ArticuloRepository;
import com.example.buensaborback.repositories.UnidadMedidaRepository;
import com.example.buensaborback.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImp extends BaseServiceImp<Usuario,Long> implements UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public Usuario findByEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario == null) {
            throw new RuntimeException("Usuario con email " + email + " no encontrado");
        }
        return usuario;
    }
}
