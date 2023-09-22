package Repositorios;

import Cartas.Carta;
import java.util.ArrayList;

/**
 * Contiene la creación y obtención de los mazos del juego.
 */
public class RepositorioMazos {

    private static ArrayList<ArrayList<Carta>> Mazos = new ArrayList();

    /**
     * Crea los mazos del juego.
     */
    public static void crearMazos() {
        //CREACION MAZO ESTANDAR/
        Mazos.add(new ArrayList());
        Mazos.get(0).add(RepositorioCartas.obtenerCarta("Empleado deprimido"));
        Mazos.get(0).add(RepositorioCartas.obtenerCarta("Empleado deprimido"));
        Mazos.get(0).add(RepositorioCartas.obtenerCarta("Estudiante DAW"));
        Mazos.get(0).add(RepositorioCartas.obtenerCarta("Estudiante DAW"));
        Mazos.get(0).add(RepositorioCartas.obtenerCarta("Profesor de Bases de Datos"));
        Mazos.get(0).add(RepositorioCartas.obtenerCarta("Profesor de Bases de Datos"));
        Mazos.get(0).add(RepositorioCartas.obtenerCarta("Profesor de Programacion"));
        Mazos.get(0).add(RepositorioCartas.obtenerCarta("Profesor de Programacion"));
        Mazos.get(0).add(RepositorioCartas.obtenerCarta("Programador Senior"));
        Mazos.get(0).add(RepositorioCartas.obtenerCarta("Programador Senior"));
        Mazos.get(0).add(RepositorioCartas.obtenerCarta("Empleado deprimido"));
        Mazos.get(0).add(RepositorioCartas.obtenerCarta("Empleado en paro"));
        Mazos.get(0).add(RepositorioCartas.obtenerCarta("Empleado en paro"));
        Mazos.get(0).add(RepositorioCartas.obtenerCarta("Profesor de Sistemas"));
        Mazos.get(0).add(RepositorioCartas.obtenerCarta("Profesor de Sistemas"));
        Mazos.get(0).add(RepositorioCartas.obtenerCarta("Programador Web"));
        Mazos.get(0).add(RepositorioCartas.obtenerCarta("Programador Web"));
        Mazos.get(0).add(RepositorioCartas.obtenerCarta("Estudiante de DAM"));
        Mazos.get(0).add(RepositorioCartas.obtenerCarta("Estudiante de DAM"));
        Mazos.get(0).add(RepositorioCartas.obtenerCarta("Estudiante de DAM"));
        Mazos.get(0).add(RepositorioCartas.obtenerCarta("Gatito"));
        Mazos.get(0).add(RepositorioCartas.obtenerCarta("Gatito"));
        Mazos.get(0).add(RepositorioCartas.obtenerCarta("Bug"));
        Mazos.get(0).add(RepositorioCartas.obtenerCarta("Bug"));
        Mazos.get(0).add(RepositorioCartas.obtenerCarta("Actualizacion"));
        Mazos.get(0).add(RepositorioCartas.obtenerCarta("Actualizacion"));
        Mazos.get(0).add(RepositorioCartas.obtenerCarta("Sobre Carga"));
        Mazos.get(0).add(RepositorioCartas.obtenerCarta("Back Up"));
        Mazos.get(0).add(RepositorioCartas.obtenerCarta("Implementacion"));
        Mazos.get(0).add(RepositorioCartas.obtenerCarta("Implementacion"));

        //CREACION MAZO OFENSIVO/INDIE
        Mazos.add(new ArrayList());
        Mazos.get(1).add(RepositorioCartas.obtenerCarta("Progamador Novato"));
        Mazos.get(1).add(RepositorioCartas.obtenerCarta("Progamador Novato"));
        Mazos.get(1).add(RepositorioCartas.obtenerCarta("Progamador Novato"));
        Mazos.get(1).add(RepositorioCartas.obtenerCarta("Profesor de Sistemas"));
        Mazos.get(1).add(RepositorioCartas.obtenerCarta("Profesor de Sistemas"));
        Mazos.get(1).add(RepositorioCartas.obtenerCarta("Profesor de Sistemas"));
        Mazos.get(1).add(RepositorioCartas.obtenerCarta("Programador Web"));
        Mazos.get(1).add(RepositorioCartas.obtenerCarta("Programador Web"));
        Mazos.get(1).add(RepositorioCartas.obtenerCarta("Estudiante DAW"));
        Mazos.get(1).add(RepositorioCartas.obtenerCarta("Estudiante DAW"));
        Mazos.get(1).add(RepositorioCartas.obtenerCarta("Estudiante de DAM"));
        Mazos.get(1).add(RepositorioCartas.obtenerCarta("Estudiante de DAM"));
        Mazos.get(1).add(RepositorioCartas.obtenerCarta("Empleado en paro"));
        Mazos.get(1).add(RepositorioCartas.obtenerCarta("Empleado en paro"));
        Mazos.get(1).add(RepositorioCartas.obtenerCarta("Empleado en paro"));
        Mazos.get(1).add(RepositorioCartas.obtenerCarta("Empleado deprimido"));
        Mazos.get(1).add(RepositorioCartas.obtenerCarta("Empleado deprimido"));
        Mazos.get(1).add(RepositorioCartas.obtenerCarta("Empleado deprimido"));
        Mazos.get(1).add(RepositorioCartas.obtenerCarta("Estudiante de DAM"));
        Mazos.get(1).add(RepositorioCartas.obtenerCarta("Programador"));
        Mazos.get(1).add(RepositorioCartas.obtenerCarta("Programador"));
        Mazos.get(1).add(RepositorioCartas.obtenerCarta("Desarrollador Indie"));
        Mazos.get(1).add(RepositorioCartas.obtenerCarta("Desarrollador Indie"));
        Mazos.get(1).add(RepositorioCartas.obtenerCarta("Comprar Licencia"));
        Mazos.get(1).add(RepositorioCartas.obtenerCarta("Implementacion"));
        Mazos.get(1).add(RepositorioCartas.obtenerCarta("Sobre Carga"));
        Mazos.get(1).add(RepositorioCartas.obtenerCarta("Sobre Carga"));
        Mazos.get(1).add(RepositorioCartas.obtenerCarta("Bug"));
        Mazos.get(1).add(RepositorioCartas.obtenerCarta("Bug"));
        Mazos.get(1).add(RepositorioCartas.obtenerCarta("Bug"));

        //CREACION MAZO CONTROL/
        Mazos.add(new ArrayList());
        Mazos.get(2).add(RepositorioCartas.obtenerCarta("Profesor de Programacion"));
        Mazos.get(2).add(RepositorioCartas.obtenerCarta("Profesor de Programacion"));
        Mazos.get(2).add(RepositorioCartas.obtenerCarta("Programador"));
        Mazos.get(2).add(RepositorioCartas.obtenerCarta("Programador Senior"));
        Mazos.get(2).add(RepositorioCartas.obtenerCarta("Programador Senior"));
        Mazos.get(2).add(RepositorioCartas.obtenerCarta("Estudiante de DAM"));
        Mazos.get(2).add(RepositorioCartas.obtenerCarta("Estudiante de DAM"));
        Mazos.get(2).add(RepositorioCartas.obtenerCarta("Estudiante de DAM"));
        Mazos.get(2).add(RepositorioCartas.obtenerCarta("Programador Web"));
        Mazos.get(2).add(RepositorioCartas.obtenerCarta("Programador Web"));
        Mazos.get(2).add(RepositorioCartas.obtenerCarta("Estudiante DAW"));
        Mazos.get(2).add(RepositorioCartas.obtenerCarta("Estudiante DAW"));
        Mazos.get(2).add(RepositorioCartas.obtenerCarta("Profesor de Bases de Datos"));
        Mazos.get(2).add(RepositorioCartas.obtenerCarta("Profesor de Bases de Datos"));
        Mazos.get(2).add(RepositorioCartas.obtenerCarta("Programador"));
        Mazos.get(2).add(RepositorioCartas.obtenerCarta("Empleado deprimido"));
        Mazos.get(2).add(RepositorioCartas.obtenerCarta("Empleado deprimido"));
        Mazos.get(2).add(RepositorioCartas.obtenerCarta("Empleado deprimido"));
        Mazos.get(2).add(RepositorioCartas.obtenerCarta("Back Up"));
        Mazos.get(2).add(RepositorioCartas.obtenerCarta("Back Up"));
        Mazos.get(2).add(RepositorioCartas.obtenerCarta("Sobre Carga"));
        Mazos.get(2).add(RepositorioCartas.obtenerCarta("Sobre Carga"));
        Mazos.get(2).add(RepositorioCartas.obtenerCarta("Actualizacion"));
        Mazos.get(2).add(RepositorioCartas.obtenerCarta("Actualizacion"));
        Mazos.get(2).add(RepositorioCartas.obtenerCarta("Bug"));
        Mazos.get(2).add(RepositorioCartas.obtenerCarta("Bug"));
        Mazos.get(2).add(RepositorioCartas.obtenerCarta("Comprar Licencia"));
        Mazos.get(2).add(RepositorioCartas.obtenerCarta("Comprar Licencia"));
        Mazos.get(2).add(RepositorioCartas.obtenerCarta("Pausa para Piti"));
        Mazos.get(2).add(RepositorioCartas.obtenerCarta("Pausa para Piti"));

    }

    /**
     * @param a el número del mazo deseado
     * @return el mazo que se desea
     */
    public static ArrayList<Carta> elegirMazo(int a) {
        ArrayList<Carta> aux = new ArrayList();
        ArrayList<Carta> aux1 = Mazos.get(a);
        for (int i = 0; i < aux1.size(); i++) {
            aux.add(aux1.get(i).clone());
        }
        return aux;
    }
}
