package com.example.buensaborback.business.service.Imp;

import com.example.buensaborback.business.service.Base.BaseServiceImp;
import com.example.buensaborback.business.service.CategoriaService;
import com.example.buensaborback.business.service.DomicilioService;
import com.example.buensaborback.domain.entities.Categoria;
import com.example.buensaborback.domain.entities.Domicilio;
import com.example.buensaborback.domain.entities.Sucursal;
import com.example.buensaborback.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CategoriaServiceImp extends BaseServiceImp<Categoria,Long> implements CategoriaService {

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
        mapearSubcategorias(categoria);
        Set<Sucursal> sucursalesNuevas = new HashSet<>();

        if (categoria.getSucursales() != null && !categoria.getSucursales().isEmpty()) {
            for (Sucursal sucursal : categoria.getSucursales()) {
                Sucursal sucursalBd = sucursalService.getById(sucursal.getId());
                if (sucursalBd == null) {
                    throw new RuntimeException("La sucursal con el id " + sucursal.getId() + " no existe.");
                }
                sucursalBd.getCategorias().add(categoria);
                sucursalesNuevas.add(sucursalBd);
            }
        }


        // Establecer la nueva colección de sucursales en la categoría
        categoria.setSucursales(sucursalesNuevas);

        // Mapear subcategorías y guardar la categoría
        mapearSubcategorias(categoria);
        return categoriaRepository.save(categoria);
    }


    private void mapearSubcategorias(Categoria categoria){
        if (!categoria.getSubCategorias().isEmpty()){
            for(Categoria subcategoria: categoria.getSubCategorias()){
                subcategoria.setCategoriaPadre(categoria);
                mapearSubcategorias(subcategoria);
            }
        }
    }

}
