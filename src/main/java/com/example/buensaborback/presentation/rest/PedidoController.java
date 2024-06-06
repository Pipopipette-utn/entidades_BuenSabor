package com.example.buensaborback.presentation.rest;

import com.example.buensaborback.business.facade.Imp.PedidoFacadeImp;
import com.example.buensaborback.domain.dto.ArticuloInsumoDtos.ArticuloInsumoDto;
import com.example.buensaborback.domain.dto.PedidoDtos.PedidoDto;
import com.example.buensaborback.domain.dto.PedidoDtos.PedidoEstadoDto;
import com.example.buensaborback.domain.dto.PedidoDtos.PedidoGetDto;
import com.example.buensaborback.domain.entities.Pedido;
import com.example.buensaborback.presentation.rest.Base.BaseControllerImp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos")
@CrossOrigin("*")
public class PedidoController extends BaseControllerImp<Pedido, PedidoDto, PedidoGetDto,Long, PedidoFacadeImp> {
    public PedidoController(PedidoFacadeImp facade) {
        super(facade);
    }

    @PutMapping("/cambiarEstado/{id}")
    public ResponseEntity<PedidoEstadoDto> cambiarEstado(@RequestBody PedidoEstadoDto entity, @PathVariable Long id){
        return ResponseEntity.ok(facade.cambiarEstado(entity, id));
    }

    @GetMapping("/porSucursal/{sucursalId}")
    public ResponseEntity<Page<PedidoGetDto>> findAllBySucursalId(@PathVariable Long sucursalId, Pageable pageable) {
        return  ResponseEntity.ok(facade.findBySucursal(sucursalId, pageable));
    }
}
