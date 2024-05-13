package com.example.buensaborback.presentation.rest;

import com.example.buensaborback.business.facade.Imp.EmpresaFacadeImpl;
import com.example.buensaborback.domain.dto.EmpresaDto;
import com.example.buensaborback.domain.dto.EmpresaLargeDto;
import com.example.buensaborback.domain.entities.Empresa;
import com.example.buensaborback.presentation.rest.Base.BaseControllerImp;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/empresa")
@CrossOrigin("*")
public class EmpresaController extends BaseControllerImp<Empresa, EmpresaDto, Long, EmpresaFacadeImpl> {
    public EmpresaController(EmpresaFacadeImpl facade) {
        super(facade);
    }

    @PutMapping("/addSucursal/{idEmpresa}/{idSucursal}")
    public ResponseEntity<EmpresaLargeDto> addSucursal(Long idEmpresa, Long idSucursal){
        return ResponseEntity.ok(facade.addSucursal(idEmpresa,idSucursal));
    }
}
