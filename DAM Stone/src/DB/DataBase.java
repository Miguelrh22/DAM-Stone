package DB;

import java.sql.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import Misc.Macro;

/**
 * Todo lo relacionado con la base de datos, ya sea DML (SELECT, INSERT, UPDATE,
 * DELETE) o DDL (CREATE, ALTER, DROP, TRUNCATE) se realiza con los métodos
 * estáticos de esta clase.
 */
public final class DataBase {

    /*estos atributos son final ya que siempre nos conectamos a la misma base
    de datos, y siempre con el mismo usuario*/
    private static final String CADENA_CONEXION = "jdbc:mysql://localhost:3306/";
    private static final String NOMBRE_BBDD = "proyecto_java";
    private static final String USUARIO = "root";   //usuario por defecto que no tiene contraseña
    //las variables de la base de datos no pueden ser final
    private static Connection con = null;
    private static Statement stm = null;
    private static ResultSet rs = null;
    private static String consulta;

    /**
     * Se realiza la conexión con el driver de sql. Además, se crea la base de
     * datos y las tablas en caso de que no existan. Es suficiente con hacer
     * todo esto una vez, por lo que sólo ocurre al iniciar el juego.
     */
    public static void inicializar() {
        try {
            //primero, el Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            //ahora conexión y creación de base de datos y tablas
            con = DriverManager.getConnection(CADENA_CONEXION, USUARIO, "");
            stm = con.createStatement();
            stm.execute(Macro.CREACION_DATABASE);
            stm.execute(Macro.USAR_DATABASE);
            stm.execute(Macro.CREACION_TABLA_USUARIOS);
            stm.execute(Macro.CREACION_TABLA_PUNTUACIONES);

            //por último, insertamos datos de prueba (guardados previamente en ficheros)
            //primero los usuarios
            insertarDatosDePrueba("./rsc/BBDD/usuarios_prueba.txt");
            //luego sus puntuaciones
            insertarDatosDePrueba("./rsc/BBDD/puntuaciones_prueba.txt");
            System.out.println("BASE DE DATOS CREADA CORRECTAMENTE.");
        } catch (SQLException ex) {
            System.out.println("ERROR AL CREAR LA BASE DE DATOS.");
        } catch (ClassNotFoundException ex) {
            System.out.println("ERROR. EL DRIVER DE SQL NO ESTÁ DISPONIBLE.");
        } finally {
            cerrarConexion();   //cerramos la conexión. Sólo conectamos con la BBDD cuando nos interesa
        }
    }

    /**
     * Sirve para insertar datos de prueba en la base de datos (de esta manera
     * el juego no está vacío al abrirlo y se pueden probar las funcionalidades
     * de los diferentes métodos de esta clase).
     *
     * @param path la ruta del fichero .txt que contiene los datos a insertar
     */
    private static void insertarDatosDePrueba(String path) {
        try ( Scanner sc = new Scanner(new File(path))) {    //esto es un try-with-resources, cierra automáticamente el scanner al terminarse
            while (sc.hasNext()) {
                stm.executeUpdate(sc.nextLine());
            }
        } catch (FileNotFoundException ex) {
            System.out.println("ERROR AL CARGAR LOS DATOS DE PRUEBA.");
        } catch (SQLException ex) {
            System.out.println("ERROR DE SQL.");
        }
    }

