package laberintoIASI;

public class Heuristica {

//Calcula la distancia manhattan
	public int manhattan(int finX, int finY, int x, int y) {
		return Math.abs(finX-x)+Math.abs(finY - y);
	}
}
