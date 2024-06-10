package com.example.buensaborback.presentation.rest;

import com.example.buensaborback.business.facade.Imp.EmpleadoFacadeImp;
import com.example.buensaborback.domain.dto.EmpleadoDtos.EmpleadoPostDto;
import com.example.buensaborback.domain.dto.PedidoDtos.PedidoGetDto;
import com.example.buensaborback.domain.entities.Empleado;
import com.example.buensaborback.presentation.rest.Base.BaseControllerImp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/empleados")
@CrossOrigin("*")
public class EmpleadoController extends BaseControllerImp<Empleado, EmpleadoPostDto, EmpleadoPostDto,Long, EmpleadoFacadeImp> {
    public EmpleadoController(EmpleadoFacadeImp facade) {
        super(facade);
    }

    @GetMapping("/filter")
    public ResponseEntity<?> findByEmail(@RequestParam String email) {
        try {
            EmpleadoPostDto empleado = facade.findByEmail(email);
            return ResponseEntity.ok(empleado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/porSucursal/{sucursalId}")
    public ResponseEntity<Page<EmpleadoPostDto>> findAllBySucursalId(@PathVariable Long sucursalId, Pageable pageable) {
        return  ResponseEntity.ok(facade.findBySucursal(sucursalId, pageable));
    }

}
