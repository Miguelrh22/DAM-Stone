package Padres;

/**
 * Útil para todo aquello que debe actualizarse: cartas, botones, vidas, mana,
 * etc.
 */
public interface Actualizable {

    /**
     * Esto sucede 60 veces por segundo.
     */
    public abstract void actualizar();

    /**
     * Esto sucede 1 vez por segundo.
     */
    public abstract void actualizarSegundo();
}
