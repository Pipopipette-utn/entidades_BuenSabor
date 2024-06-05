package com.example.buensaborback.presentation.rest;

import com.example.buensaborback.business.facade.Imp.ArticuloInsumoFacadeImp;
import com.example.buensaborback.domain.dto.ArticuloInsumoDtos.ArticuloInsumoDto;
import com.example.buensaborback.domain.dto.ArticuloInsumoDtos.ArticuloInsumoPostDto;
import com.example.buensaborback.domain.entities.ArticuloInsumo;
import com.example.buensaborback.presentation.rest.Base.BaseControllerImp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/articulosInsumos")
@CrossOrigin("*")
public class ArticuloInsumoController extends BaseControllerImp<ArticuloInsumo, ArticuloInsumoDto, ArticuloInsumoDto,Long, ArticuloInsumoFacadeImp> {

    public ArticuloInsumoController(ArticuloInsumoFacadeImp facade) {
        super(facade);
    }

    @GetMapping("/paged/insumosParaElaborar")
    public ResponseEntity<Page<ArticuloInsumoDto>> findByEsParaElaborarTrue(Pageable pageable) {
        //logger.info("INICIO GET ALL insumos PARA ELABORAR");
        return ResponseEntity.ok(facade.findByEsParaElaborarTrue(pageable));
    }

    @GetMapping("/paged/insumosDirectos")
    public ResponseEntity<Page<ArticuloInsumoDto>> findByEsParaElaborarFalse(Pageable pageable) {
        //logger.info("INICIO GET ALL insumos (gaseosas)");
        return ResponseEntity.ok(facade.findByEsParaElaborarFalse(pageable));
    }
    @PostMapping("/create")
    public ResponseEntity<List<ArticuloInsumoDto>> create(@RequestBody ArticuloInsumoPostDto entity) {
        return ResponseEntity.ok(facade.createConSucursales(entity));
    }

    @GetMapping("/filtrar/{idSucursal}")
    public ResponseEntity<Page<ArticuloInsumoDto>> filtrarArticulos(
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
    public ResponseEntity<Page<ArticuloInsumoDto>> findAllBySucursalId(@PathVariable Long sucursalId, Pageable pageable) {
        return  ResponseEntity.ok(facade.findBySucursal(sucursalId, pageable));
    }

    @GetMapping("/insumoDirecto/{sucursalId}")
    public ResponseEntity<Page<ArticuloInsumoDto>> findBySucursalAltaAndElaborarFalse(@PathVariable Long sucursalId, Pageable pageable) {
        return  ResponseEntity.ok(facade.findBySucursalAltaAndElaborarFalse(sucursalId, pageable));
    }
}
