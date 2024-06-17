package com.example.buensaborback.presentation.rest;

import com.example.buensaborback.business.facade.ImagenEmpresaFacade;
import com.example.buensaborback.business.facade.ImagenPersonaFacade;
import com.example.buensaborback.domain.entities.ImagenEmpresa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/imagenesEmpresa")
@CrossOrigin("*")
public class ImagenEmpresaController {

    @Autowired
    private ImagenEmpresaFacade imagenEmpresaFacade;
    @PostMapping("/uploads/{idEmpresa}")
    public String uploadImages(@RequestParam("uploads") MultipartFile file, @PathVariable Long idEmpresa) {
        return imagenEmpresaFacade.uploadImages(file, idEmpresa);
    }

    @PostMapping("/deleteImg")
    public String deleteById(@RequestParam("publicId") String publicId, @RequestParam("id") Long id) {
        return imagenEmpresaFacade.deleteImage(publicId, id);
    }
}
