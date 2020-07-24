package principal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import service.CovidService;
import util.Utilidades;

public class Test {
	static CovidService service = new CovidService();
	public static void main(String[] args) {
		
		picoContagios();
		mediaPositivosDiarios();
		totalPositivosComunidad();


	}
	
	static void picoContagios() {
	
		LocalDate dia = Utilidades.dateToLocalDate(service.picoContagios());
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		System.out.println("Fecha en la que se notificaron más contagios: "+dia.format(dtf));
	}
	
	static void mediaPositivosDiarios() {
		System.out.println("La media de positivos diarios hasta el 20/07/2020 es de: "+service.mediaPositivosDiarios());
	}
	
	static void totalPositivosComunidad() {
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader bf=new BufferedReader(isr);
		System.out.println("Introduzca Comunidad Autonoma:");
		String ciudad;
		try {
			ciudad = bf.readLine();
			System.out.println("El total de casos notificados en "+ciudad+" es de "+service.totalPositivosComunidad(ciudad));
		} catch (IOException e) {
			
			e.printStackTrace();
		}
			
			
		
	}

}
