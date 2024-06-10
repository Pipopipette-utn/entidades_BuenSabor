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
import java.util.List;
import java.util.Optional;
import java.util.Set;
@Service
public class ClienteServiceImp extends BaseServiceImp<Cliente,Long> implements ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

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
        if (!verified) {
            throw new RuntimeException("Credenciales incorrectas");
        }
        return cliente;
    }

    @Override
    public Cliente update(Cliente request, Long id) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);
        if (clienteOptional.isEmpty()) {
            throw new RuntimeException("El cliente con id " + id + " no se ha encontrado");
        }

        Cliente clienteExistente = clienteOptional.get();

        // Comprobar y guardar los nuevos domicilios
        Set<Domicilio> nuevosDomicilios = request.getDomicilios();
        if (nuevosDomicilios != null && !nuevosDomicilios.isEmpty()) {
            for (Domicilio domicilio : nuevosDomicilios) {
                Domicilio domicilioGuardado = domicilioRepository.save(domicilio);
                clienteExistente.getDomicilios().add(domicilioGuardado);
            }
        }

        // Guardar el cliente actualizado
        return clienteRepository.save(clienteExistente);
    }

    @Override
    public void removeDomicilio(Long clienteId, Long domicilioId) throws Exception {
        Optional<Cliente> clienteOptional = clienteRepository.findById(clienteId);
        if (clienteOptional.isEmpty()) {
            throw new RuntimeException("El cliente con id " + clienteId + " no se ha encontrado");
        }

        Cliente cliente = clienteOptional.get();

        // Buscar el domicilio en el conjunto de domicilios del cliente
        Domicilio domicilioToRemove = null;
        for (Domicilio domicilio : cliente.getDomicilios()) {
            if (domicilio.getId().equals(domicilioId)) {
                domicilioToRemove = domicilio;
                break;
            }
        }

        if (domicilioToRemove == null) {
            throw new RuntimeException("El domicilio con id " + domicilioId + " no está asociado al cliente con id " + clienteId);
        }

        // Eliminar el domicilio del conjunto de domicilios del cliente
        cliente.getDomicilios().remove(domicilioToRemove);

        // Guardar el cliente actualizado
        clienteRepository.save(cliente);

        // Dar de baja el domicilio
        domicilioRepository.delete(domicilioToRemove);
    }

    @Override
    public Set<Pedido> getAllPedidos(Long clienteId) throws Exception {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("El cliente con id " + clienteId + " no se ha encontrado"));

        return cliente.getPedidos();
    }


}
