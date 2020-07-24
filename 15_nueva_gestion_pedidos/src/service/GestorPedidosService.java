package service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import model.Pedido;
import util.Utilidades;

public class GestorPedidosService  {
	Path path;
	String dir="pedidos.txt";
	final String separador="-";
	public GestorPedidosService() {
		path=Paths.get(dir);
		if(!Files.exists(path)) { 
			try {
			Files.createFile(path);
		} catch (IOException e) {
			e.printStackTrace();
		}};
	}
	
	//almacena el pedido recibido
	public void grabarPedido(Pedido pedido) {
		try {
			Files.writeString(path,
					Utilidades.construirCadena(pedido, separador)+
					System.lineSeparator(), StandardOpenOption.APPEND);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//media de ventas pedidos de esa seccion
	public double promedioSeccion(String seccion) {
		try {
			return Files.lines(path)
			.map(p-> Utilidades.construirPedido(p, separador))
			.filter(p -> p.getSeccion().equalsIgnoreCase(seccion))
			.collect(Collectors.averagingDouble(p-> p.getPrecioUnitario()*p.getUnidades()));
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}

	}

	//ventas totales de el producto indicado
	public double totalProducto(String producto) {
		try {
			return Files.lines(path).map(p -> Utilidades.construirPedido(p, separador))
			.filter(p -> p.getProducto().contains(producto))
			.mapToDouble(p -> p.getPrecioUnitario()*p.getUnidades())
			.sum();
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
	}

	//devuelve el pedido con venta superior
	
	public Pedido pedidoSuperior() {
		try {
			return Files.lines(path).map(p -> Utilidades.construirPedido(p, separador))
			.max((p1,p2) -> 
			p1.getPrecioUnitario()*p1.getUnidades()<p2.getPrecioUnitario()*p2.getUnidades()?1:-1)
			.orElse(null);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
	}

	//devuelve lista de pedidos de una sección
	public List<Pedido> pedidosSeccion(String seccion) {
		try {
			return Files.lines(path).map(p -> Utilidades.construirPedido(p, separador))
			.filter(p -> p.getSeccion().equalsIgnoreCase(seccion))
			.collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	//devuelve el pedido con fecha más reciente
	public Pedido pedidoMasReciente() {
		try {
			return Files.lines(path).map(p -> Utilidades.construirPedido(p, separador))
			.max((p1,p2) ->
			p1.getFecha().compareTo(p2.getFecha()))
			.orElse(null);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	//devuelve lista de pedidos, posteriores a la fecha indicada
	public List<Pedido> pedidosPosterioresFecha(Date fecha) {
		try {
			return Files.lines(path).map(p -> Utilidades.construirPedido(p, separador))
			.filter(p -> p.getFecha().getTime()>fecha.getTime())
			.collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	//lista de nombres de sección, no repetidas
	public List<String> secciones() {
		try {
			return Files.lines(path).map(p -> Utilidades.construirPedido(p, separador))
			.map(p -> p.getSeccion())
			.distinct()
			.collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public List<Pedido> pedidosRangoFecha (LocalDate fecha, Period periodo){
		
		LocalDate fechaFin = fecha.plus(periodo); //sumamos un periodo a un LocalDate
		
		try {
			return Files.lines(path).map(p -> Utilidades.construirPedido(p, separador))
			.filter(p -> p.getFecha().after(Utilidades.convertLocalDateToDate(fecha))
					&&p.getFecha().before(Utilidades.convertLocalDateToDate(fechaFin)))
			.collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	

}
