/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java;

import co.edu.javeriana.algoritmos.proyecto.ColorJugador;
import co.edu.javeriana.algoritmos.proyecto.Jugada;
import co.edu.javeriana.algoritmos.proyecto.Tablero;
import java.util.ArrayList;
import java.util.List;
import java.awt.Point;
import java.util.Iterator;
import java.util.stream.Collectors;

/**
 *
 * @author USUARIO
 */
public class TableroHex implements Tablero {
    ColorJugador tablero[][];
    public boolean interno;
    
    public int Pot[][][];
    public int Bridge[][][];
    public boolean Upd[][];
    
    List<Point> Libres;
    List<Point> Blancas;
    List<Point> Negras;
    
    public int Size = 11;
    int turno;
    
    public TableroHex(boolean i) {
        this();
        interno = i;
    }
    public TableroHex() {
        tablero = new ColorJugador[Size][Size];
        Pot = new int[Size][Size][4];
        Bridge = new int[Size][Size][4];
        Upd = new boolean[Size][Size];
        
        Libres = new ArrayList<>();
        Blancas = new ArrayList<>();
        Negras = new ArrayList<>();
        
        turno = 1;

        for (int i = 0; i < Size; i++) {
            for (int j = 0; j < Size; j++) {
                Libres.add(new Point(i,j));
            }
        }
        
    }
    @Override
    public ColorJugador ganador() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ColorJugador casilla(int fila, int columna) {
        return tablero[fila][columna];
    }
    
    @Override
    public void aplicarJugada(Jugada jugada, ColorJugador colorJugador) {
        int columna = jugada.getColumna();
        int fila = jugada.getFila();
        aplicarJugada(new Point(fila, columna), colorJugador);
        Libres.remove(new Point(fila,columna));
        
        turno++;
    }
    
    public ColorJugador casilla(Point p) {
        return tablero[p.x][p.y];
    }
    
    private boolean OutOfBounds(int x, int y){
	return x<0 || y<0 || x>=Size || y>=Size;
    }
    
    public void SetUpd(int x,int y)
    {
        if (OutOfBounds(x,y))
            return;
        Upd[x][y]=true;
    }
    
    public ColorJugador GetCell(int x,int y)
    {
        if (x<0)     return ColorJugador.NEGRO;
        if (y<0)     return ColorJugador.BLANCO;
        if (x>=Size) return ColorJugador.NEGRO;
        if (y>=Size) return ColorJugador.BLANCO;
        return tablero[x][y];
    }
    
    public void setCasilla(Point p, ColorJugador color) {
        tablero[p.x][p.y] = color;
    }
    
    public int GetPot(int x,int y,int p, ColorJugador color)
    {
        if (OutOfBounds(x,y))
            return 30000;

        if (tablero[x][y]==null)
            return Pot[x][y][p];
        if (tablero[x][y]==color)
            return Pot[x][y][p]-30000;
        return 30000;
    }

    public List<Point> getLibres() {
        return Libres;
    }

    public List<Point> getBlancas() {
        return Blancas;
    }

    public List<Point> getNegras() {
        return Negras;
    }
    public void Actualizar(Tablero t){
        for (Iterator<Point> iterator = Libres.iterator(); iterator.hasNext(); ) {
            Point p = iterator.next();
            if(t.casilla(p.x,p.y)!=null)
            {
                if(interno)System.out.println(" >Enemigo jugo en "+ p.toString() + " turno: " +(turno));
                aplicarJugada(p, t.casilla(p.x,p.y),iterator);
                turno++;
                break;
            }
        }
    }
    
    private void aplicarJugada(Point p, ColorJugador colorJugador) {
        setCasilla(p, colorJugador);
        ((colorJugador==ColorJugador.BLANCO)?Blancas:Negras).add(p);
        ((colorJugador!=ColorJugador.BLANCO)?Blancas:Negras).remove(p);
        if(interno)System.out.println(" > Aplicar Jugada ["+p.getX()+","+p.getY()+"]");
    }
    private void aplicarJugada(Point p, ColorJugador colorJugador, Iterator<Point> iterator) {
        aplicarJugada(p, colorJugador);
        iterator.remove();
    }    
    public void tiempo(){
        double s=System.nanoTime();
        double e=System.nanoTime();
        System.out.println("T: "+(e-s)/1000000);
    }
    
    /**
     * imprimesion del tablero para ver movimientos
     * Negro es O
     * Blanco es X
     * Nada es -
     */

    public void imprimirTablero() {
        int n = 11;
        
        System.out.println("  | ____________________: "+(turno-1));
        System.out.println("  | A B C D E F G H I J K    ");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                System.out.print(" ");
            }
            for (int j = 0; j < n; j++) {
                if (j==0){
                    String v=(i>=9) ? "" : " ";
                    System.out.print((i+1)+ v+"|");
                }
                if (tablero[i][j] == null) {
                    System.out.print(" -");
                }
                if (tablero[i][j] == ColorJugador.NEGRO) {
                    System.out.print(" O");
                }
                if (tablero[i][j] == ColorJugador.BLANCO) {
                    System.out.print(" X");
                }
            }
            System.out.print("\n");
        }
        System.out.println("========================================");
        String resultL = Libres.stream().map(p->"("+p.x+","+p.y+")").collect(Collectors.joining(","));
        System.out.println("Libres: "+resultL);
        String resultB = Blancas.stream().map(p->"("+p.x+","+p.y+")").collect(Collectors.joining(","));
        System.out.println("Blancas: "+resultB);
        String resultN = Negras.stream().map(p->"("+p.x+","+p.y+")").collect(Collectors.joining(","));
        System.out.println("Negras: "+resultN);
    }
}
