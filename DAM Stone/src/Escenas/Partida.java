package Escenas;

import Cartas.*;
import Padres.*;
import Controladores.*;
import Main.Juego;
import Misc.*;
import Tableros.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 * Controla la partida multijugador local.
 */
public class Partida extends Escena {

    public final String nombre = "Partida";

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

    private boolean espectador = false; //en el multijugador local no se permiten espectadores, te manda al menú
    private Tablero tablero = null;
    private Tablero tableroOponente = new TableroOponente(new Carta[4], new Carta[4]);
    public int cartaSeleccionada = -1;
    private String jugador = "P1";
    private boolean turno = false;
    private int mazo;
    //private boolean rendirse = false;
    private boolean x = false;
    private boolean t = false;

    private int segundosParaActualizar = 0; //-------------hacemos un delay para actualizar el tablero despues de mandarse-------------

    private final Boton botonPasarTurno = new Boton(720, 380, "", "boton_partida_");
    private final Boton botonSalir = new Boton(6, 142, "", "boton_salir_");

    //imagenes//
    private BufferedImage marco_mana = null;
    //private BufferedImage marco_unidades = null;
    //private BufferedImage marco_hechizos = null;
    private BufferedImage turno_activo = null;
    private BufferedImage turno_espera = null;
    private BufferedImage fondo_tablero = null;
    private BufferedImage extension_abierta = null;
    private BufferedImage extension_cerrada = null;

    //TextoTemporal//
    private final ArrayList<TextoTemporal> textoPotencia = new ArrayList<>();

    @Override
    public String getNombre() {
        return nombre;
    }

    /**
     * @param partidaReto sea true o false, indica que la partida es contra la
     * IA
     * @param mazo mazo previamente elegido (véase Escenas.EleccionMazo.java)
     */
    public Partida(boolean partidaReto, int mazo) {
        this.tablero = new TableroJugador(mazo);
        this.mazo = mazo;
        cargarMarcos();
        //textoPotencia.add(new TextoTemporal("-1",400,400));
    }

    /**
     * Se utiliza para la creación de partida multijador local y la preparación
     * del mazo y mano.
     *
     * @param mazo mazo previamente elegido (véase Escenas.EleccionMazo.java)
     */
    public Partida(int mazo) {
        Juego.ventana.actualizarTitulo(nombre);
        cargarMarcos();
        iniciarPartidaMultijugador();
        this.tablero = new TableroJugador(mazo);
        tableroOponente.setMazo(tableroOponente.crearMazo(0));
        tableroOponente.setMano(tableroOponente.crearMano());
        if (turno) {
            tablero.inicioTurno();
        }
    }

    /**
     * Inicia la partida de multijugador local. Sólo pueden jugar P1 vs P2. Si
     * mientras juegan los dos primeros un tercero intenta entrar, el juego le
     * devolverá al menú.
     */
    private void iniciarPartidaMultijugador() {
        File partidaIniciada = new File(Macro.LOCAL_MULTIJUGADOR + "\\partidaIniciada.eth");

        if (partidaIniciada.exists()) { //esto se ejecuta cuando entra el tercer, cuarto, quinto... jugador
            espectador = true;
            System.out.println("Limite de jugadores");
        } else {
            File iniciarPartida = new File(Macro.LOCAL_MULTIJUGADOR + "\\iniciar.eth");
            if (iniciarPartida.exists()) {  //esto se ejecuta cuando entra el segundo jugador
                iniciarPartida.delete();
                jugador = "P2";
                botonPasarTurno.setActivo(false);
                try {
                    partidaIniciada.createNewFile();
                } catch (IOException ex) {
                    System.out.println("Error Crear Fichero Iniciada");
                }
            } else {    //esto se ejecuta cuando entra el primer jugador
                turno = true;
                try {
                    iniciarPartida.createNewFile();
                } catch (IOException ex) {
                    System.out.println("Error Crear Fichero Iniciar");
                }
            }
        }
    }

    /**
     * Carga los marcos del mana y de las unidades y hechizos, así como los
     * botones de turno (activo y espera) y el fondo del tablero.
     */
    private void cargarMarcos() {
        try {
            marco_mana = ImageIO.read(new File(".\\rsc\\sprites\\partida\\marco_m.png"));   //este marco, en un principio, ya  no lo usamos (quitamos el mana a última hora)
            //marco_unidades = ImageIO.read(new File(".\\rsc\\sprites\\partida\\marco_e.png"));
            //marco_hechizos = ImageIO.read(new File(".\\rsc\\sprites\\partida\\marco_h.png"));
            turno_activo = ImageIO.read(new File(".\\rsc\\sprites\\interfaz\\turno_activo.png"));
            turno_espera = ImageIO.read(new File(".\\rsc\\sprites\\interfaz\\turno_espera.png"));
            fondo_tablero = ImageIO.read(new File(".\\rsc\\sprites\\fondos\\tablero.png"));
            extension_abierta = ImageIO.read(new File(".\\rsc\\sprites\\partida\\extension_abierta.png"));
            extension_cerrada = ImageIO.read(new File(".\\rsc\\sprites\\partida\\extension_cerrada.png"));
        } catch (IOException ex) {
            System.out.println("Error: Cargar imagen marcos");
        }
    }

