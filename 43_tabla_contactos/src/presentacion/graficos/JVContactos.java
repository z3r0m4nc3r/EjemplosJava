package presentacion.graficos;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.Contacto;
import presentacion.adaptadores.TablaContactosModel;
import service.ContactosService;

import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

public class JVContactos extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4886218561248164456L;
	private JPanel contentPane;
	private JTable tableContactos;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JVContactos frame = new JVContactos();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public JVContactos() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 77, 414, 173);
		contentPane.add(scrollPane);
		
		tableContactos = new JTable();
		tableContactos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableContactos.setModel(new TablaContactosModel());
		
		scrollPane.setViewportView(tableContactos);
		
		JLabel lblEtiqueta1 = new JLabel("Tabla de Contactos");
		lblEtiqueta1.setHorizontalAlignment(SwingConstants.CENTER);
		lblEtiqueta1.setBounds(145, 52, 151, 14);
		contentPane.add(lblEtiqueta1);
		
		
		
		
	}
}
