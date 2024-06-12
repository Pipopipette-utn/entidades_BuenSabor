package com.example.buensaborback.presentation.rest;

import com.example.buensaborback.business.facade.Imp.ClienteFacadeImpl;
import com.example.buensaborback.domain.dto.ClienteDto.ClienteDto;
import com.example.buensaborback.domain.dto.ClienteDto.ClienteLoginDto;
import com.example.buensaborback.domain.dto.PedidoDtos.PedidoGetClienteDto;
import com.example.buensaborback.domain.entities.Cliente;
import com.example.buensaborback.presentation.rest.Base.BaseControllerImp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/clientes")
@CrossOrigin("*")
public class ClienteController extends BaseControllerImp<Cliente, ClienteDto,ClienteDto, Long, ClienteFacadeImpl> {

    public ClienteController(ClienteFacadeImpl facade) {
        super(facade);
    }


    @PostMapping("/login")
    public ResponseEntity<?> create(@RequestBody ClienteLoginDto entity){
        try{
            String email = entity.getEmail();
            return ResponseEntity.ok(facade.login(entity, email));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/{clienteId}/removeDomicilio/{domicilioId}")
    public ResponseEntity<?> removeDomicilio(@PathVariable Long clienteId, @PathVariable Long domicilioId) throws Exception{
        try{
            facade.removeDomicilio(clienteId, domicilioId);
            return ResponseEntity.ok(null);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{clienteId}/pedidos")
    public ResponseEntity<?> getAllPedidos(@PathVariable Long clienteId) throws Exception {
        try{
            return ResponseEntity.ok(facade.getAllPedidos(clienteId));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
