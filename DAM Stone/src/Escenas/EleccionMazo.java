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
 * Escena que sucede tras entrar en partida (ya sea multijugador local o reto).
 */
public class EleccionMazo extends Escena {

    public final String nombre = "EleccionMazo";

    private final Boton botonIniciar = new Boton(Macro.PANTALLA_ANCHO / 2 - 40, Macro.PANTALLA_ALTO - 200, "Iniciar");
    private final Boton botonSalir = new Boton(8, Macro.PANTALLA_ALTO - 48, "", "boton_salir_");
    private boolean multijugador = false;
    private int mazo = 0;

    private BufferedImage iconoMazos[] = new BufferedImage[3];
    private BufferedImage marcoMazo = null;
    private BufferedImage fondo = null;

    /**
     * Almacena las imágenes de los tres mazos en el array iconoMazo para que se
     * puedan dibujar con dibujar(g).
     *
     * @param multijugador true si la partida es multijugador local, false si es
     * partida reto
     */
    public EleccionMazo(boolean multijugador) {
        Juego.ventana.actualizarTitulo(Macro.NOMBRE_JUEGO);
        this.multijugador = multijugador;
        try {
            iconoMazos[0] = ImageIO.read(new File(".\\rsc\\sprites\\cartas\\u_13.png"));
            iconoMazos[1] = ImageIO.read(new File(".\\rsc\\sprites\\cartas\\u_15.png"));
            iconoMazos[2] = ImageIO.read(new File(".\\rsc\\sprites\\cartas\\h_06.png"));
            marcoMazo = ImageIO.read(new File(".\\rsc\\sprites\\partida\\marco_m.png"));
            fondo = ImageIO.read(new File(".\\rsc\\sprites\\fondos\\fondo_generico.png"));
        } catch (IOException ex) {
            System.out.println("Error: " + ex);
        }
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    /**
     * Dibuja la escena: fondo, mazos, texto y botones.
     *
     * @param g gráficos. Necesarios para dibujar
     */
    @Override
    public void dibujar(Graphics g) {
        //primero se dibuja el fondo en color gris
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, Macro.PANTALLA_ANCHO, Macro.PANTALLA_ALTO);
        g.drawImage(fondo, 0, 0, null);
        //después se añaden los mazos
        for (int i = 0; i < iconoMazos.length; i++) {
            g.drawImage(iconoMazos[i], 250 + 120 * i, Macro.PANTALLA_ALTO / 2, null);
        }
        g.drawImage(marcoMazo, 250 + 120 * mazo, Macro.PANTALLA_ALTO / 2, null);

        //se añade el texto
        if (multijugador) {
            new Texto("Partida Multijugador", Macro.PANTALLA_ANCHO / 2, Macro.PANTALLA_ALTO / 2 - 420, Color.WHITE, Enums.alinearVert.CENTER, Enums.alinearHorz.CENTER, true).dibujar(g);
        } else {
            new Texto("Partida Reto", Macro.PANTALLA_ANCHO / 2, Macro.PANTALLA_ALTO / 2 - 420, Color.WHITE, Enums.alinearVert.CENTER, Enums.alinearHorz.CENTER, true).dibujar(g);
        }
        if (this.mazo== 0) {
            new Texto("Mazo Gatuno", Macro.PANTALLA_ANCHO / 2, Macro.PANTALLA_ALTO / 2 - 130, Color.WHITE, Enums.alinearVert.CENTER, Enums.alinearHorz.CENTER, true).dibujar(g);
            new Texto("Mazo equilibrado de ataque y defensa", Macro.PANTALLA_ANCHO / 2, Macro.PANTALLA_ALTO / 2 - 100, Color.WHITE, Enums.alinearVert.CENTER, Enums.alinearHorz.CENTER, true).dibujar(g);
            new Texto("Legendaria única: Gatito", Macro.PANTALLA_ANCHO / 2, Macro.PANTALLA_ALTO / 2 - 80, Color.WHITE, Enums.alinearVert.CENTER, Enums.alinearHorz.CENTER, true).dibujar(g);
        }else if (this.mazo == 1){
            new Texto("Mazo Indie", Macro.PANTALLA_ANCHO / 2, Macro.PANTALLA_ALTO / 2 - 130, Color.WHITE, Enums.alinearVert.CENTER, Enums.alinearHorz.CENTER, true).dibujar(g);
            new Texto("Mazo ofensivo y veloz", Macro.PANTALLA_ANCHO / 2, Macro.PANTALLA_ALTO / 2 - 100, Color.WHITE, Enums.alinearVert.CENTER, Enums.alinearHorz.CENTER, true).dibujar(g);
            new Texto("Legendaria única: Programador Indie", Macro.PANTALLA_ANCHO / 2, Macro.PANTALLA_ALTO / 2 - 80, Color.WHITE, Enums.alinearVert.CENTER, Enums.alinearHorz.CENTER, true).dibujar(g);
        }else if (this.mazo == 2) {
            new Texto("Mazo Piti", Macro.PANTALLA_ANCHO / 2, Macro.PANTALLA_ALTO / 2 - 130, Color.WHITE, Enums.alinearVert.CENTER, Enums.alinearHorz.CENTER, true).dibujar(g);
            new Texto("Mazo defensivo y de control", Macro.PANTALLA_ANCHO / 2, Macro.PANTALLA_ALTO / 2 - 100, Color.WHITE, Enums.alinearVert.CENTER, Enums.alinearHorz.CENTER, true).dibujar(g);
            new Texto("Legendaria única: Pausa para fumar", Macro.PANTALLA_ANCHO / 2, Macro.PANTALLA_ALTO / 2 - 80, Color.WHITE, Enums.alinearVert.CENTER, Enums.alinearHorz.CENTER, true).dibujar(g);
        }
        //por último, se dibujan los botones
        botonIniciar.dibujar(g);
        botonSalir.dibujar(g);
    }

    /**
     * Se actualizan constantemente los botones. Útil para saber si el cursor se
     * encuenntra encima de los mismos, o si son pulsados.
     */
    @Override
    public void actualizar() {
        botonIniciar.actualizar();
        botonSalir.actualizar();
    }

    @Override
    public void actualizarSegundo() {
    }

    /**
     * Se controla el click del usuario para la elección del mazo y la creación
     * de nuevas escenas (multijugador, reto o menú).
     */
    @Override
    public void clickIzq() {
        if (botonIniciar.getCursorEncima()) {
            if (this.multijugador) {
                Juego.escena = new Partida(mazo);
            } else {
                Juego.escena = new PartidaReto(mazo);
            }

        }
        if (botonSalir.getCursorEncima()) {
            Juego.escena = new Menu();
        }

        //aquí controlamos la elección del mazo, por defecto viene seleccionado el mazo estándar (el de la izquierda)
        int auxMazo = getMazoSeleccionado();
        if (auxMazo != -1) {
            this.mazo = auxMazo;
        }
    }

    @Override
    public void clickDch() {
    }

    private int getMazoSeleccionado() {
        int mx = Juego.mouse_x;
        int my = Juego.mouse_y;
        if (mx > 250 && mx < 570) { //comprobación horizontal
            if (my > Macro.PANTALLA_ALTO / 2 && my < Macro.PANTALLA_ALTO / 2 + 120) {   //comprobación vertical
                return ((mx - 250) / 120);
            }
        }
        return -1;
    }
}
