package Controladores;

import Main.Juego;
import Misc.Enums;
import Padres.Actualizable;
import Padres.Dibujable;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * �til para crear botones en las escenas.
 */
public class Boton implements Dibujable, Actualizable {

    private boolean activo = true;
    private boolean cursorEncima = false;

    private String textoBoton = "";

    private int x = 0;
    private int y = 0;
    private int ancho = 40;
    private int alto = 40;
    private String spriteDefecto = "boton_default_";    //a�adimos normal, hover y desactivado
    private BufferedImage[] imagenes = new BufferedImage[3];

    public Boton(int x, int y) {
        this.x = x;
        this.y = y;
        imagenes = addImagenes(this.spriteDefecto);
    }

    public Boton(int x, int y, String textoBoton) {
        this.x = x;
        this.y = y;
        this.textoBoton = textoBoton;   //si queremos a�adir texto al boton
        imagenes = addImagenes(this.spriteDefecto);

    }

    public Boton(int x, int y, String textoBoton, String spriteDefecto) {
        this.x = x;
        this.y = y;
        this.textoBoton = textoBoton;
        this.spriteDefecto = spriteDefecto;
        imagenes = addImagenes(spriteDefecto);
    }

    /**
     * Se crea un array que siempre es de tres cajas (las 3 posibilidaddes que
     * tiene el bot�n: normal, cuando el cursor est� encima y desactivado). Se
     * rellena dicho array con las im�genes mediante ImageIO.
     *
     * @param ruta el nombre del bot�n que se queiere dibujar
     * @return las im�genes del bot�n
     */
    private BufferedImage[] addImagenes(String ruta) {
        BufferedImage[] aux = new BufferedImage[3];

        try {
            aux[0] = ImageIO.read(new File(".\\rsc\\sprites\\interfaz\\" + ruta + "normal.png"));
            aux[1] = ImageIO.read(new File(".\\rsc\\sprites\\interfaz\\" + ruta + "hover.png"));
            aux[2] = ImageIO.read(new File(".\\rsc\\sprites\\interfaz\\" + ruta + "desactivado.png"));
            this.ancho = aux[0].getWidth();
            this.alto = aux[0].getHeight();
        } catch (IOException ex) {
            System.out.println("Error al buscar imagen boton");
        }
        return aux;
    }

    /**
     * Se dibuja el bot�n para cada uno de los casos (v�ase addImagenes()).
     *
     * @param g gr�ficos. Necesarios para dibujar
     */
    @Override
    public void dibujar(Graphics g) {

        if (activo) {
            if (cursorEncima) {
                g.drawImage(imagenes[1], x, y, null);
            } else {
                g.drawImage(imagenes[0], x, y, null);
            }
        } else {
            g.drawImage(imagenes[2], x, y, null);
        }
        //finalmente, se dibuja el texto del bot�n (hay casos en los que el texto es "", por lo que no se dibuja nada)
        new Texto(textoBoton, x + (this.ancho / 2), y + (this.alto / 2), Color.BLACK, Enums.alinearVert.CENTER, Enums.alinearHorz.CENTER, false).dibujar(g);
    }

    /**
     *
     * @return true s�lo si el cursor est� encima del bot�n
     */
    public boolean getCursorEncima() {
        if (cursorEncima) {
            return cursorEncima;
        }
        return false;
    }

    /**
     * Utiliza las coordenadas actuales del rat�n y comprueba si �ste se
     * encuentra encima del cursor.
     */
    @Override
    public void actualizar() {
        int mx = Juego.mouse_x;
        int my = Juego.mouse_y;
        cursorEncima = false;   //siempre lo ponemos a false antes de comprobar
        if (mx > x && mx < x + ancho) { //comprobaci�n horizontal
            if (my > y && my < y + alto) {  //comprobaci�n vertical
                cursorEncima = true;
            }
        }
    }

    @Override
    public void actualizarSegundo() {
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public boolean getActivo() {
        return activo;
    }

}
