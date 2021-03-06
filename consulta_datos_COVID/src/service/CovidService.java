package service;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import model.Caso;

public class CovidService {
	static String ruta;
	
	public CovidService() {
		
	}

	public static String getRuta() {
		return ruta;
	}

	public static void setRuta(String ruta) {
		CovidService.ruta = ruta;
	}

	public List<Caso> listaCasos (Date fecha1, Date fecha2){
		return crearStreamSQL()
		.filter(c -> c.getFecha().getTime()>=fecha1.getTime()&&c.getFecha().getTime()<=fecha2.getTime())
		.collect(Collectors.toList());
	}
	
	
	
	public static Date picoContagios () {
		
		return crearStreamSQL().collect(Collectors.groupingBy(c -> c.getFecha()))
				.values().stream()
				.max((c1,c2) -> c1.stream().mapToLong(e -> e.getPositivos()).sum() 
						< c2.stream().mapToLong(e -> e.getPositivos()).sum()?-1:1)
				.get().get(0).getFecha();
		
	}
	
	public static long mediaPositivosDiarios() {
		return Math.round(crearStreamSQL()
				.mapToLong(c -> c.getPositivos())
				.average().getAsDouble());
		
	}
	
	public static long totalPositivosComunidad(String comunidad) {
		return crearStreamSQL()
				.filter(c -> c.getNombreComunidad().toLowerCase().contentEquals(comunidad.toLowerCase())
						|c.getNombreComunidad().toLowerCase().endsWith(comunidad))
				.mapToLong(c -> c.getPositivos()).sum();
		
	}
	
	public static long totalPositivosDia (Date fecha) {
		return crearStreamSQL().collect(Collectors.groupingBy(c -> c.getFecha()))
				.get(fecha).stream().mapToLong(e -> e.getPositivos()).sum();
				
	}
	
	public static long totalPositivosPais() {
		return crearStreamSQL()
				.mapToLong(c -> c.getPositivos())
				.sum();
	}
	
	public static Map<String,List<Caso>> listaCasosComunidad(){
		return crearStreamSQL()
		.collect(Collectors.groupingBy(c -> c.getNombreComunidad()));
	}
	
	public static Stream <Caso> crearStreamSQL(){
		List<Caso> casos = new ArrayList<>();
		SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
		try (Connection con = Datos.getConnection()) {

			String sql = "SELECT * FROM registro";

			PreparedStatement st = con.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while(rs.next()) {	
				try {
					casos.add(new Caso(rs.getString("nombreComunidad"),sdt.parse(rs.getDate("fecha").toString()),rs.getLong("positivos")));
				} catch (ParseException e) {
				
					e.printStackTrace();
				}

			}
			return casos.stream();
		} catch (SQLException e) {

			e.printStackTrace();
			return null;
		}

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
	
	public static String [] arrayComunidades() {
		String [] comunidades = new String [19];
		for(int i=0;i<comunidades.length;i++) {
		comunidades [i]=crearStreamSQL().map(c -> c.getNombreComunidad()).distinct().collect(Collectors.toList()).get(i);
		}
		return comunidades;
	}
	
	private static String nombreComunidad(String nombre) {
		String nombreComunidad ="";
		
		switch (nombre){
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
			 nombreComunidad="Catalu�a";
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
	
private static Caso crearCaso(ObjectNode jo) {
	
	SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
	Date nuevaFecha=null;
	String nombreComunidad=nombreComunidad(jo.get("ccaa_iso").asText());
	
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

@SuppressWarnings("resource")
public static void pulsarTeclaParaContinuar(){
   
  
   try{
   	Scanner sc = new Scanner(System.in);
   	 System.out.println("");
   	 System.out.println("Pulsa Enter para continuar...");
     sc.nextLine();
       
   }
   catch(Exception e){
   	
   }
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


public static boolean grabarCasos(List<Caso> casos) {
	try (Connection con = Datos.getConnection()) {

		String sql = "INSERT INTO registro (fecha, nombreComunidad, positivos) VALUES(?,?,?)";

		PreparedStatement st = con.prepareStatement(sql);

		for (Caso c : casos) {	

			st.setDate(1, new java.sql.Date(c.getFecha().getTime()));
			st.setString(2, c.getNombreComunidad());
			st.setInt(3, (int) c.getPositivos());

			try {
				st.executeUpdate();
			} catch (java.sql.SQLIntegrityConstraintViolationException e) {

			}
		}
		return true;
	} catch (SQLException e) {

		e.printStackTrace();
		return false;
	}


}


}
