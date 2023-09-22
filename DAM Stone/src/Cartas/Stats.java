package Cartas;

import java.io.Serializable;

/**
 * Es utilizada por las unidades y los hechizos, contiene los valores del coste
 * de maná, la potencia y la salud.
 */
public class Stats implements Serializable {

    private final int coste;
    private int potencia;
    private int salud;

    public Stats(int coste, int potencia, int salud) {
        this.coste = coste;
        this.potencia = potencia;
        this.salud = salud;
    }

    public int getCoste() {
        return coste;
    }

    public int getSalud() {
        return salud;
    }

    public int getPotencia() {
        return potencia;
    }

    public void setSalud(int salud) {
        this.salud = salud;
    }

    public void setPotencia(int potencia) {
        this.potencia = potencia;
    }

}
