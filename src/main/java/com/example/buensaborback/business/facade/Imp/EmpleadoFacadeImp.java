package com.example.buensaborback.business.facade.Imp;

import com.example.buensaborback.business.facade.Base.BaseFacadeImp;
import com.example.buensaborback.business.facade.EmpleadoFacade;
import com.example.buensaborback.business.mapper.BaseMapper;
import com.example.buensaborback.business.mapper.EmpleadoMapper;
import com.example.buensaborback.business.service.Base.BaseService;
import com.example.buensaborback.business.service.EmpleadoService;
import com.example.buensaborback.domain.dto.EmpleadoDtos.EmpleadoPostDto;
import com.example.buensaborback.domain.dto.PromocionDtos.PromocionDto;
import com.example.buensaborback.domain.entities.Empleado;
import com.example.buensaborback.domain.entities.Promocion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmpleadoFacadeImp extends BaseFacadeImp<Empleado, EmpleadoPostDto, EmpleadoPostDto, Long> implements EmpleadoFacade {
    @Autowired
    EmpleadoService empleadoService;

    @Autowired
    EmpleadoMapper empleadoMapper;

    public EmpleadoFacadeImp(BaseService<Empleado, Long> baseService, BaseMapper<Empleado, EmpleadoPostDto, EmpleadoPostDto> baseMapper) {
        super(baseService, baseMapper);
    }

    public EmpleadoPostDto findByEmail(String email) throws Exception {
        // Busca una entidad por id
        var entity = empleadoService.findByEmail(email);
        // convierte la entidad a DTO
        return empleadoMapper.toDTO(entity);
    }

    public Page<EmpleadoPostDto> findBySucursal(Long sucursalId, Pageable pageable) {
        Page<Empleado> articulosFiltrados = empleadoService.findBySucursal_Id(sucursalId, pageable);
        // Mapea las entidades a DTOs
        List<EmpleadoPostDto> dtos = articulosFiltrados.getContent().stream()
                .map(empleadoMapper::toDTO)
                .collect(Collectors.toList());
        // Devuelve una página de DTOs
        return new PageImpl<>(dtos, pageable, articulosFiltrados.getTotalElements());
    }
}
