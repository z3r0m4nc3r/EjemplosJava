package principal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import model.Caso;
import service.CovidService;

public class presentacion {

	public static void main(String[] args) {
		CovidService service = new CovidService();
		String ruta;
		List <Caso> listaCompleta = new ArrayList<Caso>();
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader bf=new BufferedReader(isr);
		SimpleDateFormat sdt = new SimpleDateFormat("dd/MM/yyyy");
		
		System.out.println("Introduzca nombre del archivo con su extesion (.json o .csv):");
		
		try {
			ruta=bf.readLine();
			CovidService.setRuta(ruta);
			System.out.println("Datos disponibles en el archivo con fechas desde "+sdt.format(
					CovidService.crearStream().map(c -> c.getFecha()).findFirst().get())
			+" Hasta "+sdt.format(CovidService.crearStream().map(c -> c.getFecha())
					.max((c1,c2) ->c1.getTime()<c2.getTime()?-1:1).get()));
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		System.out.println("");
		System.out.println("Procesando entradas en la Base de Datos");
		System.out.println("");
		System.out.println("Por favor espere");
		System.out.println("");
		
		listaCompleta = CovidService.crearStream().collect(Collectors.toList());
		
		if(service.grabarCasos(listaCompleta)) {
			System.out.println("Base de Datos actualizada con exito");
		}else System.out.println("Error!!, no se pudo actualizar");

	}

}
