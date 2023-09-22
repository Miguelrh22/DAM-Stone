package Cartas;

import Controladores.Texto;
import Misc.*;
import java.awt.Color;
import java.awt.Graphics;

public class Hechizo extends CartaDeAccion {

    private Enums.tipoHechizo tipoHechizo;
    private Enums.duracionHechizo duracionHechizo;

    public Hechizo(Enums.tipoHechizo tipoHechizo, Stats stats, String nombreSprite,String nombre,String descripcion) {
        super(Enums.tipoCarta.HECHIZO, stats, nombreSprite, nombre, descripcion);
        this.tipoHechizo = tipoHechizo;
    }
     public Hechizo(Enums.tipoHechizo tipoHechizo, Stats stats, String nombreSprite,String nombre,String descripcion, int cont) {
        super(Enums.tipoCarta.HECHIZO, stats, nombreSprite, nombre, descripcion, cont);
        this.tipoHechizo = tipoHechizo;
    }

    /**
     * Todos los hechizos instantáneos pondrán el atributo vivo en false al
     * finar de este método.
     */
    @Override
    public void invocar() {
//        if (this.duracionHechizo.equals(Enums.duracionHechizo.INSTANTANEO)){
//            super.setVivo(false);
//        }
    }

    /**
     * Dibuja toda la información de la carta en la esquina inferior derecha de
     * la pantalla.
     *
     * @param g objeto de la clase abstracta Graphics
     */
    @Override
    public void descripcion(Graphics g) {
        new Texto(super.getTipoCarta().name(), Macro.PANTALLA_ANCHO - 136, Macro.PANTALLA_ALTO/2 + 16,Color.WHITE,Enums.alinearVert.CENTER,Enums.alinearHorz.LEFT, true).dibujar(g);
        new Texto(tipoHechizo.name(), Macro.PANTALLA_ANCHO - 136, Macro.PANTALLA_ALTO/2 + 32,Color.WHITE,Enums.alinearVert.CENTER,Enums.alinearHorz.LEFT, true).dibujar(g);
        new Texto("Coste: " + super.getStats().getCoste(), Macro.PANTALLA_ANCHO - 136, Macro.PANTALLA_ALTO/2 + 48,Color.WHITE,Enums.alinearVert.CENTER,Enums.alinearHorz.LEFT, true).dibujar(g);
        new Texto("Potencia: " + super.getStats().getPotencia(), Macro.PANTALLA_ANCHO - 136, Macro.PANTALLA_ALTO/2 + 64,Color.WHITE,Enums.alinearVert.CENTER,Enums.alinearHorz.LEFT, true).dibujar(g);
    }

    @Override
    public Carta clone() {
        return new Hechizo(this.tipoHechizo, new Stats(this.getStats().getCoste(), this.getStats().getPotencia(),this.getStats().getSalud()), this.getNombreSprite(), this.getNombre(), this.getDescripcion(),  this.getCont());
    }

    public Enums.tipoHechizo getTipoHechizo() {
        return tipoHechizo;
    }
    
}
