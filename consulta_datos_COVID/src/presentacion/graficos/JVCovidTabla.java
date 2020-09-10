package presentacion.graficos;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.opencsv.CSVWriter;

import presentacion.adaptadores.TablaCovidModel;
import principal.JVInicio;
import service.CovidService;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;

public class JVCovidTabla extends JFrame {

	private JPanel contentPane;
	private JTable table;

	
	public JVCovidTabla(Date fecha1, Date fecha2, String comunidad) {
		setTitle("Relacion de Casos");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 530, 640, 539);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(5, 5, 614, 403);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setModel(new TablaCovidModel(fecha1, fecha2, comunidad));
		scrollPane.setViewportView(table);
		
		JButton btnExportar = new JButton("Exportar a CSV");
		
		btnExportar.setBounds(252, 432, 125, 23);
		contentPane.add(btnExportar);
		btnExportar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String archCSV = "Casos_por_Comunidad.csv";
				try (CSVWriter writer = new CSVWriter(new FileWriter(archCSV))){
					String [] cabecera = {"nombreComunidad","fecha","positivos"};
					writer.writeNext(cabecera);
					
					CovidService.listaCasosComunidad().get(comunidad)
					.forEach(c -> writer.writeNext(CovidService.mapToArray(c)));
					JOptionPane.showMessageDialog(JVCovidTabla.this, "Lista creada en el archivo "+System.getProperty("user.dir")+System.getProperty("file.separator")+archCSV);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		
	}
}
