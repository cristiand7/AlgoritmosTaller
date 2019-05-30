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
        boolean turno = true,suerte=true;//deberia ser un random

        TableroHex tablero = new TableroHex();
        
        JugadorHex jugador1;
        JugadorHex jugador2;
        if(suerte){
            jugador1 = new JugadorManual();
            jugador2 = new Jugador();
        }
        else{
            jugador1 = new Jugador();
            jugador2 = new JugadorManual();
        }

        ColorJugador color1 = ColorJugador.BLANCO;
        ColorJugador color2 = ColorJugador.NEGRO;
        while (true) {
            System.out.print("turno del jugador ");
            if (turno) {
                System.out.println(color1);
                Jugada j=jugador1.jugar(tablero, color1);
                
                tablero.aplicarJugada(j, color1);
                
                if(suerte){
                    tablero.imprimirTablero();
                }
            } else {
                System.out.println(color2);
                Jugada j=jugador2.jugar(tablero, color2);
                
                if(j.isCambioColores()){
                    ColorJugador temp=color2;
                    color2=color1;
                    color1=temp;
                }
                else{
                    tablero.aplicarJugada(j, color2);
                }
                
                if(!suerte){
                    tablero.imprimirTablero();
                }
            }
            turno = !turno;
            if(turno)
                System.out.println("FIN DE LA RONDA");
        }
    }

}
