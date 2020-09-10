package principal;

import java.awt.EventQueue;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.Caso;
import presentacion.graficos.*;
import service.CovidService;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

public class JVInicio extends JFrame {

	
	private static final long serialVersionUID = -2941550157403693402L;
	private JPanel contentPane;
	static final String NAME="datos_ccaas.csv";


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JVInicio frame = new JVInicio();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public JVInicio() {
		setResizable(false);
		setTitle("Informes Pandemia COVID-19");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(1450, 10, 383, 301);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnConsultas = new JMenu("Consultas");
		mnConsultas.setFont(new Font("Segoe UI", Font.BOLD, 14));
		menuBar.add(mnConsultas);
		
		JMenuItem mntmComunidad = new JMenuItem("Por Comunidad");
		
		mnConsultas.add(mntmComunidad);
		
		JMenu mnEstadisticas = new JMenu("Estadisticas");
		mnEstadisticas.setFont(new Font("Segoe UI", Font.BOLD, 14));
		menuBar.add(mnEstadisticas);
		
		JMenuItem mntmInformes = new JMenuItem("Informes");
	
		
		mnEstadisticas.add(mntmInformes);
		
		JMenu mnSistema = new JMenu("Sistema");
		mnSistema.setFont(new Font("Segoe UI", Font.BOLD, 14));
		menuBar.add(mnSistema);
		
		JMenuItem mntmActualizarDatos = new JMenuItem("Actualizar Datos");
		
		mnSistema.add(mntmActualizarDatos);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Datos Disponibles");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel.setBounds(10, 11, 116, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblFechaInicio = new JLabel("");
		lblFechaInicio.setBounds(136, 11, 82, 14);
		SimpleDateFormat sdt = new SimpleDateFormat("dd/MM/yyyy");
		lblFechaInicio.setText(sdt.format(CovidService.crearStreamSQL().map(c -> c.getFecha()).findFirst().get()));
		contentPane.add(lblFechaInicio);
		
		JLabel lblNewLabel_1 = new JLabel("hasta");
		lblNewLabel_1.setBounds(239, 11, 48, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblFechaFin = new JLabel("");
		lblFechaFin.setBounds(297, 11, 82, 14);
		lblFechaFin.setText(sdt.format(CovidService.crearStreamSQL().map(c -> c.getFecha())
		.max((c1,c2) ->c1.getTime()<c2.getTime()?-1:1).get()));
		contentPane.add(lblFechaFin);
		
		JLabel lblNewLabel_2 = new JLabel("New label");
		lblNewLabel_2.setIcon(new ImageIcon(JVInicio.class.getResource("/presentacion/graficos/covidIcon.jpg")));
		lblNewLabel_2.setBounds(102, 36, 185, 179);
		contentPane.add(lblNewLabel_2);
		
		mntmComunidad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							JVCovidPrincipal frame = new JVCovidPrincipal();
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		
		mntmActualizarDatos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int b = 0;
				String ruta;
				String url ="https://cnecovid.isciii.es/covid19/resources/datos_ccaas.csv";
				File file =  new File(System.getProperty("user.dir")+System.getProperty("file.separator")+System.getProperty("file.separator")+ NAME);
				URLConnection conn; 
				try { 
					conn = new  URL(url).openConnection(); 
					conn.connect();
					InputStream in = conn.getInputStream(); 
					OutputStream out = new	FileOutputStream(file); 
						
					while (b != -1) { 
						b = in.read(); 
						if (b != -1) { 
							out.write(b);
						} 
					}
					out.close(); 
					in.close();
					ruta=file.getAbsolutePath();
					
					CovidService.setRuta(ruta);
					List <Caso> listaCompleta = new ArrayList<Caso>();
					listaCompleta = CovidService.crearStream().collect(Collectors.toList());
					if(CovidService.grabarCasos(listaCompleta)) {
						JOptionPane.showMessageDialog(JVInicio.this, "Base de Datos actualizada con exito");
						
					}else JOptionPane.showMessageDialog(JVInicio.this, "Error!! No se pudo actualizar");
				}
				
				catch (MalformedURLException ex1) {

					ex1.printStackTrace(); 
				} 
				catch (IOException ex1) {

					ex1.printStackTrace(); 
				}
			}
		});
		
		mntmInformes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							JVCovidInforme frame = new JVCovidInforme(CovidService.mediaPositivosDiarios(),CovidService.picoContagios());
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
	}
}
