package com.example.buensaborback.business.facade.Imp;

import com.example.buensaborback.business.facade.ImagenPromocionFacade;
import com.example.buensaborback.business.service.ImagenPromocionService;
import com.example.buensaborback.domain.dto.PromocionDtos.ImagenPromocionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
public class ImagenPromocionFacadeImp implements ImagenPromocionFacade {

    @Autowired
    private ImagenPromocionService imagenPromocionService;

    @Override
    public List<ImagenPromocionDto> getAllImages() {
        return imagenPromocionService.getAllImages().getBody();
    }

    @Override
    public String uploadImages(MultipartFile[] files, Long idPromocion) {
        return imagenPromocionService.uploadImages(files, idPromocion).getBody();
    }

    @Override
    public String deleteImage(String publicId, Long id) {
        return imagenPromocionService.deleteImage(publicId, id).getBody();
    }
}
