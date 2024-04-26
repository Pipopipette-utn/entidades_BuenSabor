package com.example.buensaborback.controllers.impl;

import com.example.buensaborback.controllers.IBaseController;
import com.example.buensaborback.services.impl.BaseServiceImpl;
import com.example.buensaborback.entities.Base;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public abstract class BaseControllerImpl <E extends Base, baseService extends BaseServiceImpl<E, Long>> implements IBaseController<E, Long> {
    protected baseService servicio;

    public BaseControllerImpl(baseService servicio) {
        this.servicio = servicio;
    }

    @GetMapping("")
    public ResponseEntity<?> findAll(){
        try{
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(servicio.findAll());
        }catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\""+ e.getMessage() + "\"}");
        }
    }

    @GetMapping("/paged")
    public ResponseEntity<?> findAll(Pageable pageable){
        try{
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(servicio.findAll(pageable));
        }catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"Error, por favor intente mas tarde.\"}");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id){
        try{
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(servicio.findById(id));
        }catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\""+ e.getMessage() + "\"}");
        }
    }

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody E entity){
        try{
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(servicio.save(entity));
        }catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\""+ e.getMessage() + "\"}");
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody E entity){
        try{
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(servicio.update(id, entity));
        }catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\""+ e.getMessage() + "\"}");
        }
    }

    @PutMapping("/alta/{id}")
    public ResponseEntity<?> darDeAlta(@PathVariable Long id){
        try{
            boolean dadoDeAlta =  servicio.darDeAlta(id);
            if (dadoDeAlta){
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body("{\"éxito\":\""+"Registro dado de alta exitosamente."+ "\"}");
            }else{
                throw new Exception("{\"error\":\""+"Error al dar de alta el registro."+ "\"}");
            }
        }catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\""+ e.getMessage() + "\"}");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        try{
            boolean dadoDeBaja =  servicio.delete(id);
            if (dadoDeBaja){
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body("{\"éxito\":\""+ "Dado de baja con éxito."+ "\"}");
            }else{
                throw new Exception("{\"error\":\""+ "Error al dar de baja el registro."+ "\"}");
            }
        }catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\""+ e.getMessage() + "\"}");
        }
    }

}
