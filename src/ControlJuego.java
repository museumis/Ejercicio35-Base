import java.util.ArrayList;
import java.util.Random;

/**
 * Clase gestora del tablero de juego. Guarda una matriz de enteros representado
 * el tablero. Si hay una mina en una posición guarda el número -1 Si no hay
 * una mina, se guarda cuántas minas hay alrededor. Almacena la puntuación de
 * la partida
 * 
 * @author jesusredondogarcia
 *
 */
public class ControlJuego {

	private final static int MINA = -1;
	final int MINAS_INICIALES = 20;
	final int LADO_TABLERO = 10;

	private int[][] tablero;
	private int puntuacion;

	public ControlJuego() {
		// Creamos el tablero:
		tablero = new int[LADO_TABLERO][LADO_TABLERO];

		// Inicializamos una nueva partida
		inicializarPartida();
		depurarTablero();

	}

	/**
	 * Método para generar un nuevo tablero de partida:
	 * 
	 * @pre: La estructura tablero debe existir.
	 * @post: Al final el tablero se habrá inicializado con tantas minas como
	 *        marque la variable MINAS_INICIALES. El resto de posiciones que no son
	 *        minas guardan en el entero cuántas minas hay alrededor de la celda
	 */
	public void inicializarPartida() {
		// Variables para obtener la colocacion de las minas
		Random r = new Random();
		int r1;
		int r2;
		// Por cada mina, de las que hay como iniciales
		for (int i = MINAS_INICIALES; i > 0; i--) {
			r1 = r.nextInt(10);
			r2 = r.nextInt(10);
			// Sa pinta en el tablero
			if (tablero[r1][r2] == MINA) {
				i++;
			} else {
				tablero[r1][r2] = MINA;
			}
		}

		// Para colocar el indicador de minas que hay alrededor de una mina
		// Se recorre el tablero
		for (int k = 0; k < tablero.length; k++) {
			for (int j = 0; j < tablero.length; j++) {
				// Si es una casilla con mina salta a la siguiente casilla
				if (tablero[k][j] != MINA) {
					tablero[k][j] = calculoMinasAdjuntas(k, j);
				}
			}
		}

	}// Fin de inicializar Partida

	/**
	 * Cálculo de las minas adjuntas: Para calcular el número de minas tenemos que
	 * tener en cuenta que no nos salimos nunca del tablero. Por lo tanto, como
	 * mucho la i y la j valdrán LADO_TABLERO-1. Por lo tanto, como mucho la i y la
	 * j valdrán como poco 0.
	 * 
	 * @param i:
	 *            posición verticalmente de la casilla a rellenar
	 * @param j:
	 *            posición horizontalmente de la casilla a rellenar
	 * @return : El número de minas que hay alrededor de la casilla [i][j]
	 **/
	private int calculoMinasAdjuntas(int i, int j) {
		int c = 0;
		for (int x = i - 1; x <= i + 1; x++) {
			for (int k = j - 1; k <= j + 1; k++) {
				try {
					if (tablero[x][k] == MINA) {
						c++;
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}
		return c;

	}// Fin de calculo minas en casillas cerca del limite

	/**
	 * Método que nos permite
	 * 
	 * @pre : La casilla nunca debe haber sido abierta antes, no es controlado por
	 *      el GestorJuego. Por lo tanto siempre sumaremos puntos
	 * @param i:
	 *            posición verticalmente de la casilla a abrir
	 * @param j:
	 *            posición horizontalmente de la casilla a abrir
	 * @return : Verdadero si no ha explotado una mina. Falso en caso contrario.
	 */
	public boolean abrirCasilla(int i, int j) {

		if (tablero[i][j] != MINA) {
			this.puntuacion++;
			return true;
		} else {
			return false;
		}

	}

	/**
	 * Método que checkea si se ha terminado el juego porque se han abierto todas
	 * las casillas.
	 * 
	 * @return Devuelve verdadero si se han abierto todas las celdas que no son
	 *         minas.
	 **/
	public boolean esFinJuego() {
		int puntuacionPosible = (LADO_TABLERO * LADO_TABLERO) - MINAS_INICIALES;
		if (puntuacion == puntuacionPosible) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * Método que pinta por pantalla toda la información del tablero, se utiliza
	 * para depurar
	 */
	public void depurarTablero() {
		System.out.println("---------TABLERO--------------");
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero[i].length; j++) {
				System.out.print(tablero[i][j] + "\t");
			}
			System.out.println();
		}
		System.out.println("\nPuntuaci�n: " + puntuacion);
	}

	/**
	 * Método que se utiliza para obtener las minas que hay alrededor de una celda
	 * 
	 * @pre : El tablero tiene que estar ya inicializado, por lo tanto no hace falta
	 *      calcularlo, símplemente consultarlo
	 * @param i
	 *            : posición vertical de la celda.
	 * @param j
	 *            : posición horizontal de la cela.
	 * @return Un entero que representa el número de minas alrededor de la celda
	 */
	public int getMinasAlrededor(int i, int j) {
		return tablero[i][j];
	}// Fin de obtener minas con casilla sin limites

	/**
	 * Método que devuelve la puntuación actual
	 * 
	 * @return Un entero con la puntuación actual
	 */
	public int getPuntuacion() {
		return this.puntuacion;

	}

}
