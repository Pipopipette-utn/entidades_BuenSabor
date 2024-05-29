package com.example.buensaborback.presentation.rest;

import com.example.buensaborback.business.facade.Imp.EmpleadoFacadeImp;
import com.example.buensaborback.business.facade.Imp.PromocionFacadeImp;
import com.example.buensaborback.domain.dto.EmpleadoDto;
import com.example.buensaborback.domain.dto.PromocionDtos.PromocionDto;
import com.example.buensaborback.domain.dto.SucursalDtos.SucursalShortDto;
import com.example.buensaborback.domain.entities.Empleado;
import com.example.buensaborback.domain.entities.Promocion;
import com.example.buensaborback.presentation.rest.Base.BaseControllerImp;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/promociones")
@CrossOrigin("*")
public class PromocionController extends BaseControllerImp<Promocion, PromocionDto,PromocionDto,Long, PromocionFacadeImp> {
    public PromocionController(PromocionFacadeImp facade) {
        super(facade);
    }

    @DeleteMapping("/baja/{id}/{idSucursal}")
    public void deleteById(@PathVariable Long id, @PathVariable Long idSucursal) {
        facade.deleteInSucursales(id, idSucursal);
    }
}
