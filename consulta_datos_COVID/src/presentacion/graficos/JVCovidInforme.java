package presentacion.graficos;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JVCovidInforme extends JFrame {

	private JPanel contentPane;

	
	public JVCovidInforme(Long media, Date fecha) {
		setTitle("Informe de datos generales");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(740, 50, 640, 480);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnConsultas = new JMenu("Consultas");
		menuBar.add(mnConsultas);
		
		JMenuItem mntmComunidad = new JMenuItem("Por Comunidad");
		mnConsultas.add(mntmComunidad);
		
		JMenu mnEstadisticas = new JMenu("Estadisticas");
		menuBar.add(mnEstadisticas);
		
		JMenuItem mntmInformes = new JMenuItem("Informes");
		mnEstadisticas.add(mntmInformes);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Media de contagios diarios:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(10, 11, 232, 27);
		contentPane.add(lblNewLabel);
		
		JLabel lblFechaDelPico = new JLabel("Fecha del Pico de Contagios:");
		lblFechaDelPico.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblFechaDelPico.setBounds(10, 49, 232, 27);
		contentPane.add(lblFechaDelPico);
		
		JLabel lblMedia = new JLabel("");
		lblMedia.setBounds(252, 19, 176, 14);
		lblMedia.setText(media.toString());
		contentPane.add(lblMedia);
		
		JLabel lblPico = new JLabel("");
		lblPico.setBounds(252, 57, 176, 14);
		SimpleDateFormat sdt = new SimpleDateFormat("dd/MM/yyyy");
		lblPico.setText(sdt.format(fecha));
		contentPane.add(lblPico);
	}
}
