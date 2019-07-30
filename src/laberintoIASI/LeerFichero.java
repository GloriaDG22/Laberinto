package laberintoIASI;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


//Lee el fichero en este caso el laberinto que se le pase y divide la string
public class LeerFichero {
	public void muestraContenido(String archivo, laberinto l) throws FileNotFoundException, IOException {
		String S = new String();
		int fila = 0;

		FileReader f = new FileReader(archivo);
		BufferedReader bufferIn = new BufferedReader(f);

		S = bufferIn.readLine(); // leer solo la primera linea y pillar el numero
		System.out.println(S);
		l.setUmbral(Integer.parseInt(S));

		
		while ((S = bufferIn.readLine()) != null) {
			trocearLinea(S, l, fila);
			fila++;

		}
		bufferIn.close(); // cerramos el filtro
	}

	private static void trocearLinea(String S, laberinto l, int fila) {
		int col = 0;
		String str[] = S.split(",");
		List<String> al = new ArrayList<String>();
		al = Arrays.asList(str);
		for (String A : al) {
			l.setPos(fila, col,Integer.parseInt(A));
			col++;
		}
	}


}