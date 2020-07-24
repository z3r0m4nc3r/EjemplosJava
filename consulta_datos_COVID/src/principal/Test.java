package principal;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import service.CovidService;
import util.Utilidades;

public class Test {

	public static void main(String[] args) {
		
		picoContagios();


	}
	
	static void picoContagios() {
		CovidService service = new CovidService();
		LocalDate dia = Utilidades.dateToLocalDate(service.picoContagios());
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		System.out.println("Fecha en la que se notificaron más contagios: "+dia.format(dtf));
	}

}
