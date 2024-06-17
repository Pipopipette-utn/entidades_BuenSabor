package com.example.buensaborback.business.service.Imp;

import com.example.buensaborback.business.service.CloudinaryService;
import com.example.buensaborback.business.service.ImagenEmpresaService;
import com.example.buensaborback.domain.entities.Empresa;
import com.example.buensaborback.domain.entities.ImagenEmpresa;
import com.example.buensaborback.repositories.EmpresaRepository;
import com.example.buensaborback.repositories.ImagenEmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImagenEmpresaServiceImpl implements ImagenEmpresaService {
    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private ImagenEmpresaRepository imagenEmpresaRepository;

    @Autowired
    private EmpresaRepository empresaRepository;


    @Override
    public ResponseEntity<String> uploadImages(MultipartFile file, Long idEmpresa) {
        List<String> urls = new ArrayList<>();
        try {
            Empresa empresa = empresaRepository.getById(idEmpresa);
            if (empresa == null) {
                throw new RuntimeException("La empresa con id " + idEmpresa + " no se ha encontrado");
            }

            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("El archivo está vacío.");
            }

            ImagenEmpresa image = new ImagenEmpresa();
            image.setName(file.getOriginalFilename());
            image.setUrl(cloudinaryService.uploadFile(file));

            if (image.getUrl() == null) {
                return ResponseEntity.badRequest().body("No se pudo cargar el archivo");
            }

            ImagenEmpresa imagenGuardada = imagenEmpresaRepository.save(image);
            urls.add(image.getUrl());

            empresa.setImagenEmpresa(imagenGuardada);
            empresaRepository.save(empresa);

            return new ResponseEntity<>("Subido exitosamente: " + String.join(", ", urls), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Ocurrió un error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<String> deleteImage(String publicId, Long id) {
        try {
            ImagenEmpresa imagenEmpresa = imagenEmpresaRepository.getById(id);
            imagenEmpresaRepository.delete(imagenEmpresa);
            cloudinaryService.deleteImage(publicId, id);
            return new ResponseEntity<>("Image deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
