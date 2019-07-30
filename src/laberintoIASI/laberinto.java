package laberintoIASI;

import java.util.ArrayList;

public class laberinto {

	private Nodo[][] matriz;
	private int umbral;
	private int posxInicial = 0;
	private int posyInicial = 0;
	private int posxFinal = 0;
	private int posyFinal = 0;

	public laberinto() {
		umbral = 0;
		matriz = new Nodo[10][10];
		}

	public Nodo[][] getMatriz() {
		return matriz;
	}

	public Nodo getNodo(int x, int y) {
		return matriz[x][y];
	}

	public void setMatriz(Nodo[][] matriz) {
		this.matriz = matriz;
	}
	
	public void getsetLocalizacionDeLaMeta() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
					if (this.getCoste(i, j) == 0) {
					setPosxFinal(i);
					setPosyFinal(j);
				}
			}
		}
	}

	public int getPosxInicial() {
		return posxInicial;
	}

	public void setPosxInicial(int posxInicial) {
		this.posxInicial = posxInicial;
	}
	
	public int getPosxFinal() {
		return posxFinal;
	}

	public void setPosxFinal(int posxFinal) {
		this.posxFinal = posxFinal;
	}

	public int getPosyInicial() {
		return posyInicial;
	}

	public void setPosyInicial(int posyInicial) {
		this.posyInicial = posyInicial;
	}

	public int getPosyFinal() {
		return posyFinal;
	}

	public void setPosyFinal(int posyFinal) {
		this.posyFinal = posyFinal;
	}

	public Nodo getNodoInicial() {
		return matriz[0][0];
	}

	public int getUmbral() {
		return umbral;
	}

	public void setUmbral(int umbral) {
		this.umbral = umbral;
	}

	public void setPos(int a, int b, int c) {
		matriz[a][b]=new Nodo(a, b, c);
	}

	public int getCoste(int a, int b) {
		return matriz[a][b].getcoste();
	}

	public void show() {
		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 10; y++) {
				System.out.print(matriz[x][y].getcoste() + "|");
			}
			System.out.println();
		}
	}

	public boolean isGoal(Nodo auxNod) {
		boolean a=false;
		if (auxNod.getX()==getPosxFinal()&&auxNod.getY()==getPosyFinal())
			a=true;
		return a;
	}
}
