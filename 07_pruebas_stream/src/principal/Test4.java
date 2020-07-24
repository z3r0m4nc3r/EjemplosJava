package principal;

import java.util.Optional;
import java.util.stream.Stream;

public class Test4 {

	public static void main(String[] args) {
		Stream <String> nombre = Stream.of("Pepe","Lucas","Ana","Marcos","Pepe","Marta","Juan","Ana","Juanjo");
		nombre
			  .distinct() //eliminamos duplicados
			  .limit(5)   //limitamos a los 5 primeros
			  .forEach(n -> System.out.println(n));  //recorremos e imprimimos
		
		System.out.println("*************");
		
		Stream <String> nombre3 = Stream.of("Pepe","Lucas","Ana","Marcos","Pepe","Marta","Juan","Ana","Juanjo");
		
		Optional <String> opt = nombre3.max((n1,n2) -> n1.length() - n2.length());
		
		System.out.println(opt.get());
		
		System.out.println("*************");
		
		Stream<String> cursos=Stream.of("Principios de Java","JavaScript","PHP",".NET","c++","Aprenda Java ya","Python","Java EE","Todo Java","Linux");
		
		System.out.println(cursos
			  .map(s -> s.toLowerCase())
		      .filter(s -> s.contains("Java"))
		      .sorted()
		      .findFirst().orElse("No encontrado")); // orElse metodo de Optional que da el contenido o si no la cadena que pongamos
		
		
		


	}

}
