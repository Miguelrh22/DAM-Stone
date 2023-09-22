package Padres;

import java.awt.Graphics;

/**
 * �til para diferenciar cuando el juego est� en el men� o en la partida. Obliga
 * a sus hijos a tener una serie de m�todos necesarios para el correcto
 * funcionamiento del juego.
 */
public abstract class Escena implements Dibujable, Actualizable {

    
    private char entradaTeclado = '\000';
    
    public char getEntradaTeclado(){
        char aux = entradaTeclado;
        entradaTeclado = '\000';
        return aux;
    }
    public void setEntradaTeclado(char e){
        entradaTeclado = e;
    }
    
    public abstract String getNombre();

    public abstract void clickIzq();

    public abstract void clickDch();

    @Override
    public abstract void actualizarSegundo();
    
    @Override
    public abstract void actualizar();
    
    @Override
    public abstract void dibujar(Graphics g);
}
