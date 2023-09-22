package Cartas;

import Controladores.Texto;
import Misc.*;
import java.awt.Color;
import java.awt.Graphics;

public class Unidad extends CartaDeAccion {

    private Enums.tipoUnidad tipo;
    private Enums.talentoUnidad talento;
    private boolean puedeAtacar;
    private Carta transfor;
    private int vidaMax;

    public Unidad(Stats stats, Enums.tipoUnidad tipoUnidad, Enums.talentoUnidad talento, String nombreSprite, String nombre, String descripcion) {
        super(Enums.tipoCarta.UNIDAD, stats, nombreSprite, nombre, descripcion);
        this.tipo = tipoUnidad;
        this.talento = talento;
        this.puedeAtacar = false;
        this.vidaMax = stats.getSalud();
        invocar();
    }

    public Unidad(Stats stats, Enums.tipoUnidad tipoUnidad, Enums.talentoUnidad talento, String nombreSprite, String nombre, String descripcion, int cont, Carta transfor) {
        super(Enums.tipoCarta.UNIDAD, stats, nombreSprite, nombre, descripcion, cont);
        this.tipo = tipoUnidad;
        this.talento = talento;
        this.transfor = transfor;
        this.puedeAtacar = false;
        this.vidaMax = stats.getSalud();
        invocar();
    }

    /**
     * Si la unidad es de tipo TORRE, nunca podrá atacar. En caso de que no lo
     * sea se comprueba si el atributo puedeAtacar es verdadero.
     *
     * @return el valor de ataque, es decir, la potencia actual de la unidad
     */
    public int atacar() {
        if (this.tipo.equals(Enums.tipoUnidad.TORRE)) {
            return 0;
        }

        if (this.puedeAtacar) {
            this.setPuedeAtacar(false);
            return super.getStats().getPotencia();
        }
        return 0;
    }

    /**
     * Reduce la salud de la unidad en función del ataque recibido.
     *
     * @param ataque la potencia actual de la unidad atacante
     */
    public void recibirAtaque(int ataque) {
        super.getStats().setSalud(this.getStats().getSalud() - ataque);
        verificarVida();
    }

    private void verificarVida() {
        if (super.getStats().getSalud() <= 0) {
            super.setVivo(false);
        }
    }

    /**
     * Se dibuja la luz verde/roja en la unidad, según si esta puede o no
     * atacar. Las unidades veloces siempre tendrán la luz verde ya que siempre
     * pueden atacar. Las unidades torre, por ende, tendrá la luz roja.
     *
     * @param g gráficos. Necesarios para dibujar
     */
    @Override
    public void dibujar(Graphics g) {
        super.dibujar(g);
        if (puedeAtacar) {
            g.drawImage(this.getLedVerde(), this.getX() + 64, this.getY(), null);
        } else {
            g.drawImage(this.getLedRoja(), this.getX() + 64, this.getY(), null);
        }

    }

    /**
     * Las unidades tipo VELOZ podrán atacar nada más ser invocadas.
     */
    @Override
    public void invocar() {
        if (this.tipo.equals(Enums.tipoUnidad.VELOZ)) {
            setPuedeAtacar(true);
        }
    }

    /**
     * Se dibuja el recuadro que contiene la descripción de las cartas (está en
     * la mitad de la pantalla, en la parte derecha).
     *
     * @param g gráficos. Necesarios para dibujar
     */
    @Override
    public void descripcion(Graphics g) {
        new Texto(super.getTipoCarta().name(), Macro.PANTALLA_ANCHO - 136, Macro.PANTALLA_ALTO / 2 + 16, Color.WHITE, Enums.alinearVert.CENTER, Enums.alinearHorz.LEFT, true).dibujar(g);
        new Texto(getNombre(), Macro.PANTALLA_ANCHO - 136, Macro.PANTALLA_ALTO / 2 + 32, Color.WHITE, Enums.alinearVert.CENTER, Enums.alinearHorz.LEFT, true).dibujar(g);
        new Texto(tipo.name(), Macro.PANTALLA_ANCHO - 136, Macro.PANTALLA_ALTO / 2 + 48, Color.WHITE, Enums.alinearVert.CENTER, Enums.alinearHorz.LEFT, true).dibujar(g);
        new Texto("Coste: " + super.getStats().getCoste(), Macro.PANTALLA_ANCHO - 136, Macro.PANTALLA_ALTO / 2 + 64, Color.WHITE, Enums.alinearVert.CENTER, Enums.alinearHorz.LEFT, true).dibujar(g);
        new Texto("Potencia: " + super.getStats().getPotencia(), Macro.PANTALLA_ANCHO - 136, Macro.PANTALLA_ALTO / 2 + 80, Color.WHITE, Enums.alinearVert.CENTER, Enums.alinearHorz.LEFT, true).dibujar(g);
        new Texto("Salud: " + super.getStats().getSalud(), Macro.PANTALLA_ANCHO - 136, Macro.PANTALLA_ALTO / 2 + 96, Color.WHITE, Enums.alinearVert.CENTER, Enums.alinearHorz.LEFT, true).dibujar(g);
    }

    @Override
    public Carta clone() {
        return new Unidad(new Stats(this.getStats().getCoste(), this.getStats().getPotencia(), this.getStats().getSalud()), this.tipo, this.talento, this.getNombreSprite(), this.getNombre(), this.getDescripcion(), this.getCont(), this.transfor);
    }

    public int getVidaMax() {
        return vidaMax;
    }

    public boolean isPuedeAtacar() {
        return puedeAtacar;
    }

    public Enums.tipoUnidad getTipo() {
        return tipo;
    }

    public Enums.talentoUnidad getTalento() {
        return talento;
    }

    public void setPuedeAtacar(boolean puedeAtacar) {
        this.puedeAtacar = puedeAtacar;
    }

    public Carta getTransformacion() {
        return transfor.clone();
    }

}
