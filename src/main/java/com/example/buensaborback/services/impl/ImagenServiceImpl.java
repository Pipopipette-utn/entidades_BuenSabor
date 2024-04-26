package com.example.buensaborback.services.impl;

import com.example.buensaborback.services.IImagenService;
import com.example.buensaborback.entities.Imagen;
import com.example.buensaborback.repositories.BaseRepository;

import org.springframework.stereotype.Service;

@Service
public class ImagenServiceImpl extends BaseServiceImpl<Imagen, Long> implements IImagenService {
    public ImagenServiceImpl(BaseRepository<Imagen, Long> baseRepository) {
        super(baseRepository);
    }
}
