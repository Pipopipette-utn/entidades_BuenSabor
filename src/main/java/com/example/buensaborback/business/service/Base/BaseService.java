package com.example.buensaborback.business.service.Base;

import com.example.buensaborback.domain.entities.Base;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.io.Serializable;
import java.util.List;

public interface BaseService <E extends Base, ID extends Serializable>{
    public E create(E request);
    public E getById(ID id);
    public Page<E> getAll(Pageable pageable);
    public Page<E> getAllByBajaFalse(Pageable pageable);
    public void deleteById(ID id);
    public E update(E request, ID id);
}
