package Escenas;

import Cartas.Carta;
import Cartas.Hechizo;
import Cartas.Stats;
import Cartas.Unidad;
import Main.Juego;
import Misc.Enums;
import Tableros.TableroOponente;
import java.util.Random;

/**
 * Controla la partida contra la IA. Hay 3 niveles de dificultad.
 */
public class PartidaReto extends Partida {

    private final int TABLERO_UNIDADES_IA_X = 207;
    private final int TABLERO_UNIDADES_IA_Y = 292;
    private final int TABLERO_HECHIZOS_IA_X = 207;
    private final int TABLERO_HECHIZOS_IA_Y = 164;
    private final int CARTA_ANCHO_IA = 84;
    private final int CARTA_ALTO_IA = 120;
    private final int MARGEN_HORIZONTAL_IA = 6;
    private final int MARGEN_VERTICAL_IA = 8;
    public final String nombre = "PartidaReto";
    private final int dificultad;

    /**
     * Sirve para la creación del primer nivel.
     *
     * @param mazo mazo del jugador (elegido previamente en EleccionMazo)
     */
    public PartidaReto(int mazo) {
        super(true, mazo);
        Juego.ventana.actualizarTitulo(nombre);
        this.setTableroOponente(new TableroOponente(new Carta[4], new Carta[4]));
        this.setTurno(true);
        this.getTablero().inicioTurno();
        this.dificultad = 1;
    }

    /**
     * Sirve para la creación de los niveles 2 y 3.
     *
     * @param dificultad nivel
     * @param vida vida con la que empieza el jugador (la misma con la que
     * terminó el nivel anterior)
     * @param mazo mazo del jugador (elegido previamente en EleccionMazo)
     */
    public PartidaReto(int dificultad, int vida, int mazo) {
        super(true, mazo);
        Juego.ventana.actualizarTitulo(nombre);
        this.setTableroOponente(new TableroOponente(new Carta[4], new Carta[4]));
        this.setTurno(true);
        this.getTablero().inicioTurno();
        this.dificultad = dificultad;
        getTablero().setVida(vida);
    }

    @Override
    public void finPartida() {
        if (getTableroOponente().getVida() <= 0) {
            if (dificultad == 3) {
                Juego.escena = new FinPartidaReto(Inicio.getNombreUsuario(), calcularPuntuacion(), this.dificultad);    //si se termina el juego se calcula la puntuación
            } else {
                Juego.escena = new PartidaReto(dificultad + 1, getTablero().getVida(), this.getMazo()); //si se pasa un nivel, se avanza al siguiente
            }
        }
        if (getTablero().getVida() <= 0) {
            Juego.escena = new FinPartidaReto(Inicio.getNombreUsuario(), calcularPuntuacion(), this.dificultad);    //si el jugador muere se calcula la puntuación
        }
    }

    @Override
    public boolean recibirTablero() {
        return true;
    }

    /**
     * @return siempre true
     */
    @Override
    public boolean enviarTablero() {
        //ejecutamos los eventos de nuestro turno y pasamos el turno, el actualizar ejecuta el del oponente
        this.setTurno(false);
        //this.ataqueTablero();
        this.atacar(this.getTablero(), this.getTableroOponente());
        this.comprobarVida();
        this.getTablero().cambiarContador();
        this.getTablero().setAtacar();
        eventosTurno();
        return true;
    }

    /**
     * Calcula la puntuación que obtiene el usuario según el nivel de dificultad
     * y las vidas restantes.
     *
     * @return puntuación que se guardará en la base de datos
     */
    private int calcularPuntuacion() {
        return (dificultad * 100) + ((getTablero().getVida() * 5) * dificultad);
    }

