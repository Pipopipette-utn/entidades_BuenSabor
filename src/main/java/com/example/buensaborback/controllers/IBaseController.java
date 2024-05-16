package com.example.buensaborback.controllers;

import com.example.buensaborback.entities.Base;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.Serializable;

public interface IBaseController <E extends Base, Id extends Serializable> {
    ResponseEntity<?> findAll();
    ResponseEntity<?> findAllActive();

    ResponseEntity<?> findAll(Pageable pageable);

    ResponseEntity<?> getOne(@PathVariable Id id);

    ResponseEntity<?> save(@RequestBody E entity);

    ResponseEntity<?> update(@PathVariable Id id, @RequestBody E entity);

    ResponseEntity<?> darDeAlta(@PathVariable Id id);

    ResponseEntity<?> delete(@PathVariable Id id);

}
