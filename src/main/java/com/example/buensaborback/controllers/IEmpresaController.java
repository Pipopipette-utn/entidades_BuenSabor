package com.example.buensaborback.controllers;


import com.example.buensaborback.services.impl.EmpresaServiceImpl;
import com.example.buensaborback.entities.Empresa;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(path= "empresas")
public interface IEmpresaController extends IBaseController<Empresa, Long> {
}
