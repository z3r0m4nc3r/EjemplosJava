package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import model.CaseJson;
import model.Caso;

public class Utilidades {
	static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	static HashMap<String, String> comunidades = new HashMap<>();
	static {
		comunidades.put("AN", "Andaluc�a");
		comunidades.put("AR", "Arag�n");
		comunidades.put("AS", "Asturias");
		comunidades.put("CN", "Canarias");
		comunidades.put("CB", "Cantabria");
		comunidades.put("CM", "Castilla-La Mancha");
		comunidades.put("CL", "Castilla y Le�n");
		comunidades.put("CT", "Catalu�a");
		comunidades.put("EX", "Extremadura");
		comunidades.put("GA", "Galicia");
		comunidades.put("IB", "Islas Baleares");
		comunidades.put("RI", "La Rioja");
		comunidades.put("MD", "Madrid");
		comunidades.put("MC", "Murcia");
		comunidades.put("NC", "Navarra");
		comunidades.put("PV", "Pa�s Vasco");
		comunidades.put("VC", "Valenciana");
		comunidades.put("CE", "Ceuta");
		comunidades.put("ML", "Melilla");
	}
	
	public static Caso stringToCaso(String f) {
		String[] partes=f.split("[,]");
		try {
			return new Caso(convertirAbreviaturaNombre(partes[0]),
					sdf.parse(partes[1]),
					Integer.parseInt(partes[2]));
		}
		catch(ParseException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	public static String convertirAbreviaturaNombre(String abr) {
		
			
		return comunidades.get(abr);
	}
	
	public static Caso convertirJsonACaso(CaseJson json) {
		return new Caso(convertirAbreviaturaNombre(json.getCcaa_iso()),json.getFecha(),json.getNum_casos());
	}
}
