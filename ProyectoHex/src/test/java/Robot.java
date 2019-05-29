/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.java;

import co.edu.javeriana.algoritmos.proyecto.ColorJugador;
import co.edu.javeriana.algoritmos.proyecto.Jugada;
import co.edu.javeriana.algoritmos.proyecto.JugadorHex;
import co.edu.javeriana.algoritmos.proyecto.Tablero;
import main.java.Jugador;
import main.java.JugadorManual;
import main.java.TableroHex;

/**
 *
 * @author USUARIO
 */
public class Robot {

    public static void main(String args[]) {        
        JugadorHex jugador = new Jugador();
        System.out.println(jugador.jugar(null, null));
        
        System.out.println("Test hex");
        boolean turno = false;
        int aleatorio = (int) (Math.random() * 100) + 1;
        if (aleatorio % 2 == 0) {
            turno = true;
        }

        JugadorHex jugador1 = new JugadorManual();
        JugadorHex jugador2 = new Jugador();//jugador a programar *****

        Tablero tablero = new TableroHex();
        while (true) {
            System.out.print("turno del jugador ");
            if (turno) {
                System.out.println(" Blanco");
            } else {
                System.out.println(" Negro");
            }
            if (turno) {
                Jugada j=jugador1.jugar(tablero, ColorJugador.BLANCO);
                tablero.aplicarJugada(j, ColorJugador.BLANCO);
            } else {
                Jugada j=jugador2.jugar(tablero, ColorJugador.NEGRO);
                tablero.aplicarJugada(j, ColorJugador.NEGRO);
            }
            turno = !turno;
            
        }
    }

}
