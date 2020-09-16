package service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Base64;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Datos  {
	static String driver;
	static String cadenaConexion;
	static String user;
	static String password;
	static String FILE="Y29uZmlnLmpzb24=";
	
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
		String lineaActual, resultado;
		CodeService service = new CodeService();
		BufferedReader reader =null;
		
		reader = new BufferedReader(new FileReader("Artesta.sic"));
		while((lineaActual=reader.readLine()) !=null) {
			try {
				service.setaesKey(new String(Base64.getDecoder().decode(lineaActual.getBytes())));
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}
		reader.close();
		FileOutputStream fout = new FileOutputStream("temp.json",true);
		PrintStream out = new PrintStream(fout);
		
		reader = new BufferedReader(new FileReader(FILE));
		while((lineaActual=reader.readLine()) !=null) {
			try {
				resultado = service.decrypt(lineaActual);
				out.println(resultado);
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			
		}

		reader.close();
		out.close();
		
		 File temp = new File("temp.json");
		 ObjectMapper objectMapper = new ObjectMapper();
		 ObjectNode conn = objectMapper.readValue(temp, ObjectNode.class);
		 driver = conn.get("driver").asText(); 
		 cadenaConexion = conn.get("cadenaConexion").asText(); 
		 user =	 conn.get("user").asText();
		 password =	 conn.get("password").asText();
		 temp.delete();

	}
}
