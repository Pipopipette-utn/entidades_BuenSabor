package com.example.buensaborback.business.facade.Imp;

import com.example.buensaborback.business.facade.Base.BaseFacadeImp;
import com.example.buensaborback.business.facade.PedidoFacade;
import com.example.buensaborback.business.mapper.BaseMapper;
import com.example.buensaborback.business.mapper.PedidoMapper;
import com.example.buensaborback.business.service.Base.BaseService;
import com.example.buensaborback.business.service.PedidoService;
import com.example.buensaborback.domain.dto.ArticuloInsumoDtos.ArticuloInsumoDto;
import com.example.buensaborback.domain.dto.PedidoDtos.PedidoDto;
import com.example.buensaborback.domain.dto.PedidoDtos.PedidoEstadoDto;
import com.example.buensaborback.domain.dto.PedidoDtos.PedidoGetDto;
import com.example.buensaborback.domain.entities.ArticuloInsumo;
import com.example.buensaborback.domain.entities.Pedido;
import com.example.buensaborback.domain.enums.Estado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoFacadeImp extends BaseFacadeImp<Pedido, PedidoDto, PedidoGetDto, Long> implements PedidoFacade {

    @Autowired
    PedidoMapper pedidoMapper;
    @Autowired
    PedidoService pedidoService;

    public PedidoFacadeImp(BaseService<Pedido, Long> baseService, BaseMapper<Pedido, PedidoDto, PedidoGetDto> baseMapper) {
        super(baseService, baseMapper);
    }

    public PedidoGetDto cambiarEstado(PedidoEstadoDto request, Long id) throws Exception{
        var pedidoEntidad = pedidoMapper.toEntityEstado(request);
        var pedidoUpdate = pedidoService.cambiarEstado(pedidoEntidad, id);
        return pedidoMapper.toDTO(pedidoUpdate);
    }

    public Page<PedidoGetDto> findBySucursalAndEstado(Long sucursalId, Estado estado, Pageable pageable) {
        Page<Pedido> pedidosFiltrados = pedidoService.findBySucursalAndEstado(sucursalId, estado, pageable.getPageNumber(), pageable.getPageSize());
        // Mapea las entidades a DTOs
        List<PedidoGetDto> dtos = pedidosFiltrados.getContent().stream()
                .map(pedidoMapper::toDTO)
                .collect(Collectors.toList());
        // Devuelve una página de DTOs
        return new PageImpl<>(dtos, pageable, pedidosFiltrados.getTotalElements());
    }
}
