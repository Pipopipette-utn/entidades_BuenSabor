package com.example.buensaborback.presentation.rest;

import com.example.buensaborback.business.facade.Imp.ArticuloManufacturadoFacadeImp;
import com.example.buensaborback.domain.dto.ArticuloInsumoDtos.ArticuloInsumoDto;
import com.example.buensaborback.domain.dto.ArticuloInsumoDtos.ArticuloInsumoPostDto;
import com.example.buensaborback.domain.dto.ArticuloManufacturado.ArticuloManufacturadoDto;
import com.example.buensaborback.domain.dto.ArticuloManufacturado.ArticuloManufacturadoPostDto;
import com.example.buensaborback.domain.dto.SucursalDtos.SucursalShortDto;
import com.example.buensaborback.domain.entities.ArticuloManufacturado;
import com.example.buensaborback.presentation.rest.Base.BaseControllerImp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/articulosManufacturados")
@CrossOrigin("*")
public class ArticuloManufacturadoController extends BaseControllerImp<ArticuloManufacturado, ArticuloManufacturadoDto, ArticuloManufacturadoDto,Long, ArticuloManufacturadoFacadeImp> {

    public ArticuloManufacturadoController(ArticuloManufacturadoFacadeImp facade) {
        super(facade);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody ArticuloManufacturadoPostDto entity) {
        try{
            return ResponseEntity.ok(facade.createConSucursales(entity));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/{id}/duplicate")
    public ResponseEntity<?> duplicateArticuloInOtherSucursales(
            @PathVariable Long id,
            @RequestBody Set<SucursalShortDto> sucursales
    ) {
        try{
            return ResponseEntity.ok(facade.duplicateInOtherSucursales(id, sucursales));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/filtrar/{idSucursal}")
    public ResponseEntity<Page<ArticuloManufacturadoDto>> filtrarArticulos(
            @PathVariable Long idSucursal,
            @RequestParam(required = false) Long categoriaId,
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false, defaultValue = "asc") String sortDirection,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size) {

        Sort.Direction direction = sortDirection.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, "precioVenta"));

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

    @GetMapping("/activos/porSucursal/{sucursalId}")
    public ResponseEntity<List<ArticuloManufacturadoDto>> findAllBySucursalId(@PathVariable Long sucursalId) {
        return ResponseEntity.ok(facade.findBySucursal(sucursalId));
    }
}
