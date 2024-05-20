package com.example.buensaborback.business.facade;

import com.example.buensaborback.business.facade.Base.BaseFacade;
import com.example.buensaborback.business.service.ImagenArticuloService;
import com.example.buensaborback.domain.dto.EmpresaDto;
import com.example.buensaborback.domain.dto.ImagenArticuloDto;
import org.springframework.beans.factory.annotation.Autowired;

public interface ImagenArticuloFacade extends BaseFacade<ImagenArticuloDto,ImagenArticuloDto, Long> {
}
