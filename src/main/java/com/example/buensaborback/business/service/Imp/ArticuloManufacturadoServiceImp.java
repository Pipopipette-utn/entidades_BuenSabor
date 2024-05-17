package com.example.buensaborback.business.service.Imp;

import com.example.buensaborback.business.service.ArticuloManufacturadoService;
import com.example.buensaborback.business.service.Base.BaseServiceImp;
import com.example.buensaborback.domain.entities.ArticuloManufacturado;
import com.example.buensaborback.domain.entities.ImagenArticulo;
import com.example.buensaborback.repositories.ArticuloManufacturadoRepository;
import com.example.buensaborback.repositories.ImagenArticuloRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class ArticuloManufacturadoServiceImp extends BaseServiceImp<ArticuloManufacturado,Long> implements ArticuloManufacturadoService {

    @Autowired
    ArticuloManufacturadoRepository articuloManufacturadoRepository;

    @Autowired
    ImagenArticuloRepository imagenArticuloRepository;

    public ArticuloManufacturadoServiceImp() {
    }

    @Override
    @Transactional
    public ArticuloManufacturado create(ArticuloManufacturado request) {
        Set<ImagenArticulo> imagenes = request.getImagenes();
        Set<ImagenArticulo> imagenesPersistidas = new HashSet<>();

        if (!imagenes.isEmpty()) {
            for (ImagenArticulo imagen : imagenes) {
                if (imagen.getId() != null) {
                    Optional<ImagenArticulo> imagenBd = imagenArticuloRepository.findById(imagen.getId());
                    imagenBd.ifPresent(imagenesPersistidas::add);
                } else {
                    imagen.setBaja(false);
                    ImagenArticulo savedImagen = imagenArticuloRepository.save(imagen);
                    imagenesPersistidas.add(savedImagen);
                }
            }
            request.setImagenes(imagenesPersistidas);
        }

        return articuloManufacturadoRepository.save(request);
    }

    @Override
    @Transactional
    public ArticuloManufacturado update(ArticuloManufacturado request, Long id) {
        ArticuloManufacturado articulo = articuloManufacturadoRepository.getById(id);
        if (articulo == null) {
            throw new RuntimeException("Articulo manufacturado no encontrado: { id: " + id + " }");
        }

        Set<ImagenArticulo> imagenes = request.getImagenes();
        Set<ImagenArticulo> imagenesPersistidas = new HashSet<>();

        if (imagenes != null && !imagenes.isEmpty()) {
            for (ImagenArticulo imagen : imagenes) {
                if (imagen.getId() != null) {
                    ImagenArticulo imagenBd = imagenArticuloRepository.getById(imagen.getId());
                    imagenesPersistidas.add(imagenBd);
                } else {
                    ImagenArticulo savedImagen = imagenArticuloRepository.save(imagen);
                    imagenesPersistidas.add(savedImagen);
                }
            }
            articulo.setImagenes(imagenesPersistidas);
        }

        if (!imagenesPersistidas.isEmpty()) {
            request.setImagenes(imagenesPersistidas);
        }

        return articuloManufacturadoRepository.save(request);
    }
}
