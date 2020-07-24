package principal;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TestFormato {

	public static void main(String[] args) {
		
		//formateado fecha
		
		LocalDate fecha = LocalDate.of(2010, 11, 24);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		System.out.println(fecha.format(dtf));
		
		//parseado de fecha desde un String
		
		String miFecha="30/08/2012";
		LocalDate nfecha = LocalDate.parse(miFecha, dtf); //pasamos la fecha y el DateTimeFormatter con el formato con el que viene
		System.out.println(nfecha);

	}

}
