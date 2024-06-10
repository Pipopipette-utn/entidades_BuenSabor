package com.example.buensaborback.business.facade.Imp;

import com.example.buensaborback.business.facade.ImagenArticuloFacade;
import com.example.buensaborback.business.facade.ImagenPersonaFacade;
import com.example.buensaborback.business.service.ImagenArticuloService;
import com.example.buensaborback.business.service.ImagenPersonaService;
import com.example.buensaborback.domain.dto.ImagenArticuloDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
public class ImagenPersonaFacadeImp implements ImagenPersonaFacade {

    @Autowired
    private ImagenPersonaService imagenPersonaService;

    @Override
    public String uploadImages(MultipartFile file, Long idPersona) {
        var respuesta = imagenPersonaService.uploadImages(file, idPersona);
        System.out.println(respuesta);
        System.out.println(respuesta.getBody());
        return respuesta.getBody();
    }

    @Override
    public String deleteImage(String publicId, Long id) {
        return imagenPersonaService.deleteImage(publicId, id).getBody();
    }
}
