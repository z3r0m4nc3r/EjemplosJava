package util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.dataformat.csv.CsvSchema.Builder;

public class Utilidades {
	
	public void jsonAcsv(String ruta) {
	//Convierte el archivo JSON indicado en la variable ruta a CSV
		JsonNode jsonTree;
		
		try {
			jsonTree = new ObjectMapper().readTree(new File(ruta));
		
			Builder csvSchemaBuilder = new CsvSchema.Builder();
			JsonNode firstObject = jsonTree.elements().next();
			firstObject.fieldNames().forEachRemaining(fieldName -> {csvSchemaBuilder.addColumn(fieldName);} );
			CsvSchema csvSchema = csvSchemaBuilder.build().withHeader();
		
			CsvMapper csvMapper = new CsvMapper();
			csvMapper.writerFor(JsonNode.class)
			  .with(csvSchema)
			  .writeValue(new File(System.getProperty("user.dir")+System.getProperty("file.separator")+System.getProperty("file.separator")+"resultado.csv"), jsonTree);
			System.out.println("Archivo CSV exportado en: "+System.getProperty("user.dir")+System.getProperty("file.separator")+"resultado.csv");
			
		} catch (JsonProcessingException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}
	
	public void csvAJson (String ruta) {
		//Convierte el archivo CSV indicado en la variable ruta a JSON
		 File input = new File(ruta);
	      try {
	         CsvSchema csv = CsvSchema.emptySchema().withHeader();
	         CsvMapper csvMapper = new CsvMapper();
	         ObjectMapper mapper = new ObjectMapper();
	         MappingIterator<Map<?, ?>> mappingIterator =  csvMapper.reader().forType(Map.class).with(csv).readValues(input);
	         List<Map<?, ?>> list = mappingIterator.readAll();        
	         mapper.writeValue(Paths.get("resultado.json").toFile(), list);
	         System.out.println("Archivo JSON exportado en: "+System.getProperty("user.dir")+System.getProperty("file.separator")+"resultado.json");
			
	      } catch(Exception e) {
	         e.printStackTrace();
	      }
	}
	
	public static LocalDate dateToLocalDate(Date d) {
		// Recibe un objeto java.util.date y lo transforma en java.util.LocalDate
		return Instant.ofEpochMilli(d.getTime()) 
				.atZone(ZoneId.systemDefault()) 
				.toLocalDate(); 
	}

	public static Date localDateToDate (LocalDate ld) {
		//Recibe un objeto java.util.LocalDate y lo transforma en java.util.Date
		return Date.from(
				ld.atStartOfDay(ZoneId.systemDefault())
				.toInstant());
	}

	public static void pulsarTeclaParaContinuar(){
	    
	    try{
	    	@SuppressWarnings("resource")
			Scanner sc = new Scanner(System.in);
	    	 System.out.println("");
	    	 System.out.println("Pulsa Enter para continuar...");
	         sc.nextLine();
	    }
	    catch(Exception e){
	    	
	    }
	}
	
	public static double redondearDouble(double d) {
		//Redeondea un double a dos decimales
		return (double) Math.round(d*100d/100d);
	}

}
