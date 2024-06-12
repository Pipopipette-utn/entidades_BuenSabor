package com.example.buensaborback.business.facade.Imp;

import com.example.buensaborback.business.facade.Base.BaseFacadeImp;
import com.example.buensaborback.business.facade.EmpleadoFacade;
import com.example.buensaborback.business.facade.UsuarioFacade;
import com.example.buensaborback.business.mapper.BaseMapper;
import com.example.buensaborback.business.mapper.EmpleadoMapper;
import com.example.buensaborback.business.mapper.UsuarioMapper;
import com.example.buensaborback.business.service.Base.BaseService;
import com.example.buensaborback.business.service.EmpleadoService;
import com.example.buensaborback.business.service.UsuarioService;
import com.example.buensaborback.domain.dto.EmpleadoDtos.EmpleadoPostDto;
import com.example.buensaborback.domain.dto.UsuarioDto;
import com.example.buensaborback.domain.entities.Empleado;
import com.example.buensaborback.domain.entities.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioFacadeImp extends BaseFacadeImp<Usuario, UsuarioDto, UsuarioDto, Long> implements UsuarioFacade {
    @Autowired
    UsuarioService usuarioService;

    @Autowired
    UsuarioMapper usuarioMapper;

    public UsuarioFacadeImp(BaseService<Usuario, Long> baseService, BaseMapper<Usuario, UsuarioDto, UsuarioDto> baseMapper) {
        super(baseService, baseMapper);
    }

    public UsuarioDto findByEmail(String email) throws Exception {
        // Busca una entidad por id
        var entity = usuarioService.findByEmail(email);
        // convierte la entidad a DTO
        return usuarioMapper.toDTO(entity);
    }

}
