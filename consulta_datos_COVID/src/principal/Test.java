package principal;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.opencsv.CSVWriter;

import model.Caso;
import service.CovidService;

public class Test {
	static CovidService service = new CovidService();
	
	public static void main(String[] args) {
		seleccionarArchivo();
		
int opcion=0;
SimpleDateFormat sdt = new SimpleDateFormat("dd/MM/yyyy");
		
System.out.println("*******************************************************");
System.out.println("* Sistema de gestion de datos pandemia COVID-19       *");
System.out.println("* Datos disponibles desde "+sdt.format(
		CovidService.crearStream().map(c -> c.getFecha()).findFirst().get())
+" Hasta "+sdt.format(CovidService.crearStream().map(c -> c.getFecha())
		.max((c1,c2) ->c1.getTime()<c2.getTime()?-1:1).get())+" *");
System.out.println("*******************************************************");

		do{		
			opcion = menu();
			switch(opcion){
			case 1:
				System.out.println("");
				listaCasos();
				break;
			case 2:
				System.out.println("");
				positivosDia();
				break;
			case 3:
				System.out.println("");
				picoContagios();
				break;
			case 4:
				System.out.println("");
				mediaPositivosDiarios();
				break;
			case 5:
				System.out.println("");
				totalPositivosComunidad();
				break;
			case 6:
				System.out.println("");
				listaCasosComunidad();
				break;
			case 7:
				System.out.println("");
				actualizarBD();
				break;
			case 8:
				System.out.println("");
				System.out.println("Programa Finalizado");
				break;
			default:
				System.out.println("");
				System.out.println("debes escribir una opcion valida");
			}
		}while(opcion!=8);

	}
	
	static void picoContagios() {
	
		LocalDate dia = CovidService.dateToLocalDate(service.picoContagios());
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		System.out.println("Fecha en la que se notificaron mas contagios: "
		+dia.format(dtf)+
		" Total de positivos : "
		+service.totalPositivosDia(service.picoContagios()));
		CovidService.pulsarTeclaParaContinuar();
	}
	
	static void mediaPositivosDiarios() {
		SimpleDateFormat sdt = new SimpleDateFormat("dd/MM/yyyy");
		System.out.println("La media de positivos diarios hasta el "
		+sdt.format(CovidService.crearStream().map(c -> c.getFecha())
				.max((c1,c2) ->c1.getTime()<c2.getTime()?-1:1).get())+" es de: "
	+service.mediaPositivosDiarios());
		CovidService.pulsarTeclaParaContinuar();
	}
	
	static void totalPositivosComunidad() {
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader bf=new BufferedReader(isr);
		System.out.println("Introduzca Comunidad Autonoma:");
		String ciudad;
		try {
			ciudad = bf.readLine();
			System.out.println("El total de casos notificados en "+ciudad+" es de "
			+service.totalPositivosComunidad(ciudad)+" Total en España: "+service.totalPositivosPais());
			System.out.println("Porcentaje de contagiados de "+ciudad
					+" respecto del total en España "+(service.totalPositivosComunidad(ciudad)*100)
					/service.totalPositivosPais()+"%");
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		CovidService.pulsarTeclaParaContinuar();
			
	}
	
	static void listaCasos () {
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader bf=new BufferedReader(isr);
		SimpleDateFormat sdt = new SimpleDateFormat("dd/MM/yyyy");
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		System.out.println("Lista de casos notificados entre dos fechas:");
		System.out.println("Introduzca las fechas en formato dd/MM/yyyy\n");
		System.out.println("");
		
		try {
			System.out.println("Introduzca fecha inicial dd/MM/yyyy : ");
			Date fechaInicial = sdt.parse(bf.readLine());
			System.out.println("");
			
			System.out.println("Introduzca fecha final dd/MM/yyyy : ");
			Date fechaFinal = sdt.parse(bf.readLine());
			
			System.out.println("");
			service.listaCasos(fechaInicial, fechaFinal).stream()
			.forEach(c -> System.out.println(c.getNombreComunidad()+" "
			+CovidService.dateToLocalDate(c.getFecha()).format(dtf)+" "
					+c.getPositivos()+" positivos"));
			
			
		} catch (ParseException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		CovidService.pulsarTeclaParaContinuar();
		
		
	}
	
	static void positivosDia() {
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader bf=new BufferedReader(isr);
		SimpleDateFormat sdt = new SimpleDateFormat("dd/MM/yyyy");
		System.out.println("Introduzca fecha en formato dd/MM/yyyy : ");
		System.out.println("");
		try {
			Date fecha = sdt.parse(bf.readLine());
			System.out.println("Total de positivos : "+service.totalPositivosDia(fecha));
		} catch (ParseException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		CovidService.pulsarTeclaParaContinuar();
	}
	
	static void listaCasosComunidad() {
		String archCSV = "Casos_por_Comunidad.csv";
		try (CSVWriter writer = new CSVWriter(new FileWriter(archCSV))){
			String [] cabecera = {"nombreComunidad","fecha","positivos"};
			writer.writeNext(cabecera);
			
			service.listaCasosComunidad().values().stream()
			.forEach(c -> c.forEach(d -> writer.writeNext(CovidService.mapToArray(d))));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		System.out.println("Lista creada en el archivo "+archCSV);
		CovidService.pulsarTeclaParaContinuar();
	}
	
	static void actualizarBD() {
		List <Caso> listaCompleta = new ArrayList<Caso>();
		listaCompleta = CovidService.crearStream().collect(Collectors.toList());
		if(service.grabarCasos(listaCompleta)) {
			System.out.println("Base de Datos actualizada con exito");
		}else System.out.println("Error!!, no se pudo actualizar");
		
	}
	
static int menu() {
		
		int op=0;
		System.out.println("1.- Lista de Casos entre dos fechas");
		System.out.println("2.- Total de casos en un dia");
		System.out.println("3.- Fecha del pico de contagios");
		System.out.println("4.- Media de positivos diarios");
		System.out.println("5.- Total de positivos en una comunidad");
		System.out.println("6.- Tabla con listas de Casos por comunidad");
		System.out.println("7.- Actualizar Base de Datos");
		System.out.println("8.- Salir");
		
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader bf=new BufferedReader(isr);
		
		try {
			System.out.println("Selecciona opcion");
			op = Integer.parseInt(bf.readLine());
		} catch (IOException e) {
			e.getMessage();
			e.printStackTrace();
		}
		return op;
		
	}

static void seleccionarArchivo() {
	String ruta;
	InputStreamReader isr = new InputStreamReader(System.in);
	BufferedReader bf=new BufferedReader(isr);
	System.out.println("Introduzca nombre del archivo con su extesion (.json o .csv):");
	
	try {
		ruta=bf.readLine();
		CovidService.setRuta(ruta);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
	System.out.println("");
	
}

}
