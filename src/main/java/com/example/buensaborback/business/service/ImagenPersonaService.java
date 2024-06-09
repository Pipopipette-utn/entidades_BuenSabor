package com.example.buensaborback.business.service;


import com.example.buensaborback.domain.dto.PromocionDtos.ImagenPromocionDto;
import com.example.buensaborback.domain.entities.ImagenPersona;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImagenPersonaService {
    ResponseEntity<String> uploadImages(MultipartFile file, Long idPersona);

    ResponseEntity<String> deleteImage(String publicId, Long id);
}
