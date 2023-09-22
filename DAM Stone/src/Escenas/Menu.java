package Escenas;

import Controladores.Boton;
import Padres.Escena;
import Controladores.Texto;
import Main.Juego;
import Misc.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Controla los diferentes caminos del jugador: salir del juego, jugar
 * multijugador local, jugar partida reto u observar las puntuaciones.
 */
public class Menu extends Escena {

    public final String nombre = "Menu";

    private final Boton botonJugarLocal = new Boton(Macro.PANTALLA_ANCHO / 2 - 48, Macro.PANTALLA_ALTO / 2, "Jugar");
    private final Boton botonJugarReto = new Boton(Macro.PANTALLA_ANCHO / 2 - 48, Macro.PANTALLA_ALTO / 2 + 60, "Reto");
    private final Boton botonPuntuacion = new Boton(Macro.PANTALLA_ANCHO / 2 - 48, Macro.PANTALLA_ALTO / 2 + 120, "Puntos");
    private final Boton botonSalir = new Boton(8, Macro.PANTALLA_ALTO - 48, "", "boton_partida_");
    private BufferedImage fondo = null;
    
    public Menu() {
        Juego.ventana.actualizarTitulo(Macro.NOMBRE_JUEGO_FORMATO);
        try{
            fondo = ImageIO.read(new File(".\\rsc\\sprites\\fondos\\fondo_menu.png"));
        } catch(IOException ex){
            System.out.println("Error Carga Imagen Fondo");
        }
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    /**
     * Dibuja el nombre del juego, el nombre del usuario y los botones.
     *
     * @param g gráficos. Necesarios para dibujar
     */
    @Override
    public void dibujar(Graphics g) {
        //primero se dibuja el fondo en color gris
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, Macro.PANTALLA_ANCHO, Macro.PANTALLA_ALTO);
        g.drawImage(fondo, 0, 0, null);
        //después se añade el texto
        //new Texto(Macro.NOMBRE_JUEGO_FORMATO, Macro.PANTALLA_ANCHO / 2, 160, Color.WHITE, Enums.alinearVert.CENTER, Enums.alinearHorz.CENTER, true).dibujar(g);

        //Nombre del jugador
        new Texto(Inicio.getNombreUsuario(), Macro.PANTALLA_ANCHO - 18, Macro.PANTALLA_ALTO - 18, Color.WHITE, Enums.alinearVert.TOP, Enums.alinearHorz.RIGHT, true).dibujar(g);

        botonJugarLocal.dibujar(g);
        botonJugarReto.dibujar(g);
        botonPuntuacion.dibujar(g);
        botonSalir.dibujar(g);
    }

    /**
     * Se actualian los botones para ver si se ha pulsado alguno. Esta
     * actualización se hace 60 veces por segundo.
     */
    @Override
    public void actualizar() {
        botonJugarLocal.actualizar();
        botonJugarReto.actualizar();
        botonPuntuacion.actualizar();
        botonSalir.actualizar();
    }

    @Override
    public void actualizarSegundo() {
    }

    /**
     * Controla las diferentes escenas a las que conducen los botones.
     */
    @Override
    public void clickIzq() {
        if (botonJugarLocal.getCursorEncima()) {
            Juego.escena = new EleccionMazo(true);
        }
        if (botonJugarReto.getCursorEncima()) {
            Juego.escena = new EleccionMazo(false);
        }
        if (botonPuntuacion.getCursorEncima()) {
            Juego.escena = new MostrarPuntuaciones();
        }
        if (botonSalir.getCursorEncima()) {
            Juego.ventana.dispatchEvent(new WindowEvent(Juego.ventana, WindowEvent.WINDOW_CLOSING));
        }
    }

    @Override
    public void clickDch() {
    }

}
