/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java;

import co.edu.javeriana.algoritmos.proyecto.ColorJugador;
import co.edu.javeriana.algoritmos.proyecto.Jugada;
import co.edu.javeriana.algoritmos.proyecto.Tablero;

/**
 *
 * @author USUARIO
 */
public class TableroHex implements Tablero {

    ColorJugador tablero[][];

    public TableroHex() {
        int n = 11;
        tablero = new ColorJugador[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tablero[i][j] = null;
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
    
    public void setCasilla(int fila, int columna,ColorJugador color) {
        tablero[fila][columna]=color;
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
