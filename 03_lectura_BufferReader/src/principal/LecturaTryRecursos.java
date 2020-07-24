package principal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LecturaTryRecursos {

	public static void main(String[] args) {

		String nombre;
		
		try(InputStreamReader isr = new InputStreamReader(System.in);
				BufferedReader bf=new BufferedReader(isr);) {
			System.out.println("Introduce tu nombre");
			nombre = bf.readLine();
			System.out.println("Te llamas "+nombre);
		} catch (IOException e) {
			e.getMessage();
			e.printStackTrace();
		}
		

}
}
