/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Grafo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author sala a
 */
public class Nodo {

    private String nombre;
    private int numeroNodo;
    private boolean marca = false;
    private Object objeto;

    private List<Nodo> menorDistancia = new LinkedList<>();

    private Double distancia = Double.MAX_VALUE;

    List<Nodo> nodos = new ArrayList<>();

    public Nodo(String nombre) {
        this.nombre = nombre;
        this.numeroNodo = -1;
    }

    public Nodo(String nombre, int numeroNodo) {
        this.nombre = nombre;
        this.numeroNodo = numeroNodo;
    }

    public void addConexion(Nodo destino, Object objeto) {
        this.setObjeto(objeto);
        this.getNodos().add(destino);
    }

    public int existConexxion(String destino) {
        for (int i = 0; i < nodos.size(); i++) {
            Nodo n;
            n = nodos.get(i);
            if (n.getNombre().equals(destino)) {
                return i;
            }
        }
        return -1;
    }

    public void addConexionRestriccion(Nodo nodo2) {
        this.getNodos().add(nodo2);
    }

    public Nodo() {
        super();
        this.setNodos(new ArrayList<>());
    }

    public boolean equals(Nodo n) {
        return nombre.equals(n.getNombre());
    }

    public boolean isMarca() {
        return marca;
    }

    public void setMarca(boolean marca) {
        this.marca = marca;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumeroNodo() {
        return numeroNodo;
    }

    public void setNumeroNodo(int numeroNodo) {
        this.numeroNodo = numeroNodo;
    }

    public Object getObjeto() {
        return objeto;
    }

    public void setObjeto(Object objeto) {
        this.objeto = objeto;
    }

    public List<Nodo> getMenorDistancia() {
        return menorDistancia;
    }

    public void setMenorDistancia(List<Nodo> menorDistancia) {
        this.menorDistancia = menorDistancia;
    }

    public Double getDistancia() {
        return distancia;
    }

    public void setDistancia(double d) {
        this.distancia = d;
    }

    public List<Nodo> getNodos() {
        return nodos;
    }

    public void setNodos(List<Nodo> nodos) {
        this.nodos = nodos;
    }

}
