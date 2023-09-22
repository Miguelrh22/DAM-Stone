package Escenas;

import Padres.Escena;
import Controladores.*;
import Main.Juego;
import Misc.*;
import DB.DataBase;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 * Es la escena que controla el log in y el registro de usuarios.
 */
public class Inicio extends Escena {

    public final String nombre = "Inicio";
    private static String nombreUsuario = "";
    private final ArrayList<CajaDeTexto> cajasDeTexto = new ArrayList<>();

    private final Boton enviarInicio = new Boton(150, 700, "Enviar");
    private final Boton enviarRegistro = new Boton(500, 700, "Registrar", "boton_xl_");
    private BufferedImage fondo = null;

    /**
     * Se añaden, como ArrayList, las 5 cajas de texto necesarias para el log in
     * y el registro.
     */
    public Inicio() {
        Juego.ventana.actualizarTitulo(nombre);
        //login
        cajasDeTexto.add(new CajaDeTexto(50, 350, "Nombre Usuario"));
        cajasDeTexto.add(new CajaDeTexto(50, 450, "Password"));
        //register
        cajasDeTexto.add(new CajaDeTexto(400, 350, "Nombre Usuario"));
        cajasDeTexto.add(new CajaDeTexto(400, 450, "Nickname"));
        cajasDeTexto.add(new CajaDeTexto(400, 550, "Password"));
        
        try{
            fondo = ImageIO.read(new File(".\\rsc\\sprites\\fondos\\fondo_generico.png"));
        } catch(IOException ex){
            System.out.println("Error Carga Imagen Fondo");
        }
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    /**
     * Dibuja la escena: fondo, cajas de texto, botones, etc.
     *
     * @param g gráficos. Necesarios para dibujar
     */
    @Override
    public void dibujar(Graphics g) {
        //se dibuja el fondo en color gris
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, Macro.PANTALLA_ANCHO, Macro.PANTALLA_ALTO);
        g.drawImage(fondo, 0, 0, null);

        //se dibuja el texto
        new Texto("Inicio de Sesión", 100, 200, true).dibujar(g);
        new Texto("Registro", 500, 200, true).dibujar(g);

        //se dibujan las cajas de texto
        for (CajaDeTexto cajaDeTexto : cajasDeTexto) {
            cajaDeTexto.dibujar(g);
        }
        //se dibujan los botones
        enviarInicio.dibujar(g);
        enviarRegistro.dibujar(g);
    }

    /**
     * Sirve para actualizar las cajas de texto. Esta función se realiza 60
     * veces por segundo.
     */
    @Override
    public void actualizar() {
        char tecla = getEntradaTeclado();
        if (tecla != '\000') {
            for (CajaDeTexto cajaDeTexto : cajasDeTexto) {
                if (cajaDeTexto.getActivo()) {
                    cajaDeTexto.addLetra(tecla);
                    break;
                }
            }

        }
        enviarInicio.actualizar();
        enviarRegistro.actualizar();
    }

    @Override
    public void actualizarSegundo() {
    }

    /**
     *
     */
    @Override
    public void clickIzq() {
        //desactivamos todas las cajas de texto
        for (CajaDeTexto cajaDeTexto : cajasDeTexto) {
            cajaDeTexto.desactivar();
        }
        //activamos la presionada
        for (CajaDeTexto cajaDeTexto : cajasDeTexto) {
            if (cajaDeTexto.activar()) {
                return;
            }
        }
        //se hace el log in del usuario
        if (enviarInicio.getCursorEncima()) {
            if (DataBase.loginUsuario(cajasDeTexto.get(0).getTexto(), cajasDeTexto.get(01).getTexto()) == 1) {
                nombreUsuario = cajasDeTexto.get(0).getTexto(); //se guarda el nombre de usuario para guardar luego puntuaciones
                Juego.escena = new Menu();
            } else {
                //TODO: dibujar un mensaje de error en cada caso???????
            }
        }

        if (enviarRegistro.getCursorEncima()) {
            if (DataBase.crearUsuario(cajasDeTexto.get(2).getTexto(), cajasDeTexto.get(4).getTexto(), cajasDeTexto.get(3).getTexto()) == 1) {
                nombreUsuario = cajasDeTexto.get(2).getTexto(); //se guarda el nombre de usuario para guardar luego puntuaciones
                DataBase.cerrarConexion();  //aquí hace falta cerrar conexión por usar el método crearUsuario()
                Juego.escena = new Menu();
            } else {
                DataBase.cerrarConexion();
                //TODO: dibujar un mensaje de error en cada caso???????
            }
        }
    }

    @Override
    public void clickDch() {
        //
    }

    public static String getNombreUsuario() {
        return nombreUsuario;
    }

}
