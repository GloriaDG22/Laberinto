package laberintoIASI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.time.Duration;
import java.time.Instant;

public class main {

	static int posFinX = 0;
	static int posFinY = 0;
	static Heuristica heur;

	static enum Moves {
		N, S, O, E
	};

	public static void main( String[] args) {
		heur = new Heuristica();
		LeerFichero f = new LeerFichero();
		laberinto l = new laberinto();
		Solucion jugador = new Solucion();
		System.out.println("Introduce el laberinto que quieras: ");
		Scanner scanner = new Scanner(System.in);
		String nlab = scanner.nextLine();
		System.out.println("Has escogido el laberinto: " + nlab);
		System.out.println("El laberinto se esta generando...");
		try {
			f.muestraContenido("laberinto"+nlab+".txt", l);
		} catch (Exception e) {
			//System.out.println("Error");
		}
		l.show();
		getStart(l);
		System.out.println("La salida se encuentra en la posición :" + l.getPosxFinal() + "," + l.getPosyFinal());
		System.out.println("Escoge la opción deseada para resolver el laberinto:");
		System.out.println("1-MaximaPendiente");
		System.out.println("2-PrimeroMejor");
		System.out.println("3-AEstrella");

		int a=0;
		a=scanner.nextInt();
		System.out.println("Tu opcion escogida es la "+ a);
		switch(a) {
		case 1:
			MaximaPendiente(l, jugador);
			break;
		case 2:
			PrimeroMejor(l, jugador);
			break;
		case 3:
			AEstrella(l, jugador);
			break;
		default:
			System.out.println("No has escogido una opción válida, se detiene la ejecución.");
			break;
		}
		scanner.close();

	}

	//Genera los nodos y sus adyacentes

