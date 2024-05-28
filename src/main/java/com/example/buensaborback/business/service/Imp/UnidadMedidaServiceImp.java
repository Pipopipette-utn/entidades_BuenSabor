package com.example.buensaborback.business.service.Imp;

import com.example.buensaborback.business.service.Base.BaseServiceImp;
import com.example.buensaborback.business.service.UnidadMedidaService;
import com.example.buensaborback.domain.entities.Articulo;
import com.example.buensaborback.domain.entities.UnidadMedida;
import com.example.buensaborback.repositories.ArticuloRepository;
import com.example.buensaborback.repositories.UnidadMedidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnidadMedidaServiceImp extends BaseServiceImp<UnidadMedida,Long> implements UnidadMedidaService {

    @Autowired
    UnidadMedidaRepository unidadMedidaRepository;

    @Autowired
    ArticuloRepository articuloRepository;

    @Override
    public void deleteById(Long id) {
        UnidadMedida unidadMedida = unidadMedidaRepository.getById(id);
        if (unidadMedida == null) {
            throw new RuntimeException("La unidad de medida con id " + id + " no se ha encontrado");
        }

        List<Articulo> unidadMedidaEsUtilizado = articuloRepository.getByUnidadMedida(unidadMedida);
        if (!unidadMedidaEsUtilizado.isEmpty()) {
            throw new RuntimeException("No se puede eliminar la unidad de medida porque está presente en un artículo");
        }

        unidadMedidaRepository.delete(unidadMedida);
    }
}
