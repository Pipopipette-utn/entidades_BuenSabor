package com.example.buensaborback.business.service.Imp;

import com.example.buensaborback.business.mapper.SucursalMapper;
import com.example.buensaborback.business.mapper.SucursalMapperImpl;
import com.example.buensaborback.business.service.Base.BaseServiceImp;
import com.example.buensaborback.business.service.CategoriaService;
import com.example.buensaborback.business.service.DomicilioService;
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
    SucursalMapperImpl mapper;

    @Autowired
    CategoriaRepository categoriaRepository;

    @Autowired
    SucursalServiceImpl sucursalService;

    @Override
    public Page<Categoria> findByEsInsumoTrue(Pageable pageable) {
        return categoriaRepository.findByEsInsumoTrue(pageable);
    }

    @Override
    public Page<Categoria> findByEsInsumoFalse(Pageable pageable) {
        return categoriaRepository.findByEsInsumoFalse(pageable);
    }


    @Override
    public Categoria create(Categoria categoria) {
        Set<Sucursal> sucursales = new HashSet<>();
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
        System.out.println(categoria.isEsInsumo());

        // Establecer la nueva colección de sucursales en la categoría
        categoria.setSucursales(sucursales);

        // Mapear subcategorías y guardar la categoría
        if (!categoria.getSubCategorias().isEmpty()) {
            mapearSubcategorias(categoria, sucursales);
        }

        return categoriaRepository.save(categoria);
    }

    public void deleteInSucursales(Long id, SucursalShortDto sucursalShort) {
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
        // Obtener la categoría existente por su ID
        Categoria categoriaExistente = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("La categoría con el ID " + id + " no existe."));

        // Actualizar los campos relevantes de la categoría existente
        categoriaExistente.setDenominacion(newCategoria.getDenominacion());
        categoriaExistente.setEsInsumo(newCategoria.isEsInsumo());

        Set<Sucursal> newSucursales = new HashSet<>();
        for(Sucursal sucursal: newCategoria.getSucursales()){
            Sucursal sucursalBD = sucursalService.getById(sucursal.getId());
            newSucursales.add(sucursalBD);
        }

        eliminarSubcategorias(categoriaExistente, newCategoria, newSucursales);

        if (!newCategoria.getSubCategorias().isEmpty()) {
            mapearSubcategorias(newCategoria, newSucursales);
        }

        // Guardar la categoría actualizada
        return categoriaRepository.save(categoriaExistente);
    }

    private void mapearSubcategorias(Categoria categoria, Set<Sucursal> sucursales){
        if (!categoria.getSubCategorias().isEmpty()){
            for(Categoria subcategoria: categoria.getSubCategorias()){
                subcategoria.setCategoriaPadre(categoria);
                subcategoria.setSucursales(sucursales);
                sucursales.forEach(sucursal -> {
                    boolean categoriaExists = false;
                    if (categoria.getId() != null){
                        categoriaExists = sucursal.getCategorias().stream()
                                .anyMatch(cat -> cat.getId().equals(subcategoria.getId()));
                    }
                    if (!categoriaExists) {
                        sucursal.getCategorias().add(subcategoria);
                    }
                });
                mapearSubcategorias(subcategoria, sucursales);
            }
        }
    }

    private void eliminarSubcategorias(Categoria categoria, Categoria newCategoria, Set<Sucursal> sucursales) {
        // Obtener las subcategorías eliminadas
        Set<Categoria> subcategoriasEliminadas = new HashSet<>(categoria.getSubCategorias());
        for (Categoria subcategoria : categoria.getSubCategorias()) {
            boolean existeEnNewCategoria = newCategoria.getSubCategorias().stream()
                    .anyMatch(s -> Objects.equals(subcategoria.getId(), s.getId()));

            if (!existeEnNewCategoria) {
                // Eliminar la relación entre sucursales y subcategorías no coincidentes
                for (Sucursal sucursal : sucursales) {
                    sucursal.getCategorias().remove(subcategoria);
                }
                subcategoriasEliminadas.remove(subcategoria);
            }
        }
        categoria.setSubCategorias(subcategoriasEliminadas);

        //Hago lo mismo en las subcategorias
        for (Categoria subcategoria : newCategoria.getSubCategorias()) {
            if (subcategoria.getId() != null) {
                Categoria categoriaExistente = categoriaRepository.findById(subcategoria.getId())
                        .orElseThrow(() -> new RuntimeException("La categoría con el ID " + subcategoria.getId() + " no existe."));
                eliminarSubcategorias(subcategoria, categoriaExistente, sucursales);
            }
        }
    }

}
