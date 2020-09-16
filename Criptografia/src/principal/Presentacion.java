package principal;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import service.CodeService;

import javax.swing.JFileChooser;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

public class Presentacion extends JFrame {

	private static final long serialVersionUID = -6024252720766252315L;
	private JPanel contentPane;
	private final JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
	private String ruta;
	CodeService service = new CodeService();
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Presentacion frame = new Presentacion();
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
	public Presentacion() {
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 799, 611);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnEncriptar = new JButton("Encriptar");
		
		btnEncriptar.setBounds(149, 92, 89, 23);
		contentPane.add(btnEncriptar);
		
		JButton btnDesencriptar = new JButton("Desencriptar");
		
		btnDesencriptar.setBounds(262, 92, 114, 23);
		contentPane.add(btnDesencriptar);
		
		JButton btnSeleccion = new JButton("Seleccionar archivo");
		
		btnSeleccion.setBounds(149, 11, 227, 23);
		contentPane.add(btnSeleccion);
		
		JLabel lblruta = new JLabel("");
		lblruta.setHorizontalAlignment(SwingConstants.CENTER);
		lblruta.setBounds(149, 45, 624, 14);
		contentPane.add(lblruta);
		
		JLabel lblNewLabel = new JLabel("Archivo Seleccionado");
		lblNewLabel.setBounds(10, 45, 129, 14);
		contentPane.add(lblNewLabel);
		
		btnSeleccion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
				int valor = fileChooser.showOpenDialog(fileChooser);
				if (valor == JFileChooser.APPROVE_OPTION) {
				ruta = fileChooser.getSelectedFile().getAbsolutePath();
				lblruta.setText(ruta);
				}
				
				}
		});
		
		btnEncriptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				service.setaesKey(JOptionPane.showInputDialog(Presentacion.this, "Introduzca clave de encriptacion"));
				try {
					File file1 = fileChooser.getSelectedFile();
					service.encryptFile(file1);
					JOptionPane.showMessageDialog(Presentacion.this, "Archivo codificado");
				} catch (Exception e1) {
					
					e1.printStackTrace();
				}
			}
		});
		
		btnDesencriptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				service.setaesKey(JOptionPane.showInputDialog(Presentacion.this, "Introduzca clave de encriptacion"));
				try {
					File file1 = fileChooser.getSelectedFile();
					service.decryptFile(file1);
					JOptionPane.showMessageDialog(Presentacion.this, "Archivo descifrado");
				} catch (Exception e1) {
					
					e1.printStackTrace();
				}
			}
		});
	}
}
