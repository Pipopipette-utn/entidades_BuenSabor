package com.example.buensaborback.services.impl;

import com.example.buensaborback.services.IEmpresaService;
import com.example.buensaborback.entities.Empresa;
import com.example.buensaborback.repositories.BaseRepository;
import org.springframework.stereotype.Service;

@Service
public class EmpresaServiceImpl extends BaseServiceImpl<Empresa, Long> implements IEmpresaService {
    public EmpresaServiceImpl(BaseRepository<Empresa, Long> baseRepository) {
        super(baseRepository);
    }
}
