package service;

import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Datos  {
	static String driver;
	static String cadenaConexion;
	static String user;
	static String password;
	static String FILE="config.json";
	static {
		
		
		
		try {
			cargarPropiedades();
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (JsonParseException e) {
			
			e.printStackTrace();
		} catch (JsonMappingException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(cadenaConexion, user, password);
	}

	private static void cargarPropiedades() throws JsonParseException, JsonMappingException, IOException {
		
		 ObjectMapper objectMapper = new ObjectMapper();
		 ObjectNode conn = objectMapper.readValue(Paths.get("config.json").toFile(), ObjectNode.class);
		 driver = conn.get("driver").asText(); 
		 cadenaConexion = conn.get("cadenaConexion").asText(); 
		 user =	 conn.get("user").asText();
		 password =	 conn.get("password").asText();

	}
}
