package com.example.buensaborback.business.facade.Imp;

import com.example.buensaborback.business.facade.Base.BaseFacadeImp;
import com.example.buensaborback.business.facade.EmpleadoFacade;
import com.example.buensaborback.business.mapper.BaseMapper;
import com.example.buensaborback.business.mapper.EmpleadoMapper;
import com.example.buensaborback.business.service.Base.BaseService;
import com.example.buensaborback.business.service.EmpleadoService;
import com.example.buensaborback.domain.dto.EmpleadoDtos.EmpleadoPostDto;
import com.example.buensaborback.domain.entities.Empleado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
