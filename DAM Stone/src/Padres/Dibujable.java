package Padres;

import java.awt.Graphics;

/**
 * Interfaz que implementan varias clases del juego (véase Texto y/o Carta).
 */
public interface Dibujable {

    /**
     * g da acceso a una serie de métodos de la clase abstracta Graphics que
     * sirven para dibujar texto, poner colores al fondo, etc.
     *
     * @param g objeto de la clase abstracta Graphics
     */
    public abstract void dibujar(Graphics g);
}
