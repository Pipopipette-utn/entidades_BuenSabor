package com.example.buensaborback.business.service;

import com.example.buensaborback.business.service.Base.BaseService;
import com.example.buensaborback.domain.dto.ImagenArticuloDto;
import com.example.buensaborback.domain.entities.ImagenArticulo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ImagenArticuloService extends BaseService<ImagenArticulo,Long> {

    // Método para obtener todas las imágenes almacenadas
    List<ImagenArticulo> getAllImages();

    // Método para subir imágenes al sistema
    String uploadImages(MultipartFile[] files);

    // Método para eliminar una imagen por su identificador público y UUID
    String deleteImage(String publicId, Long id);
}
