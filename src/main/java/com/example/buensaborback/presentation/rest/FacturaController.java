package com.example.buensaborback.presentation.rest;

import com.example.buensaborback.business.service.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;

@RestController
@RequestMapping("/factura")
@CrossOrigin("*")
public class FacturaController {

    @Autowired
    FacturaService facturaService;

    @GetMapping("/generar/{pedidoId}")
    public ResponseEntity<byte[]> generarFactura(@PathVariable Long pedidoId) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            // Crear un nuevo documento
            facturaService.crearFacturaPdf(pedidoId, outputStream);

            // Establecer las cabeceras de la respuesta
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/pdf"));
            headers.setContentDispositionFormData("attachment", "documento.pdf");
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

            // Devolver el archivo PDF como parte de la respuesta HTTP
            return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
