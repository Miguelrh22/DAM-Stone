package Tableros;

import Padres.Tablero;
import Cartas.Carta;
import Controladores.Texto;
import Misc.Enums;
import java.awt.Color;
import java.awt.Graphics;

/**
 * Se aconseja ver la documentación de Padres.Tablero.java.
 */
public class TableroOponente extends Tablero {

    //---------- COORDENADAS ----------//
    private final int TABLERO_UNIDADES_X = 207;
    private final int TABLERO_UNIDADES_Y = 292;
    private final int TABLERO_HECHIZOS_X = 207;
    private final int TABLERO_HECHIZOS_Y = 164;
    private final int CARTA_ANCHO = 84;
    private final int CARTA_ALTO = 120;
    private final int MARGEN_HORIZONTAL = 6;
    private final int MARGEN_VERTICAL = 8;

    /**
     * Se prepara el tablero del oponente en su correspondiente lugar.
     *
     * @param unidades las unidades que tiene el enemigo en su tablero
     * @param hechizos los hechizos que tiene el enemigo en su tablero
     */
    public TableroOponente(Carta[] unidades, Carta[] hechizos) {
        setUnidades(unidades);
        setHechizos(hechizos);
        for (int i = 0; i < getUnidades().length; i++) {    //primero las unidades
            if (getUnidades()[i] != null) {
                getUnidades()[i].setX(TABLERO_UNIDADES_X + (i * (CARTA_ANCHO + MARGEN_HORIZONTAL)));
                getUnidades()[i].setY(TABLERO_UNIDADES_Y);
                getUnidades()[i].asignarImagen();
            }
        }
        for (int i = 0; i < getHechizos().length; i++) {    //luego los hechizos
            if (getHechizos()[i] != null) {
                getHechizos()[i].setX(TABLERO_HECHIZOS_X + (i * (CARTA_ANCHO + MARGEN_HORIZONTAL)));
                getHechizos()[i].setY(TABLERO_HECHIZOS_Y);
                getHechizos()[i].asignarImagen();
            }
        }
    }

    public void recalcular() {
    }

    /**
     * Dibuja la información del oponente: vida, mana, mano, mazo, unidades y
     * hechizos.
     *
     * @param g gráficos. Necesarios para dibujar
     */
    @Override
    public void dibujar(Graphics g) {
        //dibujamos vida, mana, mano y mazo
        new Texto("Bytes", 96, 52, Color.WHITE, Enums.alinearVert.CENTER, Enums.alinearHorz.CENTER, true).dibujar(g);
        new Texto(getMana_max() + "/" + getMana_max(), 96, 72, Color.WHITE, Enums.alinearVert.CENTER, Enums.alinearHorz.CENTER, true).dibujar(g);
        
        new Texto("Núcleos", 288, 52, Color.WHITE, Enums.alinearVert.CENTER, Enums.alinearHorz.CENTER, true).dibujar(g);
        new Texto(getVida() + "/" + getVida_max(), 288, 72, Color.WHITE, Enums.alinearVert.CENTER, Enums.alinearHorz.CENTER, true).dibujar(g);
        
        new Texto("RAM", 480, 52, Color.WHITE, Enums.alinearVert.CENTER, Enums.alinearHorz.CENTER, true).dibujar(g);
        new Texto(getMano().size() + "", 480, 72, Color.WHITE, Enums.alinearVert.CENTER, Enums.alinearHorz.CENTER, true).dibujar(g);
        
        new Texto("Almacenamiento", 672, 52, Color.WHITE, Enums.alinearVert.CENTER, Enums.alinearHorz.CENTER, true).dibujar(g);
        new Texto(getMazo().size() + "/30", 672, 72, Color.WHITE, Enums.alinearVert.CENTER, Enums.alinearHorz.CENTER, true).dibujar(g);

        //dibujamos unidaddes
        for (int i = 0; i < getUnidades().length; i++) {
            if (getUnidades()[i] != null) {
                getUnidades()[i].dibujar(g);
            }
        }

        //dibujamos hechizos
        for (int i = 0; i < getHechizos().length; i++) {
            if (getHechizos()[i] != null) {
                getHechizos()[i].dibujar(g);
            }
        }
    }

    @Override
    public boolean jugarUnidad(int manoIndex, int slotIndex) {
        return false;
    }

    @Override
    public boolean jugarHechizo(int manoIndex, int slotIndex) {
        return false;
    }

    @Override
    public void actualizar() {
    }

}
