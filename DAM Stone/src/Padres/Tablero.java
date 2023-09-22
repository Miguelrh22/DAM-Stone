package Padres;

import Misc.*;
import Cartas.*;
import Repositorios.RepositorioCartas;
import Repositorios.RepositorioMazos;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * útil para crear el mazo y barajarlo, crear la mano, iniciar el turno y
 * controlar los ataques de las unidades. Además, también comprueba y controla
 * los contadores (necesarios para el correcto funcionamiento del juego).
 */
public abstract class Tablero implements Dibujable, Actualizable, Serializable {

    private Carta[] unidades = new Carta[4];
    private Carta[] hechizos = new Carta[4];

    private int vida_max = 18;
    private int vida = vida_max;

    private int mana_max = 0;
    private int mana = mana_max;

    private ArrayList<Carta> mazo = new ArrayList();
    private ArrayList<Carta> mano = new ArrayList();

    public abstract boolean jugarUnidad(int manoIndex, int slotIndex);

    public abstract boolean jugarHechizo(int manoIndex, int slotIndex);
    
    private boolean rendido = false;

    /**
     * Útil para crear una mazo (ArrayList) añadiendo nuevas cartas.
     */
    public ArrayList<Carta> crearMazo(int mazoID) {
        //tenemos que añadir que valor le pasamos
        return RepositorioMazos.elegirMazo(mazoID);
    }

    /**
     * Útil para barajar el mazo. Mediante un número random se selecciona una
     * carta del ArrayList original, se lleva al nuevo mazo, y se borra del
     * origial.
     *
     * @param e valor de entrada. Carta que se añade al nuevo mazo
     */
    public ArrayList<Carta> barajarMazo(ArrayList<Carta> e) {
        ArrayList<Carta> mazo = new ArrayList();
        Random rand = new Random();
        while (!e.isEmpty()) {
            mazo.add(e.remove(rand.nextInt(e.size())));
        }
        return mazo;
    }

    /**
     * Útil para crear una mano (ArrayList) con cartas cogidas del mazo. Se
     * utilizan las primeras cartas, que se eliminan del mazo original.
     */
    public ArrayList<Carta> crearMano() {
        ArrayList<Carta> mano = new ArrayList();
        for (int i = 0; i < 6; i++) {
            mano.add(this.mazo.remove(i));
        }
        return mano;
    }

    /**
     * Se inicia el turno y el mana aumenta automáticamente en +1. Cuando llega
     * a 10, el mana no sigue aumentando.
     */
    public void inicioTurno() {
        if (this.mazo.isEmpty()) {  //el jugador pierde la partida si su mazo está vacío al inicio de su turno
            this.vida = 0;
        } else {
            if (this.mana_max < 10) {
                this.mana_max++;
            }
            if (this.mano.size() < 8) { //el número máximo de cartas en la mano es 7
                this.mano.add(this.mazo.remove(0));
            }
            this.mana = mana_max;
        }

    }

    /**
     * Útil para reducir en 1 los contadores de todas las unidades y hechizos
     * que están en juego. Esto sirve para las unidades con trasformación y para
     * los hechizos. En caso de que una unidad no tenga tranformación, su
     * contador por defecto ya es negativo, por lo que restarle 1 no es ningún
     * problema.
     */
    public void cambiarContador() {
        for (int i = 0; i < this.getUnidades().length; i++) {  //Unidades lo volvemos a igualar, hechizos no para comprobar lo del puntero
            if (this.getUnidades()[i] != null) {
                Unidad aux1 = (Unidad) this.getUnidades()[i];   //casting necesario para acceder al setter del contador
                aux1.setCont(aux1.getCont() - 1);
            }
        }
        for (int i = 0; i < this.getHechizos().length; i++) {
            if (this.getHechizos()[i] != null) {
                Hechizo aux2 = (Hechizo) this.getHechizos()[i]; //casting necesario para acceder al setter del contador
                aux2.setCont(aux2.getCont() - 1);
            }
        }
        comprobarContador();
    }

