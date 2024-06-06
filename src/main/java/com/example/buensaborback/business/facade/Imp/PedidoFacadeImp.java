package com.example.buensaborback.business.facade.Imp;

import com.example.buensaborback.business.facade.Base.BaseFacadeImp;
import com.example.buensaborback.business.facade.PedidoFacade;
import com.example.buensaborback.business.mapper.BaseMapper;
import com.example.buensaborback.business.mapper.PedidoMapper;
import com.example.buensaborback.business.service.Base.BaseService;
import com.example.buensaborback.business.service.PedidoService;
import com.example.buensaborback.domain.dto.PedidoDtos.PedidoDto;
import com.example.buensaborback.domain.dto.PedidoDtos.PedidoEstadoDto;
import com.example.buensaborback.domain.dto.PedidoDtos.PedidoGetDto;
import com.example.buensaborback.domain.entities.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoFacadeImp extends BaseFacadeImp<Pedido, PedidoDto, PedidoGetDto, Long> implements PedidoFacade {

    @Autowired
    PedidoMapper pedidoMapper;
    @Autowired
    PedidoService pedidoService;

    public PedidoFacadeImp(BaseService<Pedido, Long> baseService, BaseMapper<Pedido, PedidoDto, PedidoGetDto> baseMapper) {
        super(baseService, baseMapper);
    }

    public PedidoEstadoDto cambiarEstado(PedidoEstadoDto request, Long id){
        var pedidoEntidad = pedidoMapper.toEntityEstado(request);
        var pedidoUpdate = pedidoService.cambiarEstado(pedidoEntidad, id);
        return pedidoMapper.toDtoEstado(pedidoUpdate);
    }
}
