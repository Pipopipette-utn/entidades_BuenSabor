package com.example.buensaborback.utils;

import org.springframework.stereotype.Service;

@Service
public class PublicIdService {
    public String obtenerPublicId(String url) {
        String[] parts = url.split("/");
        String publicId = parts[parts.length - 1];
        return publicId;
    }
}
