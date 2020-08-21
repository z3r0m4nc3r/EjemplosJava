package principal;

import service.CovidService;

public class Presentacion {
static CovidService service = new CovidService();

	public static void main(String[] args) {
		//service.actualizarDatos();
		System.out.println("Media de muertes diarias por COVID-19 en el mundo "+service.mediaMuertesDiarias());
		System.out.println("Total muertes confirmadas por COVID-19 en España "+service.totalMuertesPais("Spain"));
	}

}
