package com.example.buensaborback.business.service.BaseImagen;

/*import com.example.buensaborback.business.service.Base.BaseService;
import com.example.buensaborback.business.service.CloudinaryService;
import com.example.buensaborback.domain.dto.ImagenArticuloDto;
import com.example.buensaborback.domain.entities.Articulo;
import com.example.buensaborback.domain.entities.Base;
import com.example.buensaborback.domain.entities.ImagenArticulo;
import com.example.buensaborback.repositories.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BaseImagenImpl<E extends Base,ID extends Serializable> implements BaseImagenService<E, ID> {

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    protected BaseRepository<E,ID> baseRepository;

    @Override
    public List<E> getAllImages() {
        List<E> image = baseRepository.findAll();
        return image;
    }

    @Override
    public String uploadImages(MultipartFile[] files, ID idEntity) {
        List<String> urls = new ArrayList<>();
        try {
            var entityImagen = baseRepository.getById(idEntity);
            Set<E> imagenes = new HashSet<>();
            if (entityImagen == null) {
                throw new RuntimeException("la entidad con id " + idEntity + " no se ha encontrado");
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

                E imagenGuardada= baseRepository.save(image);
                imagenes.add(imagenGuardada);
                urls.add(image.getUrl());
            }

            articulo.setImagenes(imagenes);
            articuloRepository.save(articulo);

            return new ResponseEntity<>("Subido exitosamente: " + String.join(", ", urls), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Ocurrió un error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public String deleteImage(String publicId, ID id) {
        return null;
    }
}
*/