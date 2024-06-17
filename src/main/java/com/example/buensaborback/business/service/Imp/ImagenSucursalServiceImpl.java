package com.example.buensaborback.business.service.Imp;

import com.example.buensaborback.business.service.CloudinaryService;
import com.example.buensaborback.business.service.ImagenSucursalService;
import com.example.buensaborback.domain.entities.ImagenSucursal;
import com.example.buensaborback.domain.entities.Sucursal;
import com.example.buensaborback.repositories.ImagenSucursalRepository;
import com.example.buensaborback.repositories.SucursalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImagenSucursalServiceImpl implements ImagenSucursalService {
    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private ImagenSucursalRepository imagenSucursalRepository;

    @Autowired
    private SucursalRepository sucursalRepository;


    @Override
    public ResponseEntity<String> uploadImages(MultipartFile file, Long idSucursal) {
        List<String> urls = new ArrayList<>();
        try {
            Sucursal sucursal = sucursalRepository.getById(idSucursal);
            if (sucursal == null) {
                throw new RuntimeException("La sucursal con id " + idSucursal + " no se ha encontrado");
            }

            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("El archivo está vacío.");
            }

            ImagenSucursal image = new ImagenSucursal();
            image.setName(file.getOriginalFilename());
            image.setUrl(cloudinaryService.uploadFile(file));

            if (image.getUrl() == null) {
                return ResponseEntity.badRequest().body("No se pudo cargar el archivo");
            }

            ImagenSucursal imagenGuardada = imagenSucursalRepository.save(image);
            urls.add(image.getUrl());

            sucursal.setImagenSucursal(imagenGuardada);
            sucursalRepository.save(sucursal);

            return new ResponseEntity<>("Subido exitosamente: " + String.join(", ", urls), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Ocurrió un error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<String> deleteImage(String publicId, Long id) {
        try {
            ImagenSucursal imagenSucursal = imagenSucursalRepository.getById(id);
            imagenSucursalRepository.delete(imagenSucursal);
            cloudinaryService.deleteImage(publicId, id);
            return new ResponseEntity<>("Image deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
