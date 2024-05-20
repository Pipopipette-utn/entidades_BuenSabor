package com.example.buensaborback.business.facade.Imp;

import com.example.buensaborback.business.facade.ImagenArticuloFacade;
import com.example.buensaborback.business.facade.Base.BaseFacadeImp;
import com.example.buensaborback.business.mapper.BaseMapper;
import com.example.buensaborback.business.mapper.ImagenArticuloMapper;
import com.example.buensaborback.business.service.Base.BaseService;
import com.example.buensaborback.business.service.ImagenArticuloService;
import com.example.buensaborback.domain.dto.ImagenArticuloDto;
import com.example.buensaborback.domain.entities.ImagenArticulo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Service
public class ImagenArticuloFacadeImp extends BaseFacadeImp<ImagenArticulo, ImagenArticuloDto, ImagenArticuloDto, Long> implements ImagenArticuloFacade {

    @Autowired
    private ImagenArticuloService imagenArticuloService;

    @Autowired
    private ImagenArticuloMapper imagenArticuloMapper;

    public ImagenArticuloFacadeImp(BaseService<ImagenArticulo, Long> baseService, BaseMapper<ImagenArticulo, ImagenArticuloDto, ImagenArticuloDto> baseMapper) {
        super(baseService, baseMapper);
    }

    public List<ImagenArticuloDto> getAllImages() {
        // Trae todas las entidades de ImagenArticulo
        List<ImagenArticulo> entities = imagenArticuloService.getAllImages();

        // Mapea las entidades a DTOs
        List<ImagenArticuloDto> dtos = entities.stream()
                .map(imagenArticuloMapper::toDTO)
                .collect(Collectors.toList());

        // Convierte DTOs a la estructura esperada en la respuesta
        List<Map<String, Object>> response = dtos.stream()
                .map(dto -> Map.of(
                        "id", dto.getId(),
                        "name", dto.getName(),
                        "url", dto.getUrl()
                ))
                .collect(Collectors.toList());

        // Devuelve la lista de mapas
        return response;
    }
}

