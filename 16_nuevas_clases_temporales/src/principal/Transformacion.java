package principal;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Transformacion {

	public static void main(String[] args) {
		
		// Convertir de Date a LocalDate
		
		Date f=new Date();
		LocalDate ld=Instant.ofEpochMilli(f.getTime()) //Instant
				.atZone(ZoneId.systemDefault()) //ZoneDateTime
				.toLocalDate(); //a LocalDate
		System.out.println(ld);
		
		//Convertir de LocalDate a Date
		
		LocalDate local=LocalDate.of(1999, 9, 25);
		Date fe=Date.from(
				local.atStartOfDay(ZoneId.systemDefault())
				.toInstant());
		System.out.println(fe);


	}

}
