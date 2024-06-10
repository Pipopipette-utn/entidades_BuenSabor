package com.example.buensaborback.business.facade;

import org.springframework.web.multipart.MultipartFile;


public interface ImagenPersonaFacade {
    String uploadImages(MultipartFile file, Long idPersona);
    String deleteImage(String publicId, Long id);
}
