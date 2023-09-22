package Escenas;

import Controladores.Boton;
import Padres.Escena;
import Controladores.Texto;
import Main.Juego;
import Misc.*;
import java.awt.Color;
import java.awt.Graphics;
import DB.DataBase;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 * Escena que muestra las puntuaciones (3 puntuaciones del usuario actualmente
 * logueado y 5 máximas puntuaciones).
 */
public class MostrarPuntuaciones extends Escena {

    public final String nombre = "Puntuaciones";
    private final Boton botonContinuar = new Boton(8, Macro.PANTALLA_ALTO - 48, "", "boton_salir_");
    private ResultSet puntosUsuario = null;
    private ResultSet puntosGenerales = null;
    private ArrayList<Texto> puntos = new ArrayList();
    //consulta SQL de las puntuaciones del usuario
    private static final String PUNTUACIONES_USUARIO = "SELECT CONCAT(U.NICKNAME, '#', U.ID), P.PUNTUACION, P.NIVEL "
            + "FROM PUNTUACIONES P "
            + "JOIN USUARIOS U ON U.ID = P.ID "
            + "WHERE U.NOMBRE = '" + Inicio.getNombreUsuario()
            + "' ORDER BY P.PUNTUACION DESC, P.NIVEL DESC, U.NOMBRE DESC "
            + "LIMIT 3;";

    private BufferedImage fondo = null;
    /**
     * Las puntuaciones se recogen en un ResultSet y después se pasan a
     * ArrayList (si no se hace esto la base de datos da un error al dibujar,
     * porque se consuta demasiadas veces por segundo). Se añaden las
     * coordenadas donde se debe dibujar cada caja del ArrayList para que la
     * función dibujar() haga el resto del trabajo.
     */
    public MostrarPuntuaciones() {
        Juego.ventana.actualizarTitulo(Macro.NOMBRE_JUEGO);
        puntosUsuario = DataBase.consultar(PUNTUACIONES_USUARIO);
        puntosGenerales = DataBase.consultar(Macro.PUNTUACIONES_GENERALES);
        try{
            fondo = ImageIO.read(new File(".\\rsc\\sprites\\fondos\\fondo_generico.png"));
        } catch(IOException ex){
            System.out.println("Error Carga Imagen Fondo");
        }
        try {
            int separador = Macro.PANTALLA_ALTO / 2 - 240;

            puntos.add(new Texto("NICKNAME", Macro.PANTALLA_ANCHO / 2 - 300, separador - 40, true));
            puntos.add(new Texto("PUNTOS", Macro.PANTALLA_ANCHO / 2, separador - 40, true));
            puntos.add(new Texto("NIVEL", Macro.PANTALLA_ANCHO / 2 + 200, separador - 40, true));

            while (puntosUsuario != null && puntosUsuario.next()) {

                puntos.add(new Texto(puntosUsuario.getString(1), Macro.PANTALLA_ANCHO / 2 - 300, separador, true));
                puntos.add(new Texto(puntosUsuario.getString(2), Macro.PANTALLA_ANCHO / 2 + 15, separador, true));
                puntos.add(new Texto(puntosUsuario.getString(3), Macro.PANTALLA_ANCHO / 2 + 220, separador, true));
                separador += 20;
            }

            separador += 40;

            while (puntosGenerales != null && puntosGenerales.next()) {
                puntos.add(new Texto(puntosGenerales.getString(1), Macro.PANTALLA_ANCHO / 2 - 300, separador, true));
                puntos.add(new Texto(puntosGenerales.getString(2), Macro.PANTALLA_ANCHO / 2 + 15, separador, true));
                puntos.add(new Texto(puntosGenerales.getString(3), Macro.PANTALLA_ANCHO / 2 + 220, separador, true));
                separador += 20;
            }
        } catch (SQLException ex) {
            System.out.println("Error al cargar puntuaciones");
        } finally { //cerramos todas las conexiones
            try {
                if (puntosUsuario != null) {
                    puntosUsuario.close();
                }
                if (puntosGenerales != null) {
                    puntosGenerales.close();
                }
                DataBase.cerrarConexion();  //debemos cerrar la conexión con la base de datos
            } catch (SQLException ex) {
                System.out.println("ERROR DE SQL");
            }
        }
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    /**
     * Se dibujan las puntuaciones y el botón para salir.
     *
     * @param g gráficos. Necesarios para dibujar
     */
    @Override
    public void dibujar(Graphics g) {
        //primero se dibuja el fondo en color gris
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, Macro.PANTALLA_ANCHO, Macro.PANTALLA_ALTO);
        g.drawImage(fondo, 0, 0, null);
        if (puntos != null) {
            for (Texto punto : puntos) {
                punto.dibujar(g);
            }
        }

        botonContinuar.dibujar(g);

    }

    @Override
    public void clickIzq() {
        if (botonContinuar.getCursorEncima()) {
            Juego.escena = new Menu();
        }
    }

    @Override
    public void actualizar() {
        botonContinuar.actualizar();
    }

    @Override
    public void actualizarSegundo() {
    }

    @Override
    public void clickDch() {
    }

}
