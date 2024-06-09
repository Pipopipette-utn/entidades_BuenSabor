package com.example.buensaborback.business.facade.Imp;

import com.example.buensaborback.business.facade.Base.BaseFacadeImp;
import com.example.buensaborback.business.facade.EmpleadoFacade;
import com.example.buensaborback.business.mapper.BaseMapper;
import com.example.buensaborback.business.service.Base.BaseService;
import com.example.buensaborback.domain.dto.EmpleadoDtos.EmpleadoPostDto;
import com.example.buensaborback.domain.entities.Empleado;
import org.springframework.stereotype.Service;

@Service
public class EmpleadoFacadeImp extends BaseFacadeImp<Empleado, EmpleadoPostDto, EmpleadoPostDto, Long> implements EmpleadoFacade {
    public EmpleadoFacadeImp(BaseService<Empleado, Long> baseService, BaseMapper<Empleado, EmpleadoPostDto, EmpleadoPostDto> baseMapper) {
        super(baseService, baseMapper);
    }
}
