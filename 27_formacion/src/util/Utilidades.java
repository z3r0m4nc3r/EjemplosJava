package util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Scanner;

public class Utilidades {
	
	public static LocalDate dateToLocalDate(Date d) {
		return Instant.ofEpochMilli(d.getTime()) //Instant
				.atZone(ZoneId.systemDefault()) //ZoneDateTime
				.toLocalDate(); //a LocalDate
	}

	public static Date localDateToDate (LocalDate ld) {
		return Date.from(
				ld.atStartOfDay(ZoneId.systemDefault())
				.toInstant());
	}

	public static void pulsarTeclaParaContinuar(){
	    
	   
	    try{
	    	Scanner sc = new Scanner(System.in);
	    	 System.out.println("");
	    	 System.out.println("Pulsa Enter para continuar...");
	         sc.nextLine();
	         sc.close();
	     
	        
	    }
	    catch(Exception e){
	    	
	    }
	   
	}
	

}
