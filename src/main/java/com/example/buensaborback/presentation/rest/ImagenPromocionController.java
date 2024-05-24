package com.example.buensaborback.presentation.rest;

import com.example.buensaborback.business.facade.ImagenPromocionFacade;
import com.example.buensaborback.domain.dto.PromocionDtos.ImagenPromocionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/imagenesPromocion")
@CrossOrigin("*")
public class ImagenPromocionController {

    @Autowired
    private ImagenPromocionFacade imagenPromocionFacade;

    @GetMapping("/getImages")
    public List<ImagenPromocionDto> getAll() {
        return imagenPromocionFacade.getAllImages();
    }

    @PostMapping("/uploads/{idPromocion}")
    public String uploadImages(@RequestParam("uploads") MultipartFile[] files, @PathVariable Long idPromocion) {
        return imagenPromocionFacade.uploadImages(files, idPromocion);
    }

    @PostMapping("/deleteImg")
    public String deleteById(@RequestParam("publicId") String publicId, @RequestParam("id") Long id) {
        return imagenPromocionFacade.deleteImage(publicId, id);
    }
}
