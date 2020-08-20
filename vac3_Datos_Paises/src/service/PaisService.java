package service;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import model.Pais;

public class PaisService {
	
	public List <Pais> paisesRegion(String region){
		return crearStream().filter(p -> p.getRegion()
				.toLowerCase().contentEquals(region.toLowerCase()))
				.collect(Collectors.toList());
	}
	
	public Pais paisMasPoblado() {
		
		return crearStream().max((p1,p2) -> p1.getPopulation()<p2.getPopulation()?-1:1).orElse(null);
	}
	
	public int paisesMasPoblacion(long population) {
		
		return (int) crearStream().filter(p -> p.getPopulation()>population).count();
	}
	
	public List <Pais> paisesContains(String letras){
		return crearStream().filter(p -> p.getName().toLowerCase()
				.contains(letras.toLowerCase()))
		.collect(Collectors.toList());
		
	}
	
	public double[] posicionPais(String alpha2code) {
		return crearStream().filter(p -> p.getAlpha2code().toLowerCase()
				.contentEquals(alpha2code.toLowerCase()))
				.findAny().get().getLatlng();
	}
	
	public long poblacionMedia(String region) {
		return Math.round(crearStream().filter(p -> p.getRegion().toLowerCase()==region.toLowerCase())
				.mapToLong(m -> m.getPopulation())
				.average().getAsDouble());
	}
	
	public Map <String,Long> tablaPoblacionRegion(){
		HashMap <String,Long> mapRegiones = new HashMap <>();
	
		List<String> listaregiones = crearStream().map(p -> p.getRegion()).collect(Collectors.toList());
		
		listaregiones.forEach(r -> mapRegiones.put(r,paisesRegion(r).stream()
				.mapToLong(p -> p.getPopulation()).sum()));
		
		return mapRegiones;
		
	}
	
	private static Stream <Pais> crearStream(){
		
		  ObjectMapper objectMapper = new ObjectMapper();
		  
		  try { ObjectNode[] lista =
		  objectMapper.readValue(Paths.get("paises.json").toFile(), ObjectNode[].class); 
		  return Arrays.stream(lista).map(ob -> crearPais(ob));
		  
		  } catch (JsonProcessingException e) {
		  
		  e.printStackTrace(); } catch (IOException e) {
		  
		  e.printStackTrace(); }
		  
		  return null;
		 
		
	}
	
	private static Pais crearPais(ObjectNode jo) {
		int i =0;
		double[] latlng = new double[2];
		JsonNode latlngNode = jo.get("latlng");
		Iterator<JsonNode> elements = latlngNode.elements();
		while(elements.hasNext()){
			JsonNode dato = elements.next();
			latlng [i] = dato.asDouble();
			i++;
		}	
			return new Pais(jo.get("name").asText()
					,jo.get("region").asText()
					,jo.get("population").asLong()
					,jo.get("alpha2Code").asText()
					,latlng);
		}

}
