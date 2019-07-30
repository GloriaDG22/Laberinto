package laberintoIASI;

import java.util.Comparator;
public class NodoComparator implements Comparator<Nodo> {
 //Comparador de la clase nodo

	public int compare(Nodo nodoA, Nodo nodoB) {
		if(nodoA.getformula()==nodoB.getformula())
			return 0;
		else if(nodoA.getformula()>nodoB.getformula())
			return 1;
		else
			return -1;
	}
}
