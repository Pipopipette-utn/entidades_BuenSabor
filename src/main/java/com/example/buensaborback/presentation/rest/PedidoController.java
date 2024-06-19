package com.example.buensaborback.presentation.rest;

import com.example.buensaborback.business.facade.Imp.PedidoFacadeImp;
import com.example.buensaborback.domain.dto.PedidoDtos.PedidoDto;
import com.example.buensaborback.domain.dto.PedidoDtos.PedidoEstadoDto;
import com.example.buensaborback.domain.dto.PedidoDtos.PedidoGetDto;
import com.example.buensaborback.domain.entities.Pedido;
import com.example.buensaborback.domain.enums.Estado;
import com.example.buensaborback.presentation.rest.Base.BaseControllerImp;
import com.example.buensaborback.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
@CrossOrigin("*")
public class PedidoController extends BaseControllerImp<Pedido, PedidoDto, PedidoGetDto,Long, PedidoFacadeImp> {
    public PedidoController(PedidoFacadeImp facade) {
        super(facade);
    }

    @PutMapping("/cambiarEstado/{id}")
    public ResponseEntity<PedidoGetDto> cambiarEstado(@RequestBody PedidoEstadoDto entity, @PathVariable Long id) throws Exception{
        return ResponseEntity.ok(facade.cambiarEstado(entity, id));
    }

    @GetMapping("/porSucursal/{sucursalId}")
    public ResponseEntity<Page<PedidoGetDto>> findAllBySucursalId(
            @PathVariable Long sucursalId,
            @RequestParam(required = false) Estado estado,
            Pageable pageable) {
        return ResponseEntity.ok(facade.findBySucursalAndEstado(sucursalId, estado, pageable));
    }

    // REPORTES -------------------------------------------------------------------------------------
    @GetMapping("/rankingComidas")
    public List<List<Object>> getRankingComidasMasPedidas(@RequestParam LocalDate fechaInicio, @RequestParam LocalDate fechaFin, @RequestParam Long sucursalId) throws SQLException {
        return facade.getRankingComidasMasPedidas(fechaInicio, fechaFin, sucursalId);
    }

    @GetMapping("/totalRecaudadoDiario")
    public List<List<Object>> totalRecaudadoDiario(@RequestParam LocalDate fechaInicio, @RequestParam LocalDate fechaFin, @RequestParam Long sucursalId) throws SQLException {
        return facade.getTotalRecaudadoDiario(fechaInicio, fechaFin, sucursalId);
    }

    @GetMapping("/totalRecaudadoMensual")
    public List<List<Object>> totalRecaudadoMensual(@RequestParam LocalDate fechaInicio, @RequestParam LocalDate fechaFin, @RequestParam Long sucursalId) throws SQLException {
        return facade.getTotalRecaudadoMensual(fechaInicio, fechaFin, sucursalId);
    }

    @GetMapping("/rankingClientes")
    public List<List<Object>> getClientePedidos(@RequestParam LocalDate fechaInicio, @RequestParam LocalDate fechaFin, @RequestParam Long sucursalId) throws SQLException {
        return facade.getClientePedidos(fechaInicio, fechaFin, sucursalId);
    }

    @GetMapping("/totalGananciaDiario")
    public List<List<Object>> totalGananciaDiario(@RequestParam LocalDate fechaInicio, @RequestParam LocalDate fechaFin, @RequestParam Long sucursalId) throws SQLException {
        return facade.getTotalGananciaDiario(fechaInicio, fechaFin, sucursalId);
    }

    @GetMapping("/totalGananciaMensual")
    public List<List<Object>> totalGananciaMensual(@RequestParam LocalDate fechaInicio, @RequestParam LocalDate fechaFin, @RequestParam Long sucursalId) throws SQLException {
        return facade.getTotalGananciaMensual(fechaInicio, fechaFin, sucursalId);
    }
}
