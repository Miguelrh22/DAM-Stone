package Repositorios;

import Cartas.Carta;
import Cartas.CartaMana;
import Cartas.Hechizo;
import Cartas.Stats;
import Cartas.Unidad;
import Misc.Enums;
import java.util.ArrayList;

/**
 * Contiene la creación y obtención de las cartas del juego.
 */
public class RepositorioCartas {

    private static ArrayList<Carta> cartas = new ArrayList();

    /**
     * Crea las cartas del juego.
     */
    public static void crearCartas() {

        //UNIDADES
        Unidad Emp_dep = new Unidad(new Stats(1, 1, 1), Enums.tipoUnidad.POR_DEFECTO, Enums.talentoUnidad.POR_DEFECTO, "u_00", "Empleado deprimido", "Malditos Recursos humanos...");
        Unidad Est_DAW = new Unidad(new Stats(1, 2, 1), Enums.tipoUnidad.POR_DEFECTO, Enums.talentoUnidad.POR_DEFECTO, "u_02", "Estudiante DAW", "WEB WEB WEB");
        Unidad Prog_Nova = new Unidad(new Stats(2, 3, 1), Enums.tipoUnidad.VELOZ, Enums.talentoUnidad.POR_DEFECTO, "u_03", "Progamador Novato", "Tiene ganas de comerse el mundo, a ver cuanto le dura");
        Unidad Prof_BBDD = new Unidad(new Stats(4, 3, 2), Enums.tipoUnidad.POR_DEFECTO, Enums.talentoUnidad.POR_DEFECTO, "u_11", "Profesor de Bases de Datos", "SELECT FROM WHERE");
        Unidad Prof_Prog = new Unidad(new Stats(4, 0, 6), Enums.tipoUnidad.TORRE, Enums.talentoUnidad.POR_DEFECTO, "u_10", "Profesor de Programacion", "Herencia, implementacion, objetos!");
        Unidad Prog_Sen = new Unidad(new Stats(3, 0, 5), Enums.tipoUnidad.TORRE, Enums.talentoUnidad.POR_DEFECTO, "u_09", "Programador Senior", "No trabaja, solo aparenta ");
        Unidad Emp_paro = new Unidad(new Stats(3, 2, 1), Enums.tipoUnidad.POR_DEFECTO, Enums.talentoUnidad.POR_DEFECTO, "u_08", "Empleado en paro", "=(");
        Unidad Prof_Sis = new Unidad(new Stats(5, 5, 2), Enums.tipoUnidad.VELOZ, Enums.talentoUnidad.POR_DEFECTO, "u_12", "Profesor de Sistemas", "Subneting, Hexadecimal, Linux, DIAPOSITIVAAAAS");
        //Unidad Debug = new Unidad(new Stats(5, 2, 1), Enums.tipoUnidad.POR_DEFECTO, Enums.talentoUnidad.POR_DEFECTO, "u_00", "Debugger", "Test Results");
        Unidad Prog = new Unidad(new Stats(5, 4, 3), Enums.tipoUnidad.POR_DEFECTO, Enums.talentoUnidad.POR_DEFECTO, "u_05", "Programador", "While True");
        //Unidad RRHH = new Unidad(new Stats(4, 2, 3), Enums.tipoUnidad.POR_DEFECTO, Enums.talentoUnidad.POR_DEFECTO, "u_00", "Recursos Humanos", "A nadie le gusta RRHH");
        Unidad Prog_Web = new Unidad(new Stats(3, 4, 3), Enums.tipoUnidad.TRANSFORMACION, Enums.talentoUnidad.POR_DEFECTO, "u_04", "Programador Web", "Mira mi CSS, mira mi HTML", 3, Emp_paro);
        Unidad Est_DAM = new Unidad(new Stats(3, 2, 2), Enums.tipoUnidad.TRANSFORMACION, Enums.talentoUnidad.POR_DEFECTO, "u_01", "Estudiante de DAM", "Mucho estudiar, de algo valdra", 5, Prog);

        cartas.add(Emp_dep);
        cartas.add(Est_DAW);
        cartas.add(Prog_Nova);
        cartas.add(Prof_BBDD);
        cartas.add(Prof_Prog);
        cartas.add(Prog_Sen);
        cartas.add(Emp_paro);
        cartas.add(Prof_Sis);
        //cartas.add(Debug);
        cartas.add(Prog);
        //cartas.add(RRHH);
        cartas.add(Prog_Web);
        cartas.add(Est_DAM);

        //UNIDADES LEGENDARIAS
        Unidad Emperador_Gatuno = new Unidad(new Stats(0, 6, 6), Enums.tipoUnidad.POR_DEFECTO, Enums.talentoUnidad.PENETRACION, "u_14", "Emperdador Gatuno", "FURGOOOOOL");
        Unidad Gatito = new Unidad(new Stats(1, 1, 1), Enums.tipoUnidad.TRANSFORMACION, Enums.talentoUnidad.POR_DEFECTO, "u_13", "Gatito", "Miau Miau", 5, Emperador_Gatuno);
        Unidad Desarrollador_Indie_Exitoso = new Unidad(new Stats(0, 4, 3), Enums.tipoUnidad.POR_DEFECTO, Enums.talentoUnidad.POR_DEFECTO, "u_16", "Desarrollador Indie Exitoso", "¿Saco parte 2?");
        Unidad Desarrollador_Indie = new Unidad(new Stats(3, 2, 2), Enums.tipoUnidad.TRANSFORMACION, Enums.talentoUnidad.POR_DEFECTO, "u_15", "Desarrollador Indie", "¡Tremendo Life Sim me voy a sacar solo!", 3, Desarrollador_Indie_Exitoso);

        cartas.add(Emperador_Gatuno);
        cartas.add(Gatito);
        cartas.add(Desarrollador_Indie_Exitoso);
        cartas.add(Desarrollador_Indie);

        //HECHIZOS
        Hechizo Bug = new Hechizo(Enums.tipoHechizo.ATAQUE, new Stats(1, 1, 0), "h_00", "Bug", "Hace 1 punto de daño a la unidad objetivo", 1);
        Hechizo SobreCarga = new Hechizo(Enums.tipoHechizo.ATAQUE, new Stats(4, 3, 0), "h_01", "Sobre Carga", "Hace 3 puntos de daño a la unidad objetivo", 1);
        Hechizo Actualizacion = new Hechizo(Enums.tipoHechizo.CURACION, new Stats(1, 1, 0), "h_02", "Actualizacion", "Cura 1 de salud a la unidad objetivo", 1);
        Hechizo BackUp = new Hechizo(Enums.tipoHechizo.CURACION, new Stats(3, 2, 0), "h_03", "Back Up", "Cura 2 de salud a la unidad objetivo", 1);
        Hechizo Implementacion = new Hechizo(Enums.tipoHechizo.MAS_STATS, new Stats(2, 2, 0), "h_04", "Implementacion", "Otorga +2+2 a la unidad objetivo ", 5);
        Hechizo ComprarLicencia = new Hechizo(Enums.tipoHechizo.ELIMINAR, new Stats(2, 0, 0), "h_05", "Comprar Licencia", "Elimina el hezhizo permanente objetivo", 1);
        Hechizo PausaParaPiti = new Hechizo(Enums.tipoHechizo.ELIMINAR, new Stats(6, 0, 0), "h_06", "Pausa para Piti", "Elimina a todas las unidades que hay en juego", 1);
        
        //Hechizo Testing = new Hechizo(Enums.tipoHechizo.ATAQUE, new Stats(3, 0, 0), "h_01", "Testing", "Cada vez que la unidad ataca, cura 2 de vida a su propietario", 3);
        
        cartas.add(Bug);
        cartas.add(Actualizacion);
        cartas.add(SobreCarga);
        cartas.add(BackUp);
        cartas.add(Implementacion);
        cartas.add(ComprarLicencia);
        //cartas.add(Testing);
        cartas.add(PausaParaPiti);

        //MANA
        CartaMana Mana = new CartaMana();

        cartas.add(Mana);
    }

    /**
     * @param nombre el nombre de la carta deseada
     * @return la carta que se desea
     */
    public static Carta obtenerCarta(String nombre) {
        for (int i = 0; i < cartas.size(); i++) {
            if (cartas.get(i).getNombre().equals(nombre)) {
                return cartas.get(i);
            }
        }
        return null;
    }
}
