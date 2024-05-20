package com.example.buensaborback.presentation.rest;

import com.entidades.buenSabor.business.service.ImageService;
import java.util.List;
import java.util.Map;
import java.util.UUID;
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
public class ImageController {
    @Autowired
    private ImageService imageService;

    public ImageController() {
    }

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
    public ResponseEntity<String> deleteById(@RequestParam(value = "publicId",required = true) String publicId, @RequestParam(value = "uuid",required = true) String uuidString) {
        try {
            UUID uuid = UUID.fromString(uuidString);
            return this.imageService.deleteImage(publicId, uuid);
        } catch (IllegalArgumentException var4) {
            var4.printStackTrace();
            return ResponseEntity.badRequest().body("Invalid UUID format");
        } catch (Exception var5) {
            var5.printStackTrace();
            return ResponseEntity.status(500).body("An error occurred");
        }
    }

    @GetMapping({"/getImages"})
    public ResponseEntity<List<Map<String, Object>>> getAll() {
        try {
            return this.imageService.getAllImages();
        } catch (Exception var2) {
            var2.printStackTrace();
            return null;
        }
    }
}
