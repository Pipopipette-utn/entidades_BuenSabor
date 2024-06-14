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

    @Autowired
    PedidoRepository pedidoRepository;

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

    @GetMapping("/rankingComidas")
    public List<List<Object>> getRankingComidasMasPedidas(@RequestParam Date fechaInicio, @RequestParam Date fechaFin, @RequestParam Long sucursalId) throws SQLException {
        List<List<Object>> data = new ArrayList<>();
        // Agregar encabezados de las columnas
        data.add(Arrays.asList("ID Articulo", "Denominacion", "Veces Vendido"));

        List<Object[]> results = pedidoRepository.findRankingComidasMasPedidas(fechaInicio, fechaFin, sucursalId);

        for (Object[] result : results) {
            Long idArticulo = (Long) result[0];
            String denominacion = (String) result[1];
            Long vecesVendido = (Long) result[2];
            data.add(Arrays.asList(idArticulo, denominacion, vecesVendido));
        }

        return data;
    }

    @GetMapping("/totalRecaudado")
    public Double totalRecaudado(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha1, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha2, @RequestParam Long sucursalId) {
        return pedidoRepository.calcularTotalRecaudado(fecha1, fecha2, sucursalId);
    }

    @GetMapping("/rankingClientes")
    public List<List<Object>> getClientePedidos(@RequestParam Date fechaInicio, @RequestParam Date fechaFin, @RequestParam Long sucursalId) throws SQLException {
        List<List<Object>> data = new ArrayList<>();
        // Agregar encabezados de las columnas
        data.add(Arrays.asList("Cliente ID", "Username", "Total Pedidos"));

        List<Object[]> results = pedidoRepository.findClientePedidos(fechaInicio, fechaFin, sucursalId);

        for (Object[] result : results) {
            Long id = (Long) result[0];
            String username = (String) result[1];
            Long pedidos = (Long) result[2];
            data.add(Arrays.asList(id, username, pedidos));
        }

        return data;
    }
    @GetMapping("/ganancias")
    public Double calcularGanancia(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha1, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha2, @RequestParam Long sucursalId) {
        return pedidoRepository.calcularGanancia(fecha1, fecha2, sucursalId);
    }
}
