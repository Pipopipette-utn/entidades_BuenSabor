package com.example.buensaborback.business.service.Imp;

import com.example.buensaborback.business.service.ArticuloManufacturadoService;
import com.example.buensaborback.business.service.Base.BaseServiceImp;
import com.example.buensaborback.domain.dto.SucursalDtos.SucursalShortDto;
import com.example.buensaborback.domain.entities.*;
import com.example.buensaborback.repositories.*;
import com.example.buensaborback.utils.PublicIdService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ArticuloManufacturadoServiceImp extends BaseServiceImp<ArticuloManufacturado, Long> implements ArticuloManufacturadoService {

    @Autowired
    ArticuloManufacturadoRepository articuloManufacturadoRepository;

    @Autowired
    ImagenArticuloServiceImpl imagenArticuloService;

    @Autowired
    ArticuloManufacturadoDetalleRepository articuloManufacturadoDetalleRepository;

    @Autowired
    ArticuloInsumoRepository articuloInsumoRepository;

    @Autowired
    CategoriaRepository categoriaRepository;

    @Autowired
    PublicIdService publicIdService;

    @Autowired
    SucursalRepository sucursalRepository;

    @Transactional
    public List<ArticuloManufacturado> create(ArticuloManufacturado request, Set<Sucursal> sucursales) {
        List<ArticuloManufacturado> articulosCreados = new ArrayList<>();
        Categoria categoria = fetchAndValidateCategoria(request);

        for (Sucursal sucursal : sucursales) {
            Sucursal sucursalBd = sucursalRepository.findById(sucursal.getId())
                    .orElseThrow(() -> new RuntimeException("La sucursal con id " + sucursal.getId() + " no se ha encontrado"));
            ArticuloManufacturado nuevoArticulo = createArticuloManufacturado(request, sucursalBd, categoria);
            articulosCreados.add(nuevoArticulo);
        }
        return articuloManufacturadoRepository.saveAll(articulosCreados);
    }

    @Transactional
    @Override
    public ArticuloManufacturado update(ArticuloManufacturado request, Long id) {
        ArticuloManufacturado articuloExistente = articuloManufacturadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El artículo manufacturado con id " + id + " no se ha encontrado"));

        updateImagenes(request, articuloExistente);

        Categoria categoria = fetchAndValidateCategoria(request);

        // Eliminar detalles antiguos
        articuloManufacturadoDetalleRepository.deleteAll(articuloExistente.getArticuloManufacturadoDetalles());

        Set<ArticuloManufacturadoDetalle> nuevosDetalles = request.getArticuloManufacturadoDetalles();
        if (nuevosDetalles == null || nuevosDetalles.isEmpty()) {
            throw new RuntimeException("El artículo debe tener al menos un detalle.");
        }

        Set<ArticuloManufacturadoDetalle> detallesActualizados = new HashSet<>();
        for (ArticuloManufacturadoDetalle detalle : nuevosDetalles) {
            ArticuloInsumo insumo = articuloInsumoRepository.findById(detalle.getArticulo().getId())
                    .orElseThrow(() -> new RuntimeException("El insumo con id " + detalle.getArticulo().getId() + " no se ha encontrado"));
            ArticuloManufacturadoDetalle nuevoDetalle = new ArticuloManufacturadoDetalle();
            nuevoDetalle.setCantidad(detalle.getCantidad());
            nuevoDetalle.setArticulo(insumo);
            detallesActualizados.add(nuevoDetalle);
        }

        request.setArticuloManufacturadoDetalles(detallesActualizados);
        request.setCategoria(categoria);

        return articuloManufacturadoRepository.save(request);
    }

    private ArticuloManufacturado createArticuloManufacturado(ArticuloManufacturado request, Sucursal sucursal, Categoria categoria) {
        ArticuloManufacturado nuevoArticulo = new ArticuloManufacturado(
                request.getDenominacion(),
                request.getPrecioVenta(),
                request.getUnidadMedida(),
                request.getDescripcion(),
                request.getTiempoEstimadoMinutos(),
                request.getPreparacion()
        );

        nuevoArticulo.setImagenes(new HashSet<>(request.getImagenes()));

        if (categoria != null) {
            boolean categoriaExisteEnSucursal = sucursal.getCategorias().stream()
                    .anyMatch(cat -> cat.getId().equals(categoria.getId()));

            if (!categoriaExisteEnSucursal) {
                throw new RuntimeException("La categoría " + categoria.getDenominacion() + " no existe en la sucursal " + sucursal.getNombre());
            }
            nuevoArticulo.setCategoria(categoria);
        }

        Set<ArticuloManufacturadoDetalle> detalles = request.getArticuloManufacturadoDetalles();
        Set<ArticuloManufacturadoDetalle> nuevosDetalles = new HashSet<>();

        if (detalles != null && !detalles.isEmpty()) {
            for (ArticuloManufacturadoDetalle detalle : detalles) {
                ArticuloInsumo insumoExistente = articuloInsumoRepository.findBySucursal_IdAndDenominacionContainingIgnoreCase(sucursal.getId(), detalle.getArticulo().getDenominacion());
                if (insumoExistente == null){
                    throw new RuntimeException("El insumo con nombre " + detalle.getArticulo().getDenominacion() + " no se ha encontrado en la sucursal " + sucursal.getNombre());
                }
                ArticuloManufacturadoDetalle nuevoDetalle = new ArticuloManufacturadoDetalle();
                nuevoDetalle.setCantidad(detalle.getCantidad());
                nuevoDetalle.setArticulo(insumoExistente);
                nuevosDetalles.add(nuevoDetalle);
            }
            nuevoArticulo.setArticuloManufacturadoDetalles(nuevosDetalles);
        } else {
            throw new RuntimeException("El artículo debe tener al menos un detalle.");
        }

        nuevoArticulo.setSucursal(sucursal);
        return nuevoArticulo;
    }

    @Transactional
    public List<ArticuloManufacturado> duplicateInOtherSucursales(Long id, Set<SucursalShortDto> sucursales) {
        ArticuloManufacturado articuloExistente = articuloManufacturadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artículo manufacturado no encontrado: { id: " + id + " }"));

        List<ArticuloManufacturado> articulosDuplicados = new ArrayList<>();
        Categoria categoria = articuloExistente.getCategoria();

        for (SucursalShortDto sucursal : sucursales) {
            Sucursal sucursalBd = sucursalRepository.findById(sucursal.getId())
                    .orElseThrow(() -> new RuntimeException("La sucursal con id " + sucursal.getId() + " no se ha encontrado"));
            ArticuloManufacturado nuevoArticulo = createArticuloManufacturado(articuloExistente, sucursalBd, categoria);
            articulosDuplicados.add(nuevoArticulo);
        }

        return articuloManufacturadoRepository.saveAll(articulosDuplicados);
    }

    private void updateImagenes(ArticuloManufacturado request, ArticuloManufacturado insumoExistente) {
        Set<ImagenArticulo> imagenes = request.getImagenes();
        Set<ImagenArticulo> imagenesEliminadas = new HashSet<>(insumoExistente.getImagenes());
        imagenesEliminadas.removeAll(imagenes);

        for (ImagenArticulo imagen : imagenesEliminadas) {
            imagenArticuloService.deleteImage(publicIdService.obtenerPublicId(imagen.getUrl()), imagen.getId());
        }

        insumoExistente.setImagenes(imagenes);
    }

    private Categoria fetchAndValidateCategoria(ArticuloManufacturado request) {
        if (request.getCategoria() != null) {
            return categoriaRepository.findById(request.getCategoria().getId())
                    .filter(categoria -> !categoria.isEsInsumo())
                    .orElseThrow(() -> new RuntimeException("Categoría no válida"));
        }
        return null;
    }

    @Override
    public void deleteById(Long id) {
        ArticuloManufacturado producto = articuloManufacturadoRepository.getById(id);
        if (producto == null) {
            throw new RuntimeException("Producto no encontrado: { id: " + id + " }");
        }
        articuloManufacturadoRepository.delete(producto);
    }

    @Override
    public Page<ArticuloManufacturado> buscarPorCategoriaYNombre(Pageable pageable, Long idSucursal, Long categoriaId, String nombre) {
        List<Long> subcategoryIds = findAllSubcategoryIds(categoriaId);
        subcategoryIds.add(categoriaId);  // Include the parent category ID
        return articuloManufacturadoRepository.findBySucursalIdAndCategoriaIdInAndDenominacionContainingIgnoreCase(idSucursal, subcategoryIds, nombre, pageable);
    }

    public Page<ArticuloManufacturado> getArticulosByCategoria(Pageable pageable, Long idSucursal, Long categoriaId) {
        List<Long> subcategoryIds = findAllSubcategoryIds(categoriaId);
        subcategoryIds.add(categoriaId);  // Include the parent category ID
        return articuloManufacturadoRepository.findBySucursalIdAndCategoriaIdIn(idSucursal, subcategoryIds, pageable);
    }

    public List<Long> findAllSubcategoryIds(Long categoriaId) {
        List<Long> subcategoryIds = new ArrayList<>();
        findSubcategoryIdsRecursive(categoriaId, subcategoryIds);
        return subcategoryIds;
    }

    private void findSubcategoryIdsRecursive(Long categoriaId, List<Long> subcategoryIds) {
        List<Categoria> subcategories = categoriaRepository.findByCategoriaPadre_Id(categoriaId);
        for (Categoria subcategory : subcategories) {
            subcategoryIds.add(subcategory.getId());
            findSubcategoryIdsRecursive(subcategory.getId(), subcategoryIds);
        }
    }

    @Override
    public Page<ArticuloManufacturado> getArticulosByNombre(Pageable pageable, Long idSucursal, String nombre) {
        return articuloManufacturadoRepository.findBySucursal_IdAndDenominacionContainingIgnoreCase(idSucursal, nombre, pageable);
    }

    @Override
    public Page<ArticuloManufacturado> findBySucursal(Long sucursalId, Pageable pageable) {
        return articuloManufacturadoRepository.findBySucursal_Id(sucursalId, pageable);
    }

    @Override
    public List<ArticuloManufacturado> findBySucursal(Long sucursalId) {
        return articuloManufacturadoRepository.findBySucursal_IdAndBajaFalse(sucursalId);
    }
}
