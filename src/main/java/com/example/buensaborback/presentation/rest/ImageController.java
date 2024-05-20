package com.example.buensaborback.presentation.rest;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.example.buensaborback.business.service.ImagenArticuloService;
import com.example.buensaborback.domain.dto.ImagenArticuloDto;
import com.example.buensaborback.domain.entities.ImagenArticulo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping({"/images"})
@CrossOrigin({"*"})
public class ImageController  {
    @Autowired
    private ImagenArticuloService imageService;


    @PostMapping({"/uploads"})
    public ResponseEntity<String> uploadImages(@RequestParam(value = "uploads",required = true) MultipartFile[] files) {
        try {
            return this.imageService.uploadImages(files);
        } catch (Exception var3) {
            var3.printStackTrace();
            return null;
        }
    }

    @PostMapping({"/deleteImg"})
    public ResponseEntity<String> deleteById(@RequestParam(value = "publicId",required = true) String publicId, @RequestParam(value = "id",required = true) String idString) {
        try {
            Long id = Long.parseLong(idString,16);
            return this.imageService.deleteImage(publicId, id);
        } catch (IllegalArgumentException var4) {
            var4.printStackTrace();
            return ResponseEntity.badRequest().body("Invalid UUID format");
        } catch (Exception var5) {
            var5.printStackTrace();
            return ResponseEntity.status(500).body("An error occurred");
        }
    }

    @GetMapping({"/getImages"})
    public List<ImagenArticulo> getAll() {
        try {
            return this.imageService.getAllImages();
        } catch (Exception var2) {
            var2.printStackTrace();
            return null;
        }
    }
}
