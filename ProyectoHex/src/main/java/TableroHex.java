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

    int tablero[][];

    public TableroHex() {
        int n = 11;
        tablero = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tablero[i][j] = -1;
            }
        }

    }

    @Override
    public void aplicarJugada(Jugada jugada, ColorJugador colorJugador) {
        int columna = jugada.getColumna();
        int fila = jugada.getFila();
        tablero[columna][fila] = (colorJugador == ColorJugador.NEGRO) ? 0 : 1;

        imprimirTablero();
    }

    @Override
    public ColorJugador ganador() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ColorJugador casilla(int fila, int columna) {
        
        if (tablero[fila][columna] == 0) {
            return ColorJugador.BLANCO;
        }
        if (tablero[fila][columna] == 1) {
            return ColorJugador.BLANCO;
        }
        return null;
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
            for (int j = 0; j < n; j++) {
                if (j==0){
                    String v=(i>=9) ? "" : " ";
                    System.out.print((i+1)+ v+"|");
                }
                if (tablero[i][j] == -1) {
                    System.out.print(" -");
                }
                if (tablero[i][j] == 0) {
                    System.out.print(" O");
                }
                if (tablero[i][j] == 1) {
                    System.out.print(" X");
                }
            }
            System.out.print("\n");
        }
        System.out.println("========================================");

    }
}
