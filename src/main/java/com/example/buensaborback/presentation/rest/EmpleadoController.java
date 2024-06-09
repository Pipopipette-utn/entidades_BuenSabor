package com.example.buensaborback.presentation.rest;

import com.example.buensaborback.business.facade.Imp.EmpleadoFacadeImp;
import com.example.buensaborback.domain.dto.EmpleadoDtos.EmpleadoPostDto;
import com.example.buensaborback.domain.entities.Empleado;
import com.example.buensaborback.presentation.rest.Base.BaseControllerImp;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/empleados")
@CrossOrigin("*")
public class EmpleadoController extends BaseControllerImp<Empleado, EmpleadoPostDto, EmpleadoPostDto,Long, EmpleadoFacadeImp> {
    public EmpleadoController(EmpleadoFacadeImp facade) {
        super(facade);
    }
}
