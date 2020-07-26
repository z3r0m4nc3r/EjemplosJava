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
		return Utilidades.crearStream()
		.filter(c -> c.getFecha().getTime()>fecha1.getTime()&&c.getFecha().getTime()<fecha2.getTime())
		.collect(Collectors.toList());
	}
	
	public long totalPositivosDia (Date fecha) {
		return Utilidades.crearStream()
		.filter(c -> c.getFecha().equals(fecha))
		.max((c1,c2) -> c1.getPositivos()<c2.getPositivos()?-1:1)
		.orElse(null).getPositivos();
	}
	
	public Date picoContagios () {
		return Utilidades.crearStream()
		.max((c1,c2) -> c1.getPositivos()<c2.getPositivos()?-1:1)
		.orElse(null).getFecha();
		
	}
	
	public double mediaPositivosDiarios() {
		return Utilidades.crearStream()
				.mapToDouble(c -> (double)c.getPositivos())
				.average().orElse(0);
		
	}
	
	public long totalPositivosComunidad(String comunidad) {
		return Utilidades.crearStream()
				.filter(c -> c.getNombreComunidad().toLowerCase()
						.contains(comunidad.toLowerCase()))
				.mapToLong(c -> c.getPositivos())
				.sum();
		
	}
	
	public Map<String,List<Caso>> listaCasosComunidad(){
		return Utilidades.crearStream()
		.collect(Collectors.groupingBy(c -> c.getNombreComunidad()));
	}
}
