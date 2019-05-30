package co.edu.javeriana.algoritmos.proyecto.reakt;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


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
         System.out.println("tu respuesta (ejm 0,0) (c=cambio, solo en el segundo turno):");

        String[] respuesta = leer.nextLine().split(",");
       try{
           if(respuesta[0].equals("c"))
            return new Jugada(true,0,0);           
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
