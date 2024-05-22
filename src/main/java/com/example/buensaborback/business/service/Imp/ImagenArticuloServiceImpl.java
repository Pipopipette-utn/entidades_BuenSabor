package com.example.buensaborback.business.service.Imp;

import com.example.buensaborback.business.mapper.ImagenArticuloMapper;
import com.example.buensaborback.business.service.CloudinaryService;
import com.example.buensaborback.business.service.ImagenArticuloService;
import com.example.buensaborback.domain.dto.ImagenArticuloDto;
import com.example.buensaborback.domain.entities.ImagenArticulo;
import com.example.buensaborback.repositories.ImagenArticuloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImagenArticuloServiceImpl implements ImagenArticuloService {

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private ImagenArticuloRepository imagenArticuloRepository;

    @Autowired
    private ImagenArticuloMapper imagenArticuloMapper;

    @Override
    public ResponseEntity<List<ImagenArticuloDto>> getAllImages() {
        try {
            //List<ImagenArticulo> images = imagenArticuloRepository.findAll(); //Eliminar?
            List<ImagenArticulo> entities = imagenArticuloRepository.findAll();
            List<ImagenArticuloDto> imageDtos = new ArrayList<>();

            // Mapea las entidades a DTOs
            List<ImagenArticuloDto> dtos = entities.stream()
                    .map(imagenArticuloMapper::toDTO)
                    .collect(Collectors.toList());
            //ELIMINAR COMENTANDO?
            /*
            for (ImagenArticulo image : images) {
                imageDtos.add(imagenArticuloMapper.toDto(image));
            }
*/
            return new ResponseEntity<>(dtos, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<String> uploadImages(MultipartFile[] files) {
        List<String> urls = new ArrayList<>();

        try {
            for (MultipartFile file : files) {
                if (file.isEmpty()) {
                    return ResponseEntity.badRequest().body("File is empty");
                }

                ImagenArticulo image = new ImagenArticulo();
                image.setName(file.getOriginalFilename());
                image.setUrl(cloudinaryService.uploadFile(file));

                if (image.getUrl() == null) {
                    return ResponseEntity.badRequest().body("Failed to upload file");
                }

                imagenArticuloRepository.save(image);
                urls.add(image.getUrl());
            }

            return new ResponseEntity<>("Uploaded successfully: " + String.join(", ", urls), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<String> deleteImage(String publicId, Long id) {
        try {
            imagenArticuloRepository.deleteById(id);
            cloudinaryService.deleteImage(publicId, id);
            return new ResponseEntity<>("Image deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
