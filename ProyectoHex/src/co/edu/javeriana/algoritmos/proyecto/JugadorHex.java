/**
 * 
 */
package co.edu.javeriana.algoritmos.proyecto;

/**
 * Esta es la interfaz que su jugador debe implementar.  
 *
 * NO MODIFIQUE NADA EN ESTE ARCHIVO, NI LO COPIE, NI LO MUEVA A OTRO PAQUETE.
 * 
 */
public interface JugadorHex 
{
    /**
     * Calcula una jugada sobre el tablero que viene como parámetro con el color indicado.
     * 
     * @param tablero
     * @param color
     * @return la jugada que su jugador hará a continuación.
     */
    public Jugada jugar( Tablero tablero, ColorJugador color );
    
    /**
     * 
     * @return el nombre con el que el jugador quiere identificarse.  
     * Por favor sólo usar letras y números
     */
    public String nombreJugador();

}
