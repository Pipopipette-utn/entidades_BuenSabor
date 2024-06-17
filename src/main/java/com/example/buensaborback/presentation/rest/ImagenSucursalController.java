package com.example.buensaborback.presentation.rest;

import com.example.buensaborback.business.facade.ImagenPersonaFacade;
import com.example.buensaborback.business.facade.ImagenSucursalFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/imagenesSucursal")
@CrossOrigin("*")
public class ImagenSucursalController {

    @Autowired
    private ImagenSucursalFacade imagenSucursalFacade;
    @PostMapping("/uploads/{idSucursal}")
    public String uploadImages(@RequestParam("uploads") MultipartFile file, @PathVariable Long idSucursal) {
        return imagenSucursalFacade.uploadImages(file, idSucursal);
    }

    @PostMapping("/deleteImg")
    public String deleteById(@RequestParam("publicId") String publicId, @RequestParam("id") Long id) {
        return imagenSucursalFacade.deleteImage(publicId, id);
    }
}