    /**
     * Cierra todas las conexiones con la base de datos (ResultSet, Connection,
     * Statement, etc.). Se aconseja llamar a este método tras llamar a
     * consultar(), crearUsuario() o ponerPuntuacion().
     */
    public static void cerrarConexion() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException ex) {
            System.out.println("ERROR DE SQL AL CERRAR LA CONEXIÓN CON LA BASE DE DATOS");
        }
    }

    /**
     * Sirve tanto para las puntuaciones personales como para las puntuaciones
     * generales.Tras llamar a este método, se aconseja utilizar
     * cerrarConexion().
     *
     * @param consulta contiene la consulta
     * @return los resultados de la consulta que se realiza
     */
    public static ResultSet consultar(String consulta) {
        try {
            con = DriverManager.getConnection(CADENA_CONEXION + NOMBRE_BBDD, USUARIO, "");
            stm = con.createStatement();
            return stm.executeQuery(consulta);
        } catch (SQLException ex) {
            System.out.println("ERROR DE SQL AL CONSULTAR LA BASE DE DATOS");
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Se utiliza para la creación de nuevos usuarios. Tras llamar a este
     * método, se aconseja utilizar cerrarConexion().
     *
     * @param user nombre de usuario
     * @param password contraseña de usuario
     * @param nickname apodo de usuario
     * @return 1 si el registro se ha realizado correctamente, -1, -2, -3 si el
     * usuario, la contraseña y el nickname, respectivamente, son demasiado
     * cortos, -4 si el usuario ya existe y 0 si hay cualquier otro error
     */
    public static int crearUsuario(String user, String password, String nickname) {
        consulta = "INSERT INTO USUARIOS VALUES (NULL, '" + user + "', '" + password + "', '" + nickname + "');";

        if (user.length() < 4) {
            System.out.println("ERROR: EL NOMBRE DE USUARIO DEBE TENER ENTRE 4 Y 24 CARACTERES.");  //el control de 24 caracteres lo hace la caja de texto de manera automática, ya que no permite introducir más
            return -1;
        }
        if (password.length() < 4) {
            System.out.println("ERROR: EL PASSWORD DEBE TENER ENTRE 4 Y 24 CARACTERES.");
            return -2;
        }
        if (nickname.length() < 4) {
            System.out.println("ERROR: EL NICKNAME DEBE TENER ENTRE 4 Y 24 CARACTERES.");
            return -3;
        }

        try {
            con = DriverManager.getConnection(CADENA_CONEXION + NOMBRE_BBDD, USUARIO, "");
            stm = con.createStatement();
            stm.executeUpdate(consulta);
            System.out.println("USUARIO " + user + " REGISTRADO CORRECTAMENTE.");
            return 1;
        } catch (SQLIntegrityConstraintViolationException ex) {
            System.out.println("ERROR: EL USUARIO " + user + " YA EXISTE.");
            return -4;
        } catch (SQLException ex) {
            System.out.println("ERROR DE SQL AL CONSULTAR LA BASE DE DATOS.");
            return 0;
        }
    }

    /**
     * Se utiliza para que los usuarios ya existentes puedan acceder al juego.
     * NO hay que llamar a cerrarConexion() tras utilizar este método.
     *
     * @param user nombre de usuario
     * @param password contraseña de ususario
     * @return 1 si el usuario obtiene acceso correctamente, -1 si el nombre de
     * usuario introducido no existe, -2 si la contraseña es incorrecta y -3 si
     * hay cualquier error relacionado con la base de datos (SQLException)
     */
    public static int loginUsuario(String user, String password) {
        rs = consultar("SELECT NOMBRE FROM USUARIOS WHERE NOMBRE = '" + user + "';");
        try {
            if (rs.next()) {
                rs = consultar("SELECT PASSWORD FROM USUARIOS WHERE NOMBRE = '" + user + "' AND PASSWORD = '" + password + "';");

                if (rs.next()) {
                    return 1;
                } else {
                    System.out.println("ERROR: EL PASSWORD ES INCORRECTO.");
                    return -2;
                }
            } else {
                System.out.println("ERROR: EL USUARIO NO EXISTE.");
                return -1;
            }
        } catch (SQLException ex) {
            System.out.println("ERROR DE SQL");
            return -3;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                System.out.println("ERROR DE SQL");
                return -3;
            }
        }
    }

    /**
     * Sirve para registrar las puntuaciones en la base de datos. Un jugador
     * puede registrar todas las puntuaciones que desee, pero nunca podrá tener
     * la misma puntuación y el mismo nivel dos veces (en este caso la
     * puntuación no se registrará, pues ya se encuentra presenta en la base de
     * datos). Se aconseja llamar a cerrarConexion() tras utilizar este método.
     *
     * @param user el usuario actual
     * @param puntuacion la puntuación que se desea registar
     * @param nivel el nivel que se desea registrar
     * @return true sólo si se registra correctamente
     */
    public static boolean ponerPuntuacion(String user, int puntuacion, int nivel) {
        consulta = "INSERT INTO PUNTUACIONES VALUES ((SELECT ID FROM USUARIOS WHERE NOMBRE = '" + user + "'), " + puntuacion + ", " + nivel + ");";

        /*No es necesario controlar la existencia del usuario, ya que si no existe
        no puede realizar el login y, por ende, no puede jugar.*/
        try {
            con = DriverManager.getConnection(CADENA_CONEXION + NOMBRE_BBDD, USUARIO, "");
            stm = con.createStatement();
            stm.executeUpdate(consulta);
            System.out.println("PUNTUACIÓN REGISTRADA CORRECTAMENTE");
            return true;
        } catch (SQLIntegrityConstraintViolationException ex) {
            System.out.println("ESTA PUNTUACIÓN YA EXISTE PARA EL USUARIO " + user);
            return false;
        } catch (SQLException ex) {
            System.out.println("ERROR DE SQL AL REGISTRAR LA PUNTUACIÓN");
            return false;
        }
    }
}
