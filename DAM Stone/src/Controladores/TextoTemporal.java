package Controladores;

import Padres.Dibujable;
import java.awt.Color;
import java.awt.Graphics;

/**
 * Se utiliza para dar feedback al usuario, por ejemplo, mostrar el daño o la
 * sanación de las unidades o del enemigo.
 */
public class TextoTemporal implements Dibujable {

    private final int x;
    private final int y;
    private int y2 = 0;
    private int oscilacion = 0;
    private final String texto;
    private double oscilacionReducida = 0;

    private final int amplificacion = 5;
    private final double velocidadOscilacion = .3;

    private final double tiempoMax = 120;   //segundos * 60 
    private double tiempo = tiempoMax;
    private final Color colorR = new Color(231, 76, 60);
    private final Color colorV = new Color(72, 201, 176);
    private final Color colorA = new Color(142, 68, 173);

    public TextoTemporal(String texto, int x, int y) {
        this.texto = texto;
        this.x = x;
        this.y = y;
    }

    /**
     * ejecuta la animación de moverse del texto y su tiempo de vida.
     * Usa una funcion armonica y una oscilacion para calcular la variacion de la posicion del texto.
     * La oscilación va de 0-360 grados, al pasarlo por la funcion sin, nos da un valor entre -1 y 1. 
     * Esto permite que el texto de la sensación de botar o levitar.
     * 
     * @return true si el texto deja de moverse, false si el texto sigue moviendose.
     */
    public boolean efecto() {
        oscilacionReducida += velocidadOscilacion;
        if (oscilacionReducida > 1) {
            oscilacion++;
            if (oscilacion >= 360) {
                oscilacion = 0;
            }
            oscilacionReducida = 0;
        }

        int variacionAmplificacion = (int) ((tiempo / tiempoMax) * amplificacion);
        y2 = (int) (Math.sin(oscilacion) * variacionAmplificacion);
        tiempo--;
        return variacionAmplificacion == 1;
    }

    /**
     * Dibuja el texto, en el caso de tener un '-' lo identifica como restar vida y por lo tanto se le asigna el color rojo.
     * En caso contrario usa el color verde.
     *
     * @param g gráficos. Necesarios para dibujar
     */
    @Override
    public void dibujar(Graphics g) {
        if (texto.charAt(0) == '-') {
            new Texto(texto, x, y + y2, colorR, true).dibujar(g);
        } else {
            if (texto.charAt(0) == '+') {
                new Texto(texto, x, y + y2, colorV, true).dibujar(g);
            } else {
                if (texto.charAt(0) == '*') {
                    new Texto(texto, x, y + y2, colorA, true).dibujar(g);
                } else {
                    new Texto(texto, x, y, Color.WHITE, true).dibujar(g);
                }
            }
        }
        
    }

}
