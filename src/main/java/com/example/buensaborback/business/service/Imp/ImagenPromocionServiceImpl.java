package com.example.buensaborback.business.service.Imp;

import com.example.buensaborback.business.mapper.ImagenPromocionMapper;
import com.example.buensaborback.business.service.CloudinaryService;
import com.example.buensaborback.business.service.ImagenPromocionService;
import com.example.buensaborback.domain.dto.PromocionDtos.ImagenPromocionDto;
import com.example.buensaborback.domain.entities.ImagenArticulo;
import com.example.buensaborback.domain.entities.ImagenPromocion;
import com.example.buensaborback.domain.entities.Promocion;
import com.example.buensaborback.repositories.ImagenPromocionRepository;
import com.example.buensaborback.repositories.PromocionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ImagenPromocionServiceImpl implements ImagenPromocionService {
    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private ImagenPromocionRepository imagenPromocionRepository;

    @Autowired
    private ImagenPromocionMapper imagenPromocionMapper;

    @Autowired
    private PromocionRepository promocionRepository;

    @Override
    public ResponseEntity<List<ImagenPromocionDto>> getAllImages() {
        try {
            List<ImagenPromocion> entities = imagenPromocionRepository.findAll();
            List<ImagenPromocionDto> imageDtos = new ArrayList<>();

            // Mapea las entidades a DTOs
            List<ImagenPromocionDto> dtos = entities.stream()
                    .map(imagenPromocionMapper::toDTO)
                    .collect(Collectors.toList());

            return new ResponseEntity<>(dtos, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<String> uploadImages(MultipartFile[] files, Long idPromocion) {
        List<String> urls = new ArrayList<>();
        try {
            Promocion promocion = promocionRepository.getById(idPromocion);
            Set<ImagenPromocion> imagenes = new HashSet<>();
            if (promocion == null) {
                throw new RuntimeException("La promoción con id " + idPromocion + " no se ha encontrado");
            }

            for (MultipartFile file : files) {
                if (file.isEmpty()) {
                    return ResponseEntity.badRequest().body("El archivo está vacío.");
                }

                ImagenPromocion image = new ImagenPromocion();
                image.setName(file.getOriginalFilename());
                image.setUrl(cloudinaryService.uploadFile(file));

                if (image.getUrl() == null) {
                    return ResponseEntity.badRequest().body("No se pudo cargar el archivo");
                }

                ImagenPromocion imagenGuardada = imagenPromocionRepository.save(image);
                imagenes.add(imagenGuardada);
                urls.add(image.getUrl());
            }
            promocion.getImagenes().addAll(imagenes);
            promocionRepository.save(promocion);

            return new ResponseEntity<>("Subido exitosamente: " + String.join(", ", urls), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Ocurrió un error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<String> deleteImage(String publicId, Long id) {
        try {
            ImagenPromocion imagenPromocion = imagenPromocionRepository.getById(id);
            imagenPromocionRepository.delete(imagenPromocion);
            cloudinaryService.deleteImage(publicId, id);
            return new ResponseEntity<>("Image deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
