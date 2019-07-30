package laberintoIASI;

import java.util.ArrayList;

public class Nodo {

	ArrayList<Nodo> adyacentes; //Lista de nodos adyacentes a su posicion
	int coste; // Valor de la casilla
	boolean visitado; // Si la casilla ha sido visitado con anterioridad
	float formula; //Valor con respecto a f' 
	Nodo anterior = null; // Nodo padre
	int x; //Posicion X
	int y; //Posicion y
	boolean isGoal; //si la casilla es la salida del 

	public Nodo(int x, int y, int coste) {
		adyacentes = new ArrayList<Nodo>();
		formula = Integer.MAX_VALUE;
		this.x = x;
		this.y = y;
		this.visitado = false;
		this.isGoal = false;
		this.coste = coste;
	}
//Getters y setters por defecto
	public int getcoste() {
		return coste;
	}

	public void setcoste(int coste) {
		this.coste = coste;
	}

	public ArrayList<Nodo> getadyacentes() {
		return adyacentes;
	}

	public boolean isvisitado() {
		return visitado;
	}

	public void setvisitado() {
		visitado=true;
	}

	public Nodo getanterior() {
		return anterior;
	}

	public void setanterior(Nodo anterior) {
		this.anterior = anterior;
	}

	public float getformula() {
		return formula;
	}

	public void setformula(float f) {
		this.formula = f;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean isGoal() {
		return isGoal;
	}

	public void setGoal(boolean isGoal) {
		this.isGoal = isGoal;
	}

	public boolean equals(Nodo node) {
		return (node.x == x) && (node.y == y);
	}

	//Genera las casillas adyacentes al nodo
	
	public void crearAdyacentes(laberinto l) {
		// SUR
		if (this.x + 1 < 10&&l.getNodo(x+1, y)!=null) {
			adyacentes.add(l.getNodo(x+1, y));
		}
		// NORTE
		if (this.x - 1 >= 0&&l.getNodo(x-1, y)!=null) {
			adyacentes.add(l.getNodo(x-1, y));
		}
		// ESTE
		if (this.y + 1 < 10 &&l.getNodo(x, y+1)!=null) {
			adyacentes.add(l.getNodo(x, y+1));;
		}
		// OESTE
		if (this.y - 1 >= 0&&l.getNodo(x, y-1)!=null) {
			adyacentes.add(l.getNodo(x, y-1));
		}
	}

}
