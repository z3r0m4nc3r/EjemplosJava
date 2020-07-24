package principal;

import java.time.LocalDate;
import java.time.Period;

public class TestPeriodo {

	public static void main(String[] args) {
		
		long f1 = 89488995L;
		long f2 = 22342367L;
		
		LocalDate ld1 = LocalDate.ofEpochDay(f1);
		LocalDate ld2 = LocalDate.ofEpochDay(f2);
		
		Period periodo = Period.between(ld2, ld1);
		
		System.out.println(periodo);
		

	}

}
