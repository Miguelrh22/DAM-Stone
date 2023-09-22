package Misc;

/**
 * Almacén de constantes. La finalidad principal de esta clase es la de no
 * cargar el main Juego.java que se encuentra en el paquete Main.
 */
public class Macro {

    public static final String NOMBRE_JUEGO = "dam_stone";
    public static final String NOMBRE_JUEGO_FORMATO = "DAM Stone";

    public static final int PANTALLA_ANCHO = 768;  //1280
    public static final int PANTALLA_ALTO = 960;    //720

    public static final String LOCAL_PATH = System.getenv("LOCALAPPDATA") + "\\"+NOMBRE_JUEGO; //ruta de guardado de la carpeta principal del juego
    public static final String LOCAL_MULTIJUGADOR = LOCAL_PATH + "\\partida_local"; //ruta de guardado del estado de partida multijugador

    //creación de base de datos y tablas (ponemos todo en una línea porque estos CREATE suelen dar problemas)
    public static final String CREACION_DATABASE = "CREATE DATABASE IF NOT EXISTS proyecto_java;";
    public static final String USAR_DATABASE = "USE proyecto_java;";    //colocar esto en la constante anterior hace que deje de funcionar. Lo ponemos en una consulta aparte
    public static final String CREACION_TABLA_USUARIOS = "CREATE TABLE IF NOT EXISTS USUARIOS(ID INT AUTO_INCREMENT PRIMARY KEY,NOMBRE VARCHAR(24) UNIQUE,PASSWORD VARCHAR(255),NICKNAME VARCHAR (24));";
    public static final String CREACION_TABLA_PUNTUACIONES = "CREATE TABLE IF NOT EXISTS PUNTUACIONES(ID INT,PUNTUACION INT,NIVEL INT,CONSTRAINT PUNTOS_PK PRIMARY KEY (ID, PUNTUACION, NIVEL),CONSTRAINT PUNTOS_FK FOREIGN KEY (ID) REFERENCES USUARIOS (ID) ON DELETE CASCADE);";

    //consulta de las puntuaciones generales (se limita a 5 resultados)
    public static final String PUNTUACIONES_GENERALES = "SELECT CONCAT(U.NICKNAME, '#', U.ID), P.PUNTUACION, P.NIVEL "
            + "FROM PUNTUACIONES P "
            + "JOIN USUARIOS U ON U.ID = P.ID "
            + "ORDER BY P.PUNTUACION DESC, P.NIVEL DESC, U.NOMBRE DESC "
            + "LIMIT 5;";
    
    
}
