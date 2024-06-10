package com.example.buensaborback.presentation.rest;

import com.example.buensaborback.business.facade.ImagenPersonaFacade;
import com.example.buensaborback.business.facade.ImagenPromocionFacade;
import com.example.buensaborback.domain.dto.PromocionDtos.ImagenPromocionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/imagenesPersona")
@CrossOrigin("*")
public class ImagenPersonaController {

    @Autowired
    private ImagenPersonaFacade imagenPromocionFacade;
    @PostMapping("/uploads/{idPersona}")
    public String uploadImages(@RequestParam("uploads") MultipartFile file, @PathVariable Long idPersona) {
        return imagenPromocionFacade.uploadImages(file, idPersona);
    }

    @PostMapping("/deleteImg")
    public String deleteById(@RequestParam("publicId") String publicId, @RequestParam("id") Long id) {
        return imagenPromocionFacade.deleteImage(publicId, id);
    }
}
