package service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import model.Caso;
import util.Utilidades;

public class CovidService {
		
	public CovidService() {
				
	}
	
	public List<Caso> listaCasos (Date fecha1, Date fecha2){
		return Utilidades.crearStream().map(c-> Utilidades.crearCaso(c))
		.filter(c -> c.getFecha().before(fecha1)&&c.getFecha().after(fecha2))
		.collect(Collectors.toList());
	}
	
	public int totalPositivosDia (Date fecha) {
		return Utilidades.crearStream().map(c-> Utilidades.crearCaso(c))
		.filter(c -> c.getFecha().equals(fecha))
		.max((c1,c2) -> c1.getPositivos()-c2.getPositivos())
		.orElse(null).getPositivos();
	}
	
	public Date picoContagios () {
		return Utilidades.crearStream().map(c->Utilidades.crearCaso(c))
		.max((c1,c2) -> c1.getPositivos()-c2.getPositivos())
		.orElse(null).getFecha();
		
	}
	
	public double mediaPositivosDiarios() {
		return Utilidades.crearStream()
				.mapToDouble(c -> (double)c.get("num_casos"))
				.average().orElse(0);
		
	}
	
	public int totalPositivosComunidad(String comunidad) {
		return Utilidades.crearStream()
				.filter(c -> c.get("ccaa_iso").equals(comunidad))
				.mapToInt(c -> (int)c.get("num_casos"))
				.sum();
		
	}
	
	public Map<String,List<Caso>> listaCasosComunidad(){
		return Utilidades.crearStream().map(c-> Utilidades.crearCaso(c))
		.collect(Collectors.groupingBy(c -> c.getNombreComunidad()));
	}
}
