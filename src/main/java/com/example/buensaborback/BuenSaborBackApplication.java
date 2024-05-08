package com.example.buensaborback;

import com.example.buensaborback.entities.*;
import com.example.buensaborback.entities.enums.Estado;
import com.example.buensaborback.entities.enums.FormaPago;
import com.example.buensaborback.entities.enums.TipoEnvio;
import com.example.buensaborback.entities.enums.TipoPromocion;
import com.example.buensaborback.repositories.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

@SpringBootApplication
public class BuenSaborBackApplication {
// Aca tiene que inyectar todos los repositorios
// Es por ello que deben crear el paquete repositorio

	private static final Logger logger = LoggerFactory.getLogger(BuenSaborBackApplication.class);

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private PaisRepository paisRepository;

	@Autowired
	private ProvinciaRepository provinciaRepository;

	@Autowired
	private LocalidadRepository localidadRepository;

	@Autowired
	private EmpresaRepository empresaRepository;

	@Autowired
	private SucursalRepository sucursalRepository;

	@Autowired
	private DomicilioRepository domicilioRepository;

	@Autowired
	private UnidadMedidaRepository unidadMedidaRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ArticuloInsumoRepository articuloInsumoRepository;

	@Autowired
	private ArticuloManufacturadoRepository articuloManufacturadoRepository;

	@Autowired
	private ArticuloManufacturadoDetalleRepository articuloManufacturadoDetalleRepository;

	@Autowired
	private ImagenRepository imagenRepository;

	@Autowired
	private PromocionRepository promocionRepository;

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public static void main(String[] args) {
		SpringApplication.run(BuenSaborBackApplication.class, args);
		logger.info("Estoy activo en el main");
	}


