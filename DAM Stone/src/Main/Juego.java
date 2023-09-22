package Main;

import Padres.Escena;
import Escenas.*;
import Controladores.*;
import Misc.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.File;
import DB.DataBase;
import Repositorios.*;
import java.io.IOException;

/**
 * Main del juego. Herede de la clase del sistema Canvas, que es un lienzo en
 * blanco que sirve para ejecutar los métodos dibujar(). Implementa la interfaz
 * del sistema Runnable, que sirve para implementar el método run(). Asimismo,
 * si en un futuro se quisieran implementar varios hilos, Runnable sería
 * necesaria.
 */
public class Juego extends Canvas implements Runnable {

    private static boolean running;

    private Font f = null;              //------------tipografia

    public static Escena escena = null;
    public static boolean mostrarAjustes = false;
    public static int mouse_x = 0;
    public static int mouse_y = 0;
    public static Ventana ventana = null;

    public static void main(String[] args) {
        new Ventana(new Juego());
    }

    public void iniciar() {
        running = true;
        run();
    }

    public static void detener() {
        running = false;
    }

    /**
     * Crea la carpeta del proyecto, establece la tipografía que usará el juego,
     * crea la conexión con la base de datos, las cartas y los mazos en sus
     * correspondientes repositorios, añade los controladores de teclado y ratón
     * y comienza una nueva escena dando la posibilidad al usuario de hacer log
     * in o registrarse.
     */
    public void inicializar() {
        //se crea la carpeta de datos del proyecto
        File dirPrincipal = new File(Macro.LOCAL_PATH);
        if (!dirPrincipal.exists()) {
            dirPrincipal.mkdir();
        }
        File dirLocalPartida = new File(Macro.LOCAL_MULTIJUGADOR);
        if (!dirLocalPartida.exists()) {
            dirLocalPartida.mkdir();
        }

        //se carga la tipografía
        try {
            f = Font.createFont(Font.TRUETYPE_FONT, new File("./rsc/fuente_juego2.ttf"));
            f = f.deriveFont(18f);      //12, 18, 24 (TAMAÑO FUENTE)

        } catch (FontFormatException | IOException ex) {
            System.out.println("Error al cargar Tipografias");
        }

        //se inicia la conexión con la BBDD
        DataBase.inicializar();

        //se crean cartas y mazos
        RepositorioCartas.crearCartas();
        RepositorioMazos.crearMazos();

        //ratón y teclado
        addKeyListener(new ControladorTeclado());
        addMouseListener(new ControladorRaton());

        //nueva escena
        escena = new Inicio();
    }

    /**
     * Borra todos los ficheros que haya creado el jugador
     */
    public static void limpiarLocal() {
        if (Juego.escena.getNombre().equals("Partida") || Juego.escena.getNombre().equals("ResultadosMultijugador")) {
            //creamos un array con todos los ficheros creados por nuestro juego
            File dir = new File(Macro.LOCAL_MULTIJUGADOR);
            File[] arr = dir.listFiles();

            //si el array no está vacío y es > 0 significa que tenemos archivos, así que los borramos todos
            if (arr != null) {
                if (arr.length > 0) {
                    for (int i = 0; i < arr.length; i++) {
                        arr[i].delete();
                    }
                }
            }
        }

    }

    /**
     * Calcula la posición del ratón en la pantalla con ayuda del
     * MouseInfo.getPointerInfo().getLocation().
     */
    public void actualizar() {
        //calculamos la posicion relativa a la interfaz
        if (Juego.ventana != null) {
            mouse_x = Math.max(0, Math.min(((int) MouseInfo.getPointerInfo().getLocation().getX()) - ventana.getX() - 8, Macro.PANTALLA_ANCHO));
            mouse_y = Math.max(0, Math.min(((int) MouseInfo.getPointerInfo().getLocation().getY()) - ventana.getY() - 32, Macro.PANTALLA_ALTO));
        }
        escena.actualizar();
    }

    /**
     * Dibuja la pantalla gestionando los elementos gráficos mediante el objeto
     * del sistema BufferStrategy.
     */
    public void dibujar() {
        BufferStrategy bs = this.getBufferStrategy(); //gestion de pixeles propia de Java

        if (bs == null) {
            createBufferStrategy(3);  //IMPORTANTE, Mirar por qué ponemos un 3
            return;
        }
        Graphics g = bs.getDrawGraphics();

        g.setFont(f);           //------------tipografia

        try {
            escena.dibujar(g);
        } catch (Exception ex) {
            //a veces salta un error de concurrency, con esto evitamos ese error y perdemos un frame
            System.out.println("Frame Perdido");
        }

        //mostramos los ajustes
        if (mostrarAjustes) {
            new Texto(mouse_x + ", " + mouse_y, 0, 1, Color.WHITE, Enums.alinearVert.TOP, Enums.alinearHorz.LEFT, true).dibujar(g);
        }
        g.dispose();
        bs.show();
    }

    public void actualizarSegundo() {
        escena.actualizarSegundo();
    }

    /**
     * El bucle principal del juego, limitado a 60 FPS.
     */
    @Override
    public void run() {
        inicializar();
        this.requestFocus();

        long tiempoTranscurrido = System.nanoTime();
        double apsEstimado = 60.0;
        double ns = 1000000000 / apsEstimado; //cuantos nanosegundos vamos a necesitar (fraciones de 60)
        double delta = 0; //tiempo que utilizamos para calcular si ha pasado minimo un frame 
        long timer = System.currentTimeMillis();

        int aps = 0;
        int fps = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - tiempoTranscurrido) / ns; //comprueba si ha pasado el tiempo necesario
            tiempoTranscurrido = now;
            while (delta > 0) { //si delta > 0 actualizamos toda la pantalla, fps, dibujar...
                actualizar();
                aps++;

                dibujar();
                fps++;

                delta--;
            }
            if (System.currentTimeMillis() - timer > 1000) { //mostrar los FPS Y APS cada segundo y los ponerlos a 0
                timer += 1000;
                System.out.println("FPS: " + fps + " || APS: " + aps);
                actualizarSegundo();

                fps = 0;
                aps = 0;
            }
        }
    }
}
