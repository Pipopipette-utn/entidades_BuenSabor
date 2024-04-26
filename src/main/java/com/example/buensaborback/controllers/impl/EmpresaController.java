package com.example.buensaborback.controllers.impl;


import com.example.buensaborback.controllers.IEmpresaController;
import com.example.buensaborback.services.impl.EmpresaServiceImpl;
import com.example.buensaborback.entities.Empresa;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path= "empresas")
public class EmpresaController extends BaseControllerImpl<Empresa, EmpresaServiceImpl> implements IEmpresaController {
    public EmpresaController(EmpresaServiceImpl servicio) {
        super(servicio);
    }
}
