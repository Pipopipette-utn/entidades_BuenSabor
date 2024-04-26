package com.example.buensaborback.controllers.impl;


import com.example.buensaborback.controllers.IUsuarioController;
import com.example.buensaborback.services.impl.UsuarioServiceImpl;
import com.example.buensaborback.entities.Usuario;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path= "usuarios")
public class UsuarioController extends BaseControllerImpl<Usuario, UsuarioServiceImpl> implements IUsuarioController {
    public UsuarioController(UsuarioServiceImpl servicio) {
        super(servicio);
    }
}
