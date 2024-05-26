package com.example.buensaborback.business.service.Imp;

import com.example.buensaborback.business.mapper.ImagenArticuloMapper;
import com.example.buensaborback.business.service.CloudinaryService;
import com.example.buensaborback.business.service.ImagenArticuloService;
import com.example.buensaborback.domain.dto.ImagenArticuloDto;
import com.example.buensaborback.domain.entities.Articulo;
import com.example.buensaborback.domain.entities.ImagenArticulo;
import com.example.buensaborback.repositories.ArticuloRepository;
import com.example.buensaborback.repositories.ImagenArticuloRepository;
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
public class ImagenArticuloServiceImpl implements ImagenArticuloService {

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private ImagenArticuloRepository imagenArticuloRepository;

    @Autowired
    private ImagenArticuloMapper imagenArticuloMapper;

    @Autowired
    private ArticuloRepository articuloRepository;

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
    public Set<ImagenArticulo> uploadImages(MultipartFile[] files, Long idArticulo) {
        List<String> urls = new ArrayList<>();
        Articulo articulo = articuloRepository.getById(idArticulo);
        Set<ImagenArticulo> imagenes = new HashSet<>();
        if (articulo == null) {
            throw new RuntimeException("El articulo con id " + idArticulo + " no se ha encontrado");
        }

        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                throw new RuntimeException("El archivo está vacío.");
            }

            ImagenArticulo image = new ImagenArticulo();
            image.setName(file.getOriginalFilename());
            image.setUrl(cloudinaryService.uploadFile(file));

            if (image.getUrl() == null) {
                throw new RuntimeException("No se pudo cargar el archivo");
            }

            ImagenArticulo imagenGuardada= imagenArticuloRepository.save(image);
            imagenes.add(imagenGuardada);
            urls.add(image.getUrl());
        }

        articulo.setImagenes(imagenes);
        articuloRepository.save(articulo);

        return imagenes;

    }

    @Override
    public ResponseEntity<String> deleteImage(String publicId, Long id) {
        try {
            ImagenArticulo imagenArticulo = imagenArticuloRepository.getById(id);
            imagenArticuloRepository.delete(imagenArticulo);
            cloudinaryService.deleteImage(publicId, id);
            return new ResponseEntity<>("Image deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
