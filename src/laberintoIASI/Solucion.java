package laberintoIASI;
import java.util.ArrayList;

public class Solucion {
	int costet;
	ArrayList<String> movimientos;

	public Solucion() {
		costet=0;
		movimientos=new ArrayList<String>();
	}

	public int getCostet() {
		return costet;
	}

	public void setCostet(int costet) {
		this.costet +=costet;
	}

	public void addMov(String m) {
		movimientos.add(m);
	}

	public void mostrarSolucion() {
		for(int i = movimientos.size()-1; i >= 0; i--) {
			System.out.print( movimientos.get(i)+ " ");
		}
		System.out.println();
	}
}
