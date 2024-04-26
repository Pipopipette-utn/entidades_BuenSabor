package com.example.buensaborback.repositories;

import com.example.buensaborback.entities.Factura;
import org.springframework.stereotype.Repository;

@Repository
public interface FacturaRepository extends BaseRepository<Factura,Long> {
}
