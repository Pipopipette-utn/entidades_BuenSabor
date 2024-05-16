package com.example.buensaborback.business.mapper;

import java.util.List;

import com.example.buensaborback.domain.dto.BaseDto;
import com.example.buensaborback.domain.entities.Base;
import org.mapstruct.Mapping;

public interface BaseMapper<E extends Base,D extends BaseDto>{
    public D toDTO(E source);
    public E toEntity(D source);
    public List<D> toDTOsList(List<E> source);

}
