package com.example.buensaborback.business.facade.Imp;

import com.example.buensaborback.business.facade.Base.BaseFacadeImp;
import com.example.buensaborback.business.facade.ClienteFacade;
import com.example.buensaborback.business.facade.EmpresaFacade;
import com.example.buensaborback.business.mapper.BaseMapper;
import com.example.buensaborback.business.mapper.ClienteMapper;
import com.example.buensaborback.business.mapper.EmpresaMapper;
import com.example.buensaborback.business.service.Base.BaseService;
import com.example.buensaborback.business.service.ClienteService;
import com.example.buensaborback.business.service.EmpresaService;
import com.example.buensaborback.domain.dto.ClienteDto.ClienteDto;
import com.example.buensaborback.domain.dto.ClienteDto.ClienteLoginDto;
import com.example.buensaborback.domain.dto.EmpresaDto;
import com.example.buensaborback.domain.dto.EmpresaLargeDto;
import com.example.buensaborback.domain.entities.Cliente;
import com.example.buensaborback.domain.entities.Empresa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteFacadeImpl extends BaseFacadeImp<Cliente, ClienteDto, ClienteDto, Long> implements ClienteFacade {

    @Autowired
    ClienteMapper clienteMapper;

    @Autowired
    ClienteService clienteService;

    public ClienteFacadeImpl(BaseService<Cliente, Long> baseService, BaseMapper<Cliente, ClienteDto, ClienteDto> baseMapper) {
        super(baseService, baseMapper);
    }

    public ClienteDto login(ClienteLoginDto request, String email) throws Exception{
        // Convierte a entidad
        Cliente entityToCreate = clienteMapper.entityLogin(request);
        // Graba una entidad
        var entityCreated = clienteService.login(entityToCreate, email);
        // convierte a Dto para devolver
        return clienteMapper.toDTO(entityCreated);
    }

}
