package com.example.buensaborback.business.service.Imp;

import com.example.buensaborback.business.service.Base.BaseServiceImp;
import com.example.buensaborback.business.service.EmpleadoService;
import com.example.buensaborback.domain.entities.Cliente;
import com.example.buensaborback.domain.entities.Empleado;
import com.example.buensaborback.domain.entities.Sucursal;
import com.example.buensaborback.domain.entities.Usuario;
import com.example.buensaborback.domain.enums.Rol;
import com.example.buensaborback.repositories.EmpleadoRepository;
import com.example.buensaborback.repositories.SucursalRepository;
import com.example.buensaborback.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.GrantedAuthority;

import javax.management.relation.Role;
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
        Empleado existeEmpleado = empleadoRepository.findByEmail(request.getUsuario().getEmail());
        if (existeEmpleado != null) {
            throw new RuntimeException("El email del empleado ya está registrado");
        }

        Optional<Sucursal> sucursal = sucursalRepository.findById(request.getSucursal().getId());
        if (sucursal.isEmpty()) {
            throw new RuntimeException("La sucursal con el id " + request.getSucursal().getId() + " no se ha encontrado");
        }

        // Verificar si el rol es válido
        Usuario usuario = request.getUsuario();
        if (usuario != null && usuario.getId() == null) {
            usuario = usuarioRepository.save(usuario);
            request.setUsuario(usuario);
        }

        return empleadoRepository.save(request);
    }

    @Override
    public Empleado update(Empleado request, Long id) {
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El empleado con id " + id + " no se ha encontrado"));

        if (request.getSucursal() != null) {
            Optional<Sucursal> sucursal = sucursalRepository.findById(request.getSucursal().getId());
            if (sucursal.isEmpty()) {
                throw new RuntimeException("La sucursal con id " + request.getSucursal().getId() + " no se ha encontrado");
            }
            empleado.setSucursal(sucursal.get());
        }

        return empleadoRepository.save(empleado);
    }

    @Override
    public Empleado findByEmail(String email) {
        Empleado empleado = empleadoRepository.findByEmail(email);
        if (empleado == null) {
            throw new RuntimeException("Empleado con email " + email + " no encontrado");
        }
        return empleado;
    }

    @Override
    public Page<Empleado> findBySucursal_Id(Long sucursalId, Pageable pageable) {
        return empleadoRepository.findBySucursal_Id(sucursalId, pageable);
    }

}