	@Bean
	CommandLineRunner init() {
		return args -> {
			logger.info("----------------ESTOY----FUNCIONANDO---------------------");

			// Etapa del dashboard
			// Crear 1 pais
			// Crear 2 provincias para ese pais
			// crear 2 localidades para cada provincia

			Pais pais1 = new Pais("Argentina");
			paisRepository.save(pais1);

			Provincia provincia1 = new Provincia("Mendoza", pais1);
			Provincia provincia2 = new Provincia("Cordoba", pais1);
			provinciaRepository.save(provincia1);
			provinciaRepository.save(provincia2);

			Localidad localidad1 = new Localidad("Lujan de Cuyo", provincia1);
			Localidad localidad2 = new Localidad("Godoy Cruz", provincia1);
			Localidad localidad3 = new Localidad("Achiras", provincia2);
			Localidad localidad4 = new Localidad("Agua de Oro", provincia2);
			localidadRepository.save(localidad1);
			localidadRepository.save(localidad2);
			localidadRepository.save(localidad3);
			localidadRepository.save(localidad4);

			// Crear 1 empresa
			// Crear 2 sucursales para esa empresa
			// crear los Domicilios para esas sucursales
			Empresa empresaBrown = new Empresa("Lo de Brown", 30503167, "Venta de Alimentos");
			empresaRepository.save(empresaBrown);

			Sucursal sucursalChacras = new Sucursal(empresaBrown, "En chacras", LocalTime.of(17, 0), LocalTime.of(23, 0));
			Sucursal sucursalGodoyCruz = new Sucursal(empresaBrown, "En godoy cruz", LocalTime.of(16, 0), LocalTime.of(23, 30));
			Domicilio domicilioViamonte = new Domicilio("Viamonte", 5509, 500, 2, 23, localidad1);
			Domicilio domicilioSanMartin = new Domicilio("San Martin", 5511, 789, localidad2);

			sucursalChacras.setDomicilio(domicilioViamonte);
			sucursalGodoyCruz.setDomicilio(domicilioSanMartin);
			sucursalRepository.save(sucursalChacras);
			sucursalRepository.save(sucursalGodoyCruz);

			// Crear Unidades de medida
			UnidadMedida unidadMedidaLitros = new UnidadMedida("Litros");
			UnidadMedida unidadMedidaKilos = new UnidadMedida("Kilos");
			UnidadMedida unidadMedidaCantidad = new UnidadMedida("Cantidad");
			UnidadMedida unidadMedidaPorciones = new UnidadMedida("Porciones");
			unidadMedidaRepository.save(unidadMedidaLitros);
			unidadMedidaRepository.save(unidadMedidaKilos);
			unidadMedidaRepository.save(unidadMedidaCantidad);
			unidadMedidaRepository.save(unidadMedidaPorciones);

			// Crear Categorías de productos y subCategorías de los mismos
			Categoria categoriaBebidas = new Categoria("Bebidas");
			Categoria categoriaGaseosas = new Categoria("Gaseosas", categoriaBebidas);
			Categoria categoriaTragos = new Categoria("Tragos", categoriaBebidas);
			categoriaRepository.save(categoriaBebidas);
			categoriaRepository.save(categoriaGaseosas);
			categoriaRepository.save(categoriaTragos);
			Categoria categoriaPizzas = new Categoria("Pizzas");
			categoriaRepository.save(categoriaPizzas);

			// crear fotos para cada insumo
			Imagen imagenCoca = new Imagen("https://m.media-amazon.com/images/I/51v8nyxSOYL._SL1500_.jpg");
			ArrayList<Imagen> imagenesCoca = new ArrayList<>();
			imagenesCoca.add(imagenCoca);

			// Crear Insumos , coca cola , harina , etc
			ArticuloInsumo cocaCola = new ArticuloInsumo("Coca cola 0.5L", unidadMedidaCantidad, 5, 50, 10, 800.0, 1800.0, categoriaGaseosas, imagenesCoca);
			ArticuloInsumo harina = new ArticuloInsumo("Harina", unidadMedidaKilos, true, 4, 40, 10, 600.0);
			ArticuloInsumo queso = new ArticuloInsumo("Queso", unidadMedidaKilos, true, 20, 50, 10, 10000.0);
			ArticuloInsumo tomate = new ArticuloInsumo("Tomate", unidadMedidaCantidad, true, 5, 50, 15, 500.0);

			articuloInsumoRepository.save(cocaCola);
			articuloInsumoRepository.save(harina);
			articuloInsumoRepository.save(queso);
			articuloInsumoRepository.save(tomate);

			imagenCoca.setArticulo(cocaCola);
			imagenRepository.save(imagenCoca);

			// Crear fotos para los artículos manufacturados
			Imagen imagenPizzaMuzarella = new Imagen("https://storage.googleapis.com/fitia-api-bucket/media/images/recipe_images/1002846.jpg");
			ArrayList<Imagen> imagenesPizzaMuzarella = new ArrayList<>();
			imagenesPizzaMuzarella.add(imagenPizzaMuzarella);

			Imagen imagenPizzaNapolitana = new Imagen("https://assets.elgourmet.com/wp-content/uploads/2023/03/8metlvp345_portada-pizza-1024x686.jpg.webp");
			ArrayList<Imagen> imagenesPizzaNapolitana = new ArrayList<>();
			imagenesPizzaMuzarella.add(imagenPizzaNapolitana);

			// Crear Articulos Manufacturados
			ArticuloManufacturado pizzaMuzarella = new ArticuloManufacturado("Pizza Muzarella", "Una pizza clasica", unidadMedidaPorciones, 7000.0, 15, "Pasos de preparacion de una muzza de toda la vida", categoriaPizzas, imagenesPizzaMuzarella);
			ArticuloManufacturado pizzaNapolitana = new ArticuloManufacturado("Pizza Napolitana", "Una pizza napolitana italiana", unidadMedidaPorciones, 8500.0, 15, "Pasos de preparacion de una pizza napolitana italiana", categoriaPizzas, imagenesPizzaNapolitana);

			articuloManufacturadoRepository.save(pizzaNapolitana);
			articuloManufacturadoRepository.save(pizzaMuzarella);

			imagenPizzaMuzarella.setArticulo(pizzaMuzarella);
			imagenRepository.save(imagenPizzaMuzarella);
			imagenPizzaNapolitana.setArticulo(pizzaNapolitana);
			imagenRepository.save(imagenPizzaNapolitana);

			// Creo detalles de artículo manufacturado
			ArticuloManufacturadoDetalle detalle1 = new ArticuloManufacturadoDetalle(300, harina, pizzaMuzarella);
			ArticuloManufacturadoDetalle detalle2 = new ArticuloManufacturadoDetalle(600, queso, pizzaMuzarella);
			ArticuloManufacturadoDetalle detalle3 = new ArticuloManufacturadoDetalle(350, harina, pizzaNapolitana);
			ArticuloManufacturadoDetalle detalle4 = new ArticuloManufacturadoDetalle(650, queso, pizzaNapolitana);
			ArticuloManufacturadoDetalle detalle5 = new ArticuloManufacturadoDetalle(2, tomate, pizzaNapolitana);

			articuloManufacturadoDetalleRepository.save(detalle1);
			articuloManufacturadoDetalleRepository.save(detalle2);
			articuloManufacturadoDetalleRepository.save(detalle3);
			articuloManufacturadoDetalleRepository.save(detalle4);
			articuloManufacturadoDetalleRepository.save(detalle5);

			// Crear promocion para sucursal - Dia de los enamorados
			// Tener en cuenta que esa promocion es exclusivamente para una sucursal determinada de una empresa determinada
			Promocion promocionDiaEnamorados = new Promocion("Dia de los Enamorados", LocalDate.of(2024, 2, 13), LocalDate.of(2024, 2, 15), LocalTime.of(0, 0), LocalTime.of(23, 59), "El descuento que se hace por san valentin, un dia antes y un dia despues", 100.0, TipoPromocion.promocion);
			promocionDiaEnamorados.getArticulos().add(cocaCola);
			promocionDiaEnamorados.getArticulos().add(cocaCola);
			promocionDiaEnamorados.getArticulos().add(pizzaNapolitana);
			promocionRepository.save(promocionDiaEnamorados);

			Imagen imagenPromo = new Imagen("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQv0GnvcTHDxxmjqHXgKbDBSnK4yuEqwtxOZV31rmA-dg&s");
			imagenPromo.setPromocion(promocionDiaEnamorados);
			imagenRepository.save(imagenPromo);

			//Agregar categorias y promociones a sucursales
			sucursalChacras.getCategorias().add(categoriaBebidas);
			sucursalChacras.getCategorias().add(categoriaPizzas);
			sucursalChacras.getCategorias().add(categoriaGaseosas);
			sucursalChacras.getCategorias().add(categoriaTragos);
			sucursalChacras.getPromociones().add(promocionDiaEnamorados);

			sucursalGodoyCruz.getCategorias().add(categoriaBebidas);
			sucursalGodoyCruz.getCategorias().add(categoriaPizzas);
			sucursalGodoyCruz.getCategorias().add(categoriaGaseosas);
			sucursalGodoyCruz.getCategorias().add(categoriaTragos);

			sucursalRepository.save(sucursalChacras);
			sucursalRepository.save(sucursalGodoyCruz);

			//Crea un cliente y un usuario
			Imagen imagenCliente = new Imagen("https://hips.hearstapps.com/hmg-prod/images/la-la-land-final-1638446140.jpg");
			imagenRepository.save(imagenCliente);
			Usuario usuario = new Usuario("sebastian", "9565a49d-ecc1-4f4e-adea-6cdcb7edc4a3");
			usuarioRepository.save(usuario);
			Cliente cliente = new Cliente(usuario, imagenCliente, "correoFalso@gmail.com", "Sebastian", "Wilder", "2615920825");
			Domicilio domicilioCl = new Domicilio("Viamonte", 5234, 5509, 1, 1, localidad1);
			domicilioRepository.save(domicilioCl);

			cliente.getDomicilios().add(domicilioCl);
			clienteRepository.save(cliente);

			//Crea un pedido para el cliente
			Pedido pedido = new Pedido(LocalTime.now(), 300.0, 170.6, Estado.Preparacion, FormaPago.MercadoPago, TipoEnvio.Delivery, sucursalChacras, domicilioViamonte);

			DetallePedido detallePedido1 = new DetallePedido(pizzaMuzarella, 1, 200.0);
			DetallePedido detallePedido2 = new DetallePedido(cocaCola, 2, 100.0);

			pedido.getDetallePedidos().add(detallePedido1);
			pedido.getDetallePedidos().add(detallePedido2);
			pedidoRepository.save(pedido);

			cliente.getPedidos().add(pedido);
			clienteRepository.save(cliente);


			logger.info("----------------Sucursal Chacras ---------------------");
			logger.info("{}", sucursalChacras);
			logger.info("----------------Sucursal Godoy Cruz ---------------------");
			logger.info("{}", sucursalGodoyCruz);
			logger.info("----------------Pedido ---------------------");

			logger.info("{}", pedido);

		};
	}
}