package presentacion.graficos;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class JVentana extends JFrame {
	public JVentana () {
		// Titulo de la ventana
		super("Primera ventana");
		// Tamaño y posicion
		this.setBounds(200, 100, 700, 400);
		// Boton de cerrar cierra
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		// Metodo de componentes
		iniciarComponentes();
		// Visualizar la ventana
		this.setVisible(true);
		
	}
	
	private void iniciarComponentes() {
		// eliminamos el Layout (gestor de organizacion)
		this.setLayout(null);
		JButton jb1= new JButton("Boton");
		JButton jb2= new JButton("Salir");
		JLabel jl1 = new JLabel("Pasa por encima");
		jl1.setBounds(200, 200, 200, 100);
		jl1.setBackground(Color.RED);
		jl1.setOpaque(true);
		jb1.setBounds(100, 50, 150, 40);
		jb2.setBounds(300, 50, 150, 40);
		
		// añadir el control al contenedor (ventana)
		this.add(jb1);
		this.add(jb2);
		this.add(jl1);
		
		//gestion de eventos
			//implementamos manejador
		//ActionListener listener2 = e -> this.dispose();
		//ActionListener listener = e -> jb1.setText("ha pulsado");
		
		MouseListener listener3 = new MouseAdapter() {
			
			@Override
			public void mouseExited(MouseEvent e) {
				jl1.setBackground(Color.RED);
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				jl1.setBackground(Color.BLUE);
				
			}
		};
		
		//asociamos el manejador al evento de una fuente
		jb1.addActionListener(e -> jb1.setText("ha pulsado"));
		jb2.addActionListener(e -> this.dispose());
		jl1.addMouseListener(listener3);
	}

}
