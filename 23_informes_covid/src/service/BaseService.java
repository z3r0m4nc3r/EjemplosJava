package service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import model.Caso;

public abstract class BaseService {
	
	public abstract Stream<Caso> streamCaso();
	public List<Caso> casosEntreFechas(Date fechaini, Date fechafin){
		return streamCaso()
			.filter(c->(c.getFecha().getTime()>=fechaini.getTime())&&(c.getFecha().getTime()<=fechafin.getTime()))
			.collect(Collectors.toList());
	}
	
	public int casosEnDia(Date fecha) {
		return streamCaso()
			.filter(c->c.getFecha().compareTo(fecha)==0)
			//.collect(Collectors.summingInt(c->c.getPositivos()));
			.collect(Collectors.summingInt(Caso::getPositivos));
	}
	
	public Date picoContagios() {
		/*Map<Date,List<Caso>> grupoPorFecha= streamCaso()
				.collect(Collectors.groupingBy(Caso::getFecha));*/
		return streamCaso()
				.collect(Collectors.groupingBy(Caso::getFecha))
				.keySet().stream()
				/*.max((d1,d2)->grupoPorFecha.get(d1).stream().mapToInt(Caso::getPositivos).sum()-
					grupoPorFecha.get(d2).stream().mapToInt(Caso::getPositivos).sum())
				.get();*/
				.max((d1,d2)->casosEnDia(d1)-casosEnDia(d2))
				.get();
			
	}
	
	public int mediaPositivos() {
		Map<Date,List<Caso>> grupoPorFecha= streamCaso()
				.collect(Collectors.groupingBy(Caso::getFecha));
		return grupoPorFecha.values().stream()
			.collect(Collectors.averagingInt(l->l.stream().mapToInt(Caso::getPositivos).sum()))
			.intValue();			
	}
	
	public int totalPositivosComunidad(String nombre) {
		return streamCaso()
			.filter(c->c.getComunidad().equals(nombre))
			.mapToInt(Caso::getPositivos)
			.sum();
	}
	
	public Map<String,List<Caso>> casosPorComunidad(){
		return streamCaso()
				.collect(Collectors.groupingBy(Caso::getComunidad));
	}
	
}
