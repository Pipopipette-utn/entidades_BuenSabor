-- Unidad de medida
INSERT INTO unidad_medida (id, baja, denominacion) VALUES (5, false, 'Unidad');

-- Categorias
INSERT INTO categoria (id, baja, denominacion) VALUES (5, false, 'Hamburguesas');
INSERT INTO categoria (id, baja, denominacion) VALUES (6, false, 'Entradas');
INSERT INTO categoria (id, baja, denominacion, categoria_padre_id) VALUES (7, false, 'Empanadas', 6);

-- Articulo insumo
INSERT INTO articulo (id, baja, denominacion, unidad_medida_id)
VALUES (8, false, 'Aceite', 1);
INSERT INTO articulo_insumo (id, precio_compra, stock_actual, stock_maximo, stock_minimo, es_para_elaborar)
VALUES (8, 1100.00, 400, 450, 60, true);

-- Articulo manufacturado
INSERT INTO articulo (id, baja, denominacion, precio_venta, categoria_id, unidad_medida_id)
VALUES (7, false, 'Empanada de carne', 700.00, 7, 4);
INSERT INTO articulo_manufacturado (id, descripcion, preparacion, tiempo_estimado_minutos)
VALUES (7, 'Empanada de carne con cebolla y huevo', 'Bla bla bla', 60);

-- Articulo manufacturado detalle
INSERT INTO articulo_manufacturado_detalle (id, baja, cantidad, articulo_insumo_id, articulo_manufacturado_id) VALUES
(6, false, 200, 2, 7)