    /**
     * 60 veces por segundo, ejecuta los métodos de actualizar del botón y del
     * tablero. Ejecuta el actualizar del efecto de texto, el cual lee todo el
     * arrayList y si el texto no se mueve, lo elimina. Comprueba si hay algun
     * espectador y lo echa de la partida.
     */
    @Override
    public void actualizar() {
        //si eres espectador, te manda al menu
        if (espectador) {
            Juego.escena = new Menu();
            return;
        }
        botonPasarTurno.actualizar();
        botonSalir.actualizar();
        tablero.actualizar();
        for (int i = 0; i < textoPotencia.size(); i++) {
            if (textoPotencia.get(i).efecto()) {
                textoPotencia.remove(i);
            }
        }
    }

    /**
     * Se dibuja la partida en su totalidad: fondo, mazos, información de los
     * jugadores, mano del jugador, unidades, etc.
     *
     * @param g gráficos. Necesarios para dibujar
     */
    @Override
    public void dibujar(Graphics g) {

        //dibujamos el fondo
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, Macro.PANTALLA_ANCHO, Macro.PANTALLA_ALTO);
        //new Texto(jugador, Macro.PANTALLA_ANCHO - 8, 2, Color.WHITE, Enums.alinearVert.TOP, Enums.alinearHorz.RIGHT, true).dibujar(g);  //P1 o P2
        g.drawImage(fondo_tablero, 0, 0, null);

        //se dibuja el botón de pasar turno
        botonPasarTurno.dibujar(g);
        if (botonPasarTurno.getActivo()) {
            g.drawImage(turno_activo, 720, 332, null);
        } else {
            g.drawImage(turno_espera, 720, 332, null);
        }

        botonSalir.dibujar(g);

        //se dibuja la información del jugador
        tablero.dibujar(g);

        //se dibuja la información del oponente
        tableroOponente.dibujar(g);

        /*para dibujar las coordenadas en la parte superior izquierda de la pantalla (se activan y desactivan con la tecla 0)
        también muestra el slot seleccionado (útil para nosotros a la hora de programar, para ver que todo va bien)*/
        if (Juego.mostrarAjustes) {
            new Texto("slot seleccionado: " + cartaSeleccionada, Macro.PANTALLA_ANCHO - 16, Macro.PANTALLA_ALTO / 2, Color.WHITE, Enums.alinearVert.CENTER, Enums.alinearHorz.RIGHT, true).dibujar(g);
        }

        //----------------Remplazar drawRect por imagen
        if (cartaSeleccionada != -1) {
            //g.drawRect(Macro.PANTALLA_ANCHO - 114, Macro.PANTALLA_ALTO / 2 - 2, 115, 100);
            g.drawImage(extension_abierta,Macro.PANTALLA_ANCHO - 176, Macro.PANTALLA_ALTO / 2 - 2, null);
            tablero.getMano().get(cartaSeleccionada).descripcion(g);
            g.drawImage(marco_mana, MANO_X + cartaSeleccionada * (CARTA_ANCHO + MARGEN_HORIZONTAL), MANO_Y, null);
            /*
            switch (tablero.getMano().get(cartaSeleccionada).getTipoCarta()) {
                case MANA:
                    g.drawImage(marco_mana, MANO_X + cartaSeleccionada * (CARTA_ANCHO + MARGEN_HORIZONTAL), MANO_Y, null);
                    break;
                case HECHIZO:
                    g.drawImage(marco_hechizos, MANO_X + cartaSeleccionada * (CARTA_ANCHO + MARGEN_HORIZONTAL), MANO_Y, null);
                    break;
                case UNIDAD:
                    g.drawImage(marco_unidades, MANO_X + cartaSeleccionada * (CARTA_ANCHO + MARGEN_HORIZONTAL), MANO_Y, null);
                    break;
            }*/
        } else {
            //g.drawRect(Macro.PANTALLA_ANCHO - 16, Macro.PANTALLA_ALTO / 2 - 2, 17, 100);
            g.drawImage(extension_cerrada,Macro.PANTALLA_ANCHO - 34, Macro.PANTALLA_ALTO / 2 - 2, null);
        }

