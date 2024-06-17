package com.example.buensaborback.business.facade.Imp;

import com.example.buensaborback.business.facade.ImagenPersonaFacade;
import com.example.buensaborback.business.facade.ImagenSucursalFacade;
import com.example.buensaborback.business.service.ImagenPersonaService;
import com.example.buensaborback.business.service.ImagenSucursalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ImagenSucursalFacadeImp implements ImagenSucursalFacade {

    @Autowired
    private ImagenSucursalService imagenSucursalService;

    @Override
    public String uploadImages(MultipartFile file, Long idSucursal) {
        var respuesta = imagenSucursalService.uploadImages(file, idSucursal);
        System.out.println(respuesta);
        System.out.println(respuesta.getBody());
        return respuesta.getBody();
    }

    @Override
    public String deleteImage(String publicId, Long id) {
        return imagenSucursalService.deleteImage(publicId, id).getBody();
    }
}
