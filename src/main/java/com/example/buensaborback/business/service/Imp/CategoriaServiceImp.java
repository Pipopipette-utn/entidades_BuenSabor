package com.example.buensaborback.business.service.Imp;

import com.example.buensaborback.business.mapper.SucursalMapper;
import com.example.buensaborback.business.service.Base.BaseServiceImp;
import com.example.buensaborback.business.service.CategoriaService;
import com.example.buensaborback.business.service.DomicilioService;
import com.example.buensaborback.domain.dto.CategoriaDtos.CategoriaGetDto;
import com.example.buensaborback.domain.dto.SucursalDtos.SucursalShortDto;
import com.example.buensaborback.domain.entities.Categoria;
import com.example.buensaborback.domain.entities.Domicilio;
import com.example.buensaborback.domain.entities.Sucursal;
import com.example.buensaborback.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoriaServiceImp extends BaseServiceImp<Categoria,Long> implements CategoriaService {

    @Autowired
    CategoriaRepository categoriaRepository;

    @Autowired
    SucursalServiceImpl sucursalService;

    @Override
    public void deleteById(Long id) {
        Categoria categoriaExistente = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("La categoría con el ID " + id + " no existe."));

        eliminarSubcategorias(categoriaExistente);

        categoriaRepository.save(categoriaExistente);
    }

    private void eliminarSubcategorias(Categoria categoria){
        baseRepository.delete(categoria);
        if (!categoria.getSubCategorias().isEmpty()){
            for (Categoria subcategoria: categoria.getSubCategorias())
                eliminarSubcategorias(subcategoria);
        }
    }

    @Override
    public Page<Categoria> findByEsInsumoTrue(Pageable pageable) {
        return categoriaRepository.findByEsInsumoTrue(pageable);
    }

    @Override
    public Page<Categoria> findByEsInsumoFalse(Pageable pageable) {
        return categoriaRepository.findByEsInsumoFalse(pageable);
    }

    @Override
    public List<Categoria> getAllParents() {
        List<Categoria> categorias = categoriaRepository.findAll();
        Set<Categoria> filteredCategorias = new HashSet<>();
        for (Categoria categoria: categorias){
            if (categoria.getCategoriaPadre() == null && categoria.isEsParaVender()) {
                boolean exists = filteredCategorias.stream()
                        .anyMatch(existingCategoria -> existingCategoria.getDenominacion().equals(categoria.getDenominacion()));
                if (!exists) {
                    filteredCategorias.add(categoria);
                }
            }
        }

        return new ArrayList<>(filteredCategorias);
    }

    @Override
    public Categoria create(Categoria categoria) {
        Set<Sucursal> sucursales = new HashSet<>();

        // Verificar y asociar sucursales existentes
        if (categoria.getSucursales() != null && !categoria.getSucursales().isEmpty()) {
            for (Sucursal sucursal : categoria.getSucursales()) {
                Sucursal sucursalBd = sucursalService.getById(sucursal.getId());
                if (sucursalBd == null) {
                    throw new RuntimeException("La sucursal con el id " + sucursal.getId() + " no existe.");
                }
                sucursalBd.getCategorias().add(categoria);
                sucursales.add(sucursalBd);
            }
        }

        // Establecer la nueva colección de sucursales en la categoría
        categoria.setSucursales(sucursales);

        // Mapear subcategorías y guardar la categoría
        if (!categoria.getSubCategorias().isEmpty()) {
            mapearSubcategorias(categoria, sucursales);
        }

        return categoriaRepository.save(categoria);
    }

    private void mapearSubcategorias(Categoria categoria, Set<Sucursal> sucursales){
        if (!categoria.getSubCategorias().isEmpty()){
            for(Categoria subcategoria: categoria.getSubCategorias()){
                subcategoria.setCategoriaPadre(categoria);
                subcategoria.setSucursales(sucursales);
                for(Sucursal sucursal: sucursales)
                    sucursal.getCategorias().add(subcategoria);
                mapearSubcategorias(subcategoria, sucursales);
            }
        }
    }

    @Override
    public void deleteInSucursales(Long id, Sucursal sucursalShort) {
        Categoria categoriaExistente = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("La categoría con el ID " + id + " no existe."));

        Sucursal sucursal = sucursalService.getById(sucursalShort.getId());

        // Eliminar la relación entre la sucursal y la categoría existente
        sucursal.getCategorias().remove(categoriaExistente);
        categoriaExistente.getSucursales().remove(sucursal);

        categoriaRepository.save(categoriaExistente);
    }

    @Override
    public Categoria update(Categoria newCategoria, Long id) {
        Categoria categoriaExistente = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("La categoría con el ID " + id + " no existe."));

        // Actualizar los detalles básicos de la categoría
        categoriaExistente.setDenominacion(newCategoria.getDenominacion());
        categoriaExistente.setEsInsumo(newCategoria.isEsInsumo());
        categoriaExistente.setEsParaVender(newCategoria.isEsParaVender());

        // Actualizar las sucursales asociadas
        Set<Sucursal> newSucursales = new HashSet<>();
        if (newCategoria.getSucursales() != null && !newCategoria.getSucursales().isEmpty()) {
            for (Sucursal sucursal : newCategoria.getSucursales()) {
                Sucursal sucursalBd = sucursalService.getById(sucursal.getId());
                if (sucursalBd == null) {
                    throw new RuntimeException("La sucursal con el id " + sucursal.getId() + " no existe.");
                }
                boolean categoriaExists = sucursalBd.getCategorias().stream()
                        .anyMatch(cat -> cat.getId() != null && cat.getId().equals(newCategoria.getId()));

                if (!categoriaExists) {
                    sucursalBd.getCategorias().add(newCategoria);
                }
                newSucursales.add(sucursalBd);
            }
        }

        // Actualizar la relación de sucursales de la categoría existente
        categoriaExistente.setSucursales(newSucursales);

        // Manejar subcategorías
        actualizarSubcategorias(categoriaExistente, newCategoria, newSucursales, newCategoria.isEsInsumo(), newCategoria.isEsParaVender());

        return categoriaRepository.save(categoriaExistente);
    }

    private void actualizarSubcategorias(Categoria categoriaExistente, Categoria newCategoria, Set<Sucursal> sucursales, boolean esInsumo, boolean esParaVender){
        if (newCategoria.getSubCategorias() != null && !newCategoria.getSubCategorias().isEmpty()){
            for(Categoria subcategoriaNueva: newCategoria.getSubCategorias()){
                Optional<Categoria> subcategoriaExistenteOpt = categoriaExistente.getSubCategorias().stream()
                        .filter(sc -> sc.getId().equals(subcategoriaNueva.getId()))
                        .findFirst();

                if (subcategoriaExistenteOpt.isPresent()) {
                    Categoria subcategoriaExistente = subcategoriaExistenteOpt.get();
                    subcategoriaExistente.setDenominacion(subcategoriaNueva.getDenominacion());
                    subcategoriaExistente.setEsInsumo(esInsumo);  // Propagar valor de newCategoria
                    subcategoriaExistente.setEsParaVender(esParaVender);  // Propagar valor de newCategoria
                    subcategoriaExistente.setSucursales(sucursales);
                    for (Sucursal sucursal : sucursales) {
                        boolean categoriaExists = sucursal.getCategorias().stream()
                                .anyMatch(cat -> cat.getId() != null && cat.getId().equals(subcategoriaExistente.getId()));

                        if (!categoriaExists) {
                            sucursal.getCategorias().add(subcategoriaExistente);
                        }
                    }
                    actualizarSubcategorias(subcategoriaExistente, subcategoriaNueva, sucursales, esInsumo, esParaVender);
                } else {
                    subcategoriaNueva.setCategoriaPadre(categoriaExistente);
                    subcategoriaNueva.setSucursales(sucursales);
                    subcategoriaNueva.setEsInsumo(esInsumo);  // Propagar valor de newCategoria
                    subcategoriaNueva.setEsParaVender(esParaVender);  // Propagar valor de newCategoria
                    categoriaExistente.getSubCategorias().add(subcategoriaNueva);

                    // Guardar la nueva subcategoría antes de agregarla a las sucursales
                    Categoria savedSubcategoriaNueva = categoriaRepository.save(subcategoriaNueva);

                    for (Sucursal sucursal : sucursales) {
                        sucursal.getCategorias().add(savedSubcategoriaNueva);
                    }
                    actualizarSubcategorias(savedSubcategoriaNueva, subcategoriaNueva, sucursales, esInsumo, esParaVender);
                }
            }
        }
    }


    /*
    private void actualizarSubcategorias(Categoria categoriaExistente, Categoria newCategoria, Set<Sucursal> sucursales) {
        // Mapear subcategorías de la nueva categoría
        if (newCategoria.getSubCategorias() != null && !newCategoria.getSubCategorias().isEmpty()) {
            for (Categoria subcategoriaNueva : newCategoria.getSubCategorias()) {
                Optional<Categoria> subcategoriaExistenteOpt = categoriaExistente.getSubCategorias().stream()
                        .filter(sc -> sc.getId().equals(subcategoriaNueva.getId()))
                        .findFirst();

                if (subcategoriaExistenteOpt.isPresent()) {
                    Categoria subcategoriaExistente = subcategoriaExistenteOpt.get();
                    subcategoriaExistente.setDenominacion(subcategoriaNueva.getDenominacion());
                    subcategoriaExistente.setEsInsumo(subcategoriaNueva.isEsInsumo());
                    subcategoriaExistente.setSucursales(sucursales);

                    actualizarSubcategorias(subcategoriaExistente, subcategoriaNueva, sucursales);
                } else {
                    subcategoriaNueva.setCategoriaPadre(categoriaExistente);
                    subcategoriaNueva.setSucursales(sucursales);
                    categoriaExistente.getSubCategorias().add(subcategoriaNueva);

                    for (Sucursal sucursal : sucursales) {
                        sucursal.getCategorias().add(subcategoriaNueva);
                    }
                }
            }
        }

    }*/




}
