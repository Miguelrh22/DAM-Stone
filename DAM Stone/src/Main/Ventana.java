package Main;

import Misc.Macro;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

/**
 * Implementa la interfaz JFrame, la cual sirve para crear ventanas.
 */
public class Ventana extends JFrame {

    public Ventana(Juego juego) {
        setTitle(Macro.NOMBRE_JUEGO_FORMATO);   //el nombre que aparece en la ventana
        pack(); //mantiene toda la dimension del panel dentro de la ventana
        setSize(Macro.PANTALLA_ANCHO + getInsets().left + getInsets().right, Macro.PANTALLA_ALTO + getInsets().top + getInsets().bottom);   //tamaño de la ventana
        setLocationRelativeTo(null);    //crea la ventana en medio de la pantalla
        setResizable(false);    //prohibe el cambio de tamaño de la ventana
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        add(juego);
        Juego.ventana = this;   //asigna la ventana al main
        juego.iniciar();
    }

    /**
     * Limpiamos archivos temporales al cerrar la partida.
     *
     * WindowEvent es un evento de bajo nivel que indica que una ventana ha
     * cmabiado de estado; es generado por un objeto Window cuando es abierto,
     * cerrado, activado, desactivado, etc.
     *
     * @param e efecto de bajo nivel
     */
    @Override
    protected void processWindowEvent(WindowEvent e) {
        //super.processWindowEvent(e);

        if (e.getID() == WindowEvent.WINDOW_CLOSING) {  //se quiere cerrar la ventana
            if (getDefaultCloseOperation() == EXIT_ON_CLOSE) {  //comprobamos que el valor de cierre de la ventana está en EXIT_ON_CLOSE

                Juego.limpiarLocal();
                System.exit(0); //finaliza la ejecución de la Java Virtual Machine
            }
        }
    }

    /**
     * Actualiza el título de la ventana.
     *
     * @param titulo el nuevo nombre de la ventana
     */
    public void actualizarTitulo(String titulo) {
        setTitle(titulo);
    }

}
