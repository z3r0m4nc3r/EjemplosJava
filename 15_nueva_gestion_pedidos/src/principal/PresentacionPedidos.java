package principal;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import service.GestorPedidosService;

public class PresentacionPedidos {

	public static void main(String[] args) {
		String fecha = "01/10/2017";
		// pedidos realizados entre los 5 años siguientes a esa fecha
		
		GestorPedidosService service = new GestorPedidosService();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate f = LocalDate.parse(fecha, dtf);
		Period p = Period.ofYears(5);
		
		service.pedidosRangoFecha(f, p)
		.stream()
		.forEach(c -> System.out.println(c.getProducto()));;
	

	}

}
