/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Grafo.Desigualdad;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Grafo.GrafoConMatriz;
import Grafo.GrafoListAristas;
import Grafo.GrafoListVertices;
import Grafo.Nodo;

/**
 *
 * @author sala a
 */
public class main {

    /**
     * @param args the command line arguments
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        String algoritmo = "";
        while (!algoritmo.trim().equals("0")) {
            System.out.println("----�CU�L ALGORITMO DESEA REALIZAR?----");
            System.out.println("1. Dijkstra");
            System.out.println("2. Produndidad");
            System.out.println("3. Prim");
            System.out.println("4. Anchura");
            System.out.println("5. Restricciones");
            System.out.println("6. Cola de prioridad usado en Dijkstra");
            System.out.println("7. Warshall");
            System.out.println("8. Problema Burro-Paja-Lobo");
            System.out.println("9. Cambio de moneda");

            System.out.println("0. Salir");
            System.out.println();
            GrafoConMatriz grafoConMatriz = new GrafoConMatriz();
            Scanner teclado = new Scanner(System.in);
            algoritmo = teclado.nextLine();
            if (algoritmo.trim().equals("1")) {
                String cadena;
                FileReader file = new FileReader("dijkstra.txt");
                BufferedReader buffer = new BufferedReader(file);

                cadena = buffer.readLine();
                while (!(cadena = buffer.readLine()).equals("ARCOS")) {
                    grafoConMatriz.addNodo(new Nodo(cadena));
                }
                while (!(cadena = buffer.readLine()).equals("COMIENZA")) {
                    String[] result = cadena.split(",");
                    Nodo nodo1 = grafoConMatriz.obtenerNodo(result[0], grafoConMatriz);
                    Nodo nodo2 = grafoConMatriz.obtenerNodo(result[1], grafoConMatriz);
                    nodo1.addConexion(nodo2, (Object) result[2]);
                }

                 grafoConMatriz.dijkstra( grafoConMatriz.obtenerNodo(cadena = buffer.readLine(), grafoConMatriz));

                buffer.close();
              
               
            }
            if (algoritmo.trim().equals("2")) {
                String cadena;
                FileReader file = new FileReader("profundidad.txt");
                BufferedReader buffer = new BufferedReader(file);

                cadena = buffer.readLine();

                while (!(cadena = buffer.readLine()).equals("ARCOS")) {
                    grafoConMatriz.addNodo(new Nodo(cadena));
                }
                while (!(cadena = buffer.readLine()).equals("COMIENZA")) {
                    String[] result = cadena.split(",");
                    Nodo nodo1 = grafoConMatriz.obtenerNodo(result[0], grafoConMatriz);
                    Nodo nodo2 = grafoConMatriz.obtenerNodo(result[1], grafoConMatriz);
                    nodo1.addConexion(nodo2, (Object) result[2]);
                }

                List<Nodo> profundidad = new ArrayList<>();

                grafoConMatriz.recorridoGrafoProfundidad(grafoConMatriz.obtenerNodo(cadena = buffer.readLine(), grafoConMatriz), profundidad);
                buffer.close();

                for (int i = 0; i < profundidad.size(); i++) {
                    System.out.println(profundidad.get(i).getNombre());
                }

            }
            if (algoritmo.trim().equals("3")) {
                
                new GrafoListAristas().prim(); 
            }
            if (algoritmo.trim().equals("4")) {

                System.out.println("El recorrido por anchura (comenzando desde el v�rtice 2) es: ");

                new GrafoListVertices().BFS(2);
            }
            if (algoritmo.trim().equals("5")) {
                restricciones();
            }
            if (algoritmo.trim().equals("6")) {
                grafoConMatriz.colaPrioridad();
            }
            if (algoritmo.trim().equals("7")) {
                
                new GrafoListVertices().floydWarshall();
            }
            if (algoritmo.trim().equals("8")) {
                GrafoConMatriz graph = new GrafoConMatriz();

                String cadena;
                FileReader file = new FileReader("BurroPajaLobo.txt");
                BufferedReader buffer = new BufferedReader(file);
                cadena = buffer.readLine();
                while (!(cadena = buffer.readLine()).equals("ARCOS")) {
                    String[] result = cadena.split("-");
                    // System.out.println("Nodos: "+result[0]);
                    Nodo aux = new Nodo(result[0], Integer.parseInt(result[1]));
                    graph.addNodo(aux);

                }

                while (!(cadena = buffer.readLine()).equals("FIN")) {
                    String[] result = cadena.split("-");
                    // System.out.println("Arcos: "+result[0]+"-"+result[1]);
                    graph.buscarNodo(Integer.parseInt(result[0])).addConexion(graph.buscarNodo(Integer.parseInt(result[1])), 1.0);

                }

                
                camino(graph);
                
            }
            if (algoritmo.trim().equals("9")) {
            	 String cadena;
                 FileReader file = new FileReader("cambioDeMoneda.txt");
                 BufferedReader buffer = new BufferedReader(file);

                 cadena = buffer.readLine();
                 while (!(cadena = buffer.readLine()).equals("ARCOS")) {
                     grafoConMatriz.addNodo(new Nodo(cadena));
                 }
                 while (!(cadena = buffer.readLine()).equals("COMIENZA")) {
                     String[] result = cadena.split(",");
                     Nodo nodo1 = grafoConMatriz.obtenerNodo(result[0], grafoConMatriz);
                     Nodo nodo2 = grafoConMatriz.obtenerNodo(result[1], grafoConMatriz);
                     nodo1.addConexion(nodo2, (Object) result[2]);
                 }

                 dijkstraCambioDeMoneda(grafoConMatriz, grafoConMatriz.obtenerNodo(cadena = buffer.readLine(), grafoConMatriz));

                 buffer.close();
            }

            System.out.println();
        }
    }

    
    
    /**
     * Algoritmo de dijkstra para el problema de cambio de moneda
     *
     * @param graph
     * @param inicial
     * @return
     */
    public static void dijkstraCambioDeMoneda(GrafoConMatriz graph, Nodo inicial) {
        inicial.setDistancia(0);

        List<Nodo> nodosAgregados = new ArrayList<>();
        List<Nodo> nodosNoAgregados = new ArrayList<>();

        nodosNoAgregados.add(inicial);

        while (nodosNoAgregados.size() != 0) {
            Nodo NodoActual = getMenorDistanciaNodoCambioDeMoneda(nodosNoAgregados);
            nodosNoAgregados.remove(NodoActual);
            for (Nodo conexionActual : NodoActual.getNodos()) {
                Double conexionPeso = Double.parseDouble((String) conexionActual.getObjeto());
                if (!nodosAgregados.contains(conexionActual)) {
                    MinimaDistanciaCambioDeMoneda(conexionActual, conexionPeso, NodoActual);
                    if (!nodosNoAgregados.contains(conexionActual)) {
                        nodosNoAgregados.add(conexionActual);
                    }
                }
            }
            nodosAgregados.add(NodoActual);
            System.out.println("Nombre " + NodoActual.getNombre());
        }
        
    }

