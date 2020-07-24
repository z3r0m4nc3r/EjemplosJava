package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import model.Pedido;

public class Utilidades {
	public static Pedido construirPedido(String cadena, String separador) {
		String[] datos=cadena.split(separador);
		SimpleDateFormat sdt = new SimpleDateFormat("dd/MM/yyyy");
		Date nuevaFecha= new Date();
		try {
			nuevaFecha = sdt.parse(datos[4]);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new Pedido(datos[0],
				  (Integer.parseInt(datos[1])),
				  Double.parseDouble(datos[2]),
				  datos[3],
				  nuevaFecha);
	}
	public static String construirCadena(Pedido pedido,String separador) {
		SimpleDateFormat sdt = new SimpleDateFormat("dd/MM/yyyy");
		return pedido.getProducto()+separador
				+pedido.getUnidades()+separador
				+pedido.getPrecioUnitario()+separador
				+pedido.getSeccion()+separador
				+sdt.format(pedido.getFecha());
	}
	
	public static LocalDate convertDateToLocalDate (Date d) {
		
		return Instant.ofEpochMilli(d.getTime()) //Instant
				.atZone(ZoneId.systemDefault()) //ZoneDateTime
				.toLocalDate(); //a LocalDate
		
	}
	
	public static Date convertLocalDateToDate (LocalDate ld) {
		
		return Date.from(
				ld.atStartOfDay(ZoneId.systemDefault())
				.toInstant());
		
	}
}
