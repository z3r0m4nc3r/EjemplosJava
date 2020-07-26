package util;

import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.stream.Stream;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import model.Caso;

public class Utilidades {
	
	public static Stream <Caso> crearStream(){
		final String RUTA ="datos_ccaas.json";
		JSONParser parse = new JSONParser();
		try {
			JSONArray array = (JSONArray)parse.parse(new FileReader(RUTA));
			Stream <JSONObject> streamJSON = (Stream <JSONObject>)array.stream();
			return streamJSON.map(c-> Utilidades.crearCaso(c));
		} catch (IOException | ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static String nombreComunidad(JSONObject jo) {
		String nombreComunidad ="";
		
		switch ((String)jo.get("ccaa_iso")){
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
	
public static Caso crearCaso(JSONObject jo) {
	
	SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
	Date nuevaFecha=null;
	String nombreComunidad=nombreComunidad(jo);
	
	try {
		nuevaFecha = sdt.parse((String) jo.get("fecha"));
	} catch (java.text.ParseException e) {
		e.printStackTrace();
	}
	
		return new Caso(nombreComunidad
				,nuevaFecha
				,(long)jo.get("num_casos"));
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

}
