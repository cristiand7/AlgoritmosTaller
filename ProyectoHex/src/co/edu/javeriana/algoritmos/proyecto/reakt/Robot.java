/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.javeriana.algoritmos.proyecto.reakt;

import co.edu.javeriana.algoritmos.proyecto.ColorJugador;
import co.edu.javeriana.algoritmos.proyecto.Jugada;
import co.edu.javeriana.algoritmos.proyecto.JugadorHex;
import java.util.concurrent.TimeUnit;
/**
 *
 * @author USUARIO
 */
public class Robot {

    public static void main(String args[]) throws InterruptedException {        
        
        System.out.println("Test hex");
        boolean turno = false;

        TableroHex tablero = new TableroHex();

        JugadorHex jugador1 = new JugadorManual();//jugador a programar *****
        JugadorHex jugador2 = new Jugador();

        while (true) {
            System.out.print("turno del jugador ");
            if (turno) {
                System.out.println(" Blanco");
                Jugada j=jugador1.jugar(tablero, ColorJugador.BLANCO);
                tablero.aplicarJugada(j, ColorJugador.BLANCO);
                
                System.out.println(" ");
                tablero.imprimirTablero();
            } else {
                System.out.println(" Negro");
                TimeUnit.SECONDS.sleep(1);
                Jugada j=jugador2.jugar(tablero, ColorJugador.NEGRO);
                tablero.aplicarJugada(j, ColorJugador.NEGRO);
            }
            turno = !turno;
            
            System.out.println("FIN DE LA RONDA");
        }
    }

}
