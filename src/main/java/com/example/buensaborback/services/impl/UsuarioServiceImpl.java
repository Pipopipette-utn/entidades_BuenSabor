package com.example.buensaborback.services.impl;

import com.example.buensaborback.services.IUsuarioService;
import com.example.buensaborback.entities.Usuario;
import com.example.buensaborback.repositories.BaseRepository;

import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl extends BaseServiceImpl<Usuario, Long> implements IUsuarioService {
    public UsuarioServiceImpl(BaseRepository<Usuario, Long> baseRepository) {
        super(baseRepository);
    }
}
