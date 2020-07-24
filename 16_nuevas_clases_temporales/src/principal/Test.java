package principal;

import java.time.LocalDate;

public class Test {

	public static void main(String[] args) {
		/*
		 * Los objetos LocalDate son inmutables cade vez que utilizamos un metodo no
		 * modifica el objeto sino que genera uno nuevo:
		 */
		
		LocalDate ld = LocalDate.of(2004, 8, 19);
		LocalDate ld2 = ld.plusDays(20);
		LocalDate ld3 = ld.withDayOfMonth(30);
		System.out.println(ld);
		System.out.println(ld2);
		System.out.println(ld3);

	}

}
