package com.example.buensaborback.business.facade;

import org.springframework.web.multipart.MultipartFile;


public interface ImagenSucursalFacade {
    String uploadImages(MultipartFile file, Long idSucursal);
    String deleteImage(String publicId, Long id);
}
