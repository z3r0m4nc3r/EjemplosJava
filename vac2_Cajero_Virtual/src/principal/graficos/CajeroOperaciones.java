package principal.graficos;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import service.ServiceCajero;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

public class CajeroOperaciones extends JFrame {

	private static final long serialVersionUID = -776290701431987266L;
	private JPanel contentPane;
	private JTextField texCantidad;

	public CajeroOperaciones(int numeroCuenta) {
		ServiceCajero service = new ServiceCajero();
		setTitle("Cajero Virtual");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 640, 480);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblEtiqueta1 = new JLabel("Numero de Cuenta");
		lblEtiqueta1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblEtiqueta1.setHorizontalAlignment(SwingConstants.LEFT);
		lblEtiqueta1.setBounds(10, 11, 111, 14);
		contentPane.add(lblEtiqueta1);
		
		JLabel lblNumeroCuenta = new JLabel(numeroCuenta+"");
		lblNumeroCuenta.setBounds(131, 11, 132, 14);
		contentPane.add(lblNumeroCuenta);
		
		JLabel lblEtiqueta2 = new JLabel("Saldo");
		lblEtiqueta2.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblEtiqueta2.setHorizontalAlignment(SwingConstants.LEFT);
		lblEtiqueta2.setBounds(171, 11, 48, 14);
		contentPane.add(lblEtiqueta2);
		
		JLabel lblSaldo = new JLabel(service.saldo(Integer.parseInt(lblNumeroCuenta.getText()))+"");
		lblSaldo.setBounds(229, 11, 111, 14);
		contentPane.add(lblSaldo);
		
		JLabel lblEuro = new JLabel("\u20AC");
		lblEuro.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblEuro.setHorizontalAlignment(SwingConstants.LEFT);
		lblEuro.setBounds(300, 11, 57, 14);
		contentPane.add(lblEuro);
		
		texCantidad = new JTextField();
		texCantidad.setHorizontalAlignment(SwingConstants.CENTER);
		texCantidad.setBounds(10, 89, 209, 20);
		contentPane.add(texCantidad);
		texCantidad.setColumns(10);
		
		JLabel lblEtiqueta3 = new JLabel("Cantidad");
		lblEtiqueta3.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblEtiqueta3.setBounds(83, 58, 74, 20);
		contentPane.add(lblEtiqueta3);
		
		JButton btnIngresar = new JButton("Ingresar");
		btnIngresar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				service.ingresarDinero(Integer.parseInt(texCantidad.getText()), Integer.parseInt(lblNumeroCuenta.getText()));
				lblSaldo.setText(service.saldo(Integer.parseInt(lblNumeroCuenta.getText()))+"");
			}
		});
		btnIngresar.setBounds(10, 120, 89, 23);
		contentPane.add(btnIngresar);
		
		JButton btnExtraer = new JButton("Extraer");
		btnExtraer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				service.sacarDinero(Integer.parseInt(texCantidad.getText()), Integer.parseInt(lblNumeroCuenta.getText()));
				lblSaldo.setText(service.saldo(Integer.parseInt(lblNumeroCuenta.getText()))+"");
			}
		});
		btnExtraer.setBounds(131, 120, 89, 23);
		contentPane.add(btnExtraer);
		JTextArea texMovimientos = new JTextArea();
		JButton btnMovimientos = new JButton("Movimientos 30 dias");
		btnMovimientos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StringBuilder movimientos = new StringBuilder();
				
				service.movimientos(Integer.parseInt(lblNumeroCuenta.getText()))
				.forEach(mv -> movimientos.
						append(mv.getIdCuenta()+" "+mv.getFecha()+" "+mv.getCantidad()+" "+mv.getOperacion()+"\r\n"));
				
				texMovimientos.setText(movimientos.toString());
			}
		});
		btnMovimientos.setBounds(371, 59, 243, 23);
		contentPane.add(btnMovimientos);
		
		
		texMovimientos.setBounds(371, 89, 243, 341);
		contentPane.add(texMovimientos);
		
		JButton btnTrasferencia = new JButton("Realizar Trasferencia");
		
		btnTrasferencia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(service.trasferencia(Integer.parseInt(
						JOptionPane.showInputDialog(CajeroOperaciones.this, "Introduzca cantidad a transferir")),
						Integer.parseInt(lblNumeroCuenta.getText()),
						Integer.parseInt(JOptionPane.showInputDialog(CajeroOperaciones.this, "Introduzca cuenta destino")))) {
					
					JOptionPane.showInternalMessageDialog(null, "Trasferencia realizada con exito");
					lblSaldo.setText(service.saldo(Integer.parseInt(lblNumeroCuenta.getText()))+"");
					
				}else JOptionPane.showInternalMessageDialog(null, "La trasferencia no se pudo realizar");
			}
		});
		btnTrasferencia.setBounds(10, 185, 209, 23);
		contentPane.add(btnTrasferencia);
	}
}
