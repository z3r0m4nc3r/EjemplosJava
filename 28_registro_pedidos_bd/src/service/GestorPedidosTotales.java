package service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import model.Pedido;
import model.PedidoTienda;

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
		try (Connection con = Datos.getConnection()) {
			
			String sql = "INSERT INTO pedidos(tienda, producto, unidades, precioUnitario, seccion, fecha) VALUES(?,?,?,?,?,?)";
			
			PreparedStatement st = con.prepareStatement(sql);
			
			// El primer campo representa siempre la tienda
			
			st.setString(1, pedidos.get(0).getTienda());
			
			// Por cada pedido, lo insertamos en la BD
			
			for (Pedido p : pedidos) {
				
				// La fecha hay que pasarla en sql.Date

				st.setString(2, p.getProducto());
				st.setInt(3, p.getUnidades());
				st.setDouble(4, p.getPrecioUnitario());
				st.setString(5, p.getSeccion());
				st.setDate(6, new java.sql.Date(p.getFecha().getTime()));
				
				st.executeUpdate();
			}
		
		} catch (SQLException e) {

			e.printStackTrace();
		}
	

	} 
}
