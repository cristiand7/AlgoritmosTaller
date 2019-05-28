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

/**
 *
 * @author USUARIO
 */
public class TableroHex implements Tablero {
    ColorJugador tablero[][];
    
    List<Point> Libres;
    List<Point> Blancas;
    List<Point> Negras;
    
    int turno;
    
    public TableroHex() {
        int n = 11;
        tablero = new ColorJugador[n][n];
        
        Libres = new ArrayList<>();
        Blancas = new ArrayList<>();
        Negras = new ArrayList<>();
        
        turno = 1;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tablero[i][j] = null;
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
        imprimirTablero();
    }
    
    public ColorJugador casilla(Point p) {
        return tablero[p.x][p.y];
    }
    
    public void setCasilla(Point p, ColorJugador color) {
        tablero[p.x][p.y] = color;
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
                aplicarJugada(p, t.casilla(p.x,p.y),iterator);
                turno++;
                break;
            }
        }
    }
    
    private void aplicarJugada(Point p, ColorJugador colorJugador) {
        setCasilla(p, colorJugador);
        ((colorJugador==ColorJugador.BLANCO)?Blancas:Negras).add(p);   
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
        
        System.out.println("  | _______________________");
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

    }
}