    /**
     * Un random controla los eventos que puede realizar la IA durante su turno.
     */
    private void eventosTurno() {
        int cont = 0;   //sirve para la creación de unidades y hechizos

        switch (new Random().nextInt(5)) {  //Random del 0 al 4
            case 0: //NO PASA NADA 
                System.out.println("NO PASA NADA");
                break;
            case 1: //Invoca hasta dos unidades, la primera con stats de la dificultad, la segunda con más defensa y de tipo TORRE
                System.out.println("2 UNIDADES");
                cont = 0;
                for (int i = 0; i < this.getTableroOponente().getUnidades().length; i++) {
                    if (this.getTableroOponente().getUnidades()[i] == null) {
                        if (cont == 0) {
                            this.getTableroOponente().getUnidades()[i] = new Unidad(new Stats(0, this.dificultad, this.dificultad), Enums.tipoUnidad.POR_DEFECTO, Enums.talentoUnidad.POR_DEFECTO, "u_00", "Enemigo", "1000101011");
                            this.getTableroOponente().getUnidades()[i].setX(TABLERO_UNIDADES_IA_X + (i * (CARTA_ANCHO_IA + MARGEN_HORIZONTAL_IA)));
                            this.getTableroOponente().getUnidades()[i].setY(TABLERO_UNIDADES_IA_Y);
                            cont++;
                        } else if (cont == 1) {
                            this.getTableroOponente().getUnidades()[i] = new Unidad(new Stats(0, 0, this.dificultad + 2), Enums.tipoUnidad.TORRE, Enums.talentoUnidad.POR_DEFECTO, "u_08", "Enemigo", "1000101011");
                            this.getTableroOponente().getUnidades()[i].setX(TABLERO_UNIDADES_IA_X + (i * (CARTA_ANCHO_IA + MARGEN_HORIZONTAL_IA)));
                            this.getTableroOponente().getUnidades()[i].setY(TABLERO_UNIDADES_IA_Y);
                            break;
                        }
                    }
                }
                break;
            case 2: //Invoca hasta dos hechizos, el primero de ataque y el segundo de curación (su potencia es equivalente al nivel: 1, 2 ó 3)
                System.out.println("2 HECHIZOS");
                cont = 0;
                for (int i = 0; i < this.getTableroOponente().getHechizos().length; i++) {

                    if (this.getTableroOponente().getHechizos()[i] == null) {
                        if (cont == 0) {
                            this.getTableroOponente().getHechizos()[i] = new Hechizo(Enums.tipoHechizo.ATAQUE, new Stats(0, this.dificultad, 0), "h_00", "Bug", "Hace 1 punto de daño a la unidad objetivo", 1);
                            this.getTableroOponente().getHechizos()[i].setX(TABLERO_HECHIZOS_IA_X + (i * (CARTA_ANCHO_IA + MARGEN_HORIZONTAL_IA)));
                            this.getTableroOponente().getHechizos()[i].setY(TABLERO_HECHIZOS_IA_Y);
                            cont++;
                        } else if (cont == 1) {
                            this.getTableroOponente().getHechizos()[i] = new Hechizo(Enums.tipoHechizo.CURACION, new Stats(0, this.dificultad, 0), "h_02", "Actualizacion", "Cura 1 punto de daño a la unidad objetivo", 1);
                            this.getTableroOponente().getHechizos()[i].setX(TABLERO_HECHIZOS_IA_X + (i * (CARTA_ANCHO_IA + MARGEN_HORIZONTAL_IA)));
                            this.getTableroOponente().getHechizos()[i].setY(TABLERO_HECHIZOS_IA_Y);
                            break;
                        }
                    }
                }

                break;
            case 3: //Sólo se ejecuta en el último nivel, llena la mesa de la IA con unidades de potencia 2 y salud 2
                System.out.println("DIFICULTAD");
                if (this.dificultad >= 3) {
                    for (int i = 0; i < this.getTableroOponente().getUnidades().length; i++) {
                        if (this.getTableroOponente().getUnidades()[i] == null) {
                            this.getTableroOponente().getUnidades()[i] = new Unidad(new Stats(0, this.dificultad - 1, this.dificultad - 1), Enums.tipoUnidad.POR_DEFECTO, Enums.talentoUnidad.POR_DEFECTO, "u_00", "Enemigo", "1000101011");
                            this.getTableroOponente().getUnidades()[i].setX(TABLERO_UNIDADES_IA_X + (i * (CARTA_ANCHO_IA + MARGEN_HORIZONTAL_IA)));
                            this.getTableroOponente().getUnidades()[i].setY(TABLERO_UNIDADES_IA_Y);
                        }
                    }
                }
                break;
            case 4: //Invoca una torre de potencia 0 y salud nivel+2
                System.out.println("TORRE");
                for (int i = 0; i < this.getTableroOponente().getUnidades().length; i++) {
                    if (this.getTableroOponente().getUnidades()[i] == null) {
                        if (cont == 0) {
                            this.getTableroOponente().getUnidades()[i] = new Unidad(new Stats(0, this.dificultad , this.dificultad + 2), Enums.tipoUnidad.TORRE, Enums.talentoUnidad.POR_DEFECTO, "u_08", "Enemigo", "1000101011");
                            this.getTableroOponente().getUnidades()[i].setX(TABLERO_UNIDADES_IA_X + (i * (CARTA_ANCHO_IA + MARGEN_HORIZONTAL_IA)));
                            this.getTableroOponente().getUnidades()[i].setY(TABLERO_UNIDADES_IA_Y);
                            return;
                        }
                    }
                }
                break;
        }
    }
}
