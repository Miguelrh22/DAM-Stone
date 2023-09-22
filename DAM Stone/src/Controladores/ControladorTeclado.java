package Controladores;

import Main.Juego;
import Escenas.Menu;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Implementa la interfaz KeyListener, que es aquella que controla todo lo
 * relacionado al teclado.
 */
public class ControladorTeclado implements KeyListener {

    @Override
    public void keyTyped(KeyEvent e) {
        if (Juego.escena.getNombre().equals("Inicio")) {
            Juego.escena.setEntradaTeclado(e.getKeyChar());
        }
    }

    /**
     * Heredado de la interfaz KeyListener, controla las entradas por teclado y
     * permite ejecutar acciones específicas en respuesta a la pulsación de una
     * tecla por parte del usuario. El método getKeyCode() devuelve un int, que
     * se compara con el int que devuelve cada una de las teclas
     * (KeyEvent.VK_SPACE, KeyEvent.VK:0, etc.).
     *
     * @param e el evento a procesar
     */
    @Override
    public void keyPressed(KeyEvent e) {

        switch (e.getKeyCode()) {
            case KeyEvent.VK_0: //para ver o no las coordenadas del ratón (esquina superior izquierda)
                Juego.mostrarAjustes = !Juego.mostrarAjustes;
                break;
            case KeyEvent.VK_ENTER: //para ver o no las coordenadas del ratón (esquina superior izquierda)
                if (Juego.escena.getNombre().equals("Inicio")) {
                    Juego.escena = new Menu();
                }
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}
