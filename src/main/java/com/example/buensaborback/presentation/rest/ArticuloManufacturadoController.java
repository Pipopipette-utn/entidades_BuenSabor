package com.example.buensaborback.presentation.rest;

import com.example.buensaborback.business.facade.Imp.ArticuloManufacturadoFacadeImp;
import com.example.buensaborback.domain.dto.ArticuloInsumoDtos.ArticuloInsumoDto;
import com.example.buensaborback.domain.dto.ArticuloInsumoDtos.ArticuloInsumoPostDto;
import com.example.buensaborback.domain.dto.ArticuloManufacturado.ArticuloManufacturadoDto;
import com.example.buensaborback.domain.dto.ArticuloManufacturado.ArticuloManufacturadoPostDto;
import com.example.buensaborback.domain.entities.ArticuloManufacturado;
import com.example.buensaborback.presentation.rest.Base.BaseControllerImp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/articulosManufacturados")
@CrossOrigin("*")
public class ArticuloManufacturadoController extends BaseControllerImp<ArticuloManufacturado, ArticuloManufacturadoDto, ArticuloManufacturadoDto,Long, ArticuloManufacturadoFacadeImp> {

    public ArticuloManufacturadoController(ArticuloManufacturadoFacadeImp facade) {
        super(facade);
    }

    @PostMapping("/create")
    public ResponseEntity<List<ArticuloManufacturadoDto>> create(@RequestBody ArticuloManufacturadoPostDto entity) {
        return ResponseEntity.ok(facade.createConSucursales(entity));
    }

    @GetMapping("/filtrar/{idSucursal}")
    public ResponseEntity<Page<ArticuloManufacturadoDto>> filtrarArticulos(
            Pageable pageable,
            @PathVariable Long idSucursal,
            @RequestParam(required = false) Long categoriaId,
            @RequestParam(required = false) String nombre) {
        if (categoriaId != null && nombre != null && !nombre.isEmpty()) {
            return ResponseEntity.ok(facade.buscarPorCategoriaYNombre(pageable, idSucursal, categoriaId, nombre));
        } else {
            if (categoriaId != null) {
                return ResponseEntity.ok(facade.getArticulosByCategoria(pageable, idSucursal, categoriaId));
            }
            if (nombre != null && !nombre.isEmpty()) {
                return ResponseEntity.ok(facade.getArticulosByNombre(pageable, idSucursal, nombre));
            } else {
                return ResponseEntity.ok(facade.getAllPaged(pageable));
            }
        }
    }

    @GetMapping("/porSucursal/{sucursalId}")
    public ResponseEntity<Page<ArticuloManufacturadoDto>> findAllBySucursalId(@PathVariable Long sucursalId, Pageable pageable) {
        return  ResponseEntity.ok(facade.findBySucursal(sucursalId, pageable));
    }
}
