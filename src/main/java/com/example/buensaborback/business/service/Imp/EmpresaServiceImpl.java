package com.example.buensaborback.business.service.Imp;

import com.example.buensaborback.business.service.Base.BaseServiceImp;
import com.example.buensaborback.business.service.EmpresaService;
import com.example.buensaborback.business.service.SucursalService;
import com.example.buensaborback.domain.entities.Empresa;
import com.example.buensaborback.domain.entities.Sucursal;
import com.example.buensaborback.repositories.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmpresaServiceImpl extends BaseServiceImp<Empresa,Long> implements EmpresaService {

    @Autowired
    SucursalService sucursalService;

    @Autowired
    EmpresaRepository empresaRepository;

    @Override
    public Empresa addSucursal(Long idEmpresa, Long idSucursal) {
        Empresa empresa = empresaRepository.findWithSucursalesById(idEmpresa);
        empresa.getSucursales().add(sucursalService.getById(idSucursal));
        return empresa;
    }

    @Override
    public Empresa update(Empresa empresa, Long id) {
        var empresaActualizar = empresaRepository.findById(empresa.getId());
        if(empresaActualizar.isEmpty()){
            throw new RuntimeException("Empresa no encontrada: { id: " + id + " }");
        }

        // Verificar si la imagen está vacía
        if (empresa.getImagenEmpresa() != null && empresa.getImagenEmpresa().getUrl() == null) {
            // Dar de baja la imagen existente
            empresa.setImagenEmpresa(null);
        }

        return empresaRepository.save(empresa);
    }
}
