package principal;

import service.PaisService;

public class test {

	public static void main(String[] args) {
		PaisService service = new PaisService();
		
		System.out.println(service.paisesMasPoblacion(10000000));
		
		service.paisesRegion("europe").forEach(p -> System.out.println(p.getName()));
		
		System.out.println("El pais mas poblado es: "+service.paisMasPoblado().getName()+" con una poblacion de: "+service.paisMasPoblado().getPopulation());
		
		
		}
	}


