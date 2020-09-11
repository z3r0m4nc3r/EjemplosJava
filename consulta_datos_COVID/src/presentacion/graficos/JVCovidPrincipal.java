package presentacion.graficos;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Dimension;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import com.toedter.calendar.JDateChooser;

import service.CovidService;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class JVCovidPrincipal extends JFrame {

	private static final long serialVersionUID = 8619563786568252973L;
	private JPanel contentPane;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTable tableCasos;
	
	public JVCovidPrincipal() {
		setTitle("Seleccion Territorio y Fechas");
		
		setSize(new Dimension(640, 480));
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 50, 639, 477);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JComboBox <String>comboBoxComunidades = new JComboBox<String>();
		
		comboBoxComunidades.setModel(new DefaultComboBoxModel<String>(CovidService.arrayComunidades()));
		comboBoxComunidades.setBounds(10, 44, 176, 22);
		contentPane.add(comboBoxComunidades);
		
		JLabel lblNewLabel = new JLabel("Seleccione comunidad");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 19, 176, 22);
		contentPane.add(lblNewLabel);
		
		JRadioButton rdbtnTodas = new JRadioButton("Rango de Fechas Completo");
		rdbtnTodas.setSelected(true);
		buttonGroup.add(rdbtnTodas);
		rdbtnTodas.setBounds(321, 44, 274, 23);
		contentPane.add(rdbtnTodas);
		
		JRadioButton rdbtnRango = new JRadioButton("Seleccionar Fechas");
		
		
		buttonGroup.add(rdbtnRango);
		rdbtnRango.setBounds(321, 70, 274, 23);
		contentPane.add(rdbtnRango);
		
		JButton btnMostrar = new JButton("Mostrar Casos");
		
		btnMostrar.setBounds(379, 153, 113, 23);
		contentPane.add(btnMostrar);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(599, 416, -403, -199);
		contentPane.add(scrollPane);
		
		tableCasos = new JTable();
		tableCasos.setBounds(0, 0, 1, 1);
		contentPane.add(tableCasos);
		
		JPanel panel = new JPanel();
		panel.setVisible(false);
		panel.setBounds(191, 100, 404, 42);
		contentPane.add(panel);
		
		JDateChooser dateChooserInicio = new JDateChooser();
		dateChooserInicio.setDate(CovidService.crearStreamSQL().map(c -> c.getFecha()).findFirst().get());
		dateChooserInicio.setMaxSelectableDate(CovidService.crearStreamSQL().map(c -> c.getFecha())
		.max((c1,c2) ->c1.getTime()<c2.getTime()?-1:1).get());
		dateChooserInicio.setMinSelectableDate(CovidService.crearStreamSQL().map(c -> c.getFecha()).findFirst().get());
		panel.add(dateChooserInicio);
		
		JDateChooser dateChooserFin = new JDateChooser();
		dateChooserFin.setDate(CovidService.crearStreamSQL().map(c -> c.getFecha())
		.max((c1,c2) ->c1.getTime()<c2.getTime()?-1:1).get());
		dateChooserFin.setMaxSelectableDate(CovidService.crearStreamSQL().map(c -> c.getFecha())
				.max((c1,c2) ->c1.getTime()<c2.getTime()?-1:1).get());
				dateChooserFin.setMinSelectableDate(CovidService.crearStreamSQL().map(c -> c.getFecha()).findFirst().get());
		panel.add(dateChooserFin);
		
		JLabel lblNewLabel_1 = new JLabel("Seleccione Rango de Fechas");
		lblNewLabel_1.setBounds(321, 23, 171, 15);
		contentPane.add(lblNewLabel_1);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JPanel panel_1 = new JPanel();
		panel_1.setVisible(false);
		panel_1.setBounds(10, 208, 613, 218);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNombreComunidad = new JLabel("");
		lblNombreComunidad.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNombreComunidad.setBounds(10, 11, 161, 32);
		panel_1.add(lblNombreComunidad);
		
		JLabel lblNewLabel_2 = new JLabel("Total Positivos:");
		lblNewLabel_2.setBounds(10, 54, 97, 14);
		panel_1.add(lblNewLabel_2);
		
		JLabel lblTotalPositivos = new JLabel("");
		lblTotalPositivos.setBounds(123, 54, 48, 14);
		panel_1.add(lblTotalPositivos);
		
		rdbtnRango.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(rdbtnRango.isSelected()) {
					panel.setVisible(true);
				}else panel.setVisible(false);
			}
		});
		
		btnMostrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							JVCovidTabla frame = new JVCovidTabla(dateChooserInicio.getDate(),dateChooserFin.getDate(),comboBoxComunidades.getSelectedItem().toString());
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		
		comboBoxComunidades.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel_1.setVisible(true);
				lblNombreComunidad.setText(comboBoxComunidades.getSelectedItem().toString());			
				lblTotalPositivos.setText(""+String.format("%,d",CovidService.totalPositivosComunidad(comboBoxComunidades.getSelectedItem().toString())));
			}
		});
		
	}
}
