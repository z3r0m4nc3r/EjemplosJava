package principal;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PruebasStreams {

	//Pruebas con metodos para Stream. Cuando se usa el stream se consume
	
	public static void main(String[] args) {
		//metodo FINAL long count() para contar elementos
		Stream <Integer> numeros = Stream.of(1,2,3,4,5,6,7,8,9);	
		System.out.println("Hay "+numeros.count()+" Elementos");
		
		//metodo FINAL forEach
		Stream <Integer> numeros2 = Stream.of(1,2,3,4,5,6,7,8,9);
		numeros2.forEach(n -> System.out.print(" "+n));
		System.out.println("");
		
		//metodos FINALES boolean anyMatch(Predicate), allMatch(Predicate), noneMatch(Predicate)
		List <Integer> lista = Arrays.asList(1,2,30,45,5,6,7,8,9);
		Stream <Integer> numeros99 = lista.stream();
		final int limite = 10;
		System.out.println(numeros99.anyMatch(n -> n>limite)); // hay algun numero mayor de 10?
		
		// metodos FINALES max(Comparator) min(Comparator) devuelve el mayor o el menor de los elementos segun el Comparator
		// lo devuelve ecapsulado en la clase Optional
		Stream <String> nombre3 = Stream.of("Pepe","Lucas","Ana","Marcos","Pepe","Marta","Juan","Ana","Juanjo");
		
		Optional <String> opt = nombre3.max((n1,n2) -> n1.length() - n2.length());
		
		System.out.println(opt.get());
		
		// metodo FINAL findFirst() devuelve el primer elemento del stream o si esta vacio un Optional vacio
		Stream<String> cursos=Stream.of("Principios de Java","JavaScript","PHP",".NET","c++","Aprenda Java ya","Python","Java EE","Todo Java","Linux");

		System.out.println(cursos
				.filter(s -> s.toLowerCase().contains("Java"))
				.sorted()
				.findFirst().orElse("No encontrado")); // orElse metodo de Optional que da el contenido o si no la cadena que pongamos
		
		
		//metodo FINAL collect(Collector) devuelve un List, Map o Set con los datos del Stream
		Stream<String> cursos2=Stream.of("Principios de Java","JavaScript","PHP",".NET","c++","Aprenda Java ya","Python","Java EE","Todo Java","Linux");
		String palabra="Java";
		
		List <String> listaCursos = cursos2
			
				.filter(s -> s.toLowerCase().contains(palabra))
				.collect(Collectors.toList());
		
		//metodo INTERMEDIO distinct() devuelve un stream eliminando elementos duplicados
		//segun el metodo equals() del Tipo
		Stream <Integer> numeros3 = Stream.of(1,2,1,7,9,3,3,4,5,6,3,6,6,7,8,9);
		System.out.println("Hay "+numeros3
				                          .distinct()
		                                  .count()+
		                                  " Elementos eliminando duplicados");
		
		//metodo INTERMEDIO limit(long n) devuelve un stream con los primeros n elementos
		Stream <String> cadenas = Stream.of("kk","hola","que tal","como vamos");
		cadenas.limit(3).forEach(n -> System.out.println(n));
		
		//metodo INTERMEDIO skip(long n) devuelve un stream saltandose los primeros n elementos
		Stream <String> cadenaskk = Stream.of("kk","hola","que tal","como vamos");
		cadenaskk.skip(1).forEach(n -> System.out.println(n));
		//metodo INTERMEDIO filter(Predicate) devuelve un stream con los elementos que cumplen el Predicate
		Stream <Integer> numeros4 = Stream.of(1,2,1,7,9,3,3,4,5,6,3,6,6,7,8,9);
		numeros4
		        .distinct() //quito duplicados
		        .filter(n -> n%2==0) //filtro con una condicion
		        .forEach(n -> System.out.println(n)); //imprimo el stream
		//metodo INTERMEDIO sorted() y sorted(Comparator)
		Stream <Integer> numeros5 = Stream.of(10,222,1,76,9,3,3,4,3,45,5,6,3,32,62,6,7,8,9);
		numeros5
		        .distinct() //quito duplicados
		        .filter(n -> n%2==0) //filtro con una condicion
		        .sorted() //se ordenan de menor a mayor o .sorted((n1,n2) -> n2-n1) si lo queremos de mayor a menor
		        .forEach(n -> System.out.println(n)); //imprimimos
		//metodo INTERMEDIO map(Function) transforma el stream en otro definido por Function
		Stream <String> nombre = Stream.of("Pepe","Lucas","Ana","Marcos","Pepe","Marta","Juan","Ana","Juanjo");
		nombre
		      .distinct() //quito duplicados
		      .map(s -> s.length()) //transformo cada cadena en un int con su numero de caracteres
		      .forEach(n -> System.out.println(n)); //imprimo
		//metodo mapToint(TointFunciton) transforma el stream en un intStream segun Function que permite usar metodos propios
		Stream <String> nombre2 = Stream.of("Pepe","Lucas","Ana","Marcos","Pepe","Marta","Juan","Ana","Juanjo");
		System.out.println(nombre2
		      .distinct() //quito duplicados
		      .mapToInt(s -> s.length()) //transformo cada cadena en un int con su numero de caracteres
		      .sum());//suma todos los valores, METODO PROPIO DE intStream
	}

}
