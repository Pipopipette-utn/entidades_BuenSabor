package com.example.buensaborback.presentation.rest;

import com.example.buensaborback.business.facade.Imp.PedidoFacadeImp;
import com.example.buensaborback.domain.dto.PedidoDtos.PedidoDto;
import com.example.buensaborback.domain.dto.PedidoDtos.PedidoEstadoDto;
import com.example.buensaborback.domain.dto.PedidoDtos.PedidoGetDto;
import com.example.buensaborback.domain.entities.Pedido;
import com.example.buensaborback.presentation.rest.Base.BaseControllerImp;
import com.example.buensaborback.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
@CrossOrigin("*")
public class PedidoController extends BaseControllerImp<Pedido, PedidoDto, PedidoGetDto,Long, PedidoFacadeImp> {
    public PedidoController(PedidoFacadeImp facade) {
        super(facade);
    }

    @Autowired
    PedidoRepository pedidoRepository;

    @PutMapping("/cambiarEstado/{id}")
    public ResponseEntity<PedidoEstadoDto> cambiarEstado(@RequestBody PedidoEstadoDto entity, @PathVariable Long id){
        return ResponseEntity.ok(facade.cambiarEstado(entity, id));
    }

    @GetMapping("/porSucursal/{sucursalId}")
    public ResponseEntity<Page<PedidoGetDto>> findAllBySucursalId(@PathVariable Long sucursalId, Pageable pageable) {
        return  ResponseEntity.ok(facade.findBySucursal(sucursalId, pageable));
    }

    @GetMapping("/rankingComidas")
    public List<Object> getRanking(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha1, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha2, @RequestParam Long sucursalId) {
        return pedidoRepository.findRankingComidasMasPedidas(fecha1, fecha2, sucursalId);
    }

    @GetMapping("/totalRecaudado")
    public Double totalRecaudado(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha1, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha2, @RequestParam Long sucursalId) {
        return pedidoRepository.calcularTotalRecaudado(fecha1, fecha2, sucursalId);
    }

    @GetMapping("/rankingClientes")
    public List<Object> findClientePedidos(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha1, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha2, @RequestParam Long sucursalId) {
        return pedidoRepository.findClientePedidos(fecha1, fecha2, sucursalId);
    }
    @GetMapping("/ganancias")
    public Double calcularGanancia(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha1, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha2, @RequestParam Long sucursalId) {
        return pedidoRepository.calcularGanancia(fecha1, fecha2, sucursalId);
    }
}
