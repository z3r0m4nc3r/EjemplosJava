package presentacion.adaptadores;

import java.util.List;

import javax.swing.table.AbstractTableModel;
import model.Contacto;
import service.ContactosService;

public class TablaContactosModel extends AbstractTableModel {
	
	
	private static final long serialVersionUID = -7970611875759411321L;
	
	private List <Contacto> lista;
	
	public TablaContactosModel() {
		super();
		this.lista = (new ContactosService()).recuperarContactos();
	}

	
	@Override
	public int getRowCount() {
		return lista.size();
		
	}

	@Override
	public int getColumnCount() {
		return lista.get(0).getClass().getDeclaredFields().length;
		
	}

	@Override
	public String getColumnName(int column) {
		return lista.get(0).getClass().getDeclaredFields()[column].getName();
	
	}

	@Override
	public Object getValueAt(int row, int column) {
		Object res=null;
		switch(column) {
		
		case 0:
			res=lista.get(row).getIdContacto();
			break;
		case 1:
			res=lista.get(row).getNombre();
			break;
		case 2:
			res=lista.get(row).getEmail();
			break;
		case 3:
			res=lista.get(row).getEdad();
			break;
		}
		return res;
		
	}
	
}
