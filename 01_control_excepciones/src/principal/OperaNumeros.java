package principal;

import java.util.Scanner;

public class OperaNumeros {

	public static void main(String[] args) {
		
		int numerador=0, denominador=0, resultado=0;
		Scanner sc = new Scanner(System.in);
		
		try {
			System.out.println("Introduce numerador: ");
			numerador = Integer.parseInt(sc.nextLine());
			System.out.println("Introduce denominador: ");
			denominador = Integer.parseInt(sc.nextLine());
			
			resultado = numerador / denominador;
		} catch (NumberFormatException e) {
			System.out.println(e.getMessage());
			
		} catch (ArithmeticException e) {
			System.out.println(e.getMessage());
			
		} 
		
		System.out.println("Programa finalizado");
		
		System.out.println("La division es: "+ resultado);

	}

}
