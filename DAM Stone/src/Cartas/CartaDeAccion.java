package Cartas;

import Misc.Enums;

public abstract class CartaDeAccion extends Carta {

    private boolean vivo;
    private int cont;
 
    public CartaDeAccion(Enums.tipoCarta tipoCarta, Stats stats, String nombreSprite, String nombre, String descripcion) {
        super(tipoCarta, stats, nombreSprite, nombre, descripcion);
        this.vivo = true;
        this.cont = -1;
    }
     public CartaDeAccion(Enums.tipoCarta tipoCarta, Stats stats, String nombreSprite, String nombre, String descripcion, int cont) {
        super(tipoCarta, stats, nombreSprite, nombre, descripcion);
        this.vivo = true;
        this.cont = cont;
    }

    /**
     * Obgliamos a todas las cartas de acción (unidades y hechizos) a tener un
     * método invocar. Es de gran utilidad para el desarrollo del juego (véase
     * métodos override de los hijos).
     */
    public abstract void invocar();


    public boolean isVivo() {
        return vivo;
    }

    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }

    public void setCont(int cont) {
        this.cont = cont;
    }

    public int getCont() {
        return cont;
    }

}
