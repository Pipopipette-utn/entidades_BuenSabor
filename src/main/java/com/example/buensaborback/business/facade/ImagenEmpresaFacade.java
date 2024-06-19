package com.example.buensaborback.business.facade;

import org.springframework.web.multipart.MultipartFile;


public interface ImagenEmpresaFacade {
    String uploadImages(MultipartFile file, Long idEmpresa);
    String deleteImage(String publicId, Long id);
}
