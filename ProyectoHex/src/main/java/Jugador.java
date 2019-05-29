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
public class Jugador implements JugadorHex{
    public final long MAX_CALCULATION_TIME = 1000*2-1;
    public final TimeUnit MAX_CALCULATION_UNITS = TimeUnit.MILLISECONDS;
    
    private Jugada mejorJugada;
    private final TableroHex t;
    private ColorJugador colorJugador;
    
    public Jugador(){
        mejorJugada = null;
        t = new TableroHex();
    }
    
    public Jugada newJugada(Point p){
        return new Jugada(p.x, p.y);
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
    
    public int sign(int n)
    {
        if (n<0) return(-1);
        if (n>0) return(1);
        return 0;
    }
    
    private int random(int n)
    {
        Random r = new Random();
        return r.nextInt(n);
    }
        
    public Jugada encontrarMejorJugada(Tablero tablero, ColorJugador color) throws InterruptedException {
        colorJugador=color;
        
        //Actualizar lista de casillas libres
        t.Actualizar(tablero);
        
        //Escoger la unica casilla libre por si acaso
        mejorJugada = newJugada(t.Libres.get(0));
        
        mejorJugada
            = (t.turno==1)? FirstMove()
            : (t.turno==2)? SecondMove()
            : BestMove();
        
        t.aplicarJugada(mejorJugada, color); 
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
                return new Jugada(true, p.x, p.y);
            else
                return BestMove();
	}
        return null;
    }
    
    public Jugada BestMove(){
        return null;
    }

    public void CalculatePotential(){
	int borderPotential=128;

	for (int x=0; x<t.Size; x++)
            for (int y=0; y<t.Size; y++)
                for (int p=0; p<4; p++)
                {
                    t.Pot[x][y][p]=20000;
                    t.Bridge[x][y][p]=0;
                }

	for (int i=0; i<t.Size; i++)
	{
            if (t.casilla(i,0)==null)
                t.Pot[i][0][0]=borderPotential;
            if (t.casilla(i,0)==ColorJugador.BLANCO)
                t.Pot[i][0][0]=0;

            if(t.casilla(i,t.Size-1)==null)
                t.Pot[i][t.Size-1][1]=borderPotential;
            if (t.casilla(i,t.Size-1)==ColorJugador.BLANCO)
                t.Pot[i][t.Size-1][1]=0;

            if (t.casilla(0,i)==null)
                t.Pot[0][i][2]=borderPotential;
            if (t.casilla(0,i)==ColorJugador.NEGRO)
                t.Pot[0][i][2]=0;

            if(t.casilla(t.Size-1,i)==null)
                t.Pot[t.Size-1][i][3]=borderPotential;
            if (t.casilla(t.Size-1,i)==ColorJugador.NEGRO)
                t.Pot[t.Size-1][i][3]=0;
	}

	CalculatePlayerPotential(0,ColorJugador.BLANCO);
	CalculatePlayerPotential(1,ColorJugador.BLANCO);
	CalculatePlayerPotential(2,ColorJugador.NEGRO);
	CalculatePlayerPotential(3,ColorJugador.NEGRO);
    }
    
    public void CalculatePlayerPotential(int k,ColorJugador color){
        int max,total;
        for (int x=0; x<t.Size; x++)
            for (int y=0; y<t.Size; y++)
                t.Upd[x][y]=true;

        max=0; 
        do
        {
            max++;
            total=0;
            for (int x=0; x<t.Size; x++)
                for (int y=0; y<t.Size; y++)
                    if (t.Upd[x][y])
                        total+=SetPot(x, y, k, color);
            
            for (int x=t.Size-1; x>=0; x--)
                for (int y=t.Size-1; y>=0; y--)
                    if (t.Upd[x][y])
                        total+=SetPot(x, y, k, color);
        }
        while(total>0 && max<12);
    }
    
    public int SetPot(int x,int y,int p,ColorJugador color)
    {
        return 0;
    }
}
