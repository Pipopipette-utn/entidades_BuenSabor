package com.example.buensaborback.business.facade.Imp;

import com.example.buensaborback.business.facade.Base.BaseFacadeImp;
import com.example.buensaborback.business.facade.ClienteFacade;
import com.example.buensaborback.business.mapper.BaseMapper;
import com.example.buensaborback.business.mapper.ClienteMapper;
import com.example.buensaborback.business.mapper.PedidoMapper;
import com.example.buensaborback.business.service.Base.BaseService;
import com.example.buensaborback.business.service.ClienteService;
import com.example.buensaborback.domain.dto.ClienteDto.ClienteDto;
import com.example.buensaborback.domain.dto.ClienteDto.ClienteLoginDto;
import com.example.buensaborback.domain.dto.PedidoDtos.PedidoGetClienteDto;
import com.example.buensaborback.domain.entities.Cliente;
import com.example.buensaborback.domain.entities.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ClienteFacadeImpl extends BaseFacadeImp<Cliente, ClienteDto, ClienteDto, Long> implements ClienteFacade {

    @Autowired
    ClienteMapper clienteMapper;

    @Autowired
    PedidoMapper pedidoMapper;

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

    public void removeDomicilio(Long id, Long idDomicilio) throws Exception {
        clienteService.removeDomicilio(id, idDomicilio);
    }

    public Set<PedidoGetClienteDto> getAllPedidos(Long clienteId) throws Exception {
        Set<PedidoGetClienteDto> dtos = pedidoMapper.toDtoPedidoCliente(clienteService.getAllPedidos(clienteId));
        return dtos;
    }
}
