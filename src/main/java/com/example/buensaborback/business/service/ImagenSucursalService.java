package com.example.buensaborback.business.service;


import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ImagenSucursalService {
    ResponseEntity<String> uploadImages(MultipartFile file, Long idPersona);

    ResponseEntity<String> deleteImage(String publicId, Long id);
}
