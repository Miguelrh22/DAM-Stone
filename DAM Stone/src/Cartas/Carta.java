package Cartas;

import Controladores.Texto;
import Misc.Enums;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import Padres.Dibujable;
import java.io.Serializable;

public abstract class Carta implements Dibujable, Serializable {

    private Stats stats;
    private transient Enums.tipoCarta tipoCarta;
    private transient BufferedImage imagen;
    private transient BufferedImage ledRoja;    //indica que la unidad no puede atacar
    private transient BufferedImage ledVerde;   //indica que la unidad puede atacar
    private int x;
    private int y;
    private String nombre;
    private String descripcion;
    private String nombreSprite;

    public Carta(Enums.tipoCarta tipoCarta, Stats stats, String nombreSprite, String nombre, String descripcion) {
        this.tipoCarta = tipoCarta;
        this.stats = stats;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.nombreSprite = nombreSprite;
        asignarImagen();
    }

    /**
     * Utiliza ImageIO para asignar imágenes a las cartas. Las imágenes son la
     * carta y el led, que cambia de color si la carta puede atacar o no.
     * ImageIO es una clase que contiene métodos estáticos que ayudan a leer
     * imágenes.
     */
    public void asignarImagen() {
        try {
            imagen = ImageIO.read(new File(".\\rsc\\sprites\\cartas\\" + nombreSprite + ".png")); //las imágenes de las cartas se encuentran en la carpeta rsc (resources)
            ledRoja = ImageIO.read(new File(".\\rsc\\sprites\\partida\\carta_desactivada.png"));
            ledVerde = ImageIO.read(new File(".\\rsc\\sprites\\partida\\carta_activada.png"));
        } catch (IOException ex) {
            System.out.println("Error: " + ex);
        }
    }

    /**
     * Utiliza g.drawImage() para dibujar las cartas.
     *
     * @param g objeto de la clase abstracta Graphics
     */
    @Override
    public void dibujar(Graphics g) {
        //g.drawRect(x, y, 48, 96);

        g.drawImage(imagen, x, y, null);

        g.setFont(g.getFont().deriveFont(30f));
        if (stats.getCoste() != 0) {
            new Texto(stats.getCoste() + "", x + 6, y + 26, true).dibujar(g);
        }

        if (stats.getPotencia() != 0) {
            new Texto(stats.getPotencia() + "", x + 8, y + 112, true).dibujar(g);
        }

        if (stats.getSalud() != 0) {
            new Texto(stats.getSalud() + "", x + 56, y + 112, true).dibujar(g);
        }
        g.setFont(g.getFont().deriveFont(18f));
        //g.drawImage(imagen, x, y, null);
    }

    /**
     * Obliga a todas las cartas a tener una descripción.
     *
     * @param g objeto de la clase abstracta Graphics
     */
    public abstract void descripcion(Graphics g);

    @Override
    public abstract Carta clone();

    public Stats getStats() {
        return stats;
    }

    public Enums.tipoCarta getTipoCarta() {
        return tipoCarta;
    }

    public int getWidth() {
        return imagen.getWidth();
    }

    public int getHeight() {
        return imagen.getHeight();
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public void setTipoCarta(Enums.tipoCarta tipoCarta) {
        this.tipoCarta = tipoCarta;
    }

    public void setImagen(BufferedImage imagen) {
        this.imagen = imagen;
    }

    public BufferedImage getImagen() {
        return imagen;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNombreSprite() {
        return nombreSprite;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public BufferedImage getLedVerde() {
        return ledVerde;
    }

    public BufferedImage getLedRoja() {
        return ledRoja;
    }

}
