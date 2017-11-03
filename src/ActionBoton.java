import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

/**
 * Clase que implementa el listener de los botones del Buscaminas. De alguna
 * manera tendrá que poder acceder a la ventana principal. Se puede lograr
 * pasando en el constructor la referencia a la ventana. Recuerda que desde la
 * ventana, se puede acceder a la variable de tipo ControlJuego
 * 
 * @author jesusredondogarcia
 **
 */
public class ActionBoton implements ActionListener {

	private VentanaPrincipal ventana;
	private int fila;
	private int col;

	public ActionBoton(VentanaPrincipal panel, int f, int c) {
		this.ventana = panel;
		this.fila = f;
		this.col = c;
	}

	/**
	 * Acción que ocurrirá cuando pulsamos uno de los botones.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		ventana.mostrarNumMinasAlrededor(fila, col);
		//Si la casilla pulsada no es una mina se juega
		if(!ventana.juego.abrirCasilla(fila, col)) {				
			ventana.mostrarFinJuego(false);
			ventana.actualizarPuntuacion();
			ventana.refrescarPantalla();
		}
		//Si la casilla abierta es una mina
		if (ventana.juego.esFinJuego()) {
			ventana.mostrarFinJuego(true);	
			ventana.actualizarPuntuacion();
			ventana.refrescarPantalla();
		}
		ventana.actualizarPuntuacion();
		ventana.refrescarPantalla();
	}

}
