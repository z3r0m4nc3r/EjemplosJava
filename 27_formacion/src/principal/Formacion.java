package principal;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import model.Curso;
import service.AlumnoService;
import service.CursosService;
import util.Utilidades;

public class Formacion {
	static CursosService service = new CursosService ();
	static AlumnoService alumnoService = new AlumnoService ();
	
	public static void main(String[] args) {
		
		System.out.println("Numero Total de cursos: "+service.totalCursos());
		Utilidades.pulsarTeclaParaContinuar();
		
		System.out.println("Alta de nuevo Curso:");
		nuevoCurso();
		Utilidades.pulsarTeclaParaContinuar();
		
		System.out.println("Lista Completa de Cursos:");
		mostrarCursos(service.recuperarCursos());
		Utilidades.pulsarTeclaParaContinuar();
		
		System.out.println("Lista de Cursos entre dos fechas dadas:");
		listaCursos();
		Utilidades.pulsarTeclaParaContinuar();

	}
	
	public static void nuevoCurso() {
		String denominacion;
		int duracion;
		LocalDate fechaInicio;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		System.out.println("Introduzca denominacion del curso:");
		denominacion = sc.nextLine();
		System.out.println("Introduzca duracion en horas del curso:");
		duracion = Integer.parseInt(sc.nextLine());
		System.out.println("Introduzca fecha de inicio del curso (dd/MM/yyyy):");
		fechaInicio = LocalDate.parse(sc.nextLine(), dtf);

		service.altaCurso(new Curso(denominacion,duracion,Utilidades.localDateToDate(fechaInicio)));
		System.out.println("Curso añadido con exito");
		
		
	}
	
	public static void mostrarCursos(List <Curso> listaCursos) {
		
		
		listaCursos.forEach(c -> {
				System.out.println("ID: "
				+c.getIdCurso()
				+" Denominacion: "
				+c.getDenominacion()
				+" Duracion: "
				+c.getDuracion()
				+" Fecha de Inicio: "
				+c.getFechaInicio());
		});
	}
	
	public static void listaCursos() {
		LocalDate fechaInicio;
		LocalDate fechaFin;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		System.out.println("Introduzca fecha desde (dd/MM/yyyy):");
		fechaInicio = LocalDate.parse(sc.nextLine(), dtf);
		System.out.println("Introduzca fecha hasta (dd/MM/yyyy):");
		fechaFin = LocalDate.parse(sc.nextLine(), dtf);
		
		mostrarCursos(service.listaCursos(Utilidades.localDateToDate(fechaInicio)
				, Utilidades.localDateToDate(fechaFin)));
		
	}
		
	}


