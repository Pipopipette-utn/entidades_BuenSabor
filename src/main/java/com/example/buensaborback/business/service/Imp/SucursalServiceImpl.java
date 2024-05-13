package com.example.buensaborback.business.service.Imp;

import com.example.buensaborback.business.service.Base.BaseServiceImp;
import com.example.buensaborback.business.service.SucursalService;
import com.example.buensaborback.domain.entities.Sucursal;
import com.example.buensaborback.repositories.DomicilioRepository;
import com.example.buensaborback.repositories.EmpresaRepository;
import com.example.buensaborback.repositories.SucursalRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class SucursalServiceImpl extends BaseServiceImp<Sucursal,Long> implements SucursalService {
   @Autowired
   SucursalRepository sucursalRepository;
   @Autowired
   DomicilioRepository domicilioRepository;
   @Autowired
   EmpresaRepository empresaRepository;
    private static final Logger logger = LoggerFactory.getLogger(BaseServiceImp.class);

    @Override
    @Transactional
    public Sucursal guardarSucursal(Sucursal sucursal) {
        var domicilio = sucursal.getDomicilio();
        var domicilioBd = domicilioRepository.findById(domicilio.getId());
        if (domicilioBd.isEmpty()){
            domicilioRepository.save(domicilio);
            sucursal.setDomicilio(domicilio);
        }else{
            sucursal.setDomicilio(domicilioBd.get());
        }
        var empresa = empresaRepository.findById(sucursal.getEmpresa().getId());
        if(empresa.isEmpty()){
            throw new RuntimeException("No se puede guardar el empresa");
        }

        return sucursalRepository.save(sucursal);
    }

    @Override
    public Sucursal actualizarSucursal(Long id,Sucursal sucursal) {
        var sucursalActualizar = sucursalRepository.findById(sucursal.getId());
        if(sucursalActualizar.isEmpty()){
            throw new RuntimeException("No se puede actualizar el sucursal");
        }
        var domicilio = domicilioRepository.findById(sucursal.getDomicilio().getId());
        domicilioRepository.save(sucursal.getDomicilio());
        var empresa = empresaRepository.findById(sucursal.getEmpresa().getId());

        sucursal.setDomicilio(domicilio.get());
        sucursal.setEmpresa(empresa.get());
        return sucursalRepository.save(sucursal);
    }


}
