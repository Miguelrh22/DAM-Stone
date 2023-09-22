package Escenas;

import Controladores.Boton;
import Padres.Escena;
import Controladores.Texto;
import Main.Juego;
import Misc.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Una vez finalizada la partida de multijugador local, se crea esta escena para
 * informar al jugador de que la partida ha terminado y de si ha ganado o
 * perdido.
 */
public class FinPartidaMultijugador extends Escena {

    public final String nombre = "ResultadosMultijugador";

    private final Boton botonContinuar = new Boton(Macro.PANTALLA_ANCHO / 2 - 40, Macro.PANTALLA_ALTO / 2 + 40, "Continuar", "boton_xl_");
    private String resultado = "";
    private BufferedImage fondo = null;

    public FinPartidaMultijugador(int resultado) {
        Juego.ventana.actualizarTitulo(Macro.NOMBRE_JUEGO);
        

        if (resultado == 1) {
            this.resultado = "Has derrotado a tu oponente";
        } else if (resultado == 2) {
            this.resultado = "Has perdido :(";
        } else if (resultado == 3) {
            this.resultado = "Te has rendido :(";
        } else if (resultado == 4) {
            this.resultado = "Empate -_-";
        }
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
     * Se dibuja la escena: fondo, texto y botón continuar.
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
        new Texto(resultado, Macro.PANTALLA_ANCHO / 2, Macro.PANTALLA_ALTO / 2 - 80, Color.WHITE, Enums.alinearVert.CENTER, Enums.alinearHorz.CENTER, true).dibujar(g);
        //por último, se dibuja el botón
        botonContinuar.dibujar(g);
    }

    @Override
    public void actualizar() {
        botonContinuar.actualizar();
    }

    @Override
    public void clickIzq() {
        if (botonContinuar.getCursorEncima()) {
            Juego.limpiarLocal();
            Juego.escena = new Menu();
        }
    }

    @Override
    public void clickDch() {
    }

    @Override
    public void actualizarSegundo() {
    }

}
