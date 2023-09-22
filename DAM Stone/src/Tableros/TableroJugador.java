package Tableros;

import Padres.Tablero;
import Controladores.Texto;
import Misc.*;
import Cartas.Carta;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Se aconseja ver la documentación de Padres.Tablero.java.
 */
public class TableroJugador extends Tablero {

    //---------- COORDENADAS ----------//
    private final int MANO_X = 27;
    private final int MANO_Y = 832;
    private final int TABLERO_UNIDADES_X = 207;
    private final int TABLERO_UNIDADES_Y = 496;
    private final int TABLERO_HECHIZOS_X = 207;
    private final int TABLERO_HECHIZOS_Y = 624;
    private final int CARTA_ANCHO = 84;
    private final int CARTA_ALTO = 120;
    private final int MARGEN_HORIZONTAL = 6;
    private final int MARGEN_VERTICAL = 8;
    private transient BufferedImage imagenMazo;

    /**
     * Se crean el mazo y la mano. Se le asigna una imagen al mazo.
     *
     * @param mazoID el mazo seleccionado en la escena EleccionMazo
     */
    public TableroJugador(int mazoID) {
        setMazo(barajarMazo(crearMazo(mazoID)));
        setMano(crearMano());
        try {
            imagenMazo = ImageIO.read(new File(".\\rsc\\sprites\\partida\\mazo_e.png")); //las imágenes de las cartas se encuentran en la carpeta rsc (resources)
        } catch (IOException ex) {
            System.out.println("Error: " + ex);
        }
    }

    /**
     * @param manoIndex la posición (en la mano) de la unidad que se quiere
     * jugar
     * @param slotIndex la posición (en el tablero) en la que se quiere jugar la
     * unidad
     * @return true sólo si se ha jugado la unidad, false de lo contrario
     */
    @Override
    public boolean jugarUnidad(int manoIndex, int slotIndex) {
        Enums.tipoCarta auxTipoCarta = getMano().get(manoIndex).getTipoCarta(); //guardamos el tipo de carta (unidad o hechizo)

        if (auxTipoCarta == Enums.tipoCarta.UNIDAD) {   //comprobamos que es una unidad
            if (getUnidades()[slotIndex] == null) { //comprobamos que tiene sitio en la posición elegida del tablero
                getUnidades()[slotIndex] = getMano().remove(manoIndex); //se quita la unidad de la mano
                getUnidades()[slotIndex].setX(TABLERO_UNIDADES_X + (slotIndex * (CARTA_ANCHO + MARGEN_HORIZONTAL)));    //coordenadas del tablero + la carta 
                getUnidades()[slotIndex].setY(TABLERO_UNIDADES_Y);
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param manoIndex la posición (en la mano) del hechizo que se quiere jugar
     * @param slotIndex la posición (en el tablero) en la que se quiere jugar el
     * hechizo
     * @return true sólo si se ha jugado el hechizo, false de lo contrario
     */
    @Override
    public boolean jugarHechizo(int manoIndex, int slotIndex) {
        Enums.tipoCarta auxTipoCarta = getMano().get(manoIndex).getTipoCarta(); //guardamos el tipo de carta (unidad o hechizo)

        if (auxTipoCarta == Enums.tipoCarta.HECHIZO) {  //comprobamos que es un hechizo
            if (getHechizos()[slotIndex] == null) { //comprobamos que tiene sitio en la posición elegida del tablero
                getHechizos()[slotIndex] = getMano().remove(manoIndex); //se quita el hechizo de la mano
                getHechizos()[slotIndex].setX(TABLERO_HECHIZOS_X + (slotIndex * (CARTA_ANCHO + MARGEN_HORIZONTAL)));    //coordenadas del tablero + la carta 
                getHechizos()[slotIndex].setY(TABLERO_HECHIZOS_Y);
                return true;
            }
        }
        return false;
    }

    /**
     * Dibuja la escena: cartas de la mano y del tablero, vida y mana.
     *
     * @param g gráficos. Necesarios para dibujar
     */
    @Override
    public void dibujar(Graphics g) {
        //dibujamos la mano
        for (Carta carta : getMano()) {
            carta.dibujar(g);
        }

        //dibujamos las unidades
        for (int i = 0; i < getUnidades().length; i++) {
            if (getUnidades()[i] != null) {
                getUnidades()[i].dibujar(g);
            }
        }

        //dibujamos los hechizos
        for (int i = 0; i < getHechizos().length; i++) {
            if (getHechizos()[i] != null) {
                getHechizos()[i].dibujar(g);
            }
        }
        //dibujamos la imagen del mazo y su tamaño
        g.drawImage(imagenMazo, 6, Macro.PANTALLA_ALTO - 480 - 18, null);
        new Texto(getMazo().size() + "/" + 30, 45, 532, Color.WHITE, Enums.alinearVert.CENTER, Enums.alinearHorz.CENTER, true).dibujar(g);

        //dibujamos la vida y el mana
        
        new Texto("Núcleos", 45, 645, Color.WHITE, Enums.alinearVert.CENTER, Enums.alinearHorz.CENTER, true).dibujar(g);
        new Texto(getVida() + "/" + getVida_max(), 45, 670, Color.WHITE, Enums.alinearVert.CENTER, Enums.alinearHorz.CENTER, true).dibujar(g);

        
        new Texto("Bytes", 45, 735, Color.WHITE, Enums.alinearVert.CENTER, Enums.alinearHorz.CENTER, true).dibujar(g);
        new Texto(getMana() + "/" + getMana_max(), 45, 760, Color.WHITE, Enums.alinearVert.CENTER, Enums.alinearHorz.CENTER, true).dibujar(g);
        

    }

    /**
     * Actualiza la posición de las cartas para que estas se dibujen
     * correctamente en cada momento.
     */
    @Override
    public void actualizar() {
        for (int i = 0; i < getMano().size(); i++) {
            getMano().get(i).setX(MANO_X + (i * (CARTA_ANCHO + MARGEN_HORIZONTAL)));
            getMano().get(i).setY(MANO_Y);
        }
    }
}
