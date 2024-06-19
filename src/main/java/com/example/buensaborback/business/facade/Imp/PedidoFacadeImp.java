package com.example.buensaborback.business.facade.Imp;

import com.example.buensaborback.business.facade.Base.BaseFacadeImp;
import com.example.buensaborback.business.facade.PedidoFacade;
import com.example.buensaborback.business.mapper.BaseMapper;
import com.example.buensaborback.business.mapper.PedidoMapper;
import com.example.buensaborback.business.service.Base.BaseService;
import com.example.buensaborback.business.service.PedidoService;
import com.example.buensaborback.domain.dto.ArticuloInsumoDtos.ArticuloInsumoDto;
import com.example.buensaborback.domain.dto.PedidoDtos.PedidoDto;
import com.example.buensaborback.domain.dto.PedidoDtos.PedidoEstadoDto;
import com.example.buensaborback.domain.dto.PedidoDtos.PedidoGetDto;
import com.example.buensaborback.domain.entities.ArticuloInsumo;
import com.example.buensaborback.domain.entities.Pedido;
import com.example.buensaborback.domain.enums.Estado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoFacadeImp extends BaseFacadeImp<Pedido, PedidoDto, PedidoGetDto, Long> implements PedidoFacade {

    @Autowired
    PedidoMapper pedidoMapper;
    @Autowired
    PedidoService pedidoService;

    public PedidoFacadeImp(BaseService<Pedido, Long> baseService, BaseMapper<Pedido, PedidoDto, PedidoGetDto> baseMapper) {
        super(baseService, baseMapper);
    }

    public PedidoGetDto cambiarEstado(PedidoEstadoDto request, Long id) throws Exception{
        var pedidoEntidad = pedidoMapper.toEntityEstado(request);
        var pedidoUpdate = pedidoService.cambiarEstado(pedidoEntidad, id);
        return pedidoMapper.toDTO(pedidoUpdate);
    }

    public Page<PedidoGetDto> findBySucursalAndEstado(Long sucursalId, Estado estado, Pageable pageable) {
        Page<Pedido> pedidosFiltrados = pedidoService.findBySucursalAndEstado(sucursalId, estado, pageable.getPageNumber(), pageable.getPageSize());
        // Mapea las entidades a DTOs
        List<PedidoGetDto> dtos = pedidosFiltrados.getContent().stream()
                .map(pedidoMapper::toDTO)
                .collect(Collectors.toList());
        // Devuelve una página de DTOs
        return new PageImpl<>(dtos, pageable, pedidosFiltrados.getTotalElements());
    }

    // REPORTES ----------------------------------------------------------------------------
    public List<List<Object>> getRankingComidasMasPedidas(LocalDate fechaInicio, LocalDate fechaFin, Long sucursalId) throws SQLException {
        List<List<Object>> data = new ArrayList<>();
        // Agregar encabezados de las columnas
        data.add(Arrays.asList("Artículo", "Total ventas"));

        // Obtener los resultados de la consulta
        List<Object[]> results = pedidoService.findRankingComidasMasPedidas(fechaInicio, fechaFin, sucursalId);

        for (Object[] result : results) {
            String articulo = (String) result[0];
            // Obtener el BigDecimal y convertirlo a Long
            BigDecimal vecesVendidoBD = (BigDecimal) result[1];
            Long vecesVendido = vecesVendidoBD.longValue();
            data.add(Arrays.asList(articulo, vecesVendido));
        }

        return data;
    }


    public List<List<Object>> getTotalRecaudadoDiario(LocalDate fecha1, LocalDate fecha2, Long sucursalId) throws SQLException {
        List<List<Object>> data = new ArrayList<>();
        data.add(Arrays.asList("Fecha", "Total recaudado"));
        List<Object[]> results = pedidoService.calcularTotalRecaudado(fecha1, fecha2, sucursalId);

        for (Object[] result : results) {
            String fecha = result[0] + "-" + result[1] + "-" + result[2];
            Double totalRecaudado = ((Number) result[3]).doubleValue();
            data.add(Arrays.asList(fecha, totalRecaudado));
        }
        return data;
    }

    public List<List<Object>> getTotalRecaudadoMensual(LocalDate fecha1, LocalDate fecha2, Long sucursalId) throws SQLException {
        List<List<Object>> data = new ArrayList<>();
        data.add(Arrays.asList("Mes - Año", "Total recaudado"));
        List<Object[]> results = pedidoService.calcularTotalRecaudadoPorMes(fecha1, fecha2, sucursalId);

        String[] meses = {
                "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
                "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
        };

        for (Object[] result : results) {
            int mesNum = (int) result[0] - 1;
            String mes = meses[mesNum];

            String fecha = mes + "-" + result[1];
            Double totalRecaudado = ((Number) result[2]).doubleValue();
            data.add(Arrays.asList(fecha, totalRecaudado));
        }
        return data;
    }

    public List<List<Object>> getClientePedidos(LocalDate fechaInicio, LocalDate fechaFin, Long sucursalId) throws SQLException {
        List<List<Object>> data = new ArrayList<>();
        data.add(Arrays.asList("Username", "Total Pedidos"));

        List<Object[]> results = pedidoService.findClientePedidos(fechaInicio, fechaFin, sucursalId);

        for (Object[] result : results) {
            String username = (String) result[0];
            Long pedidos = ((Number) result[1]).longValue();
            data.add(Arrays.asList(username, pedidos));
        }

        return data;
    }

    public List<List<Object>> getTotalGananciaDiario(LocalDate fecha1, LocalDate fecha2, Long sucursalId) throws SQLException {
        List<List<Object>> data = new ArrayList<>();
        data.add(Arrays.asList("Fecha", "Ganancia"));
        List<Object[]> results = pedidoService.calcularGanancia(fecha1, fecha2, sucursalId);

        for (Object[] result : results) {
            String fecha = result[0] + "-" + result[1] + "-" + result[2];
            Double ganancia = ((Number) result[3]).doubleValue();
            data.add(Arrays.asList(fecha, ganancia));
        }
        return data;
    }

    public List<List<Object>> getTotalGananciaMensual(LocalDate fecha1, LocalDate fecha2, Long sucursalId) throws SQLException {
        List<List<Object>> data = new ArrayList<>();
        data.add(Arrays.asList("Mes - Año", "Ganancia"));
        List<Object[]> results = pedidoService.calcularGananciaPorMes(fecha1, fecha2, sucursalId);

        String[] meses = {
                "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
                "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
        };

        for (Object[] result : results) {
            int mesNum = (int) result[0] - 1;
            String mes = meses[mesNum];

            String fecha = mes + "-" + result[1];
            Double ganancia = ((Number) result[2]).doubleValue();
            data.add(Arrays.asList(fecha, ganancia));
        }
        return data;
    }
}
