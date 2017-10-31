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
					// Si es una casilla sin limites
					if ((k != 0) && (k != 9) && (j != 0) && (j != 9)) {
						// Se inserta el numero de minas que tiene alrededor
						tablero[k][j] = getMinasAlrededor(k, j);
						// Si es una casilla con limites
					} else {
						// Limite superior
						if (k == 0) {
							tablero[k][j] = calculoMinasAdjuntas(k, j);
						}
						// Limite Inferior
						if (k == 9) {
							tablero[k][j] = calculoMinasAdjuntas(k, j);

						}
						// Limite siniestro
						if (j == 0) {
							tablero[k][j] = calculoMinasAdjuntas(k, j);

						}
						// Limite diestro
						if (j == 9) {
							tablero[k][j] = calculoMinasAdjuntas(k, j);

						}

					}
				}
			}
		}

		depurarTablero();
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
		int a;
		// Contador de Minas alrededor
		int c = 0;

		// Si la casilla es una esquina
		// Esquina superior izquierda
		// Se comprueba la casilla de la derecha, de abajo y la esquina inferior izquierda
		if (i == 0 && j == 0) {
			//Casilla derecha
			if (tablero[i][j +1] == MINA) {
				c++;
			}
			//Casilla abajo
			if (tablero[i+1][j] == MINA) {
				c++;
			}
			//casilla esquina abajo-derecha
			if (tablero[i+1][j + 1] == MINA) {
				c++;
			}
		}
		// Esquina superior derecha
		// Se comprueba la casilla de la izquieda, de abajo y la esquina inferior derecha
		if (i == 0 && j == 9) {
			//Casilla izquierda
			if (tablero[i][j -1] == MINA) {
				c++;
			}
			//Casilla abajo
			if (tablero[i+1][j] == MINA) {
				c++;
			}
			//casilla esquina abajo-izquierda
			if (tablero[i+1][j - 1] == MINA) {
				c++;
			}
		}
		// Esquina inferior izquierda
		// Se comprueba la casilla de la derecha, de arriba y la esquina superior derecha
		if (i == 9 && j == 0) {
			//Casilla derecha
			if (tablero[i][j +1] == MINA) {
				c++;
			}
			//Casilla arriba
			if (tablero[i-1][j] == MINA) {
				c++;
			}
			//casilla esquina superior-derecha
			if (tablero[i-1][j + 1] == MINA) {
				c++;
			}
		}
		// Esquina inferior derecha
		// Se comprueba la casilla de la izquieda, de arriba y la esquina superior derecha
		if (i == 9 && j == 9) {
			//Casilla izquierda
			if (tablero[i][j -1] == MINA) {
				c++;
			}
			//Casilla abajo
			if (tablero[i-1][j] == MINA) {
				c++;
			}
			//casilla esquina abajo-izquierda
			if (tablero[i-1][j - 1] == MINA) {
				c++;
			}
		}

		// Si la casilla no es esquinas
		if (!((i == 0 && j == 0) || (i == 0 && j == 9) || (i == 9 && j == 0) || (i == 9 && j == 9))) {
			// Dependiendo de la fila
			switch (i) {
			// Si es la 0
			// Se comprueba la casilla de la izquierda, derecha y las tres inferiores
			case 0: {
				// Casillas izquierda
				if (tablero[i][j - 1] == MINA) {
					c++;
				}
				// Casillas derecha
				if (tablero[i][j + 1] == MINA) {
					c++;
				}
				// Casillas inferiores
				for (a = -1; a <= 1; a++) {
					if (tablero[i + 1][j + a] == MINA) {
						c++;
					}
				}
				break;
			}
			// Si es la 9
			// Se comprueba la casilla de la izquierda, derecha y las tres superiores
			case 9: {
				// Casillas izquierda
				if (tablero[i][j - 1] == MINA) {
					c++;
				}
				// Casillas derecha
				if (tablero[i][j + 1] == MINA) {
					c++;
				}
				// Casillas inferiores
				for (a = -1; a <= 1; a++) {
					if (tablero[i - 1][j + a] == MINA) {
						c++;
					}
				}
				break;
			}
			}

			// Dependiendo de la columna
			switch (j) {
			// Si es la columna 0
			// Se comprueba la casilla de arriba, abajo y las tres casillas de la derecha
			case 0: {
				// Casillas arriba
				if (tablero[i + 1][j] == MINA) {
					c++;
				}
				// Casillas abajo
				if (tablero[i - 1][j] == MINA) {
					c++;
				}
				// Lateral derecha
				for (a = -1; a <= 1; a++) {
					if (tablero[i + a][j +1] == MINA) {
						c++;
					}
				}
				break;
			}
			// Si es la columna 9
			// Se comprueba la casilla de arriba, abajo y las tres casillas de la izquierda
			case 9: {
				// Casillas arriba
				if (tablero[i + 1][j] == MINA) {
					c++;
				}
				// Casillas abajo
				if (tablero[i - 1][j] == MINA) {
					c++;
				}
				// Lateral izquierda
				for (a = -1; a <= 1; a++) {
					if (tablero[i - a][j -1] == MINA) {
						c++;
					}
				}	
				break;
			}
			}
		}//if de si no es esquina

		return c;
	}//Fin de calculo minas en casillas cerca del limite

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
		return true;
	}

	/**
	 * Método que checkea si se ha terminado el juego porque se han abierto todas
	 * las casillas.
	 * 
	 * @return Devuelve verdadero si se han abierto todas las celdas que no son
	 *         minas.
	 **/
	public boolean esFinJuego() {
		return true;
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
	 * Si la celda no tiene limites colindantes
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
		// Contador de Minas alrededor
		int c = 0;
		// Casillas superiores
		if (tablero[i - 1][j + 1] == MINA) {
			c++;
		}
		if (tablero[i - 1][j] == MINA) {
			c++;
		}
		if (tablero[i - 1][j - 1] == MINA) {
			c++;
		}
		// Casillas inferiores
		for (int a = -1; a <= 1; a++) {
			if (tablero[i + 1][j + a] == MINA) {
				c++;
			}
		}
		// Dos casillas laterales
		if (tablero[i][j - 1] == MINA) {
			c++;
		}
		if (tablero[i][j + 1] == MINA) {
			c++;
		}
		return c;
	}// Fin de obtener minas con casilla sin limites

	/**
	 * Método que devuelve la puntuación actual
	 * 
	 * @return Un entero con la puntuación actual
	 */
	public int getPuntuacion() {
		return 1;

	}

}
