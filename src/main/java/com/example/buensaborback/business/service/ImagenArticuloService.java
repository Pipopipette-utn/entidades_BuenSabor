package com.example.buensaborback.business.service;

import com.example.buensaborback.domain.dto.ImagenArticuloDto;
import com.example.buensaborback.domain.entities.ImagenArticulo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface ImagenArticuloService {

    ResponseEntity<List<ImagenArticuloDto>> getAllImages();

    Set<ImagenArticulo> uploadImages(MultipartFile[] files, Long idArticulo);

    ResponseEntity<String> deleteImage(String publicId, Long id);
}
