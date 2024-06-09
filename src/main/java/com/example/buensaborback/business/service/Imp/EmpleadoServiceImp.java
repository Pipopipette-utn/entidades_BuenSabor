package com.example.buensaborback.business.service.Imp;

import com.example.buensaborback.business.service.Base.BaseServiceImp;
import com.example.buensaborback.business.service.DomicilioService;
import com.example.buensaborback.business.service.EmpleadoService;
import com.example.buensaborback.domain.entities.Domicilio;
import com.example.buensaborback.domain.entities.Empleado;
import com.example.buensaborback.domain.entities.Sucursal;
import com.example.buensaborback.domain.entities.Usuario;
import com.example.buensaborback.repositories.EmpleadoRepository;
import com.example.buensaborback.repositories.SucursalRepository;
import com.example.buensaborback.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmpleadoServiceImp extends BaseServiceImp<Empleado,Long> implements EmpleadoService {

    @Autowired
    EmpleadoRepository empleadoRepository;

    @Autowired
    SucursalRepository sucursalRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public Empleado create(Empleado request) throws Exception {
        Optional<Sucursal> sucursal = sucursalRepository.findById(request.getSucursal().getId());
        if (sucursal.isEmpty()) {
            throw new RuntimeException("La sucursal con el id " + request.getSucursal().getId() + " no se ha encontrado");
        }

        // Guarda el usuario si no existe
        Usuario usuario = request.getUsuario();
        if (usuario != null && usuario.getId() == null) {
            usuario = usuarioRepository.save(usuario);
            request.setUsuario(usuario);
        }

        return empleadoRepository.save(request);
    }
}
