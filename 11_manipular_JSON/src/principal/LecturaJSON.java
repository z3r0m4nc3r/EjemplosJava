package principal;

import java.util.stream.Stream;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class LecturaJSON {

	public static void main(String[] args) {
		String json="[{\"numeroPedido\":200, \"producto\":\"Galletas\",\"precio\":20.5}," + 
				"{\"numeroPedido\":100, \"producto\":\"Balón\",\"precio\":2.2}]";
		
		//creamos objeto JSONParse
		JSONParser parse = new JSONParser();
		
		try {
			JSONArray array = (JSONArray)parse.parse(json);
			Stream <JSONObject> st = (Stream <JSONObject>)array.stream();
			st.forEach(ob -> System.out.println(ob.get("producto")));
			
			
		} catch (ParseException e) {
			e.printStackTrace();
		}


	}

}
