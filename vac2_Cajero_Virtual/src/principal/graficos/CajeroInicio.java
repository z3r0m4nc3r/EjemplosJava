package principal.graficos;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import service.ServiceCajero;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CajeroInicio extends JFrame {

	private JPanel contentPane;
	private JTextField textNumeroCuenta;
	private JLabel lblMensaje;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CajeroInicio frame = new CajeroInicio();
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
	public CajeroInicio() {
		ServiceCajero service = new ServiceCajero();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lbl1 = new JLabel("Introduzca numero de cuenta");
		lbl1.setHorizontalAlignment(SwingConstants.CENTER);
		lbl1.setBounds(138, 11, 158, 14);
		contentPane.add(lbl1);
		
		textNumeroCuenta = new JTextField();
		textNumeroCuenta.setHorizontalAlignment(SwingConstants.CENTER);
		textNumeroCuenta.setBounds(170, 36, 96, 20);
		contentPane.add(textNumeroCuenta);
		textNumeroCuenta.setColumns(10);
		lblMensaje = new JLabel("");
		lblMensaje.setHorizontalAlignment(SwingConstants.CENTER);
		
		JButton btnEnviar = new JButton("Enviar");
		btnEnviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(service.comprobarCuenta(Integer.parseInt(textNumeroCuenta.getText()))) {
					lblMensaje.setText("Numero correcto");
					EventQueue.invokeLater(new Runnable() {
						public void run() {
							try {
								CajeroOperaciones frame = new CajeroOperaciones(Integer.parseInt(textNumeroCuenta.getText()));
								frame.setVisible(true);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
					CajeroInicio.this.dispose();
				}else lblMensaje.setText("El numero de cuenta introducido no existe");
				
			}
		});
		btnEnviar.setBounds(170, 78, 96, 23);
		contentPane.add(btnEnviar);
		
		
		lblMensaje.setBounds(10, 149, 414, 14);
		contentPane.add(lblMensaje);
	}

}
