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
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
	
	private static Caso crearCaso(ObjectNode jo) {
		
		SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
		Date nuevaFecha=null;
		String nombreComunidad=jo.get("ccaa_iso").asText();
		
		try {
			nuevaFecha = sdt.parse(jo.get("fecha").asText());
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		
			return new Caso(nombreComunidad
					,nuevaFecha
					,(long)jo.get("num_casos").asLong());
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
	
	public boolean grabarCasos(List<Caso> casos) {
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
