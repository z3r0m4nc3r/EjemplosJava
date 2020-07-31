package servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;

import model.Persona;

public class HiloCliente implements Runnable {
	private Socket socket;
	public HiloCliente(Socket socket) {
		this.socket=socket;
	}
	@Override
	public void run() {
		try(ObjectOutputStream out=new ObjectOutputStream(socket.getOutputStream());
				BufferedReader bf=new BufferedReader(new InputStreamReader(socket.getInputStream()));) {
			String nombre=bf.readLine();
			Persona persona=new Persona(nombre,30);
			out.writeObject(persona);
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}
		finally {
			try {
				socket.close();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}

	}

}