	public static void getStart(laberinto l) {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				l.getNodo(i, j).crearAdyacentes(l);
				if (l.getCoste(i, j) == 0) {
					l.setPosxFinal(i);
					l.setPosyFinal(j);
					l.getNodo(i, j).setGoal(true);
				}
			}
		}
	}

	//Algoritmo que sigue la definicion de busqueda heuristica de Maxima Penediente, este algoritmo se detiene
	//en caso de encontrar una solucion o no encontrar un estado con una heuristica mejor.
	public static void MaximaPendiente(laberinto l, Solucion j) {
		Instant start = Instant.now();
		System.out.println("Va a comenzar el algoritmo de máxima pendiente");
		Nodo actual;
		Nodo nodoAux=null;
		boolean hecho = false;
		ArrayList<Nodo> listas = new ArrayList<Nodo>();
		float formula = 0;
		float formulaAux = Integer.MAX_VALUE;
		int contador=0;

		actual = l.getNodo(l.getPosxInicial(),l.getPosyInicial());
		listas.add(actual);
		formula = heur.manhattan(l.getPosxFinal(), l.getPosyFinal(), l.getPosxInicial(),l.getPosyInicial());
		actual.setformula(formula);
		while (!hecho) {
			actual = listas.get(0);
			j.setCostet(actual.getcoste());
			//System.out.println("Posicion actual   " + actual.getX() + "   " + actual.getY());

			if (actual.getformula() > l.getUmbral()) {
				System.out.println("El laberinto no tiene una solución posible porque pasa del umbral.");
				break;
			}

			if (!actual.isvisitado()) {
				actual.setvisitado();

				for (Nodo vecino : actual.getadyacentes()) {
					if (!vecino.isvisitado()) {
						contador++;
						formula = heur.manhattan(l.getPosxFinal(), l.getPosyFinal(), vecino.getX(), vecino.getY());
						vecino.setformula(formula);
						if (formula < formulaAux) {
							nodoAux = vecino;
							formulaAux = formula;
						}
					} 
				}
			}
			if (nodoAux.getX() == l.getPosxFinal() && nodoAux.getY() == l.getPosyFinal()) {
				hecho = true;
				System.out.println("Hemos llegado a la solución.");
			}
			if (actual.getformula() > nodoAux.getformula()) {
				listas.add(nodoAux);
				Collections.sort(listas, new NodoComparator());
				//				for (Nodo vecino : listas) {
				//					System.out.println("Es la casilla" + "" + "[" + vecino.getX() + "]" + "[" + vecino.getY() + "]"
				//							+ vecino.getcoste() + "   " + vecino.getformula());
				//				}
			}
			else {
				System.out.println("Se acabo porque no hay mejores nodos a los que volver.");
				break;
			}

		}	
		if (hecho) {
			for(int i = listas.size()-1; i>0; i--) {
				movimiento(listas.get(i), listas.get(i-1),j,l);
			}
		}
		Instant end = Instant.now();
		Duration interval = Duration.between(start, end);
		j.mostrarSolucion();
		System.out.println(interval.getNano() + " nanosegundos.");
		System.out.println("El número de nodos generados es: " +contador);

	}


	//Algoritmo basado en primero mejor que busca la primera solucion al laberinto
	//para ello genera una lista de nodos abiertos y cerrados la cual explora buscando la primera
	//solucion posible.
	public static void PrimeroMejor(laberinto l, Solucion j) {
		Instant start = Instant.now();
		System.out.println("Va a comenzar el algoritmo de primero mejor.");
		Nodo actual;
		int tam = 0;
		boolean hecho = false;
		float formula = 0;
		ArrayList<Nodo> Abiertos = new ArrayList<Nodo>();
		ArrayList<Nodo> Cerrados = new ArrayList<Nodo>();

		actual = l.getNodo(l.getPosxInicial(),l.getPosyInicial());
		Abiertos.add(actual);
		formula= heur.manhattan(l.getPosxFinal(), l.getPosyFinal(), l.getPosxInicial(),l.getPosyInicial());
		actual.setformula(formula);
		while (!hecho) {
			actual = Abiertos.get(0);
			j.setCostet(actual.getcoste());	
			//System.out.println("Posicion actual   " + actual.getX()+ "   "+ actual.getY());

			//Compara con el humbral y si lo supera se sale
			if (actual.getformula() > l.getUmbral()) {
				System.out.println("El laberinto no tiene una solución posible porque pasa del umbral.");
				break;
			}
			//Expande el mejor nodo y los almacena en la lista de abiertos
			if (!actual.isvisitado()) {
				actual.setvisitado();
				for (Nodo vecino : actual.getadyacentes()) {

					//Calcula la formula heurística del nodo vecino y 
					if (!vecino.isvisitado()) {
						formula = heur.manhattan(l.getPosxFinal(), l.getPosyFinal(), vecino.getX(), vecino.getY());
						vecino.setformula(formula);
						vecino.setanterior(actual);

						if(!Abiertos.contains(vecino)) {
							Abiertos.add(vecino);
						}
					}
					else {
						if(Abiertos.contains(actual)) {
							Abiertos.remove(actual);
						}
					}
				}
			}
			Abiertos.remove(actual);
			Cerrados.add(actual);
			Collections.sort(Abiertos, new NodoComparator());
			//			for(Nodo vecino: Abiertos) {
			//				System.out.println("Es la casilla" + "" + "[" + vecino.getX() + "]" + "[" + vecino.getY() + "]"
			//						+ vecino.getcoste() +"   "+ vecino.getformula());
			//			}
			//si está en la última posición finaliza
			if (actual.getX() == l.getPosxFinal() && actual.getY() == l.getPosyFinal()) {
				hecho = true;
				System.out.println("Hemos llegado a la solución.");
				break;
			}
		}
		if (hecho) {
			tam = Cerrados.size()-1;
			actual = Cerrados.get(tam);
			for(int i = Cerrados.size()-1; i>0; i--) {
				if(actual.getanterior() == Cerrados.get(i-1)) {
					movimiento(actual.getanterior(), actual, j, l);
					actual = Cerrados.get(i-1);
				}
			}
		}
		Instant end = Instant.now();
		Duration interval = Duration.between(start, end);
		j.mostrarSolucion();
		System.out.println(interval.getNano() + " nanosegundos.");
		System.out.println("El número de nodos generados es: " +(Cerrados.size()+Abiertos.size()));
	}


	//Algoritmo basado en la metodologia de A* la cual desarrolla todos las soluciones posibles y
	//encuentra la mejor.
	public static void AEstrella(laberinto l, Solucion j) {
		Instant start = Instant.now();
		System.out.println("Va a comenzar el algoritmo de A*.");
		Nodo actual;
		int tam = 0;
		boolean hecho = false;
		float formula = 0;
		ArrayList<Nodo> Abiertos = new ArrayList<Nodo>();
		ArrayList<Nodo> Cerrados = new ArrayList<Nodo>();

		actual = l.getNodo(l.getPosxInicial(),l.getPosyInicial());
		Abiertos.add(actual);
		formula= heur.manhattan(l.getPosxFinal(), l.getPosyFinal(), l.getPosxInicial(),l.getPosyInicial()) + l.getCoste(l.getPosxInicial(),l.getPosyInicial());
		actual.setformula(formula);	
		while (!hecho) {
			actual = Abiertos.get(0);
			j.setCostet(actual.getcoste());	
			//System.out.println("Posicion actual   " + actual.getX()+ "   "+ actual.getY());

			//Compara con el humbral y si lo supera se sale
			if (actual.getformula() > l.getUmbral()) {
				System.out.println("El laberinto no tiene una solución posible porque pasa del umbral.");
				break;
			}
			//Expande el mejor nodo y los almacena en la lista de abiertos
			if (!actual.isvisitado()) {
				actual.setvisitado();
				for (Nodo vecino : actual.getadyacentes()) {

					//Calcula la formula heurística del nodo vecino y 
					if (!vecino.isvisitado()) {
						formula = heur.manhattan(l.getPosxFinal(), l.getPosyFinal(), vecino.getX(), vecino.getY())+l.getCoste(vecino.getX(), vecino.getY());
						vecino.setformula(formula);
						vecino.setanterior(actual);

						if(!Abiertos.contains(vecino)) {
							Abiertos.add(vecino);
						}
					}
					else {
						if(Abiertos.contains(actual)) {
							Abiertos.remove(actual);
						}
					}
				}
			}
			Abiertos.remove(actual);
			Cerrados.add(actual);
			Collections.sort(Abiertos, new NodoComparator());
			//			for(Nodo vecino: Abiertos) {
			//				System.out.println("Es la casilla" + "" + "[" + vecino.getX() + "]" + "[" + vecino.getY() + "]"
			//						+ vecino.getcoste() +"   "+ vecino.getformula());
			//			}

			//si está en la última posición finaliza
			if (actual.getX() == l.getPosxFinal() && actual.getY() == l.getPosyFinal()) {
				hecho = true;
				System.out.println("Hemos llegado a la solución.");
				break;
			}
		}
		if (hecho) {
			tam = Cerrados.size()-1;
			actual = Cerrados.get(tam);
			for(int i = Cerrados.size()-1; i>0; i--) {
				if(actual.getanterior() == Cerrados.get(i-1)) {
					movimiento(actual.getanterior(), actual, j, l);
					actual = Cerrados.get(i-1);
				}
			}
		}
		Instant end = Instant.now();
		Duration interval = Duration.between(start, end);
		j.mostrarSolucion();
		System.out.println(interval.getNano() + " nanosegundos.");
		System.out.println("El número de nodos generados es: " +(Cerrados.size()+Abiertos.size()));
	}

	//Movimientos del tablero para mostrar a posterior la solucion.

	private static void movimiento(Nodo ant, Nodo sig, Solucion j, laberinto l) {
		int x, y;
		Moves mov=null;
		x = ant.getX();
		y = ant.getY();

		if (sig.getX()==x+1 && sig.getY() == y)
			mov = Moves.S;
		else if (sig.getX() == x-1 && sig.getY() == y)
			mov = Moves.N;
		else if (sig.getX()==x && sig.getY() == y+1)
			mov = Moves.E;
		else if (sig.getX()==x && sig.getY() == y-1)
			mov = Moves.O;

		switch (mov) {
		case S:
			//System.out.println("Te has movido al S");
			j.addMov("S");
			break;
		case N:
			//System.out.println("Te has movido al N");
			j.addMov("N");
			break;
		case O:
			//System.out.println("Te has movido al O");
			j.addMov("O");
			break;
		case E:
			//System.out.println("Te has movido al E");
			j.addMov("E");
			break;
		}

	}
}
