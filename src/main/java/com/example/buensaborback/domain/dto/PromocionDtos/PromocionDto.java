package com.example.buensaborback.domain.dto.PromocionDtos;

import com.example.buensaborback.domain.dto.BaseDto;
import com.example.buensaborback.domain.dto.SucursalDtos.SucursalDto;
import com.example.buensaborback.domain.dto.SucursalDtos.SucursalShortDto;
import com.example.buensaborback.domain.enums.TipoPromocion;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PromocionDto extends BaseDto {

    private String denominacion;
    @Schema(type = "string", format = "date", pattern = "yyyy-MM-dd")
    private LocalDate fechaDesde;
    @Schema(type = "string", format = "date", pattern = "yyyy-MM-dd")
    private LocalDate fechaHasta;
    @Schema(type = "string", format = "time", pattern = "HH:mm:ss")
    private LocalTime horaDesde;
    @Schema(type = "string", format = "time", pattern = "HH:mm:ss")
    private LocalTime horaHasta;
    private String descripcionDescuento;
    private Double precioPromocional;
    private TipoPromocion tipoPromocion;
    private Set<PromocionDetalleDto> promocionDetalles = new HashSet<>();
    private Set<ImagenPromocionDto> imagenes = new HashSet<>();
    private SucursalShortDto sucursal;
    private Set<SucursalShortDto> sucursales = new HashSet<>();
}
