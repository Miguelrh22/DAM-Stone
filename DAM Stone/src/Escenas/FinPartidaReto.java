package Escenas;

import Controladores.Boton;
import Padres.Escena;
import Controladores.Texto;
import Main.Juego;
import Misc.*;
import java.awt.Color;
import java.awt.Graphics;
import DB.DataBase;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Una vez finalizada la partida contraa la IA, se crea esta escena para
 * informar al jugador de que la partida ha terminado y de su puntuación.
 */
public class FinPartidaReto extends Escena {

    public final String nombre = "Resultados";
    private final Boton botonContinuar = new Boton(Macro.PANTALLA_ANCHO / 2 - 40, Macro.PANTALLA_ALTO / 2 + 40, "Continuar", "boton_xl_");
    private int puntuacion = 0;
    private BufferedImage fondo = null;

    public FinPartidaReto(String usuario, int puntuacion, int nivel) {
        Juego.ventana.actualizarTitulo(Macro.NOMBRE_JUEGO);
        this.puntuacion = puntuacion;
        DataBase.ponerPuntuacion(usuario, puntuacion, nivel);   //se guarda la puntuación en la BBDD
        DataBase.cerrarConexion();  //se cierra la conexión con la BBDD
        try{
            fondo = ImageIO.read(new File(".\\rsc\\sprites\\fondos\\fondo_generico.png"));
        } catch(IOException ex){
            System.out.println("Error Carga Imagen Fondo");
        }
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    /**
     * Dibuja la escena.
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
        new Texto("Has obtenido " + this.puntuacion + " puntos!", Macro.PANTALLA_ANCHO / 2, Macro.PANTALLA_ALTO / 2 - 80, Color.WHITE, Enums.alinearVert.CENTER, Enums.alinearHorz.CENTER, true).dibujar(g);

        //se dibuja el botón
        botonContinuar.dibujar(g);
    }

    /**
     * Si hace click izquierdo en el botón continuar, el jugador vuelve al menú.
     */
    @Override
    public void clickIzq() {
        if (botonContinuar.getCursorEncima()) {
            Juego.escena = new Menu();
        }
    }

    @Override
    public void actualizar() {
        botonContinuar.actualizar();
    }

    @Override
    public void actualizarSegundo() {
    }

    @Override
    public void clickDch() {
    }

}
