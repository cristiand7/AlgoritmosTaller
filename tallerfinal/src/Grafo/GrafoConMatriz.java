/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Grafo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 *
 * @author sala a
 */
public class GrafoConMatriz extends Grafo {

    public static int INFINITO = 0xFFFF;
    private int[][] matrizPeso;
    private List<Nodo> nodos = new ArrayList<>();
    private int numeroNodos;

    public GrafoConMatriz(int max) {
        matrizPeso = new int[max][max];
        for (int i = 0; i < max; i++) {
            for (int j = 0; j < max; j++) {
                matrizPeso[i][j] = INFINITO;
            }
        }
        numeroNodos = 0;
    }

    public Nodo buscarNodo(int a) {
        for (Nodo nodo : nodos) {
            if (nodo.getNumeroNodo() == a) {

                return nodo;
            }
        }
        return null;
    }

    public GrafoConMatriz() {
        int max = 30;
        matrizPeso = new int[max][max];
        for (int i = 0; i < max; i++) {
            for (int j = 0; j < max; j++) {
                matrizPeso[i][j] = INFINITO;
            }
        }
        numeroNodos = 0;
    }

    public void addNodo(Nodo nodoA) {
        nodos.add(nodoA);
    }

    public void nuevoVertice(String nombre) {
        boolean esta = numVertice(nombre) >= 0;
        if (!esta) {
            Nodo v = new Nodo(nombre);
            v.setNumeroNodo(numeroNodos);
            nodos.add(v);
            numeroNodos++;
        }
    }

    
    public int numVertice(String nombre) {
        Nodo v = new Nodo(nombre);
        boolean encontrado = false;
        int i = 0;
        for (; (i < numeroNodos) && !encontrado;) {
            encontrado = nodos.get(i).equals(v);
            if (!encontrado) {
                i++;
            }
        }
        return (i < numeroNodos) ? i : -1;
    }

    public int pesoArco(String a, String b) {
        int va, vb;
        va = numVertice(a);
        vb = numVertice(b);
        return matrizPeso[va][vb];
    }

    public int nuevoArco(String a, String b, int peso) {
        int va, vb;
        va = numVertice(a);
        vb = numVertice(b);
        return matrizPeso[va][vb] = peso;
    }

    public static int getINFINITO() {
        return INFINITO;
    }

    public static void setINFINITO(int INFINITO) {
        GrafoConMatriz.INFINITO = INFINITO;
    }

    public int[][] getMatrizPeso() {
        return matrizPeso;
    }

    public void setMatrizPeso(int[][] matrizPeso) {
        this.matrizPeso = matrizPeso;
    }

    public List<Nodo> getNodos() {
        return nodos;
    }

    public void setNodos(List<Nodo> nodos) {
        this.nodos = nodos;
    }

    public int getNumeroNodos() {
        return numeroNodos;
    }

    public void setNumeroNodos(int numeroNodos) {
        this.numeroNodos = numeroNodos;
    }

    public Nodo obtenerNodo(String string, GrafoConMatriz graph) {
        for (Nodo node : graph.getNodos()) {
            if (node.getNombre().equals(string)) {
                return node;
            }
        }
        return null;
    }

 

    /**
     * Algoritmo de dijkstra
     *
     * @param graph
     * @param inicial
     * @return
     */
    public void  dijkstra( Nodo inicial) {
        inicial.setDistancia(0);

        List<Nodo> nodosAgregados = new ArrayList<>();
        List<Nodo> nodosNoAgregados = new ArrayList<>();

        nodosNoAgregados.add(inicial);

        while (nodosNoAgregados.size() != 0) {
            Nodo NodoActual = getMenorDistanciaNodo(nodosNoAgregados);
            nodosNoAgregados.remove(NodoActual);
            for (Nodo conexionActual : NodoActual.getNodos()) {
                Double conexionPeso = Double.parseDouble((String) conexionActual.getObjeto());
                if (!nodosAgregados.contains(conexionActual)) {
                    MinimaDistancia(conexionActual, conexionPeso, NodoActual);
                    if (!nodosNoAgregados.contains(conexionActual)) {
                        nodosNoAgregados.add(conexionActual);
                    }
                }
            }
            nodosAgregados.add(NodoActual);
            System.out.println("Nombre " + NodoActual.getNombre());
        }
        
    }

    private static Nodo getMenorDistanciaNodo(List<Nodo> nodosNoAgregados) {
        Nodo menorDistanciaNodo = null;
        Double menorDistancia = Double.MAX_VALUE;
        for (Nodo nodo : nodosNoAgregados) {
            Double distanciaNodo = nodo.getDistancia();
            if (distanciaNodo < menorDistancia) {
                menorDistancia = distanciaNodo;
                menorDistanciaNodo = nodo;
            }
        }
        return menorDistanciaNodo;
    }

    private static void MinimaDistancia(Nodo nodoEvaluar, Double peso, Nodo nodoActual) {
        Double nodoActualDistancia = nodoActual.getDistancia();
        if (nodoActualDistancia + peso < nodoEvaluar.getDistancia()) {
            nodoEvaluar.setDistancia(nodoActualDistancia + peso);
            List<Nodo> menorDistancia = nodoActual.getMenorDistancia();
            menorDistancia.add(nodoActual);
            nodoEvaluar.setMenorDistancia(menorDistancia);
        }
    }


    public void colaPrioridad() throws IOException {
        int n;

        String cadena;
        FileReader file = new FileReader("colaPrioridad.txt");
        BufferedReader buffer = new BufferedReader(file);

        cadena = buffer.readLine();

        n = Integer.parseInt(cadena);

        GrafoConMatriz gra = new GrafoConMatriz(n);
        cadena = buffer.readLine();

        while (!(cadena = buffer.readLine()).equals("ARCOS")) {
            gra.nuevoVertice(cadena.trim());
        }
        while ((cadena = buffer.readLine()) != null) {
            String[] result = cadena.split(",");
            gra.nuevoArco(result[0], result[1], Integer.parseInt(result[2]));
        }
        buffer.close();

        System.out.println("Cola de prioridad usado en Dijkstra: ");

        for (int nodo = 0; nodo < n; nodo++) {
            colaPrioridadDijkstra(gra, n, nodo);
        }

    }

    public void colaPrioridadDijkstra(GrafoConMatriz gra, int V, int inicio) {
        int[] distancia = new int[V];
        int[] padre = new int[V];
        boolean[] visto = new boolean[V];

        for (int i = 0; i < V; i++) {
            distancia[i] = 1200000000;
            padre[i] = -1;
            visto[i] = false;
        }

        distancia[inicio] = 0;
        PriorityQueue<Integer> pila = new PriorityQueue<>();
        pila.clear();
        pila.add(inicio);

        while (!pila.isEmpty()) {

            int u = pila.poll();
            visto[u] = true;

            for (int i = 0; i < V; i++) {

                if (gra.getMatrizPeso()[u][i] != 0) {

                    if (distancia[i] > distancia[u] + gra.getMatrizPeso()[u][i]) {

                        distancia[i] = distancia[u] + gra.getMatrizPeso()[u][i];
                        padre[i] = u;

                        pila.add(i);
                    }
                }
            }
        }
        System.out.println("DESDE EL NODO : " + inicio);
        for (int x = 0; x < V; x++) {
            System.out.println("AL NODO " + x + " : " + distancia[x]);
        }
        System.out.println();
    }

  
}


