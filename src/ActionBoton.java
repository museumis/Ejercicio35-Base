import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

/**
 * Clase que implementa el listener de los botones del Buscaminas. De alguna
 * manera tendr√° que poder acceder a la ventana principal. Se puede lograr
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
	 * Acci√≥n que ocurrir√° cuando pulsamos uno de los botones.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		ventana.mostrarNumMinasAlrededor(fila, col);		
		
		if(!ventana.juego.abrirCasilla(fila, col)) {
			JOptionPane.showMessageDialog(null, "ExplotÛ una mina...", "°Intentalo de nuevo!", JOptionPane.ERROR_MESSAGE);
			ventana.actualizarPuntuacion();
			ventana.refrescarPantalla();
		}

		if (ventana.juego.esFinJuego()) {
			JOptionPane.showMessageDialog(null, "Victoria", "°Enhorabuena!", JOptionPane.ERROR_MESSAGE);
			ventana.actualizarPuntuacion();
			ventana.refrescarPantalla();
		}
		ventana.actualizarPuntuacion();
		ventana.refrescarPantalla();
	}

}
