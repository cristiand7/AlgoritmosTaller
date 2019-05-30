package co.edu.javeriana.algoritmos.proyecto.reakt;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import co.edu.javeriana.algoritmos.proyecto.reakt.TableroHex;
import co.edu.javeriana.algoritmos.proyecto.ColorJugador;
import co.edu.javeriana.algoritmos.proyecto.Jugada;
import co.edu.javeriana.algoritmos.proyecto.JugadorHex;
import co.edu.javeriana.algoritmos.proyecto.Tablero;
import java.awt.Point;
import java.util.Random;
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

    private Jugada mejorJugada;
    private final TableroHex t;
    private ColorJugador colorJugador;

     public Jugador(){
        mejorJugada = null;
        t = new TableroHex(true);
    }

    public Jugada newJugada(Point p){
        return new Jugada(p.x, p.y);
    }
    
     @Override
    public Jugada jugar(Tablero tablero, ColorJugador color) {
        System.out.println(" >Soy "+color);
        final ExecutorService service = Executors.newSingleThreadExecutor();
        try {
            final Future<Jugada> f = service.submit(() -> {
                encontrarMejorJugada(tablero,color);
                return mejorJugada;
            });
            mejorJugada = f.get(MAX_CALCULATION_TIME, MAX_CALCULATION_UNITS);
        } catch (final InterruptedException | ExecutionException | TimeoutException e) {
            System.err.println("ERROR: "+e.getLocalizedMessage());
            e.printStackTrace();
        } finally {
            service.shutdown();
            
            System.out.println(" >Yo juego en [x="+ mejorJugada.getFila()+ ",y="+mejorJugada.getColumna()+"] "+(mejorJugada.isCambioColores()?"cambiando el color":""));
            t.aplicarJugada(mejorJugada, color);
            t.imprimirTablero();
            return mejorJugada;
        }
    }
    
     @Override
    public String nombreJugador() {
        return "Reakt";
    }
    

    
    private int random(int n)
    {
        Random r = new Random();
        return r.nextInt(n);
    }

    public Jugada encontrarMejorJugada(Tablero tablero, ColorJugador color) throws InterruptedException {
        colorJugador = color;
        
        //Actualizar lista de casillas libres
        t.Actualizar(tablero);

        //Escoger la unica casilla libre por si acaso
        mejorJugada = newJugada(t.Libres.get(0));
        mejorJugada
            = (t.turno==1)? FirstMove()
            : (t.turno==2)? SecondMove()
            : t.GetBestMove(colorJugador);
        return mejorJugada;
    }
    
     public Jugada FirstMove(){
        //Escoger una de las esquinas
        int x = random(4);
        int y = random(4-x);
        
        // 50-50 de escojer la otra
        if (random(2)<1)
        {
            x=t.Size-1-x;
            y=t.Size-1-y;
        }
        return new Jugada(x,y);
    }
    
    public Jugada SecondMove(){
        //Buscar en la lista de movimientos otro jugador, solo deberia haber uno
        for(Point p : (colorJugador==ColorJugador.BLANCO? t.Negras: t.Blancas)){
            if ((p.x+p.y<=2)||(p.x+p.y>=2*t.Size-4))
                return t.GetBestMove(colorJugador);
            else
                return new Jugada(true, p.x, p.y);
	}
        return null;
    }    
}
