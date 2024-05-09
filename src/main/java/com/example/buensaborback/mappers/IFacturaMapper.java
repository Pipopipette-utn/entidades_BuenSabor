package com.example.buensaborback.mappers;

import com.example.buensaborback.entities.dtos.FacturaDto;
import com.example.buensaborback.entities.Factura;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IFacturaMapper extends IBaseMapper<Factura, FacturaDto> {
    FacturaDto toDTO(Factura factura);
    Factura toEntity(FacturaDto facturaDto);
    List<FacturaDto> toDTOsList(List<Factura> facturaList);
    List<Factura> toEntitiesList(List<FacturaDto> facturaDtoList);
}
