package service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import model.PedidoTienda;
import util.Utilidades;

public class GestorPedidosTotales {
	Path path;
	String RUTA="c:\\temp\\pedidos_totales.txt";
	public GestorPedidosTotales() {
		path=Paths.get(RUTA);
		if(!Files.exists(path)) {
			try {
				Files.createFile(path);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void grabarPedidos(List<PedidoTienda> pedidos) {
		pedidos.forEach(p->
			{
				try {						
						Files.writeString(path, Utilidades.pedidoTiendaToString(p)+System.lineSeparator(), 
								StandardCharsets.UTF_8,
								StandardOpenOption.APPEND);
					
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
		);
	} 
}
