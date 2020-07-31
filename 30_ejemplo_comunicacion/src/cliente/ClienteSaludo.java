package cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.net.Socket;

import model.Persona;

public class ClienteSaludo {

	public static void main(String[] args) {
		try(Socket sc=new Socket("localhost",8000);){
			PrintStream out=new PrintStream(sc.getOutputStream());
			out.println("profe");
			//BufferedReader bf=new BufferedReader(new InputStreamReader(sc.getInputStream()));
			ObjectInputStream ob=new ObjectInputStream(sc.getInputStream());
			Persona p=(Persona)ob.readObject();
			System.out.println("Soy: "+p.getNombre()+" - "+p.getEdad());
		}
		catch(IOException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
