package Cartas;

import Controladores.Texto;
import Misc.*;
import java.awt.Color;
import java.awt.Graphics;

/**
 * Dentro del juego el mana será nombrado "Bytes", pero en el código lo dejamos
 * como mana para evitar confusiones.
 */
public class CartaMana extends Carta {

    /**
     * Todas las cartas de mana son iguales.
     */
    public CartaMana() {
        super(Enums.tipoCarta.MANA, new Stats(0, 1, 0), "m_00", "Byte", "Aumenta la energía maxima del jugador");
    }

    @Override
    public void descripcion(Graphics g) {
        new Texto("Byte " + super.getStats().getPotencia(), Macro.PANTALLA_ANCHO - 112, Macro.PANTALLA_ALTO/2 + 16,Color.WHITE,Enums.alinearVert.CENTER,Enums.alinearHorz.LEFT,true).dibujar(g);
    }

    @Override
    public Carta clone() {
        return new CartaMana();
    }
}
