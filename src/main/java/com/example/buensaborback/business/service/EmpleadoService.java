package com.example.buensaborback.business.service;

import com.example.buensaborback.business.service.Base.BaseService;
import com.example.buensaborback.domain.entities.Empleado;
import com.example.buensaborback.domain.entities.Promocion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmpleadoService extends BaseService<Empleado,Long> {
    Empleado findByEmail (String email);

    Page<Empleado> findBySucursal_Id(Long sucursalId, Pageable pageable);

}
