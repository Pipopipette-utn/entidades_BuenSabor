package com.example.buensaborback.presentation.rest;

import com.example.buensaborback.business.facade.Imp.EmpleadoFacadeImp;
import com.example.buensaborback.business.facade.Imp.PromocionFacadeImp;
import com.example.buensaborback.domain.dto.ArticuloInsumoDtos.ArticuloInsumoDto;
import com.example.buensaborback.domain.dto.ArticuloManufacturado.ArticuloManufacturadoDto;
import com.example.buensaborback.domain.dto.ArticuloManufacturado.ArticuloManufacturadoPostDto;
import com.example.buensaborback.domain.dto.PromocionDtos.PromocionDto;
import com.example.buensaborback.domain.dto.SucursalDtos.SucursalShortDto;
import com.example.buensaborback.domain.entities.Empleado;
import com.example.buensaborback.domain.entities.Promocion;
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
@RequestMapping("/promociones")
@CrossOrigin("*")
public class PromocionController extends BaseControllerImp<Promocion, PromocionDto,PromocionDto,Long, PromocionFacadeImp> {
    public PromocionController(PromocionFacadeImp facade) {
        super(facade);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody PromocionDto entity) {
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

    @GetMapping("/porSucursal/{sucursalId}")
    public ResponseEntity<Page<PromocionDto>> findAllBySucursalId(@PathVariable Long sucursalId, Pageable pageable) {
        return  ResponseEntity.ok(facade.findBySucursal(sucursalId, pageable));
    }

    @GetMapping("/filtrar/{sucursalId}")
    public ResponseEntity<Page<PromocionDto>> filtrarPromociones(
            @PathVariable Long sucursalId,
            @RequestParam(required = false) String denominacion,
            @RequestParam(required = false, defaultValue = "asc") String sortDirection,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size) {

        Sort.Direction direction = sortDirection.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, "precioPromocional"));

        if (denominacion != null && !denominacion.isEmpty()) {
            return ResponseEntity.ok(facade.getPromocionesByNombre(pageable, sucursalId, denominacion));
        } else {
            return ResponseEntity.ok(facade.findBySucursal(sucursalId, pageable));
        }
    }

}
