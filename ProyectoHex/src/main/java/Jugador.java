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

/**
 *
 * @author Reakt
 */
public class Jugador implements JugadorHex{

    @Override
    public Jugada jugar(Tablero tablero, ColorJugador color) {
        return null;
    }

    @Override
    public String nombreJugador() {
        return "Automatico";
    }
    
}
