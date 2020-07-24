package principal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Lectura {

	public static void main(String[] args) {

		String nombre;
		BufferedReader bf=null;
		try {
			InputStreamReader isr = new InputStreamReader(System.in);
			bf = new BufferedReader(isr);
			System.out.println("Introduce tu nombre");
			nombre = bf.readLine();
			System.out.println("Te llamas "+nombre);
		} catch (IOException e) {
			e.getMessage();
			e.printStackTrace();
		}
		finally {
			try {
				if(bf !=null) {
				bf.close();
				}
			} catch (IOException e) {
				e.getMessage();
				e.printStackTrace();
			}
		}

	}

}
