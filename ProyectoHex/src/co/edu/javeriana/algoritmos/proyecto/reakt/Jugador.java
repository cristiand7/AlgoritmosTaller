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
            t.imprimirTablero();
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
        colorJugador = color;
        
        //Actualizar lista de casillas libres
        t.Actualizar(tablero);

        //Escoger la unica casilla libre por si acaso
        mejorJugada = newJugada(t.Libres.get(0));
        mejorJugada
            = (t.turno==1)? FirstMove()
            : (t.turno==2)? SecondMove()
            : BestMove();

        System.out.println(" >Yo juego en [x="+ mejorJugada.getFila()+ ",y="+mejorJugada.getColumna()+"] "+(mejorJugada.isCambioColores()?"cambiando el color":""));
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

    //FUNCIÓN HECHA POR DANIELA PARA CALCULAR EL MOVIMIENTO GANADOR
    public Jugada BestMove() {
        CalculatePotential();
        Jugada jugada;
        int colorBlanco = -1, colorNegro = 1, color;
        if (colorJugador == ColorJugador.BLANCO) {
            color = -1;
        } else {
            color = 1;
        }
        int conexion, x_m = 0, y_m = 0, aux3 = 0, aux4 = 0;
        double mmp, mm = 20000;
        double[] vv;
        vv = new double[100];
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if (t.casilla(i, j) != null) {
                    aux3 += 2 * i + 1 - 11;
                    aux4 += 2 * j + 1 - 11;
                }
            }
        }
        aux3 = aux3 / aux3;
        aux4 = aux4 / aux4;
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if (t.casilla(i, j) == null) {
                    mmp = Math.random() * (49 - 3 * 16);
                    mmp += (Math.abs(i - 5) + Math.abs(j - 5)) * 190;
                    mmp += 8 * (aux3 * (i - 5) + aux4 * (j - 5)) / (t.turno + 1);
                    for (int k = 0; k < 4; k++) 
                    {
                        mmp-=t.Bridge[i][j][k];
                        int pp0=t.Pot[i][j][0]+t.Pot[i][j][1];
                        int pp1=t.Pot[i][j][2]+t.Pot[i][j][3];
                        mmp+=pp0+pp1;
                        if ((pp0<=268)||(pp1<=268))
                            mmp-=400; //140+128
                        vv[i * 11 + j] = mmp;
                    }
                    if (mmp < mm) {
                        mm = mmp;
                        x_m = i;
                        y_m = j;
                    }
                }
            }
        }

        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if (color < 0) {//blanco
                    if (i > 3 && i < (11 - 1) && j > 0 && j < 3) {

                        if (t.casilla(i - 1, j + 2) != null && t.casilla(i - 1, j + 2) != colorJugador) {
                            conexion = CanConnectFarBorder(i - 1, j + 2, 0, t, colorJugador);
                            if (conexion < 2) {
                                x_m = i;
                                if (conexion < -1) {
                                    x_m--;
                                    conexion++;
                                }
                                j = j - conexion;
                                mm = vv[i * 11 + j];
                            }
                        }
                    }
                    if (i > 0 && i < (11 - 1) && j == 0) {
                        if ((t.casilla(i - 1, j + 2) != colorJugador) && (t.casilla(i - 1, j) == null) && (t.casilla(i - 1, j + 1) == null) && (t.casilla(i, j + 1) == null) && (t.casilla(i + 1, j) == null)) {
                            x_m = i;
                            y_m = j;
                            mm = vv[i * 11 + j];
                        }
                    }
                    if ((i > 0) && (i < 11 - 4) && (j < 11 - 1) && (j > 11 - 4)) {
                        if (t.casilla(i + 1, j - 2) != null && t.casilla(i + 1, j - 2) != colorJugador) {
                            conexion = CanConnectFarBorder(i - 1, j - 2, 0, t, colorJugador);
                            if (conexion < 2) {
                                x_m = i;
                                if (conexion < -1) {
                                    y_m++;
                                    conexion++;
                                }
                                y_m = j + conexion;
                                mm = vv[i * 11 + j];
                            }
                        }
                    }
                    if ((i > 0) && (i < 11 - 1) && (j == 11 - 1)) {
                        if ((t.casilla(i + 1, j - 2) != colorJugador) && (t.casilla(i + 1, j) == null) && (t.casilla(i + 1, j - 1) == null) && (t.casilla(i, j - 1) == null) && (t.casilla(i - 1, j) == null)) {
                            x_m = i;
                            y_m = j;
                            mm = vv[i * 11 + j];
                        }
                    }
                } else {

                    if ((j > 3) && (j < 11 - 1) && (i > 0) && (i < 3)) {
                        if (t.casilla(i + 2, j - 1) != null && t.casilla(i + 2, j - 1) != colorJugador) {
                            conexion = CanConnectFarBorder(i + 2, j - 1, 0, t, colorJugador);
                            if (conexion < 2) {
                                y_m = j;
                                if (conexion < -1) {
                                    y_m--;
                                    conexion++;
                                }
                                x_m = i - conexion;
                                mm = vv[i * 11 + j];
                            }
                        }
                    }
                    if ((j > 0) && (j < 11 - 1) && (i == 0)) {
                        if ((t.casilla(i + 2, j - 1) != colorJugador) && (t.casilla(i, j - 1) == null) && (t.casilla(i + 1, j - 1) == null) && (t.casilla(i + 1, j) == null) && (t.casilla(i, j + 1) == null)) {
                            {
                                x_m = i;
                                y_m = j;
                                mm = vv[i * 11 + j];
                            }
                        }
                    }
                    if ((j > 0) && (j < 11 - 4) && (i < 11 - 1) && (i > 11 - 4)) {
                        if (t.casilla(i - 2, j + 1) != null && t.casilla(i - 2, j + 1) != colorJugador) {
                            conexion = CanConnectFarBorder(i - 2, j + 1, 0, t, colorJugador);
                            if (conexion < 2) {
                                y_m = j;
                                if (conexion < -1) {
                                    y_m++;
                                    conexion++;
                                }
                                x_m = i + conexion;
                                mm = vv[i * 11 + j];
                            }
                        }
                    }
                    if ((j > 0) && (j < 11 - 1) && (i == 11 - 1)) {
                        if ((t.casilla(i - 2, j + 1) != colorJugador) && (t.casilla(i, j + 1) == null) && (t.casilla(i - 1, j + 1) == null) && (t.casilla(i - 1, j) == null) && (t.casilla(i, j - 1) == null)) {
                            x_m = i;
                            y_m = j;
                            mm = vv[i * 11 + j];
                        }
                    }

                }

            }
        }
        return new Jugada(x_m, y_m);
    }

    public int CanConnectFarBorder(int nn, int mm, int cc, Tablero tablero, ColorJugador micolor) {
        int ii, jj;
        int Size = 11;
        if (cc > 0) //blue
        {
            if (2 * mm < Size - 1) {
                for (ii = 0; ii < Size; ii++) {
                    for (jj = 0; jj < mm; jj++) {
                        if ((jj - ii < mm - nn) && (ii + jj <= nn + mm) && (tablero.casilla(ii, jj) != micolor)) {
                            return 2;
                        }
                    }
                }
                if (tablero.casilla(nn - 1, mm) != micolor) {
                    return 0;
                }
                if (tablero.casilla(nn - 1, mm - 1) != micolor) {
                    if (tablero.casilla(nn + 2, mm - 1) != micolor) {
                        return 0;
                    }
                    return -1;
                }
                if (tablero.casilla(nn + 2, mm - 1) != micolor) {
                    return (-2);
                }
            } else {
                for (ii = 0; ii < Size; ii++) {
                    for (jj = Size - 1; jj > mm; jj--) {
                        if ((jj - ii > mm - nn) && (ii + jj >= nn + mm) && (tablero.casilla(ii, jj) != null)) {
                            return (2);
                        }
                    }
                }
                if (tablero.casilla(nn + 1, mm) != micolor) {
                    return (0);
                }
                if (tablero.casilla(nn + 1, mm + 1) != micolor) {
                    if (tablero.casilla(nn - 2, mm + 1) != micolor) {
                        return (0);
                    }
                    return (-1);
                }
                if (tablero.casilla(nn - 2, mm + 1) != micolor) {
                    return (-2);
                }
            }
        } else {
            if (2 * nn < Size - 1) {
                for (jj = 0; jj < Size; jj++) {
                    for (ii = 0; ii < nn; ii++) {
                        if ((ii - jj < nn - mm) && (ii + jj <= nn + mm) && (tablero.casilla(ii, jj) != null)) {
                            return (2);
                        }
                    }
                }
                if (tablero.casilla(nn, mm - 1) != micolor) {
                    return (0);
                }
                if (tablero.casilla(nn - 1, mm - 1) != micolor) {
                    if (tablero.casilla(nn - 1, mm + 2) != micolor) {
                        return (0);
                    }
                    return (-1);
                }
                if (tablero.casilla(nn - 1, mm + 2) != micolor) {
                    return (-2);
                }
            } else {
                for (jj = 0; jj < Size; jj++) {
                    for (ii = Size - 1; ii > nn; ii--) {
                        if ((ii - jj > nn - mm) && (ii + jj >= nn + mm) && (tablero.casilla(ii, jj) != null)) {
                            return (2);
                        }
                    }
                }
                if (tablero.casilla(nn, mm + 1) != micolor) {
                    return (0);
                }
                if (tablero.casilla(nn + 1, mm + 1) != micolor) {
                    if (tablero.casilla(nn + 1, mm - 2) != micolor) {
                        return (0);
                    }
                    return (-1);
                }
                if (tablero.casilla(nn + 1, mm - 2) != micolor) {
                    return (-2);
                }
            }
        }
        return (1);
    }
}
