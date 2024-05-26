package com.example.buensaborback.business.facade.impl;

import com.example.buensaborback.business.facade.ImagenArticuloFacade;
import com.example.buensaborback.business.service.ImagenArticuloService;
import com.example.buensaborback.domain.dto.ImagenArticuloDto;
import com.example.buensaborback.domain.entities.ImagenArticulo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@Component
public class ImagenArticuloFacadeImp implements ImagenArticuloFacade {

    @Autowired
    private ImagenArticuloService imagenArticuloService;

    @Override
    public List<ImagenArticuloDto> getAllImages() {
        return imagenArticuloService.getAllImages().getBody();
    }

    @Override
    public Set<ImagenArticulo> uploadImages(MultipartFile[] files, Long idArticulo) {
        return imagenArticuloService.uploadImages(files, idArticulo);
    }

    @Override
    public String deleteImage(String publicId, Long id) {
        return imagenArticuloService.deleteImage(publicId, id).getBody();
    }
}