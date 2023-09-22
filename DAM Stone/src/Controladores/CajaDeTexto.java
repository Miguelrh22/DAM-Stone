package Controladores;

import Main.Juego;
import Padres.Dibujable;
import java.awt.Color;
import java.awt.Graphics;

/**
 * �til para crear cajas de texto. Se utiliza en escenas como la de Inicio,
 * donde los usuarios pueden registrarse o hacer el log in.
 */
public class CajaDeTexto implements Dibujable {

    private String nombre = "";
    private String texto = "";
    private String passwordCase = "";
    private boolean activo = false;
    private final int maxLetras = 24;

    private final Color colorSeleccionado = new Color(46, 134, 193);    //a�adimos un color propio

    private int x = 0;
    private int y = 0;
    private final int ancho = maxLetras * 12 + 12;
    private final int alto = 25;

    public CajaDeTexto(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public CajaDeTexto(int x, int y, String nombre) {
        this.x = x;
        this.y = y;
        this.nombre = nombre;   //si queremos a�adir nombre a la caja
    }

    /**
     * Dibuja la caja de texto con su respectivo m�rgen (cambia de color seg�n
     * si la caja est� activa o no). Tambi�n se encarga de dibujar el texto de
     * dentro de la caja.
     *
     * @param g gr�ficos. Necesarios para dibujar
     */
    @Override
    public void dibujar(Graphics g) {

        g.setColor(Color.WHITE);
        g.fillRect(x, y, ancho, alto);

        if (!activo) {
            g.setColor(Color.BLACK);    //si la caja est� desactivada es color negro
        } else {
            g.setColor(colorSeleccionado);  //si est� activada es de otro color (en este caso azul)
        }

        g.drawRect(x, y, ancho, alto);  //se dibuja la caja

        new Texto(nombre, x + 10, y - 16, true).dibujar(g); //se dibuja el texto de encima de la caja

        if (nombre.equals("Password")) {
            new Texto(passwordCase, x + 10, y + 16, Color.BLACK, false).dibujar(g);  //se oculta la contrase�a
        } else {
            new Texto(texto, x + 10, y + 16, Color.BLACK, false).dibujar(g);    //de lo contrario se dibuja el nombre/nickname
        }

    }

    /**
     * A�ade caracteres uno a uno, a no ser que se pulse la tecla BackSpace, que
     * borra el �ltimo car�cter introducido.
     *
     * @param e el car�cter que se introduce
     */
    public void addLetra(char e) {
        if (activo) {
            if (e != 8) {   //BackSpace es el valor 8 en la tabla ASCII
                if (texto.length() < maxLetras) {
                    texto += e;
                    passwordCase += '*';
                }
            } else {
                //borramos el �ltimo car�cter
                if (texto.length() > 0) {
                    texto = texto.substring(0, texto.length() - 1);
                    passwordCase = passwordCase.substring(0, passwordCase.length() - 1);
                }
            }
        }
    }

    /**
     * @return true s�lo si se hace click en la caja de texto
     */
    public boolean activar() {
        int mx = Juego.mouse_x;
        int my = Juego.mouse_y;
        if (mx > x && mx < x + ancho) { //comprobaci�n horizontal
            if (my > y && my < y + alto) {  //comprobaci�n vertical
                activo = true;
                return activo;
            }
        }
        return false;
    }

    public void desactivar() {
        activo = false;
    }

    public String getTexto() {
        return texto;
    }

    public boolean getActivo() {
        return activo;
    }
}
