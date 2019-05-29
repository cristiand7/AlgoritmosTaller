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
import java.util.Scanner;

/**
 *
 * @author USUARIO
 */
public class JugadorManual implements JugadorHex {

    @Override
    public Jugada jugar(Tablero tablero, ColorJugador color) {
        Scanner leer = new Scanner(System.in);
         System.out.println("tu respuesta (ejm 0,0) :");

        String[] respuesta = leer.nextLine().split(",");
       try{
        return new Jugada(Integer.parseInt(respuesta[0]), Integer.parseInt(respuesta[1]));           
       }catch(NumberFormatException e){
        return new Jugada(0, 0);           
       }
    }

    @Override
    public String nombreJugador() {
        return "Manual";
    }

}
