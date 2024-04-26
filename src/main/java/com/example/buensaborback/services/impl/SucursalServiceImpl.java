package com.example.buensaborback.services.impl;

import com.example.buensaborback.entities.Categoria;
import com.example.buensaborback.entities.Domicilio;
import com.example.buensaborback.entities.Empresa;
import com.example.buensaborback.repositories.CategoriaRepository;
import com.example.buensaborback.repositories.DomicilioRepository;
import com.example.buensaborback.repositories.EmpresaRepository;
import com.example.buensaborback.services.ISucursalService;
import com.example.buensaborback.entities.Sucursal;
import com.example.buensaborback.repositories.BaseRepository;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SucursalServiceImpl extends BaseServiceImpl<Sucursal, Long> implements ISucursalService {
    public SucursalServiceImpl(BaseRepository<Sucursal, Long> baseRepository) {
        super(baseRepository);
    }

    @Autowired
    private EmpresaServiceImpl empresaService;
    @Autowired
    private DomicilioServiceImpl domicilioService;
    @Autowired
    private CategoriaServiceImpl categoriaService;

    @Override
    @Transactional
    public Sucursal save(Sucursal sucursal) throws Exception {
        try{
            //Si ingresó solo el id del domicilio, lo obtengo y lo guardo en la sucursal,
            //Si ingresó un domicilio nuevo, lo guardo en la base de datos.
            Domicilio domicilio = sucursal.getDomicilio();
            System.out.println(domicilio);
            if (domicilio != null) {
                System.out.println("Id"+domicilio.getId());
                if (domicilio.getId() == null) {
                    domicilioService.save(domicilio);
                }else{
                    Domicilio domicilioBD = domicilioService.findById(domicilio.getId());
                    sucursal.setDomicilio(domicilioBD);
                }
            }
            sucursal = baseRepository.save(sucursal);
            return sucursal;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
