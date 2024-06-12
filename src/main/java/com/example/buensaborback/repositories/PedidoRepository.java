package com.example.buensaborback.repositories;

import com.example.buensaborback.domain.entities.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PedidoRepository extends BaseRepository<Pedido,Long>{
    Page<Pedido> findBySucursal_Id(Long sucursalId, Pageable pageable);

    @Query(value = "select am.id as id_articulo, am.denominacion, sum(dp.cantidad) as veces_vendido " +
            "from pedido p " +
            "inner join detalle_pedido dp on p.id = dp.pedido_id " +
            "inner join articulo am on am.id = dp.articulo_id " +
            "inner join articulo_manufacturado amf on am.id = amf.id " +
            "where p.fecha_pedido between :fecha1 and :fecha2 " +
            "and p.sucursal_id = :sucursalId " +
            "group by am.id, am.denominacion " +
            "order by veces_vendido desc", nativeQuery = true)
    List<Object> findRankingComidasMasPedidas(@Param("fecha1") Date fecha1, @Param("fecha2") Date fecha2, @Param("sucursalId") Long sucursalId);

    @Query(value = "select sum(total) total_recaudado from pedido " +
            "where fecha_pedido between :fecha1 and :fecha2 " +
            "and sucursal_id = :sucursalId", nativeQuery = true)
    Double calcularTotalRecaudado(@Param("fecha1") Date fecha1, @Param("fecha2") Date fecha2, @Param("sucursalId") Long sucursalId);

    @Query(value = "select c.id as id, u.username as username, count(p.cliente_id) as pedidos " +
            "from pedido p " +
            "inner join cliente c on c.id = p.cliente_id " +
            "inner join persona pe on pe.id = c.id " +
            "inner join usuario u on u.id = pe.usuario_id " +
            "where p.fecha_pedido between :fechaInicio and :fechaFin " +
            "and p.sucursal_id = :sucursalId " +
            "group by c.id, u.username " +
            "order by pedidos desc", nativeQuery = true)
    List<Object> findClientePedidos(@Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin, @Param("sucursalId") Long sucursalId);

    @Query(value = "select sum(total - total_costo) as total_ganancia " +
            "from pedido " +
            "where fecha_pedido between :fecha1 and :fecha2 " +
            "and sucursal_id = :sucursalId", nativeQuery = true)
    Double calcularGanancia(@Param("fecha1") Date fecha1, @Param("fecha2") Date fecha2, @Param("sucursalId") Long sucursalId);
}

/* findRankingComidasMasPedidas
Ranking comidas más pedidas en un periodo de tiempo determinado

select am.id as id_articulo, am.denominacion, sum(dp.cantidad) as veces_vendido
from pedido p
inner join detalle_pedido dp on p.id = dp.pedido_id
inner join articulo am on am.id = dp.articulo_id
inner join articulo_manufacturado amf on am.id = amf.id
where p.fecha_pedido between '2024-01-01' and '2024-07-01'
and p.sucursal_id = 2
group by am.id
order by veces_vendido desc;
*/

/* totalRecaudado
Ingresos (recaudaciones) por períodos de tiempo. Diario / Mensual

select sum(total) total_recaudado from pedido
where fecha_pedido between '2024-06-07' and '2024-06-08'
and sucursal_id = 2
*/

/*findClientePedidos
Cantidad de pedidos agrupados por cliente en un determinado
periodo de tiempo.

select c.id, u.username, count(p.cliente_id) pedidos
from pedido p
inner join cliente c on c.id = p.cliente_id
inner join persona pe on pe.id = c.id
inner join usuario u on u.id = pe.usuario_id
where p.fecha_pedido between '2024-01-07' and '2024-07-08'
and p.sucursal_id = 2
group by c.id, u.username
order by pedidos desc;
*/

/* Ganancias
Monto de Ganancia en un periodo de tiempo (ventas – costos)

select sum((total-total_costo)) total_recaudado from pedido
where fecha_pedido between '2024-01-07' and '2024-07-08'
and sucursal_id = 2
*/