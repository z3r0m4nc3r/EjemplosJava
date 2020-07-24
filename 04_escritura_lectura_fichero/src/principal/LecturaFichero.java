package principal;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LecturaFichero {

	public static void main(String[] args) {
		String ruta ="C:\\CursoJavaTemp\\pruebas.txt";
		try (FileReader fr = new FileReader(ruta);
				BufferedReader bf = new BufferedReader(fr)) {
			String linea;
			while ((linea=bf.readLine())!=null) {
				System.out.println(linea);
			}
		} catch (IOException e) {
			e.getMessage();
			e.printStackTrace();
		}

	}

}
