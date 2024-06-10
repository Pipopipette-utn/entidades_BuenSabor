package com.example.buensaborback.business.service.Imp;

import com.example.buensaborback.business.mapper.ImagenPersonaMapper;
import com.example.buensaborback.business.service.CloudinaryService;
import com.example.buensaborback.business.service.ImagenPersonaService;
import com.example.buensaborback.domain.entities.ImagenPersona;
import com.example.buensaborback.domain.entities.Persona;
import com.example.buensaborback.repositories.ImagenPersonaRepository;
import com.example.buensaborback.repositories.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImagenPersonaServiceImpl implements ImagenPersonaService {
    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private ImagenPersonaRepository imagenPersonaRepository;

    @Autowired
    private ImagenPersonaMapper imagenPersonaMapper;

    @Autowired
    private PersonaRepository personaRepository;


    @Override
    public ResponseEntity<String> uploadImages(MultipartFile file, Long idPersona) {
        List<String> urls = new ArrayList<>();
        try {
            Persona persona = personaRepository.getById(idPersona);
            if (persona == null) {
                throw new RuntimeException("La persona con id " + idPersona + " no se ha encontrado");
            }

            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("El archivo está vacío.");
            }

            ImagenPersona image = new ImagenPersona();
            image.setName(file.getOriginalFilename());
            image.setUrl(cloudinaryService.uploadFile(file));

            if (image.getUrl() == null) {
                return ResponseEntity.badRequest().body("No se pudo cargar el archivo");
            }

            ImagenPersona imagenGuardada = imagenPersonaRepository.save(image);
            urls.add(image.getUrl());

            persona.setImagenPersona(imagenGuardada);
            personaRepository.save(persona);

            return new ResponseEntity<>("Subido exitosamente: " + String.join(", ", urls), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Ocurrió un error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<String> deleteImage(String publicId, Long id) {
        try {
            ImagenPersona imagenPersona = imagenPersonaRepository.getById(id);
            imagenPersonaRepository.delete(imagenPersona);
            cloudinaryService.deleteImage(publicId, id);
            return new ResponseEntity<>("Image deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
