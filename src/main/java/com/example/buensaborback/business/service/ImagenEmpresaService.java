package com.example.buensaborback.business.service;


import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ImagenEmpresaService {
    ResponseEntity<String> uploadImages(MultipartFile file, Long idEmpresa);

    ResponseEntity<String> deleteImage(String publicId, Long id);
}
