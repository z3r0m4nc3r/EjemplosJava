package service;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.json.simple.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import model.Caso;

public class CovidService {
	static String ruta;	
	
	
	public CovidService(String ruta) {
		super();
		this.ruta = ruta;
	}



	public List<Caso> listaCasos (Date fecha1, Date fecha2){
		return crearStream()
		.filter(c -> c.getFecha().getTime()>fecha1.getTime()&&c.getFecha().getTime()<fecha2.getTime())
		.collect(Collectors.toList());
	}
	
	
	
	public Date picoContagios () {
		
		return crearStream().collect(Collectors.groupingBy(c -> c.getFecha()))
				.values().stream()
				.max((c1,c2) -> c1.stream().mapToLong(e -> e.getPositivos()).sum() 
						< c2.stream().mapToLong(e -> e.getPositivos()).sum()?-1:1)
				.get().get(0).getFecha();
		
	}
	
	public double mediaPositivosDiarios() {
		return crearStream()
				.mapToDouble(c -> (double)c.getPositivos())
				.average().orElse(0);
		
	}
	
	public long totalPositivosComunidad(String comunidad) {
		return crearStream()
				.filter(c -> c.getNombreComunidad().toLowerCase().contentEquals(comunidad.toLowerCase())
						|c.getNombreComunidad().toLowerCase().endsWith(comunidad))
				.mapToLong(c -> c.getPositivos()).sum();
		
	}
	
	public long totalPositivosDia (Date fecha) {
		return crearStream().collect(Collectors.groupingBy(c -> c.getFecha()))
				.get(fecha).stream().mapToLong(e -> e.getPositivos()).sum();
				
	}
	
	public long totalPositivosPais() {
		return crearStream()
				.mapToLong(c -> c.getPositivos())
				.sum();
	}
	
	public Map<String,List<Caso>> listaCasosComunidad(){
		return crearStream()
		.collect(Collectors.groupingBy(c -> c.getNombreComunidad()));
	}
	
	public static Stream <Caso> crearStream(){
		 Path origenPath = FileSystems.getDefault().getPath(ruta);
		 Path destinoPath = FileSystems.getDefault().getPath("datos.json");
		 ObjectMapper objectMapper = new ObjectMapper();
		 
		if(ruta.endsWith("csv")) {
			csvAJson(ruta);
		}else if(ruta.endsWith("json")) {
			try {
				Files.move(origenPath, destinoPath, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else return null;
		
		try {
			
			ObjectNode[] lista = objectMapper.readValue(Paths.get("datos.json").toFile(), ObjectNode[].class);
			return Arrays.stream(lista).map(ob -> crearCaso(ob));
			
		} catch (JsonProcessingException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		return null;
		
		
		
	}
	
	private static String nombreComunidad(ObjectNode jo) {
		String nombreComunidad ="";
		
		switch (jo.get("ccaa_iso").asText()){
		 case "AN":
			 nombreComunidad="Andalucia";
			 break;
		 case "AR":
			 nombreComunidad="Aragon";
			 break;
		 case "AS":
			 nombreComunidad="Asturias";
			 break;
		 case "IB":
			 nombreComunidad="Baleares";
			 break;
		 case "CN":
			 nombreComunidad="Canarias";
			 break;
		 case "CB":
			 nombreComunidad="Cantabria";
			 break;
		 case "CM":
			 nombreComunidad="Castilla la Mancha";
			 break;
		 case "CL":
			 nombreComunidad="Castilla Leon";
			 break;
		 case "CT":
			 nombreComunidad="Cataluña";
			 break;
		 case "VC":
			 nombreComunidad="Valencia";
			 break;
		 case "EX":
			 nombreComunidad="Extremadura";
			 break;
		 case "GA":
			 nombreComunidad="Galicia";
			 break;
		 case "RI":
			 nombreComunidad="La Rioja";
			 break;
		 case "MD":
			 nombreComunidad="Madrid";
			 break;
		 case "PV":
			 nombreComunidad="Pais Vasco";
			 break;
		 case "MC":
			 nombreComunidad="Murcia";
			 break;
		 case "ML":
			 nombreComunidad="Melilla";
			 break;
		 case "CE":
			 nombreComunidad="Ceuta";
			 break;
		 case "NC":
			 nombreComunidad="Navarra";
			 break;
		}
		return nombreComunidad;
	}
	
public static Caso crearCaso(ObjectNode jo) {
	
	SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
	Date nuevaFecha=null;
	String nombreComunidad=nombreComunidad(jo);
	
	try {
		nuevaFecha = sdt.parse(jo.get("fecha").asText());
	} catch (java.text.ParseException e) {
		e.printStackTrace();
	}
	
		return new Caso(nombreComunidad
				,nuevaFecha
				,(long)jo.get("num_casos").asLong());
	}

public static LocalDate dateToLocalDate(Date d) {
	return Instant.ofEpochMilli(d.getTime()) //Instant
			.atZone(ZoneId.systemDefault()) //ZoneDateTime
			.toLocalDate(); //a LocalDate
}

public static Date localDateToDate (LocalDate ld) {
	return Date.from(
			ld.atStartOfDay(ZoneId.systemDefault())
			.toInstant());
}

public static void pulsarTeclaParaContinuar(){
   
  
   try{
   	Scanner sc = new Scanner(System.in);
   	 System.out.println("");
   	 System.out.println("Pulsa Enter para continuar...");
        String seguir = sc.nextLine();
       
   }
   catch(Exception e){
   	
   }
}

public static double redondearDouble(double d) {
	return (double) Math.round(d*100d/100d);
}

public static String [] mapToArray (Caso c) {
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	String [] mapa = {c.getNombreComunidad()
			,dateToLocalDate(c.getFecha()).format(dtf).toString()
			,String.valueOf(c.getPositivos())};
	return mapa;

	}

private static void csvAJson (String csvFile) {
	 File input = new File(csvFile);
     try {
        CsvSchema csv = CsvSchema.emptySchema().withHeader();
        CsvMapper csvMapper = new CsvMapper();
        ObjectMapper mapper = new ObjectMapper();
        MappingIterator<Map<?, ?>> mappingIterator =  csvMapper.reader().forType(Map.class).with(csv).readValues(input);
        List<Map<?, ?>> list = mappingIterator.readAll();        
        mapper.writeValue(Paths.get("datos.json").toFile(), list);
		
     } catch(Exception e) {
        e.printStackTrace();
     }
}
}
