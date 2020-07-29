package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import model.Pedido;
import model.PedidoTienda;

public class Utilidades {
	static SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
	public static String pedidoToString(Pedido p) {
		return p.getProducto()+","+p.getUnidades()+","+p.getPrecioUnitario()+","+p.getSeccion()+","+sdf.format(p.getFecha());
	}
	public static String pedidoTiendaToString(PedidoTienda p) {
		return p.getTienda()+","+pedidoToString(p);
	}
	public static Pedido stringToPedido(String f) {
		String[] partes=f.split("[,]");
		try {
			return new Pedido(partes[0],
					Integer.parseInt(partes[1]),
					Double.parseDouble(partes[2]),
					partes[3],
					sdf.parse(partes[4]));
		}
		catch(ParseException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	public static PedidoTienda stringToPedidoTienda(String f) {
		String[] partes=f.split("[,]");
		try {
			return new PedidoTienda(partes[1],
					Integer.parseInt(partes[2]),
					Double.parseDouble(partes[3]),
					partes[4],
					sdf.parse(partes[5]),
					partes[0]);
		}
		catch(ParseException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	public static Date convertirADate(LocalDate ld) {
		return Date.from(
				ld.atStartOfDay(ZoneId.systemDefault()) //ZoneDateTime
				.toInstant());
	}
	
	public static LocalDate convertirALocalDate(Date f) {
		return Instant.ofEpochMilli(f.getTime()) //Instant
				.atZone(ZoneId.systemDefault()) //ZoneDateTime
				.toLocalDate();
	}
}
