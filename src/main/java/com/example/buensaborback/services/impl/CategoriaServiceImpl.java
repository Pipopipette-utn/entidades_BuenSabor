package com.example.buensaborback.services.impl;

import com.example.buensaborback.services.ICategoriaService;
import com.example.buensaborback.entities.Categoria;
import com.example.buensaborback.repositories.BaseRepository;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CategoriaServiceImpl extends BaseServiceImpl<Categoria, Long> implements ICategoriaService {
    public CategoriaServiceImpl(BaseRepository<Categoria, Long> baseRepository) {
        super(baseRepository);
    }

    @Override
    @Transactional
    public Categoria save(Categoria categoria) throws Exception {
        try{
            if (categoria.getSubCategorias() != null) {
                for (Categoria subCategoria : categoria.getSubCategorias()) {
                    subCategoria.setCategoriaPadre(categoria);
                    this.save(subCategoria);
                }
            }
            categoria = baseRepository.save(categoria);
            return categoria;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
