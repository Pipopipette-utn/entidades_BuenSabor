package com.example.buensaborback.domain.dto;

import com.example.buensaborback.domain.dto.CategoriaDtos.CategoriaDto;
import com.example.buensaborback.domain.entities.Categoria;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ArticuloDto extends BaseDto {
    protected String denominacion;
    protected Double precioVenta;
    protected Set<ImagenArticuloDto> imagenes = new HashSet<>(); // Crear un get dto
    protected String publicId;
    protected MultipartFile[] archivos;
    protected UnidadMedidaDto unidadMedida;
    protected CategoriaDto categoria;
}
