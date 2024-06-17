package com.example.buensaborback.business.facade.Imp;

import com.example.buensaborback.business.facade.ImagenEmpresaFacade;
import com.example.buensaborback.business.service.ImagenEmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ImagenEmpresaFacadeImp implements ImagenEmpresaFacade {

    @Autowired
    private ImagenEmpresaService imagenEmpresaService;

    @Override
    public String uploadImages(MultipartFile file, Long idEmpresa) {
        var respuesta = imagenEmpresaService.uploadImages(file, idEmpresa);
        System.out.println(respuesta);
        System.out.println(respuesta.getBody());
        return respuesta.getBody();
    }

    @Override
    public String deleteImage(String publicId, Long id) {
        return imagenEmpresaService.deleteImage(publicId, id).getBody();
    }
}
