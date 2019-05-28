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

/**
 *
 * @author USUARIO
 */
public class TableroHex implements Tablero {

    ColorJugador tablero[][];
    
    List<Point> Libres;
    List<Point> Blancas;
    List<Point> Negras;
    
    public TableroHex() {
        Libres = new ArrayList<>();
        Blancas = new ArrayList<>();
        Negras = new ArrayList<>();
        
        int n = 11;
        tablero = new ColorJugador[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tablero[i][j] = null;
                Libres.add(new Point(i,j));
            }
        }
        
    }

    @Override
    public void aplicarJugada(Jugada jugada, ColorJugador colorJugador) {
        int columna = jugada.getColumna();
        int fila = jugada.getFila();
        tablero[columna][fila] = colorJugador;

        imprimirTablero();
    }

    @Override
    public ColorJugador ganador() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ColorJugador casilla(int fila, int columna) {
        return tablero[fila][columna];
    }
    
    public void Actualizar(Tablero t){
        double s=System.nanoTime();
        int n =11;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if(t.casilla(i, j)!=null)
                {
                    if(tablero[i][j]==null){
                        Libres.remove(new Point(i,j));
                        if(t.casilla(i, j)==ColorJugador.BLANCO)
                            Blancas.add(new Point(i,j));
                        else
                            Negras.add(new Point(i,j));
                   } 
                }
                tablero[i][j] = t.casilla(i, j);
            }
        }
        double e=System.nanoTime();
        System.out.println("T: "+(e-s)/1000000);
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
