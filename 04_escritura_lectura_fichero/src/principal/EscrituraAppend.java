package principal;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class EscrituraAppend {

	public static void main(String[] args) {
		//El directorio debe existir, el archivo se crea
		
		String ruta ="C:\\CursoJavaTemp\\pruebas.txt";
		 //true es append

		try (FileOutputStream fout = new FileOutputStream(ruta,true);
				PrintStream out = new PrintStream(fout)) {
			out.println("Es la linea 1");
			out.println("Es la linea 2: 200");
			out.println("Fin del texto");
			
		} catch (FileNotFoundException e) {
			e.getMessage();
			e.printStackTrace();
		} catch (IOException e) {
			e.getMessage();
			e.printStackTrace();
		}

	}

}