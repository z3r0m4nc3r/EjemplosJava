package presentacion.adaptadores;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.table.AbstractTableModel;

import model.Caso;
import service.CovidService;

public class TablaCovidModel extends AbstractTableModel {
	
	
	private static final long serialVersionUID = -7970611875759411321L;
	
	private List <Caso> lista;
	
	public TablaCovidModel(Date fecha1, Date fecha2, String comunidad) {
		super();
		this.lista = (new CovidService())
				.listaCasos(fecha1, fecha2).stream()
				.filter(c -> c.getNombreComunidad().contentEquals(comunidad))
				.collect(Collectors.toList());
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
		SimpleDateFormat sdt = new SimpleDateFormat("dd/MM/yyyy");
		switch(column) {
		
		case 0:
			res=lista.get(row).getNombreComunidad();
			break;
		case 1:
			res=sdt.format(lista.get(row).getFecha());
			break;
		case 2:
			res=lista.get(row).getPositivos();
			break;
		
		}
		return res;
		
	}
	
}
