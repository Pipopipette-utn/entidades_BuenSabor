package com.example.buensaborback.business.facade;

import com.example.buensaborback.domain.dto.PromocionDtos.ImagenPromocionDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImagenPromocionFacade {
    List<ImagenPromocionDto> getAllImages();
    String uploadImages(MultipartFile[] files, Long idPromocion);
    String deleteImage(String publicId, Long id);
}
