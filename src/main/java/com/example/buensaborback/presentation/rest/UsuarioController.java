package com.example.buensaborback.presentation.rest;

import com.example.buensaborback.business.facade.Imp.EmpleadoFacadeImp;
import com.example.buensaborback.business.facade.Imp.UsuarioFacadeImp;
import com.example.buensaborback.domain.dto.EmpleadoDtos.EmpleadoPostDto;
import com.example.buensaborback.domain.dto.UsuarioDto;
import com.example.buensaborback.domain.entities.Usuario;
import com.example.buensaborback.presentation.rest.Base.BaseControllerImp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin("*")
public class UsuarioController extends BaseControllerImp<Usuario, UsuarioDto, UsuarioDto,Long, UsuarioFacadeImp> {
    public UsuarioController(UsuarioFacadeImp facade) {
        super(facade);
    }

    @GetMapping("/filter")
    public ResponseEntity<?> findByEmail(@RequestParam String email) {
        try {
            UsuarioDto usuario = facade.findByEmail(email);
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
