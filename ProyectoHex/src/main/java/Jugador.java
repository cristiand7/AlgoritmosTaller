/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java;

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
public class Jugador implements JugadorHex{
    public final long MAX_CALCULATION_TIME = 1000*2-1;
    public final TimeUnit MAX_CALCULATION_UNITS = TimeUnit.MILLISECONDS;
    
    private Jugada mejorJugada = null;

    public Jugada encontrarMejorJugada(Tablero tablero, ColorJugador color) throws InterruptedException {
        mejorJugada = new Jugada(1, 1);
        Thread.sleep(3000);
        mejorJugada = new Jugada(2, 2);
        return mejorJugada;
    }
    @Override
    public Jugada jugar(Tablero tablero, ColorJugador color) {
        final ExecutorService service = Executors.newSingleThreadExecutor();
        try {
            final Future<Jugada> f = service.submit(() -> {
                encontrarMejorJugada(tablero,color);
                return mejorJugada;
            });
            mejorJugada = f.get(MAX_CALCULATION_TIME, MAX_CALCULATION_UNITS);
        } catch (final InterruptedException | ExecutionException | TimeoutException e) {
        } finally {
            service.shutdown();
            return mejorJugada;
        }
    }

    @Override
    public String nombreJugador() {
        return "Reakt";
    }
    
}
