/**
 * 
 */
package co.edu.javeriana.algoritmos.proyecto;

/**
 * Interfaz que describe la funcionalidad esperada de un tablero.
 * 
 * NO MODIFIQUE NADA EN ESTE ARCHIVO, NI LO COPIE, NI LO MUEVA A OTRO PAQUETE.
 *
 */
public interface Tablero 
{
    /**
     * Aplica una jugada en el tablero, cambiando su estado.
     *
     * @param jugada la jugada enviada.
     * @param colorJugador el color del jugador que envía la jugada
     */
    public void aplicarJugada( Jugada jugada, ColorJugador colorJugador );
    
    /**
     * 
     * @return el color del jugador ganador, o <code>null</code> si no hay.
     */
    public ColorJugador ganador();
    
    /**
     * Retorna el contenido de la fila y la columna enviadas como parámetros.
     * 
     * @param fila
     * @param columna
     * @return
     */
    public ColorJugador casilla( int fila, int columna );
    
}
