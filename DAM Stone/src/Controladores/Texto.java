package Controladores;

import Misc.Enums;
import java.awt.Color;
import java.awt.Graphics;
import Padres.Dibujable;

/**
 * Sirve para crear texto en pantalla. Contiene un contructor básico que recibe
 * texto y coordenadas, y otro que, además, recibe color y alineación. Creamos
 * la clase y ejecutamos en la misma línea: new Texto(parámetros).dibujar(g);
 * para que no se guarde el objeto en memoria. Para ello, implementa la interfaz
 * Dibujable.
 */
public class Texto implements Dibujable {

    private final String texto;
    private int x;  //posición horizontal
    private int y;  //posición vertical
    private final Color c;
    private boolean sombra = false;

    public Texto(String texto, int x, int y) {
        this.texto = texto;
        this.x = x;
        this.y = y;
        this.c = Color.WHITE;   //el color por defecto es blanco
    }
    
    public Texto(String texto, int x, int y, boolean sombra) {
        this.texto = texto;
        this.x = x;
        this.y = y;
        this.c = Color.WHITE;   //el color por defecto es blanco
        this.sombra = sombra;
    }
    
    public Texto(String texto, int x, int y, Color c, boolean sombra) {
        this.texto = texto;
        this.x = x;
        this.y = y;
        this.c = c;
        this.sombra = sombra;
    }

    public Texto(String texto, int x, int y, Color c, Enums.alinearVert aV, Enums.alinearHorz aH, boolean sombra) {
        this.texto = texto;
        this.x = x;
        this.y = y;
        this.c = c;
        alinear(aV, aH);
        this.sombra = sombra;
    }

    /**
     * Se utiliza en uno de los contructores. Alinea el texto según interese.
     *
     * @param aV Enums que puede ser TOP, CENTER o BOTTOM (vertical)
     * @param aH Enums que puede ser LEFT, CENTER o RIGHT (horizontal)
     */
    private void alinear(Enums.alinearVert aV, Enums.alinearHorz aH) {
        //alineamos de forma vertical
        switch (aV) {
            case TOP:
                y = y + 11;
                break;
            case CENTER:
                y = y + (11 / 2);
                break;
            case BOTTOM:
                y = y - 11;
                break;
        }

        //alineamos de forma horizontal
        switch (aH) {
            case LEFT:
                //todavía no lo hemos necesitado
                break;
            case CENTER:
                x = x - ((texto.length() * 10) / 2);
                break;
            case RIGHT:
                x = x - (texto.length() * 10);
                break;
        }
    }

    /**
     * Heredado de la interfaz Dibujable. Utilizamos el parámetro g para darle
     * el color al texto y dibujarlo en pantalla.
     *
     * @param g objeto de la clase abstracta Graphics
     */
    @Override
    public void dibujar(Graphics g) {
        if(sombra){
            g.setColor(Color.BLACK);
            g.drawString(this.texto, this.x+1, this.y+1);
        }
        
        g.setColor(this.c);
        g.drawString(this.texto, this.x, this.y);
        g.setColor(Color.WHITE);
    }

}
