package com.example.buensaborback.controllers.impl;


import com.example.buensaborback.controllers.IImagenController;
import com.example.buensaborback.services.impl.ImagenServiceImpl;
import com.example.buensaborback.entities.ImagenArticulo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path= "imagenes")
public class ImagenController extends BaseControllerImpl<ImagenArticulo, ImagenServiceImpl> implements IImagenController {
    public ImagenController(ImagenServiceImpl servicio) {
        super(servicio);
    }
}
