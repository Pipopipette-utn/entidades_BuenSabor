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
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ArticuloInsumoServiceImp extends BaseServiceImp<ArticuloInsumo,Long> implements ArticuloInsumoService {
    @Autowired
    ArticuloInsumoRepository articuloInsumoRepository;

    @Autowired
    ImagenArticuloServiceImpl imagenArticuloService;

    @Autowired
    ArticuloManufacturadoDetalleRepository articuloManufacturadoDetalleRepository;

    @Autowired
    CategoriaRepository categoriaRepository;

    @Autowired
    ArticuloInsumoMapper articuloInsumoMapper;

    @Override
    @Transactional
    public ArticuloInsumo create(ArticuloInsumoDto request) {
        ArticuloInsumo insumo = articuloInsumoMapper.toEntity(request);

        if (insumo.getCategoria() != null) {
            Categoria categoria = categoriaRepository.getById(insumo.getCategoria().getId());
            if (categoria == null ) {
                throw new RuntimeException("La categoría con id: " + insumo.getCategoria().getId() + " no existe");
            }
            if (!categoria.isEsInsumo()) {
                throw new RuntimeException("La categoría con id: " + insumo.getCategoria().getId() + " no pertenece a una categoría de insumos");
            }

            insumo.setCategoria(categoria);
        }

        ArticuloInsumo articulo = articuloInsumoRepository.save(insumo);

        if (request.getArchivos() != null) {
            imagenArticuloService.uploadImages(request.getArchivos(), articulo.getId());
        }

        return articulo;
    }

    @Override
    public ArticuloInsumo update(ArticuloInsumoDto request, Long id) {
        ArticuloInsumo insumo = articuloInsumoMapper.toEntity(request);

        ArticuloInsumo insumoExistente = articuloInsumoRepository.getById(id);
        if (insumoExistente == null) {
            throw new RuntimeException("Insumo no encontrado: { id: " + id + " }");
        }

        Set<ImagenArticulo> imagenes = insumo.getImagenes();
        Set<ImagenArticulo> imagenesPersistidas = new HashSet<>();

        Set<ImagenArticulo> imagenesEliminadas = insumoExistente.getImagenes();
        imagenesEliminadas.removeAll(imagenes);
        for (ImagenArticulo imagen: imagenesEliminadas) {
            imagenArticuloService.deleteImage(request.getPublicId(), imagen.getId());
        }

        if (insumo.getCategoria() != null) {
            Categoria categoria = categoriaRepository.getById(insumo.getCategoria().getId());
            if (categoria == null ) {
                throw new RuntimeException("La categoría con id: " + insumo.getCategoria().getId() + " no existe");
            }
            if (!categoria.isEsInsumo()) {
                throw new RuntimeException("La categoría con id: " + insumo.getCategoria().getId() + " no pertenece a una categoría de insumos");
            }

            insumo.setCategoria(categoria);
        }

        if (request.getArchivos() != null) {
            imagenArticuloService.uploadImages(request.getArchivos(), id);
        }

        return articuloInsumoRepository.save(insumo);
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