    /**
     * Comprobación de contadores. Es un método realmente largo debido a la
     * existencia de ciertas cartas que poseen efectos adicionales o diferentes.
     */
    private void comprobarContador() {

        for (int i = 0; i < this.getUnidades().length; i++) {   //UNIDADES
            if (this.getUnidades()[i] != null) {
                Unidad aux1 = (Unidad) this.getUnidades()[i];
                if (aux1.getTipo() == Enums.tipoUnidad.TRANSFORMACION) {    //SÓLO LAS QUE TIENEN TRANSFORMACIÓN
                    if (aux1.getCont() == 0) {
                        if (aux1.getNombre().equals("Desarrollador Indie")) {   //Esta es la carta especial de Miguel, al igual que el Gatito es de Dragos y la Pausa para piti es de Pedro 
                            if (new Random().nextInt(4) == 0) { //si el random es 0 se convierte en un Desarrollador indie existoso
                                Carta auxTransfor = aux1.getTransformacion();
                                auxTransfor.setX(aux1.getX());
                                auxTransfor.setY(aux1.getY());
                                this.getUnidades()[i] = auxTransfor;
                                this.mana_max++;    //otorga un mana extra (es la única forma de tener 11 manas)
                            } else {    //de lo contrario se convierte en un Empleado en paro
                                Carta auxTransfor = RepositorioCartas.obtenerCarta("Empleado en paro").clone();
                                auxTransfor.setX(aux1.getX());
                                auxTransfor.setY(aux1.getY());
                                this.getUnidades()[i] = auxTransfor;
                            }
                        } else {    //TODAS LAS DEMÁS UNIDADES QUE TIENEN TRANSFORMACIÓN Y NO SON DESARROLLADOR INDIE
                            Carta auxTransfor = aux1.getTransformacion();
                            auxTransfor.setX(aux1.getX());
                            auxTransfor.setY(aux1.getY());
                            this.getUnidades()[i] = auxTransfor;
                        }
                    }
                }
            }
        }
        for (int i = 0; i < this.getHechizos().length; i++) {   //HECHIZOS
            if (this.getHechizos()[i] != null) {
                Hechizo aux2 = (Hechizo) this.getHechizos()[i];
                if (aux2.getTipoHechizo().equals(Enums.tipoHechizo.MAS_STATS)) {    //HECHIZOS QUE AUMENTAN AMBOS STATS DE UNA UNIDAD
                    if (aux2.getCont() == 0) {
                        this.unidades[i].getStats().setPotencia(this.unidades[i].getStats().getPotencia() - aux2.getStats().getPotencia());
                        this.unidades[i].getStats().setSalud(this.unidades[i].getStats().getSalud() - aux2.getStats().getPotencia());
                        if (this.unidades[i].getStats().getSalud() <= 0) {  //cuando el contador del hechizo llega a 0 y se restan las stats, la unidad puede morir
                            this.unidades[i] = null;
                        }
                    } else {
                        if (this.getUnidades()[i] == null) {
                            this.getHechizos()[i] = null;
                        }
                    }
                }
                if (aux2.getCont() == 0) {
                    this.getHechizos()[i] = null;
                }
            }
        }
    }

    /**
     * Las unidades no pueden atacar el turno en el que son invocadas (salvo las
     * Veloces)
     */
    public void setAtacar() {
        for (int i = 0; i < this.getUnidades().length; i++) {
            if (this.getUnidades()[i] != null) {
                Unidad aux = (Unidad) this.getUnidades()[i];
                if (aux.getTipo() != Enums.tipoUnidad.TORRE) {
                    aux.setPuedeAtacar(true);
                } else {    //si la unidad es Torre no podrá atacar nunca
                    aux.setPuedeAtacar(false);
                }
            }
        }
    }

    public int getVida() {
        return vida;
    }

    public int getVida_max() {
        return vida_max;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public void setVida_max(int vida_max) {
        this.vida_max = vida_max;
    }

    public int getMana() {
        return mana;
    }

    public int getMana_max() {
        return mana_max;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public void setMana_max(int mana_max) {
        this.mana_max = mana_max;
    }

    public ArrayList<Carta> getMazo() {
        return mazo;
    }

    public void setMazo(ArrayList<Carta> mazo) {
        this.mazo = mazo;
    }

    public ArrayList<Carta> getMano() {
        return mano;
    }

    public void setMano(ArrayList<Carta> mano) {
        this.mano = mano;
    }

    public Carta[] getUnidades() {
        return unidades;
    }

    public Carta[] getHechizos() {
        return hechizos;
    }

    public void setUnidades(Carta[] unidades) {
        this.unidades = unidades;
    }

    public void setHechizos(Carta[] hechizos) {
        this.hechizos = hechizos;
    }

    @Override
    public void dibujar(Graphics g) {
    }

    @Override
    public void actualizar() {
    }

    @Override
    public void actualizarSegundo() {
    }

    public boolean isRendido() {
        return rendido;
    }

    public void setRendido(boolean rendirse) {
        this.rendido = rendirse;
    }
}
