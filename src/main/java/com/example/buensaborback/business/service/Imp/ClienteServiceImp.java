package com.example.buensaborback.business.service.Imp;

import com.example.buensaborback.business.service.Base.BaseServiceImp;
import com.example.buensaborback.business.service.ClienteService;
import com.example.buensaborback.domain.entities.*;
import com.example.buensaborback.domain.enums.Rol;
import com.example.buensaborback.repositories.*;
import com.password4j.Hash;
import com.password4j.Password;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
@Service
public class ClienteServiceImp extends BaseServiceImp<Cliente,Long> implements ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    SucursalRepository sucursalRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    DomicilioRepository domicilioRepository;

    @Override
    public Cliente create(Cliente request) throws Exception {
        Cliente existeCliente = clienteRepository.findByEmail(request.getUsuario().getEmail());
        if (existeCliente != null) {
            throw new RuntimeException("El email del cliente ya está registrado");
        }

        // Encriptar clave
        Hash hash = Password.hash(request.getClave()).addSalt("fixedSalt").withPBKDF2();
        request.setClave(hash.getResult());

        Set<Domicilio> domicilios = new HashSet<>();

        if (request.getDomicilios() != null && !request.getDomicilios().isEmpty()) {
            for (Domicilio domicilio : request.getDomicilios()) {
                Domicilio domicilio1 = domicilioRepository.save(domicilio);
                domicilios.add(domicilio1);
            }
        }

        request.setDomicilios(domicilios);

        // Guardar el usuario si no existe
        Usuario usuario = request.getUsuario();
        if (usuario != null && usuario.getId() == null) {
            usuario.setRol(Rol.CLIENTE);
            usuario = usuarioRepository.save(usuario);
            request.setUsuario(usuario);
        }

        // Guardar el cliente
        return clienteRepository.save(request);
    }


    @Override
    public Cliente login(Cliente request, String email) throws Exception {
        Cliente cliente = clienteRepository.findByEmail(email);
        if (cliente == null) {
            throw new RuntimeException("El cliente con email " + email + " no se ha encontrado");
        }

        boolean verified = Password.check(request.getClave(), cliente.getClave()).addSalt("fixedSalt").withPBKDF2();
        return cliente;
    }
}
