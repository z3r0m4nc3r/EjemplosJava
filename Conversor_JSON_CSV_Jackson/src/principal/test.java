package principal;

import java.util.Scanner;

import service.ConversorService;

public class test {

	public static void main(String[] args) {
		ConversorService service = new ConversorService();
		String ruta;
		System.out.println("Introduzca ruta del archivo JSON: ");
		Scanner sc = new Scanner(System.in);
		ruta = sc.nextLine();
		service.jsonAcsv(ruta);
		sc.close();
		
		service.csvAJson("resultado.csv");

	}

}
