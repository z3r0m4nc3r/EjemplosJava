package service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.dataformat.csv.CsvSchema.Builder;

public class ConversorService {
	
	public void jsonAcsv(String ruta) {
		//Primero utilizamos el ObjectMapper de Jackson para leer 
		//el documento en un tree de objetos JsonNode
		JsonNode jsonTree;
		
		try {
			jsonTree = new ObjectMapper().readTree(new File(ruta));
			/*
			 * Creamos un CsvSchema. Para establecer las columnas cabeceras, tipos,
			 * y la secuencia de columnas en el archivo CSV. Para ello creamos un CsvSchema
			 * Builder y especificamos las cabeceras para que coincidan con el JSON
			 */
			Builder csvSchemaBuilder = new CsvSchema.Builder();
			JsonNode firstObject = jsonTree.elements().next();
			firstObject.fieldNames().forEachRemaining(fieldName -> {csvSchemaBuilder.addColumn(fieldName);} );
			CsvSchema csvSchema = csvSchemaBuilder.build().withHeader();
			
			/*
			 * Ahora creamos un CsvMapper con el CsvSchema, y escribimos el
			 * jsonTree al archivo CSV 
			 */
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
		 File input = new File(ruta);
	      try {
	         CsvSchema csv = CsvSchema.emptySchema().withHeader();
	         CsvMapper csvMapper = new CsvMapper();
	         ObjectMapper mapper = new ObjectMapper();
	         MappingIterator<Map<?, ?>> mappingIterator =  csvMapper.reader().forType(Map.class).with(csv).readValues(input);
	         List<Map<?, ?>> list = mappingIterator.readAll();        
	         mapper.writeValue(Paths.get("resultado.json").toFile(), list);
			
	      } catch(Exception e) {
	         e.printStackTrace();
	      }
	}

}
