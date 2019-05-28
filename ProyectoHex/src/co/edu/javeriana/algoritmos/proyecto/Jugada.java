/**
 * 
 */
package co.edu.javeriana.algoritmos.proyecto;

/**
 * Clase inmutable que describe una jugada.
 *
 * NO MODIFIQUE NADA EN ESTE ARCHIVO, NI LO COPIE, NI LO MUEVA A OTRO PAQUETE.
 * 
 */
public class Jugada 
{
    private boolean cambioColores = false;
    private int fila;
    private int columna;
    
    /**
     * Constructora de clase.
     * 
     * @param cambioColores <code>true</code> si y sólo si la jugada es de cambio de color.
     * @param fila la fila, en caso de que sea una jugada normal.
     * @param columna la columna, en caso de que sea una jugada normal.
     */
    public Jugada( boolean cambioColores, int fila, int columna ) 
    {
        this( fila, columna );
        this.cambioColores = cambioColores;
    }
    
    /**
     * Constructora de clase.  Útil para crear jugadas normales.
     * 
     * @param fila la fila, en caso de que sea una jugada normal.
     * @param columna la columna, en caso de que sea una jugada normal.
     */
    public Jugada( int fila, int columna ) 
    {
        if ( fila < 0 || fila > 11 )
            throw new IllegalArgumentException( "Fila ilegal" );
        this.fila = fila;
        if ( columna < 0 || columna > 11 )
            throw new IllegalArgumentException( "Columna ilegal" );
        this.columna = columna;
    }

    public boolean isCambioColores() {
        return cambioColores;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    @Override
    public String toString() 
    {
        return "Jugada [" 
                + ((cambioColores) ? 
                        "cambioColores": 
                        "fila=" + fila + ", columna=" + columna) 
                + "]";
    }
    
}
