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
        if(domicilio.getId() != null){
            var domicilioBd = domicilioRepository.findById(domicilio.getId());
            domicilioBd.ifPresent(sucursal::setDomicilio);
        }else{
            domicilioRepository.save(domicilio);
            sucursal.setDomicilio(domicilio);
        }
        var empresa = empresaRepository.findById(sucursal.getEmpresa().getId());
        if(empresa.isEmpty()){
            throw new RuntimeException("Empresa no encontrada");
        }

        return sucursalRepository.save(sucursal);
    }

    @Override
    public Sucursal actualizarSucursal(Long id,Sucursal sucursal) {
        var sucursalActualizar = sucursalRepository.findById(sucursal.getId());
        if(sucursalActualizar.isEmpty()){
            throw new RuntimeException("Sucursal no encontrada: { id: " + id + " }");
        }
        var domicilio = domicilioRepository.findById(sucursal.getDomicilio().getId());
        domicilioRepository.save(sucursal.getDomicilio());
        var empresa = empresaRepository.findById(sucursal.getEmpresa().getId());

        sucursal.setDomicilio(domicilio.get());
        sucursal.setEmpresa(empresa.get());
        return sucursalRepository.save(sucursal);
    }


}