    private static Nodo getMenorDistanciaNodoCambioDeMoneda(List<Nodo> nodosNoAgregados) {
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

    private static void MinimaDistanciaCambioDeMoneda(Nodo nodoEvaluar, Double peso, Nodo nodoActual) {
        Double nodoActualDistancia = nodoActual.getDistancia();
        if (nodoActualDistancia + peso < nodoEvaluar.getDistancia()) {
            nodoEvaluar.setDistancia(nodoActualDistancia + peso);
            List<Nodo> menorDistancia = nodoActual.getMenorDistancia();
            menorDistancia.add(nodoActual);
            nodoEvaluar.setMenorDistancia(menorDistancia);
        }
    }
    
    /**
     *  Algoritmo Tigre, burro y paja. 
     * @param graph 
     */
    private static void camino(GrafoConMatriz graph) {
        
        Nodo inicio = graph.buscarNodo(0);

        List<Integer> ruta = new ArrayList<Integer>();
        List<Integer> prioridad = new ArrayList<Integer>();

        ruta.add(inicio.getNumeroNodo());
        prioridad.add(inicio.getNumeroNodo());
        inicio.setMarca(true);

        while (prioridad.get(0) != 14) {

            if (prioridad.size() > 1) {
                ruta.add(prioridad.get(0));
            }

            for (Nodo e : (graph.buscarNodo(prioridad.get(0))).getNodos()) {
                if (!e.isMarca()) {
                    //System.out.println(""+e.getNumeroNodo());
                    prioridad.add(1, e.getNumeroNodo());
                    graph.buscarNodo(e.getNumeroNodo()).setMarca(true);
                }

            }

            if ((graph.buscarNodo(prioridad.get(0))).getNodos().size() == 0) {
                ruta.remove(1);
            }

            prioridad.remove(0);

        }

        for (Integer in : ruta) {
            System.out.println("Numero de nodo: " + in + " combinacion: " + graph.buscarNodo(in).getNombre());
        }
        System.out.println("Numero de nodo: " + 14 + " combinacion: " + graph.buscarNodo(14).getNombre());
    }

    
    
  public static void restricciones() throws IOException {

        String cadena;
        FileReader file = new FileReader("restricciones.txt");
        BufferedReader buffer = new BufferedReader(file);

        GrafoConMatriz grafoConMatriz = new GrafoConMatriz();

        cadena = buffer.readLine();

        while (!(cadena = buffer.readLine()).equals("IGUALDADES")) {
            grafoConMatriz.nuevoVertice(cadena.trim());
        }
        while (!(cadena = buffer.readLine()).equals("RESTRICCIONES")) {
            String[] result = cadena.split(",");
            Nodo nodo1 = grafoConMatriz.obtenerNodo(result[0], grafoConMatriz);
            Nodo nodo2 = grafoConMatriz.obtenerNodo(result[1], grafoConMatriz);
            nodo1.addConexionRestriccion(nodo2);
            nodo2.addConexionRestriccion(nodo1);
        }
        List<Desigualdad> restricciones = new ArrayList<>();
        while ((cadena = buffer.readLine()) != null) {
            String[] result = cadena.split(",");
            restricciones.add(new Desigualdad(Integer.parseInt(result[0]), Integer.parseInt(result[1])));
        }

        buffer.close();

        validarRestricciones(grafoConMatriz, restricciones);
    }

    private static void validarRestricciones(GrafoConMatriz grafoConMatriz, List<Desigualdad> restricciones) {
        for (Desigualdad a : restricciones) {
            Nodo comienza = grafoConMatriz.obtenerNodo(a.getA() + "", grafoConMatriz);
            List<Nodo> nueva = new ArrayList<>();

            if (!nueva.contains(comienza)) {
                nueva.add(comienza);
                for (int i = 0; i < comienza.getNodos().size(); i++) {
                    Nodo aa = comienza.getNodos().get(i);
                    if (!nueva.contains(aa)) {
                       grafoConMatriz.recorridoGrafoProfundidad(aa, nueva);
                    }
                }
            }

            System.out.printf("La restricci�n: " + a.getA() + " " + a.getB() + " -> ");

            Boolean bandera = true;
            for (Nodo n : nueva) {
                if (n.getNombre().equals(a.getB() + "")) {
                    bandera = false;
                    break;
                }
            }

            if (bandera) {
                System.out.printf(" Cumple");
            } else {
                System.out.printf(" Incumple");
            }

            System.out.println(" ");

        }
    }    
}
