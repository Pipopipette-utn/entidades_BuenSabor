package com.example.buensaborback.business.facade;

import com.example.buensaborback.domain.dto.ImagenArticuloDto;
import com.example.buensaborback.domain.entities.ImagenArticulo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface ImagenArticuloFacade {
    List<ImagenArticuloDto> getAllImages();
    Set<ImagenArticulo> uploadImages(MultipartFile[] files, Long idArticulo);
    String deleteImage(String publicId, Long id);
}
