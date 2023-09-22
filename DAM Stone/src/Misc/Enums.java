package Misc;

/**
 * Sirve como un diccionario. Los enum asignan una palabra a un número, por
 * ejemplo: MANA --> 0, UNIDAD --> 1, etc. Esto permite comparar sin recordar
 * los nombres exactos. En algunos enum, utilizamos POR_DEFECTO para
 * unidades/hechizos que no tengan una característica específica.
 */
public class Enums {

    public enum tipoCarta {
        MANA,
        UNIDAD,
        HECHIZO
    }

    public enum tipoHechizo {
        ATAQUE,
        CURACION,
        ELIMINAR,
        MAS_STATS,
        POR_DEFECTO
    }
    
    public enum duracionHechizo{
        INSTANTANEO,    //se invoca, lleva a cabo su efecto y se destruye
        TEMPORAL    //no se destruye en el turno de invocación
    }

    //TODO: añadir funcion estatica de info
    public enum tipoUnidad {
        VELOZ, //puede atacar cuando es invocada, no necesita esperar 1 turno
        TORRE, //no puede atacar
        TRANSFORMACION, //en n turnos cambia
        POR_DEFECTO
    }

    public enum talentoUnidad {
        PENETRACION, //el daño restante que causa a otra unidad se resta de la vida del enemigo
        ROBO_VIDA, //el daño restante que causa a otra unidad cura al jugador
        REGENERARCION, //se cura a sí misma (por ejemplo, a final de turno)
        POR_DEFECTO
    }

    public enum alinearVert {
        TOP,
        CENTER,
        BOTTOM
    }

    public enum alinearHorz {
        LEFT,
        CENTER,
        RIGHT
    }
}
