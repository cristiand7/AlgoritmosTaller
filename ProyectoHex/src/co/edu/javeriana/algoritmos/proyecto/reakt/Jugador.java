package co.edu.javeriana.algoritmos.proyecto.reakt;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import co.edu.javeriana.algoritmos.proyecto.ColorJugador;
import co.edu.javeriana.algoritmos.proyecto.Jugada;
import co.edu.javeriana.algoritmos.proyecto.JugadorHex;
import co.edu.javeriana.algoritmos.proyecto.Tablero;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 *
 * @author Reakt
 */
public class Jugador implements JugadorHex {

    public final long MAX_CALCULATION_TIME = 1000 * 2 - 1;
    public final TimeUnit MAX_CALCULATION_UNITS = TimeUnit.MILLISECONDS;
    public final boolean LOG=false;

    public Jugada mejorJugada;
    public TableroHex t;
    public ColorJugador colorJugador;

     public Jugador(){
        mejorJugada = null;
        t = new TableroHex(this,LOG);
    }
     
    public void log(String s){
        if(LOG)System.out.println(s);
    }
    public void logError(String s){
        if(LOG)System.err.println(s);
    }
    
    @Override
    public Jugada jugar(Tablero tablero, ColorJugador color) {
        colorJugador=color;
        final ExecutorService service = Executors.newSingleThreadExecutor();
        try {
            final Future<Jugada> f = service.submit(() -> {
                log(" >Soy "+colorJugador);
                t.encontrarMejorJugada(tablero);
                return mejorJugada;
            });
            mejorJugada = f.get(MAX_CALCULATION_TIME, MAX_CALCULATION_UNITS);
        } catch (final InterruptedException | ExecutionException | TimeoutException e) {
            logError("ERROR: "+e.getLocalizedMessage());
            e.printStackTrace();
        } finally {
            service.shutdown();
            
            log(" >Yo juego en [x="+ mejorJugada.getFila()+ ",y="+mejorJugada.getColumna()+"] "+(mejorJugada.isCambioColores()?"cambiando el color":""));
            t.aplicarJugada(mejorJugada, color);
            if(LOG)t.imprimirTablero();
            return mejorJugada;
        }
    }
    
     @Override
    public String nombreJugador() {
        return "Reakt EZ";
    }
}
