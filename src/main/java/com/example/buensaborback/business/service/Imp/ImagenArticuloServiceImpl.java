package com.example.buensaborback.business.service.Imp;

import com.example.buensaborback.business.service.Base.BaseServiceImp;
import com.example.buensaborback.business.service.CloudinaryService;
import com.example.buensaborback.business.service.ImagenArticuloService;
import com.example.buensaborback.domain.dto.ImagenArticuloDto;
import com.example.buensaborback.domain.entities.ImagenArticulo;
import com.example.buensaborback.repositories.ImagenArticuloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class ImagenArticuloServiceImpl extends BaseServiceImp<ImagenArticulo, Long> implements ImagenArticuloService {
    @Autowired
    private CloudinaryService cloudinaryService; // Servicio para interactuar con Cloudinary
    @Autowired
    private ImagenArticuloRepository imageRepository; // Repositorio para interactuar con la base de datos de imágenes

    // Método para obtener todas las imágenes almacenadas
    @Override
    public List<ImagenArticuloDto> getAllImages() {
        try {
            // Consultar todas las imágenes desde la base de datos
            List<ImagenArticulo> images = imageRepository.findAll();
            List<ImagenArticuloDto> imageList = new ArrayList<>();

            // Mapear las entidades a DTOs
            for (ImagenArticulo image : images) {
                ImagenArticuloDto dto = new ImagenArticuloDto();
                dto.setId(image.getId());
                dto.setName(image.getName());
                dto.setUrl(image.getUrl());
                imageList.add(dto);
            }

            // Devolver la lista de DTOs
            return imageList;
        } catch (Exception e) {
            e.printStackTrace();
            // En caso de error, devolver una lista vacía
            return Collections.emptyList();
        }
    }


    // Método para subir imágenes a Cloudinary y guardar los detalles en la base de datos
    @Override
    public String uploadImages(MultipartFile[] files) {
        List<String> urls = new ArrayList<>();

        try {
            // Iterar sobre cada archivo recibido
            for (MultipartFile file : files) {
                // Verificar si el archivo está vacío
                if (file.isEmpty()) {
                    return "{\"status\":\"ERROR\", \"message\":\"Empty file detected\"}";
                }

                // Crear una entidad Image y establecer su nombre y URL (subida a Cloudinary)
                ImagenArticulo image = new ImagenArticulo();
                image.setName(file.getOriginalFilename()); // Establecer el nombre del archivo original
                image.setUrl(cloudinaryService.uploadFile(file)); // Subir el archivo a Cloudinary y obtener la URL

                // Verificar si la URL de la imagen es nula (indicativo de fallo en la subida)
                if (image.getUrl() == null) {
                    return "{\"status\":\"ERROR\", \"message\":\"Failed to upload image\"}";
                }

                // Guardar la entidad Image en la base de datos
                imageRepository.save(image);

                // Agregar la URL de la imagen a la lista de URLs subidas
                urls.add(image.getUrl());
            }

            // Convertir la lista de URLs a un objeto JSON y devolver como String
            return "{\"status\":\"OK\", \"urls\":" + urls + "}";

        } catch (Exception e) {
            e.printStackTrace();
            // Devolver un error en caso de excepción durante el proceso de subida
            return "{\"status\":\"ERROR\", \"message\":\"" + e.getMessage() + "\"}";
        }
    }

    // Método para eliminar una imagen por su identificador en la base de datos y en Cloudinary
    @Override
    public String deleteImage(String publicId, Long idBd) {
        try {
            // Eliminar la imagen de la base de datos usando su identificador
            imageRepository.deleteById(idBd);

            // Llamar al servicio de Cloudinary para eliminar la imagen por su publicId
            boolean result = cloudinaryService.deleteImage(publicId, idBd).hasBody();   //Agregado .hasBody para evitar cambios mayores, si falla cambiar

            if (result) {
                return "{\"status\":\"OK\", \"message\":\"Image deleted successfully\"}";
            } else {
                return "{\"status\":\"ERROR\", \"message\":\"Failed to delete image from Cloudinary\"}";
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Devolver un error en caso de excepción durante la eliminación
            return "{\"status\":\"ERROR\", \"message\":\"" + e.getMessage() + "\"}";
        }
    }
}
