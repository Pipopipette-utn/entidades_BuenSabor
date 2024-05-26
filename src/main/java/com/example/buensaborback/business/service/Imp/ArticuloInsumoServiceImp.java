package com.example.buensaborback.business.service.Imp;

import com.example.buensaborback.business.mapper.ArticuloInsumoMapper;
import com.example.buensaborback.business.service.ArticuloInsumoService;
import com.example.buensaborback.business.service.Base.BaseServiceImp;
import com.example.buensaborback.domain.dto.ArticuloInsumoDto;
import com.example.buensaborback.domain.entities.ArticuloInsumo;
import com.example.buensaborback.domain.entities.ArticuloManufacturadoDetalle;
import com.example.buensaborback.domain.entities.Categoria;
import com.example.buensaborback.domain.entities.ImagenArticulo;
import com.example.buensaborback.repositories.ArticuloInsumoRepository;
import com.example.buensaborback.repositories.ArticuloManufacturadoDetalleRepository;
import com.example.buensaborback.repositories.CategoriaRepository;
import com.example.buensaborback.repositories.ImagenArticuloRepository;
import com.example.buensaborback.utils.PublicIdService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ArticuloInsumoServiceImp extends BaseServiceImp<ArticuloInsumo,Long> implements ArticuloInsumoService {
    @Autowired
    ArticuloInsumoRepository articuloInsumoRepository;

    @Autowired
    ImagenArticuloRepository imagenArticuloRepository;

    @Autowired
    ImagenArticuloServiceImpl imagenArticuloService;

    @Autowired
    ArticuloManufacturadoDetalleRepository articuloManufacturadoDetalleRepository;

    @Autowired
    CategoriaRepository categoriaRepository;

    @Autowired
    ArticuloInsumoMapper articuloInsumoMapper;

    @Autowired
    PublicIdService publicIdService;

    @Override
    @Transactional
    public ArticuloInsumo create(ArticuloInsumo request) {
        Set<ImagenArticulo> imagenes = request.getImagenes();
        Set<ImagenArticulo> imagenesPersistidas = new HashSet<>();

        if (!imagenes.isEmpty()) {
            System.out.println("Entro al if");
            for (ImagenArticulo imagen : imagenes) {
                System.out.println("imagen: " + imagen);
                if (imagen.getId() != null) {
                    Optional<ImagenArticulo> imagenBd = imagenArticuloRepository.findById(imagen.getId());
                    imagenBd.ifPresent(imagenesPersistidas::add);
                } else {
                    System.out.println("no tiene id: " + imagen);
                    imagen.setBaja(false);
                    ImagenArticulo savedImagen = imagenArticuloRepository.save(imagen);
                    imagenesPersistidas.add(savedImagen);
                }
            }
            request.setImagenes(imagenesPersistidas);
        }

        if (request.getCategoria() != null) {
            Categoria categoria = categoriaRepository.getById(request.getCategoria().getId());
            if (categoria == null ) {
                throw new RuntimeException("La categoría con id: " + request.getCategoria().getId() + " no existe");
            }
            if (!categoria.isEsInsumo()) {
                throw new RuntimeException("La categoría con id: " + request.getCategoria().getId() + " no pertenece a una categoría de insumos");
            }

            request.setCategoria(categoria);
        }

        return articuloInsumoRepository.save(request);
    }

    @Override
    public ArticuloInsumo update(ArticuloInsumo request, Long id) {

        ArticuloInsumo insumoExistente = articuloInsumoRepository.getById(id);
        if (insumoExistente == null) {
            throw new RuntimeException("Insumo no encontrado: { id: " + id + " }");
        }

        Set<ImagenArticulo> imagenes = request.getImagenes();

        Set<ImagenArticulo> imagenesEliminadas = insumoExistente.getImagenes();
        imagenesEliminadas.removeAll(imagenes);
        for (ImagenArticulo imagen: imagenesEliminadas) {
            imagenArticuloService.deleteImage(publicIdService.obtenerPublicId(imagen.getUrl()), imagen.getId());
        }

        if (request.getCategoria() != null) {
            Categoria categoria = categoriaRepository.getById(request.getCategoria().getId());
            if (categoria == null ) {
                throw new RuntimeException("La categoría con id: " + request.getCategoria().getId() + " no existe");
            }
            if (!categoria.isEsInsumo()) {
                throw new RuntimeException("La categoría con id: " + request.getCategoria().getId() + " no pertenece a una categoría de insumos");
            }

            request.setCategoria(categoria);
        }

        /*if (request.getArchivos() != null) {
            imagenArticuloService.uploadImages(request.getArchivos(), id);
        }*/

        return articuloInsumoRepository.save(request);
    }

    @Override
    public void deleteById(Long id) {
        ArticuloInsumo insumo = articuloInsumoRepository.getById(id);
        if (insumo == null) {
            throw new RuntimeException("Insumo no encontrado: { id: " + id + " }");
        }
        List<ArticuloManufacturadoDetalle> insumoEsUtilizado = articuloManufacturadoDetalleRepository.getByArticuloInsumo(insumo);
        if (!insumoEsUtilizado.isEmpty()) {
            throw new RuntimeException("No se puede eliminar el articulo porque está presente en un detalle");
        }
        articuloInsumoRepository.deleteById(id);
    }

    @Override
    public Page<ArticuloInsumo> findByEsParaElaborarTrue(Pageable pageable) {
        return articuloInsumoRepository.findByEsParaElaborarTrue(pageable);
    }

    @Override
    public Page<ArticuloInsumo> findByEsParaElaborarFalse(Pageable pageable) {
        return articuloInsumoRepository.findByEsParaElaborarFalse(pageable);
    }
}
