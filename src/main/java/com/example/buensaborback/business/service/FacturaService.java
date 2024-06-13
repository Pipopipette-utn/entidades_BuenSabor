package com.example.buensaborback.business.service;

import com.lowagie.text.DocumentException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public interface FacturaService {
    public void crearFacturaPdf(Long pedidoId, ByteArrayOutputStream outputStream) throws DocumentException, IOException;
}
