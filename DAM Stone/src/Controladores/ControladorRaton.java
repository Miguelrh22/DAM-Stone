package Controladores;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import Main.Juego;

/**
 * Implementa la interfaz MouseListener, que es aquella que controla todo lo
 * relacionado al ratón.
 *
 */
public class ControladorRaton implements MouseListener {

    /**
     * Heredado de la interfaz MouseListener, controla los click.
     *
     * @param e el evento a procesar
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        //Controlamos los click (izquierdo y derecho) cuando estamos en partida
        //if (Juego.escena.getNombre().equals("Partida")) {
        //Control de click izquierdo
        if (e.getButton() == MouseEvent.BUTTON1) {
            Juego.escena.clickIzq();
        }

        //Control de click derecho
        if (e.getButton() == MouseEvent.BUTTON3) {
            Juego.escena.clickDch();
        }
        //}
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}
