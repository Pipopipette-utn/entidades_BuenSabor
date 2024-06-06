package com.example.buensaborback.business.service.Imp;

import com.example.buensaborback.business.service.ArticuloInsumoService;
import com.example.buensaborback.business.service.Base.BaseServiceImp;
import com.example.buensaborback.domain.entities.*;
import com.example.buensaborback.repositories.*;
import com.example.buensaborback.utils.PublicIdService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
    SucursalRepository sucursalRepository;

    @Autowired
    PublicIdService publicIdService;


    @Transactional
    public List<ArticuloInsumo> create(ArticuloInsumo request, Set<Sucursal> sucursales) {
        List<ArticuloInsumo> articulosCreados = new ArrayList<>();

        for (Sucursal sucursal: sucursales) {
            // Crear un nuevo objeto ArticuloInsumo para cada sucursal
            ArticuloInsumo nuevoArticulo = new ArticuloInsumo();
            nuevoArticulo.setDenominacion(request.getDenominacion());
            nuevoArticulo.setPrecioVenta(request.getPrecioVenta());
            nuevoArticulo.setPrecioVenta(request.getPrecioVenta());
            nuevoArticulo.setUnidadMedida(request.getUnidadMedida());
            nuevoArticulo.setPrecioCompra(request.getPrecioCompra());
            nuevoArticulo.setEsParaElaborar(request.getEsParaElaborar());
            nuevoArticulo.setStockActual(request.getStockActual());
            nuevoArticulo.setStockMaximo(request.getStockMaximo());
            nuevoArticulo.setStockMinimo(request.getStockMinimo());

            Sucursal sucursalBd = sucursalRepository.getById(sucursal.getId());
            if (sucursalBd == null) {
                throw new RuntimeException("La sucursal con id " + sucursal.getId() + " no se ha encontrado");
            }

            if (request.getCategoria() != null) {
                Categoria categoria = categoriaRepository.getById(request.getCategoria().getId());
                // Verificar si la sucursal contiene la categoría por ID
                boolean categoriaExisteEnSucursal = sucursalBd.getCategorias().stream()
                        .anyMatch(cat -> cat.getId().equals(categoria.getId()));

               if (!categoriaExisteEnSucursal) {
                    throw new RuntimeException("La categoría " + categoria.getDenominacion() + " no existe en la sucursal " + sucursal.getNombre());
                }

                if (categoria == null ) {
                    throw new RuntimeException("La categoría con id: " + request.getCategoria().getId() + " no existe");
                }
                if (!categoria.isEsInsumo() && request.getEsParaElaborar()) {
                    throw new RuntimeException("La categoría con id: " + request.getCategoria().getId() + " no pertenece a una categoría de insumos");
                }

                nuevoArticulo.setCategoria(categoria);
            }

            nuevoArticulo.setSucursal(sucursalBd);
            // Guardar el artículo en la base de datos
            ArticuloInsumo articuloGuardado = articuloInsumoRepository.save(nuevoArticulo);
            articulosCreados.add(articuloGuardado);
            System.out.println(articuloGuardado.getDenominacion());
        }
        return articulosCreados;
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
            if (!categoria.isEsInsumo() && request.getEsParaElaborar()) {
                throw new RuntimeException("La categoría con id: " + request.getCategoria().getId() + " no pertenece a una categoría de insumos");
            }

            request.setCategoria(categoria);
        }

        return articuloInsumoRepository.save(request);
    }

    @Override
    public void deleteById(Long id) {
        ArticuloInsumo insumo = articuloInsumoRepository.getById(id);
        if (insumo == null) {
            throw new RuntimeException("Insumo no encontrado: { id: " + id + " }");
        }
        List<ArticuloManufacturadoDetalle> insumoEsUtilizado = articuloManufacturadoDetalleRepository.getByArticulo(insumo);
        if (!insumoEsUtilizado.isEmpty()) {
            throw new RuntimeException("No se puede eliminar el articulo porque está presente en un detalle");
        }
        articuloInsumoRepository.delete(insumo);
    }

    @Override
    public Page<ArticuloInsumo> findByEsParaElaborarTrue(Pageable pageable) {
        return articuloInsumoRepository.findByEsParaElaborarTrue(pageable);
    }

    @Override
    public Page<ArticuloInsumo> findByEsParaElaborarFalse(Pageable pageable) {
        return articuloInsumoRepository.findByEsParaElaborarFalse(pageable);
    }

    @Override
    public Page<ArticuloInsumo> buscarPorCategoriaYNombre(Pageable pageable, Long idSucursal, Long categoriaId, String nombre) {
        return articuloInsumoRepository.findBySucursal_IdAndCategoria_IdAndDenominacionContainingIgnoreCase(idSucursal, categoriaId, nombre, pageable);
    }

    @Override
    public Page<ArticuloInsumo> getArticulosByCategoria(Pageable pageable, Long idSucursal, Long categoriaId) {
        return articuloInsumoRepository.findBySucursal_IdAndCategoria_Id(idSucursal, categoriaId, pageable);
    }

    @Override
    public Page<ArticuloInsumo> findBySucursal(Long sucursalId, Pageable pageable) {
        return articuloInsumoRepository.findBySucursal_Id(sucursalId, pageable);
    }

    @Override
    public Page<ArticuloInsumo> getArticulosByNombre(Pageable pageable, Long idSucursal, String nombre) {
        return articuloInsumoRepository.findBySucursal_IdAndDenominacionContainingIgnoreCase(idSucursal, nombre, pageable);
    }

    @Override
    public List<ArticuloInsumo> findBySucursal(Long sucursalId ) {
        return articuloInsumoRepository.findBySucursal_IdAndBajaFalse(sucursalId);
    }
}
