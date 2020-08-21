package service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import model.records;

public class CovidService {
	static final String name="records.json";
	static final String folder="datos";

	public void actualizarDatos() {
		String url ="https://opendata.ecdc.europa.eu/covid19/casedistribution/json/";

		File dir = new File(folder); 
		
		int b = 0;

		if (!dir.exists()) { 
			if (!dir.mkdir()) {
				System.out.println("No se pudo crear directorio de destino"); } 
		} 
		File file =  new File(System.getProperty("user.dir")+System.getProperty("file.separator")+folder+System.getProperty("file.separator")+ name); 
		URLConnection conn; 
		try { 
			conn = new  URL(url).openConnection(); 
			conn.connect();
			System.out.println("\nEmpezando descarga: \n"); 
			System.out.println(">> URL: " + url); 
			System.out.println(">> Nombre: " + name);
			System.out.println(">> tamaño: " + conn.getContentLength() + " bytes");
			InputStream in = conn.getInputStream(); 
			OutputStream out = new	FileOutputStream(file); 
				
			while (b != -1) { 
				b = in.read(); 
				if (b != -1) { 
					out.write(b);
				} 
			}
			out.close(); 
			in.close();
			
			System.out.println("Archivo Descargado");
			System.out.println("Comenzando actualizacion");
			System.out.println("Paciencia.....");
			
			Path pt = Paths.get(System.getProperty("user.dir")+System.getProperty("file.separator")+folder+System.getProperty("file.separator")+ name);
			List<StringBuilder> fichero = new ArrayList <>();
			Files.lines(pt).forEach(s -> fichero.add(new StringBuilder(s)));
			
			fichero.stream().findFirst().get().deleteCharAt(0).append("[");
			fichero.stream().skip(1).findFirst().get().delete(0, 16);
			fichero.stream().skip((fichero.stream().count())-1).findFirst().get().deleteCharAt(0);
			
			File f = new File(System.getProperty("user.dir")+System.getProperty("file.separator")+folder+System.getProperty("file.separator")+ name);
		    FileWriter fw = new FileWriter(f, Charset.forName("UTF-8"));
		    BufferedWriter escritura = new BufferedWriter(fw);
		    for(int i=0;i<fichero.size();i++){
		        escritura.write(fichero.get(i).toString());
		        escritura.newLine();

		    }
		    escritura.close();
			System.out.println("Actualizacion finalizada");
		}
		
		catch (MalformedURLException e) {

			e.printStackTrace(); 
		} 
		catch (IOException e) {

			e.printStackTrace(); 
		}

	}
	
	public static Stream <records> crearStream(){
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			
			ObjectNode[] records = objectMapper.readValue(Paths.get(System.getProperty("user.dir")+System.getProperty("file.separator")+folder+System.getProperty("file.separator")+name).toFile(), ObjectNode[].class);
			return Arrays.stream(records).map(ob -> crearReport(ob));
			
		} catch (JsonProcessingException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	private static records crearReport(ObjectNode jo) {
		
		SimpleDateFormat sdt = new SimpleDateFormat("dd/MM/yyyy");
		Date nuevaFecha=null;
		
		try {
			nuevaFecha = sdt.parse(jo.get("dateRep").asText());
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		
			return new records(nuevaFecha
					,jo.get("day").asInt()
					,jo.get("month").asInt()
					,jo.get("year").asInt()
					,jo.get("cases").asInt()
					,jo.get("deaths").asInt()
					,jo.get("countriesAndTerritories").asText()
					,jo.get("popData2019").asLong()
					,jo.get("continentExp").asText()
					,jo.get("Cumulative_number_for_14_days_of_COVID-19_cases_per_100000").asDouble());
		}
	
public Date picoContagios () {
		
		return crearStream().collect(Collectors.groupingBy(r -> r.getCases()))
				.values().stream()
				.max((c1,c2) -> c1.stream().mapToLong(e -> e.getCases()).sum() 
						< c2.stream().mapToLong(e -> e.getCases()).sum()?-1:1)
				.get().get(0).getDateRep();
		
	}

public Date picoMuertes () {
	
	return crearStream().collect(Collectors.groupingBy(r -> r.getDeaths()))
			.values().stream()
			.max((c1,c2) -> c1.stream().mapToLong(e -> e.getDeaths()).sum() 
					< c2.stream().mapToLong(e -> e.getDeaths()).sum()?-1:1)
			.get().get(0).getDateRep();
	
}

public long mediaPositivosDiarios() {
	return Math.round(crearStream()
			.mapToLong(c -> c.getCases())
			.average().getAsDouble());
	
}

public long mediaMuertesDiarias() {
	return Math.round(crearStream()
			.mapToLong(c -> c.getDeaths())
			.average().getAsDouble());
	
}

public long totalPositivosPais(String pais) {
	return crearStream()
			.filter(c -> c.getCountriesAndTerritories().toLowerCase().contentEquals(pais.toLowerCase())
					|c.getCountriesAndTerritories().toLowerCase().endsWith(pais))
			.mapToLong(c -> c.getCases()).sum();
	
}

public long totalMuertesPais(String pais) {
	return crearStream()
			.filter(c -> c.getCountriesAndTerritories().toLowerCase().contentEquals(pais.toLowerCase())
					|c.getCountriesAndTerritories().toLowerCase().endsWith(pais))
			.mapToLong(c -> c.getDeaths()).sum();
	
}


}
