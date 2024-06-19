package com.example.buensaborback.business.service;

import com.example.buensaborback.business.service.Base.BaseService;
import com.example.buensaborback.domain.entities.Pedido;
import com.example.buensaborback.domain.enums.Estado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface PedidoService extends BaseService<Pedido,Long> {
    Pedido cambiarEstado(Pedido request, Long id) throws Exception;
    Page<Pedido> findBySucursalAndEstado(Long sucursalId, Estado estado, int page, int size);

    // REPORTES ----------------------------------------------------------------------------
    public List<Object[]> findRankingComidasMasPedidas(LocalDate fecha1, LocalDate fecha2, Long sucursalId) throws SQLException;
    public List<Object[]> calcularTotalRecaudado(LocalDate fecha1, LocalDate fecha2, Long sucursalId) throws SQLException;
    public List<Object[]> calcularTotalRecaudadoPorMes(LocalDate fecha1, LocalDate fecha2, Long sucursalId) throws SQLException;
    public List<Object[]> findClientePedidos(LocalDate fechaInicio, LocalDate fechaFin, Long sucursalId) throws SQLException;
    public List<Object[]> calcularGanancia(LocalDate fecha1, LocalDate fecha2, Long sucursalId) throws SQLException;
    public List<Object[]> calcularGananciaPorMes(LocalDate fecha1, LocalDate fecha2, Long sucursalId) throws SQLException;

}
