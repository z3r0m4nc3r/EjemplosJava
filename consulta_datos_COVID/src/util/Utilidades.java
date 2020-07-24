package util;

import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Stream;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import model.Caso;

public class Utilidades {
	
	public static Stream <JSONObject> crearStream(){
		final String RUTA ="datos_ccaas.json";
		JSONParser parse = new JSONParser();
		try {
			JSONArray array = (JSONArray)parse.parse(new FileReader(RUTA));
			return (Stream <JSONObject>)array.stream();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
public static Caso crearCaso(JSONObject jo) {
	
	SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
	Date nuevaFecha=null;
	String nombreComunidad;
	
	 switch ((String)jo.get("ccaa_iso")){
	 case "AN":
		 nombreComunidad="Andalucia";
		 break;
		 
		
	}
	try {
		nuevaFecha = sdt.parse((String) jo.get("fecha"));
	} catch (java.text.ParseException e) {
		e.printStackTrace();
	}
	
		return new Caso((String)jo.get("ccaa_iso")
				,nuevaFecha
				,(int)jo.get("num_casos"));
	}

}
