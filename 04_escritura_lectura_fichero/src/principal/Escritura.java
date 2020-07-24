package principal;

import java.io.FileNotFoundException;
import java.io.PrintStream;

public class Escritura {

	public static void main(String[] args) {
		//El directorio debe existir, el archivo se crea
		
		String ruta ="C:\\CursoJavaTemp\\pruebas.txt";

		try (PrintStream out = new PrintStream(ruta)) {
			out.println("Es la linea 1");
			out.println("Es la linea 2: 200");
			out.println("Fin del texto");
		} catch (FileNotFoundException e) {
			e.getMessage();
			e.printStackTrace();
		}

	}

}
