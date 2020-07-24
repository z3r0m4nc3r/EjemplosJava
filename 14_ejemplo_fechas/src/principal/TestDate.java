package principal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TestDate {

	public static void main(String[] args) {
		//Ejemplo con clase java.util.Date
		
		System.out.println("Ejemplo con clase java.util.Date\n");
		Date fecha = new Date();
		System.out.println("Fecha de hoy   "+fecha);
		System.out.println("Fecha absoluta   "+fecha.getTime());
		System.out.println("*************************************\n");
		
		//Ejemplo con clase java.util.Calendar
		
		System.out.println("Ejemplo con clase java.util.Calendar\n");
		Calendar cal=Calendar.getInstance();
		int mes = cal.get(Calendar.MONTH)+1;
		String diaSemana;
		switch(cal.get(Calendar.DAY_OF_WEEK)) {
		case 1:
			diaSemana ="domingo";
			break;
		case 2:
			diaSemana ="lunes";
			break;
		case 3:
			diaSemana ="martes";
			break;
		case 4:
			diaSemana ="miercoles";
			break;
		case 5:
			diaSemana ="jueves";
			break;
		case 6:
			diaSemana ="viernes";
			break;
		case 7:
			diaSemana ="sabado";
			break;
		default:
			diaSemana ="dia desconocido";
			break;
		}
		System.out.println("Año: "+cal.get(Calendar.YEAR));
		System.out.println("Mes: "+mes);
		System.out.println("Día: "+diaSemana+" "+cal.get(Calendar.DAY_OF_MONTH));
		System.out.println("*************************************\n");
		
		System.out.println("Ejemplo con clase java.text.SimpleDateFormat\n");
		SimpleDateFormat sdt = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		System.out.println(sdt.format(fecha));
		
		System.out.println("*************************************\n");
		
		//parseado de fechas. ver documentacion de SimpleDateFormat
		
		String miFecha="25-12-2020";
		SimpleDateFormat sdt2 = new SimpleDateFormat("dd-MM-yyyy");
		try {
			Date nf = sdt2.parse(miFecha);
			System.out.println(nf);

		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		
	}

}
