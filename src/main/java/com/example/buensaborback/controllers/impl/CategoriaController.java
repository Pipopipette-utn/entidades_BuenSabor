package com.example.buensaborback.controllers.impl;


import com.example.buensaborback.controllers.ICategoriaController;
import com.example.buensaborback.services.impl.CategoriaServiceImpl;
import com.example.buensaborback.entities.Categoria;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path= "categorias")
public class CategoriaController extends BaseControllerImpl<Categoria, CategoriaServiceImpl> implements ICategoriaController {
    public CategoriaController(CategoriaServiceImpl servicio) {
        super(servicio);
    }

}
