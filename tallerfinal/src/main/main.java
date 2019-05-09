/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

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

                grafoConMatriz = grafoConMatriz.dijkstra(grafoConMatriz, grafoConMatriz.obtenerNodo(cadena = buffer.readLine(), grafoConMatriz));

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
                grafoConMatriz.prim();
            }
            if (algoritmo.trim().equals("4")) {

               

                System.out.println("El recorrido por anchura (comenzando desde el v�rtice 2) es: ");

                new GrafoListVertices().BFS(2);
            }
            if (algoritmo.trim().equals("5")) {
                grafoConMatriz.restricciones();
            }
            if (algoritmo.trim().equals("6")) {
                grafoConMatriz.colaPrioridad();
            }
            if (algoritmo.trim().equals("7")) {
                //grafoConMatriz.floydWarshall();
                
                System.out.println("------- grafo general ---------");
                
                //new GrafoListVertices().floydWarshallGeneral();
                System.out.println("------- grafo general ---------");
                 new GrafoConMatriz().floydWarshallGeneral();
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

                
                graph.camino();
                
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

                 grafoConMatriz = grafoConMatriz.dijkstraCambioDeMoneda(grafoConMatriz, grafoConMatriz.obtenerNodo(cadena = buffer.readLine(), grafoConMatriz));

                 buffer.close();
            }

            System.out.println();
        }
    }

}
