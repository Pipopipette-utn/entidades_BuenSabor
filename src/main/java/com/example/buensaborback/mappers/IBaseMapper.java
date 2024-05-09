package com.example.buensaborback.mappers;

import com.example.buensaborback.entities.dtos.BaseDto;
import com.example.buensaborback.entities.Base;

import java.util.List;

public interface IBaseMapper<E extends Base,D extends BaseDto>{
    D toDTO(E source);
    E toEntity(D source);
    List<D> toDTOsList(List<E> source);
    List<E> toEntitiesList(List<D> source);
}