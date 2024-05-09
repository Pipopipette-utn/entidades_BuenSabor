package com.example.buensaborback.services.impl;

import com.example.buensaborback.services.IImagenService;
import com.example.buensaborback.entities.ImagenArticulo;
import com.example.buensaborback.repositories.BaseRepository;

import org.springframework.stereotype.Service;

@Service
public class ImagenServiceImpl extends BaseServiceImpl<ImagenArticulo, Long> implements IImagenService {
    public ImagenServiceImpl(BaseRepository<ImagenArticulo, Long> baseRepository) {
        super(baseRepository);
    }
}
