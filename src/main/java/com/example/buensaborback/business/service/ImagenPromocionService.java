package com.example.buensaborback.business.service;


import com.example.buensaborback.domain.dto.PromocionDtos.ImagenPromocionDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImagenPromocionService {
    ResponseEntity<List<ImagenPromocionDto>> getAllImages();

    ResponseEntity<String> uploadImages(MultipartFile[] files, Long idPromocion);

    ResponseEntity<String> deleteImage(String publicId, Long id);
}
