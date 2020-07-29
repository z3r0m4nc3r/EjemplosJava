package principal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import model.Caso;
import service.BaseService;
import service.CsvService;

public class InformesCovid {

	public static void main(String[] args) throws ParseException {
		BaseService service=new CsvService("c:\\temp\\datos_5_7_2020.csv");
		String fecha1="2020-04-01";
		String fecha2="2020-04-05";
		String fecha3="2020-03-20";
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		
		List<Caso> casos=service.casosEntreFechas(sdf.parse(fecha1), sdf.parse(fecha2));
		System.out.println("-----Casos entre "+fecha1+" y "+fecha2+ "-------");
		casos.forEach(c->System.out.println(c.getComunidad()+"-"+sdf.format(c.getFecha())+"-"+c.getPositivos()));
		System.out.println("-----Positivos el "+fecha3+ "-------");
		System.out.println(service.casosEnDia(sdf.parse(fecha3)));
		System.out.println("-----Pico de contagios-------");
		System.out.println(sdf.format(service.picoContagios()));
		System.out.println("-----Media positivos diarios-------");
		System.out.println(service.mediaPositivos());
		System.out.println("-----Total positivos Madrid-------");
		System.out.println(service.totalPositivosComunidad("Madrid"));
		System.out.println("-----Tabla casos comunidad-------");
		Map<String,List<Caso>> casosComunidades=service.casosPorComunidad();
		casosComunidades.forEach((k,v)->{
			System.out.print(k+":");
			System.out.println(service.totalPositivosComunidad(k));
		});
	}

}
