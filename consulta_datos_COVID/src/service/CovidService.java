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
		.filter(c -> c.getFecha().getTime()>=fecha1.getTime()&&c.getFecha().getTime()<=fecha2.getTime())
		.collect(Collectors.toList());
	}
	
	public Date picoContagios () {
		
		return Utilidades.crearStream().collect(Collectors.groupingBy(c -> c.getFecha()))
		 .values().stream() .max((c1,c2) -> c1.stream().mapToLong(e ->
		 e.getPositivos()).sum() < c2.stream().mapToLong(e ->
		 e.getPositivos()).sum()?-1:1) 
		 .get().get(0).getFecha();
	}
	
	public double mediaPositivosDiarios() {
		return Utilidades.crearStream()
				.collect(Collectors.groupingBy(c -> c.getFecha()))
				.values().stream()
				.collect(Collectors.averagingDouble(lc -> lc.stream()
						.mapToDouble(c -> c.getPositivos()).sum()))
				.doubleValue();
					
	}
	
	public long totalPositivosComunidad(String comunidad) {
		return Utilidades.crearStream()
				.filter(c -> c.getNombreComunidad().toLowerCase().contentEquals(comunidad.toLowerCase())
						|c.getNombreComunidad().toLowerCase().endsWith(comunidad))
				.mapToLong(c -> c.getPositivos()).sum();
		
	}
	
	public long totalPositivosDia (Date fecha) {
		return Utilidades.crearStream().collect(Collectors.groupingBy(c -> c.getFecha()))
				.get(fecha).stream().mapToLong(e -> e.getPositivos()).sum();
				
	}
	
	public long totalPositivosPais() {
		return Utilidades.crearStream()
				.mapToLong(c -> c.getPositivos())
				.sum();
	}
	
	public Map<String,List<Caso>> listaCasosComunidad(){
		return Utilidades.crearStream()
		.collect(Collectors.groupingBy(c -> c.getNombreComunidad()));
	}
}