        //se dibuja el texto temporal (daño, curación, etc.)
        for (TextoTemporal textoTemporal : textoPotencia) {
            textoTemporal.dibujar(g);
        }
    }

    /**
     * Una vez por segundo, se actualiza la partida. Controla los turnos de los
     * jugadores, sus ataques, los contadores de sus unidades y hechizos, el
     * botón de pasar turno, etc.
     */
    @Override
    public void actualizarSegundo() {
        if (segundosParaActualizar > 0) {
            segundosParaActualizar--;
        } else {
            if (recibirTablero() && !botonPasarTurno.getActivo()) {
                segundosParaActualizar = 3;
            }
        }
        if (segundosParaActualizar == 1) {
            if (turno) {    //TURNO DEL JUGADOR
                //ataqueTablero();
                atacar(tablero, tableroOponente);   //el jugador ataca al oponente
                comprobarVida();
                tablero.cambiarContador();  //control de contadores para el tablero del jugador
                tableroOponente.cambiarContador();  //control de contadores  para el tablero del oponente
                tablero.setAtacar();    //hace que las unidades de invocadas en este turno puedan atacar (atacarán en el siguiente turno)
                turno = false;  //TERMINA EL TURNO DEL JUGADOR
            } else {    //TURNO DEL OPONENTE
                //atacarOponente();
                atacar(tableroOponente, tablero);   //el oponente ataca al jugador
                tableroOponente.setAtacar();    //hace que las unidades de invocadas en este turno puedan atacar (atacarán en el siguiente turno)
                comprobarVida();
                tableroOponente.cambiarContador();  //control de contadores para el tablero del oponente
                tablero.cambiarContador();  //control de contadores para el tablero del jugador
                tablero.inicioTurno();  //TERMINA EL TURNO DEL OPONENTE
                turno = true;
                botonPasarTurno.setActivo(true);
            }
            finPartida();   //AL FINAL DE CADA TURNO SE COMPRUEBA SI LA PARTIDA HA TEMRINADO
        }

    }

    /**
     * Controla el fin de partida, creando una nueva escena
     * FinPartidaMultijugador.
     */
    public void finPartida() {
        if (this.tablero.isRendido()) {
            Juego.escena = new FinPartidaMultijugador(3);
        } else {
            if (this.getTableroOponente().getVida() <= 0 && this.getTablero().getVida() <= 0) {
                Juego.escena = new FinPartidaMultijugador(4);
            } else {
                if (this.getTableroOponente().getVida() <= 0) {
                    Juego.escena = new FinPartidaMultijugador(1);
                }
                if (this.getTablero().getVida() <= 0) {
                    Juego.escena = new FinPartidaMultijugador(2);
                }
            }
            
        }
    }

    /**
     *
     */
    @Override
    public void clickIzq() {
        if (botonPasarTurno.getActivo() && botonSalir.getCursorEncima() && botonSalir.getActivo()) {
            botonPasarTurno.setActivo(false);
            this.tablero.setVida(0);
            this.tablero.setRendido(true);
            enviarTablero();
            finPartida();
        }

        if (botonPasarTurno.getCursorEncima() && botonPasarTurno.getActivo()) {
            if (enviarTablero()) {
                cartaSeleccionada = -1;
                botonPasarTurno.setActivo(false);
                segundosParaActualizar = 3;
            }
        }

        int aux_carta = getManoIndex();
        if (aux_carta != -1) {
            if (aux_carta != cartaSeleccionada) {
                cartaSeleccionada = aux_carta;
            } else {
                cartaSeleccionada = -1;
            }
        } else {
            if (turno && cartaSeleccionada != -1) {

                int coste = tablero.getMano().get(cartaSeleccionada).getStats().getCoste();
                //estas en un slot y tienes una carta seleccionada
                if (tablero.getMana() >= coste) {
                    int slotUnidad = getUnidadIndex();
                    if (slotUnidad != -1) {
                        if (tablero.jugarUnidad(cartaSeleccionada, slotUnidad)) {
                            //System.out.println("Se ha colocado con exito");
                            tablero.setMana(tablero.getMana() - coste);
                            cartaSeleccionada = -1;
                        } else {
                            //System.out.println("Espacio lleno");    //REVISAR ESTO!!!!!!!!!!!!!!!!!!!!!!!!!!
                        }
                    }

                    /////////////////////////////////////
                    int slotHechizo = getHechizoIndex();

                    if (slotHechizo != -1) {
                        if (tablero.jugarHechizo(cartaSeleccionada, slotHechizo)) {
                            //System.out.println("Se ha colocado con exito");
                            tablero.setMana(tablero.getMana() - coste);
                            cartaSeleccionada = -1;
                        } else {
                            //System.out.println("Espacio lleno");
                        }
                    }
                } else {
                    System.out.println("Mana Insuficiente");
                }
            }
        }
    }

    /**
     * En un principio esto ya NO SE USA para el juego, no lo quitamos por si
     * fuera necesario usar el método para alguna prueba o cualquier
     * actualización.
     */
    @Override
    public void clickDch() {
        if (cartaSeleccionada != -1) {
            cartaSeleccionada = -1;
        }
    }

    /**
     * Envía el tablero al oponente para que este pueda dibujarlo durante su
     * turno. Este envío no es literal, sino que el método guarda un fichero
     * serializado con el tablero, que se lee con recibirTablero().
     *
     * @return true sólo si el tablero se ha enviado correctamente, false de lo
     * contrario
     */
    public boolean enviarTablero() {
        boolean enviado = true;
        File tableroPropio = new File(Macro.LOCAL_MULTIJUGADOR + "\\tablero_" + jugador + ".dat");
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(tableroPropio);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(tablero);
        } catch (FileNotFoundException ex) {
            enviado = false;
            System.out.println("Error enviar Tablero No Encontrado");
        } catch (IOException ex) {
            enviado = false;
            System.out.println("Error Enviar Fichero Generico");
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (oos != null) {
                    oos.close();
                }
            } catch (IOException ex) {
                enviado = false;
                System.out.println("Error Cerrar Ficheros Enviar Tablero");
            }
        }
        return enviado;
    }

    /**
     * Sirve para cargar el tablero del oponente. Esta carga no es literal, sino
     * que el método trata de leer un fichero serializado que se ha mandado
     * previamente con ayuda del método enviarTablero().
     *
     * @return true sólo si la lectura del tablero ha sido existosa, false de lo
     * contrario
     */
    public boolean recibirTablero() {
        boolean recibido = false;
        File dataTableroOponente;

        if (jugador.equals("P2")) {
            dataTableroOponente = new File(Macro.LOCAL_MULTIJUGADOR + "\\tablero_P1.dat");
        } else {
            dataTableroOponente = new File(Macro.LOCAL_MULTIJUGADOR + "\\tablero_P2.dat");
        }

        if (dataTableroOponente.exists()) {
            FileInputStream fis = null;
            ObjectInputStream ois = null;

            try {
                fis = new FileInputStream(dataTableroOponente);
                ois = new ObjectInputStream(fis);

                //guardamos el objeto como tablerojugador y vamos cargando SOLO LOS DATOS NECESARIOS
                TableroJugador auxTablero = (TableroJugador) ois.readObject();
                tableroOponente = new TableroOponente(auxTablero.getUnidades(), auxTablero.getHechizos());  //creamos el tablero enemigo
                tableroOponente.setVida(auxTablero.getVida());
                tableroOponente.setVida_max(auxTablero.getVida_max());
                tableroOponente.setMana_max(auxTablero.getMana_max());
                tableroOponente.setMano(auxTablero.getMano());
                tableroOponente.setMazo(auxTablero.getMazo());
                recibido = true;    //SI HEMOS LLEGADO AQUÍ ES QUE TODO SE HA LEÍDO CORRECTAMENTE
            } catch (ClassNotFoundException ex) {
                recibido = false;
            } catch (FileNotFoundException ex) {
                recibido = false;
                System.out.println("Error, no se ha encontrado del Tablero Oponente");
            } catch (IOException ex) {
                recibido = false;
                System.out.println("Error Generico Recibir Fichero");
            } finally {
                try {
                    if (fis != null) {
                        fis.close();
                    }
                    if (ois != null) {
                        ois.close();
                    }
                } catch (IOException ex) {
                    recibido = false;
                    System.out.println("Error Cerrar Ficheros");
                }
                dataTableroOponente.delete();
            }
        }
        return recibido;
    }

    /**
     * Es una función de gran tamaño porque controla tanto el ataqueOponente()
     * como ataqueTablero(). Estas dos últimas se encuentran comentadas en este
     * .java.
     *
     * @param t_atacante la iniformación del tablero que ataca (unidades y
     * hechizos)
     * @param t_defensa la información del tablero que defiende (unidades y
     * hechizos)
     */
    public void atacar(Tablero t_atacante, Tablero t_defensa) {
        Random rn = new Random();
        for (int i = 0; i < t_atacante.getHechizos().length; i++) { //PRIMERO LOS HECHIZOS
            if (t_atacante.getHechizos()[i] != null) {
                Hechizo aux1 = (Hechizo) t_atacante.getHechizos()[i];
                if (aux1.getTipoHechizo() == Enums.tipoHechizo.ATAQUE) {    //HECHIZOS DE ATAQUE
                    int dmgh = t_atacante.getHechizos()[i].getStats().getPotencia();
                    if (t_defensa.getUnidades()[i] != null) {
                        t_defensa.getUnidades()[i] = combate(dmgh, t_defensa.getUnidades()[i]);
                        textoPotencia.add(new TextoTemporal("-" + dmgh, t_defensa.getUnidades()[i].getX() + 52 + rn.nextInt(-52, 16), t_defensa.getUnidades()[i].getY() + 60 + rn.nextInt(-32, 24))); //FEEDBACK AL USUARIO (han golpeado a una unidad)
                    } else {
                        t_defensa.setVida(t_defensa.getVida() - dmgh);
                        if (t_atacante instanceof TableroOponente) {
                            textoPotencia.add(new TextoTemporal("-" + dmgh, 116, 661 + rn.nextInt(-48, 48))); //FEEDBACK AL USUARIO (han golpeado al jugador)
                        } else {
                            textoPotencia.add(new TextoTemporal("-" + dmgh, 286 + rn.nextInt(-48, 48), 112));  //FEEDBACK AL USUARIO (han golpeado al oponente)
                        }
                    }
                }
                if (aux1.getTipoHechizo() == Enums.tipoHechizo.CURACION) {  //HECHIZOS DE CURACIÓN
                    int heal = aux1.getStats().getPotencia();
                    if (t_atacante.getUnidades()[i] != null) {
                        t_atacante.getUnidades()[i] = curacion(heal, t_atacante.getUnidades()[i]);
                        textoPotencia.add(new TextoTemporal("+" + heal, t_atacante.getUnidades()[i].getX() + 52 + rn.nextInt(-52, 16), t_atacante.getUnidades()[i].getY() + 60 + rn.nextInt(-32, 24)));   //FEEDBACK AL USUARIO (se ha curado a una unidad)
                    } else {
                        t_atacante.setVida(Math.min(t_atacante.getVida() + heal, t_atacante.getVida_max()));
                        if (t_atacante instanceof TableroOponente) {
                            textoPotencia.add(new TextoTemporal("+" + heal, 286 + rn.nextInt(-48, 48), 98));  //FEEDBACK AL USUARIO (se ha curado el oponente)
                        } else {
                            textoPotencia.add(new TextoTemporal("+" + heal, 96, 661 + rn.nextInt(-48, 48))); //FEEDBACK AL USUARIO (se ha curado el Usuario)
                        }
                    }
                }
                if (aux1.getTipoHechizo() == Enums.tipoHechizo.ELIMINAR) {  //HECHIZOS DE ELIMINACIÓN
                    if (aux1.getNombre().equals("Pausa para Piti")) {   //carta especial de pedro, destruye toda la mesa
                        for (int j = 0; j < 4; j++) {
                            if (t_atacante.getHechizos()[j] != null && j != i) {
                                textoPotencia.add(new TextoTemporal("-999", t_atacante.getHechizos()[j].getX() + 52 + rn.nextInt(-52, 16), t_atacante.getHechizos()[j].getY() + 60 + rn.nextInt(-32, 24)));
                                t_atacante.getHechizos()[j] = null;
                            }
                            if (t_atacante.getUnidades()[j] != null) {
                                textoPotencia.add(new TextoTemporal("-999", t_atacante.getUnidades()[j].getX() + 52 + rn.nextInt(-52, 16), t_atacante.getUnidades()[j].getY() + 60 + rn.nextInt(-32, 24)));
                                t_atacante.getUnidades()[j] = null;
                            }
                            if (t_defensa.getHechizos()[j] != null) {
                                textoPotencia.add(new TextoTemporal("-999", t_defensa.getHechizos()[j].getX() + 52 + rn.nextInt(-52, 16), t_defensa.getHechizos()[j].getY() + 60 + rn.nextInt(-32, 24)));
                                t_defensa.getHechizos()[j] = null;
                            }
                            if (t_defensa.getUnidades()[j] != null) {
                                textoPotencia.add(new TextoTemporal("-999", t_defensa.getUnidades()[j].getX() + 52 + rn.nextInt(-52, 16), t_defensa.getUnidades()[j].getY() + 60 + rn.nextInt(-32, 24)));
                                t_defensa.getUnidades()[j] = null;
                            }
                        }
                        textoPotencia.add(new TextoTemporal("-999", t_atacante.getHechizos()[i].getX() + 52 + rn.nextInt(-52, 16), t_atacante.getHechizos()[i].getY() + 60 + rn.nextInt(-32, 24)));
                        t_atacante.getHechizos()[i] = null;
                    } else {
                        if (t_defensa.getHechizos()[i] != null) {
                            textoPotencia.add(new TextoTemporal("-999", t_defensa.getHechizos()[i].getX() + 52 + rn.nextInt(-52, 16), t_defensa.getHechizos()[i].getY() + 60 + rn.nextInt(-32, 24)));
                            t_defensa.getHechizos()[i] = null;
                        }
                    }
                }
                if (aux1.getTipoHechizo() == Enums.tipoHechizo.MAS_STATS && aux1.getCont() == 5) {  //HECHIZOS QUE AUMENTAN POTENCIA Y SALUD
                    if (t_atacante.getUnidades()[i] != null) {
                        t_atacante.getUnidades()[i].getStats().setPotencia(t_atacante.getUnidades()[i].getStats().getPotencia() + aux1.getStats().getPotencia());   //aumento de potencia
                        t_atacante.getUnidades()[i].getStats().setSalud(t_atacante.getUnidades()[i].getStats().getSalud() + aux1.getStats().getPotencia()); //aumento de salud
                        textoPotencia.add(new TextoTemporal("*" + aux1.getStats().getPotencia(), t_atacante.getUnidades()[i].getX() + 52 + rn.nextInt(-52, 16), t_atacante.getUnidades()[i].getY() + 60 + rn.nextInt(-32, 24))); //FEEDBACK AL USUARIO
                    }
                }
            }
        }

        for (int i = 0; i < t_atacante.getUnidades().length; i++) { //AHORA LAS UNIDADES
            if (t_atacante.getUnidades()[i] != null) {
                Unidad aux = (Unidad) t_atacante.getUnidades()[i];
                if (aux.isPuedeAtacar() || aux.getTipo().equals(Enums.tipoUnidad.VELOZ)) {  //se comprueba si la unidad puede atacar o si es veloz ya que las veloces siempre pueden atacar
                    int dmgu = t_atacante.getUnidades()[i].getStats().getPotencia();
                    if (t_defensa.getUnidades()[i] != null) {
                        t_defensa.getUnidades()[i] = combate(dmgu, t_defensa.getUnidades()[i]);
                        t_atacante.getUnidades()[i] = combate(t_defensa.getUnidades()[i].getStats().getPotencia(), t_atacante.getUnidades()[i]);
                        textoPotencia.add(new TextoTemporal("-" + t_defensa.getUnidades()[i].getStats().getPotencia(), t_atacante.getUnidades()[i].getX() + 52 + rn.nextInt(-52, 16), t_atacante.getUnidades()[i].getY() + 60 + rn.nextInt(-32, 24)));
                        textoPotencia.add(new TextoTemporal("-" + dmgu, t_defensa.getUnidades()[i].getX() + 52 + rn.nextInt(-52, 16), t_defensa.getUnidades()[i].getY() + 60 + rn.nextInt(-32, 24))); //FEEDBACK AL USUARIO (tu unidad ha recibido daño)
                    } else {
                        t_defensa.setVida(t_defensa.getVida() - dmgu);
                        if (t_atacante instanceof TableroOponente) {
                            textoPotencia.add(new TextoTemporal("-" + dmgu, 96, 661 + rn.nextInt(-48, 48))); //FEEDBACK AL USUARIO (el jugador ha recibido daño)
                        } else {
                            textoPotencia.add(new TextoTemporal("-" + dmgu, 286 + rn.nextInt(-48, 48), 98));  //FEEDBACK AL USUARIO (el oponente ha recibido daño)
                        }
                    }
                }
            }
        }
    }

    /**
     * ataqueTablero gestiona los ataques de tu tablero contra el tablero
     * Oponente Primero busca tus hechizos y ataca a la unidad que tenga en
     * frente (si existe). Si no, ataca al jugador. Posteriormente hace lo mismo
     * para tus unidades, sufriendo daño tanto tu unidad como la Oponente
     *
     *//*
    protected void ataqueTablero() {

        for (int i = 0; i < tablero.getHechizos().length; i++) {
            if (tablero.getHechizos()[i] != null) {
                Hechizo aux1 = (Hechizo) tablero.getHechizos()[i];
                if (aux1.getTipoHechizo() == Enums.tipoHechizo.ATAQUE) {
                    int dmgh = tablero.getHechizos()[i].getStats().getPotencia();
                    if (tableroOponente.getUnidades()[i] != null) {
                        tableroOponente.getUnidades()[i] = combate(dmgh, tableroOponente.getUnidades()[i]);
                    } else {
                        tableroOponente.setVida(tableroOponente.getVida() - dmgh);
                    }
                }
                if (aux1.getTipoHechizo() == Enums.tipoHechizo.CURACION) {
                    int heal = aux1.getStats().getPotencia();
                    if (tablero.getUnidades()[i] != null) {
                        tablero.getUnidades()[i] = curacion(heal, tablero.getUnidades()[i]);
                    } else {
                        tablero.setVida(Math.min(tablero.getVida() + heal, tablero.getVida_max()));
                    }
                }
                if (aux1.getTipoHechizo() == Enums.tipoHechizo.ELIMINAR) {
                    if (aux1.getNombre().equals("Pausa para Piti")) {
                        for (int j = 0; j < 4; j++) {
                                tablero.getHechizos()[j] = null;
                                tablero.getUnidades()[j] = null;
                                tableroOponente.getHechizos()[j] = null;
                                tableroOponente.getUnidades()[j] = null;
                        }
                    } else {
                            tableroOponente.getHechizos()[i] = null;
                    }
                }
                 if (aux1.getTipoHechizo() == Enums.tipoHechizo.MAS_STATS && aux1.getCont() == 5) {
                     if (tablero.getUnidades()[i]!= null) {
                         tablero.getUnidades()[i].getStats().setPotencia(tablero.getUnidades()[i].getStats().getPotencia()+ aux1.getStats().getPotencia());
                         tablero.getUnidades()[i].getStats().setSalud(tablero.getUnidades()[i].getStats().getSalud()+ aux1.getStats().getPotencia());
                         
                     }
                     
                 }
            }
        }

        for (int i = 0; i < tablero.getUnidades().length; i++) {
            if (tablero.getUnidades()[i] != null) {
                Unidad aux = (Unidad) tablero.getUnidades()[i];
                if (aux.isPuedeAtacar() || aux.getTipo().equals(Enums.tipoUnidad.VELOZ)) {
                    int dmgu = tablero.getUnidades()[i].getStats().getPotencia();
                    if (tableroOponente.getUnidades()[i] != null) {
                        tableroOponente.getUnidades()[i] = combate(dmgu, tableroOponente.getUnidades()[i]);
                        tablero.getUnidades()[i] = combate(tableroOponente.getUnidades()[i].getStats().getPotencia(), tablero.getUnidades()[i]);

                    } else {
                        tableroOponente.setVida(tableroOponente.getVida() - dmgu);
                    }
                }

            }
        }
    }*/

    /**
     * atacarOponente realiza la misma funcion que el metodo ataqueTablero pero
     * una vez enviado el fichero tablero para que el ataque se realice tambien
     * en el tablero del oponente
     *
     *//*
    protected void atacarOponente() {

        for (int i = 0; i < tableroOponente.getHechizos().length; i++) {
            if (tableroOponente.getHechizos()[i] != null) {//Buscamos si tiene algun hechizo en juego
                Hechizo aux1 = (Hechizo) tableroOponente.getHechizos()[i];
                if (aux1.getTipoHechizo().equals(Enums.tipoHechizo.ATAQUE)) {
                    int dmgh = tableroOponente.getHechizos()[i].getStats().getPotencia(); //guardamos la Potencia de ese hechizo (por no escribir toda la linea de nuevo)

                    if (tablero.getUnidades()[i] != null) {
                        tablero.getUnidades()[i] = combate(dmgh, tablero.getUnidades()[i]);//Buscamos unidad en la posicion correspondiente y le ejecutamos el daño con la funcion Combate
                    } else {
                        tablero.setVida(tablero.getVida() - dmgh); //si no hay unidades hacemos el daño al jugador rival

                    }
                }
                if (aux1.getTipoHechizo().equals(Enums.tipoHechizo.CURACION)) {
                    int heal = aux1.getStats().getPotencia();
                    if (tableroOponente.getUnidades()[i] != null) {
                        tableroOponente.getUnidades()[i] = curacion(heal, tableroOponente.getUnidades()[i]);
                    } else {
                        tableroOponente.setVida(Math.min(tableroOponente.getVida() + heal, tableroOponente.getVida_max()));

                    }
                }
                if (aux1.getTipoHechizo() == Enums.tipoHechizo.ELIMINAR) {
                    if (aux1.getNombre().equals("Pausa para Piti")) {
                        for (int j = 0; j < 4; j++) {
                                tablero.getHechizos()[j] = null;
                                tablero.getUnidades()[j] = null;
                                tableroOponente.getHechizos()[j] = null;
                                tableroOponente.getUnidades()[j] = null;
                        }
                    } else {
                            tablero.getHechizos()[i] = null;
                    }
                }
                 if (aux1.getTipoHechizo() == Enums.tipoHechizo.MAS_STATS && aux1.getCont() == 3) {
                     if (tableroOponente.getUnidades()[i]!= null) {
                         tableroOponente.getUnidades()[i].getStats().setPotencia(tableroOponente.getUnidades()[i].getStats().getPotencia()+ aux1.getStats().getPotencia());
                         tableroOponente.getUnidades()[i].getStats().setSalud(tableroOponente.getUnidades()[i].getStats().getSalud()+ aux1.getStats().getPotencia());
                         
                     }
                     
                 }
            }
        }

        for (int i = 0; i < tableroOponente.getUnidades().length; i++) { //Similar a lo anterior pero con unidades
            if (tableroOponente.getUnidades()[i] != null) {
                Unidad aux = (Unidad) tableroOponente.getUnidades()[i];
                if (aux.isPuedeAtacar()) {
                    int dmgu = tableroOponente.getUnidades()[i].getStats().getPotencia();

                    if (tablero.getUnidades()[i] != null) {
                        tablero.getUnidades()[i] = combate(dmgu, tablero.getUnidades()[i]);
                        tableroOponente.getUnidades()[i] = combate(tablero.getUnidades()[i].getStats().getPotencia(), tableroOponente.getUnidades()[i]);

                    } else {
                        tablero.setVida(tablero.getVida() - dmgu);
                    }
                }
            }
        }

    }
     */
    /**
     * Disminuye la salud de una unidad.
     *
     * @param dmg Potencia de ataque
     * @param e Unidad que recibe el ataque
     * @return una Unidad (con la salud modificada tras el ataque)
     */
    protected Carta combate(int dmg, Carta e) {
        Carta aux = e;
        aux.getStats().setSalud(aux.getStats().getSalud() - dmg);
        return aux;
    }

    /**
     * Aumenta la salud de una unidad.
     *
     * @param heal Potencia de curación
     * @param e Unidad que recibe la curación
     * @return una Unidad (con la salud modificada tras la curación)
     */
    protected Carta curacion(int heal, Carta e) {
        Unidad aux = (Unidad) e;
        if (aux.getStats().getSalud() + heal <= aux.getVidaMax()) {
            aux.getStats().setSalud(aux.getStats().getSalud() + heal);
        } else {
            aux.getStats().setSalud(aux.getVidaMax());  //si la curación es mayor a la salud máxima de la unidad, ésta se queda con su salud máxima
        }
        return aux;
    }

    /**
     * Comprueba la vida de todas las unidades, tanto las propias como las del
     * oponente. Si la vida es inferior o igual a 0, las unidades desaparecen.
     */
    protected void comprobarVida() {
        for (int i = 0; i < tablero.getUnidades().length; i++) {
            if (tablero.getUnidades()[i] != null) {
                if (tablero.getUnidades()[i].getStats().getSalud() <= 0) {
                    tablero.getUnidades()[i] = null;
                }
            }
        }
        for (int i = 0; i < tableroOponente.getUnidades().length; i++) {
            if (tableroOponente.getUnidades()[i] != null) {
                if (tableroOponente.getUnidades()[i].getStats().getSalud() <= 0) {
                    tableroOponente.getUnidades()[i] = null;
                }
            }
        }
    }

    /**
     * @return la carta seleccionada, o -1 si no se selecciona ninguna
     */
    protected int getManoIndex() {
        //nos guardamos las coordenadas para usarlas comodamente
        int mx = Juego.mouse_x;
        int my = Juego.mouse_y;

        //calculamos que la selección esté dentro de la zona de la mano
        if (mx > MANO_X && mx < MANO_X + (tablero.getMano().size() * (CARTA_ANCHO + MARGEN_HORIZONTAL))) {  //comprobación horizontal
            if (my > MANO_Y && my < MANO_Y + CARTA_ALTO - MARGEN_VERTICAL) {    //comprobación vertical
                //hacemos la grid, redondeamos por lo bajo y devolvemos el valor
                return (mx - MANO_X) / (CARTA_ANCHO + MARGEN_HORIZONTAL);
            }
        }
        return -1;  //si estamos fuera de la grid, devolvemos -1
    }

    /**
     * @return el slot de unidades seleccionado, o -1 si no se selecciona
     * ninguno
     */
    private int getUnidadIndex() {
        int mx = Juego.mouse_x;
        int my = Juego.mouse_y;

        if (mx > TABLERO_UNIDADES_X && mx < TABLERO_UNIDADES_X + (CARTA_ANCHO + MARGEN_HORIZONTAL) * tablero.getUnidades().length) { //son las coordenadas horizontales del tablero unidades
            if (my > TABLERO_UNIDADES_Y && my < TABLERO_UNIDADES_Y + CARTA_ALTO) {  //son las coordenadas verticales del tablero unidades
                //hacemos la grid, redondeamos por lo bajo y devolvemos el valor
                return (mx - TABLERO_UNIDADES_X) / (CARTA_ANCHO + MARGEN_HORIZONTAL);
            }
        }
        return -1;
    }

    /**
     * @return el slot de hechizos seleccionado, o -1 si no se selecciona
     * ninguno
     */
    private int getHechizoIndex() {
        int mx = Juego.mouse_x;
        int my = Juego.mouse_y;

        if (mx > TABLERO_HECHIZOS_X && mx < TABLERO_HECHIZOS_X + (CARTA_ANCHO + MARGEN_HORIZONTAL) * tablero.getHechizos().length) { //son las coordenadas horizontales del tablero hechizos
            if (my > TABLERO_HECHIZOS_Y && my < TABLERO_HECHIZOS_Y + CARTA_ALTO) {  //son las coordenadas verticales del tablero hechizos
                //hacemos la grid, redondeamos por lo bajo y devolvemos el valor
                return (mx - TABLERO_HECHIZOS_X) / (CARTA_ANCHO + MARGEN_HORIZONTAL);
            }
        }
        return -1;
    }

    public Tablero getTableroOponente() {
        return tableroOponente;
    }

    public void setTableroOponente(Tablero tableroOponente) {
        this.tableroOponente = tableroOponente;
    }

    public boolean isTurno() {
        return turno;
    }

    public void setTurno(boolean turno) {
        this.turno = turno;
    }

    public Tablero getTablero() {
        return tablero;
    }

    public boolean isEspectador() {
        return espectador;
    }

    public void setEspectador(boolean espectador) {
        this.espectador = espectador;
    }

    public int getCartaSeleccionada() {
        return cartaSeleccionada;
    }

    public void setCartaSeleccionada(int cartaSeleccionada) {
        this.cartaSeleccionada = cartaSeleccionada;
    }

    public String getJugador() {
        return jugador;
    }

    public void setJugador(String jugador) {
        this.jugador = jugador;
    }

    public int getSegundosParaActualizar() {
        return segundosParaActualizar;
    }

    public void setSegundosParaActualizar(int segundosParaActualizar) {
        this.segundosParaActualizar = segundosParaActualizar;
    }

    public Boton getBotonPasarTurno() {
        return botonPasarTurno;
    }

    public int getMazo() {
        return mazo;
    }

}
