package principal;

import service.CovidService;

public class Test {

	public static void main(String[] args) {
		CovidService service = new CovidService();
		System.out.println(service.picoContagios());


	}

}
