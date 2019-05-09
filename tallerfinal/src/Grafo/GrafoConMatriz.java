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
    
    public GrafoConMatriz(int max){
        matrizPeso = new int [max][max];
        for(int i = 0; i<max; i++){
            for(int j = 0; j<max; j++){
                matrizPeso [i][j] = INFINITO;
            }
        }
        numeroNodos=0;
    }
    
     public Nodo buscarNodo(int a){
            for (Nodo nodo : nodos) {
              if(nodo.getNumeroNodo()==a){
              
              return nodo;
              }  
            }
        return null;
        }
    
    
    public GrafoConMatriz() {
        int max=30;
    	 matrizPeso = new int [max][max];
        for(int i = 0; i<max; i++){
            for(int j = 0; j<max; j++){
                matrizPeso [i][j] = INFINITO;
            }
        }
        numeroNodos=0;
	}

	public void addNodo(Nodo nodoA) {
        nodos.add(nodoA);
    }
    
    public void nuevoVertice(String nombre){
        boolean esta = numVertice(nombre) >= 0;
        if(!esta){
            Nodo v = new Nodo(nombre);
            v.setNumeroNodo(numeroNodos);
            nodos.add(v);
            numeroNodos++;
        }
    }
    
    
    
        public  void camino() {

          Nodo  inicio=this.buscarNodo(0);
            
        List<Integer> ruta = new ArrayList<Integer>();
        List<Integer> prioridad = new ArrayList<Integer>();

        ruta.add(inicio.getNumeroNodo());
        prioridad.add(inicio.getNumeroNodo());
        inicio.setMarca(true);

        while (prioridad.get(0) != 14) {

            if (prioridad.size() > 1) {
                ruta.add(prioridad.get(0));
            }

            for (Nodo e : (this.buscarNodo(prioridad.get(0))).getNodos()) {
                if (!e.isMarca()) {
                    //System.out.println(""+e.getNumeroNodo());
                    prioridad.add(1, e.getNumeroNodo());
                    this.buscarNodo(e.getNumeroNodo()).setMarca(true);
                }

            }

            if ((this.buscarNodo(prioridad.get(0))).getNodos().size() == 0) {
                ruta.remove(1);
            }

            prioridad.remove(0);

        }

        for (Integer in : ruta) {
            System.out.println("Numero de nodo: " + in + " combinacion: " + this.buscarNodo(in).getNombre());
        }
        System.out.println("Numero de nodo: " + 14 + " combinacion: " + this.buscarNodo(14).getNombre());
    }
        
    
    
    
    public int numVertice(String nombre) {
        Nodo v = new Nodo(nombre);
        boolean encontrado = false;
        int i = 0;
        for(; (i<numeroNodos) && !encontrado;){
            encontrado = nodos.get(i).equals(v);
            if(!encontrado){
                i++;
            }
        }
        return (i<numeroNodos) ? i : -1;
    }
    
    
    
    
    
    
    public int pesoArco(String a, String b){
        int va, vb;
        va = numVertice(a);
        vb = numVertice(b);
        return matrizPeso[va][vb];
    }
    
    public int nuevoArco(String a, String b, int peso){
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
		for(Nodo node:graph.getNodos()) {
			if(node.getNombre().equals(string)) {
				return node;
			}
		}
		return null;
	}

	/**
	 * Recorrido grado en profundidad
	 * @param v
	 * @param nueva
	 */
	public void recorridoGrafoProfundidad(Nodo v, List<Nodo> nueva) {
		if (!nueva.contains(v)) {
			nueva.add(v);
			for (int i = 0; i < v.getNodos().size(); i++) {
				Nodo a = v.getNodos().get(i);
				if (!nueva.contains(a)) {
					recorridoGrafoProfundidad(a, nueva);
				}
			}
		}
	}
	
	/**
	 * Algoritmo de dijkstra
	 * 
	 * @param graph
	 * @param inicial
	 * @return
	 */
	public static GrafoConMatriz dijkstra(GrafoConMatriz graph, Nodo inicial) {
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
		return graph;
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
	
	/**
	 * Algoritmo de dijkstra para el problema de cambio de moneda
	 * 
	 * @param graph
	 * @param inicial
	 * @return
	 */
	public static GrafoConMatriz dijkstraCambioDeMoneda(GrafoConMatriz graph, Nodo inicial) {
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
		return graph;
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
	 * @throws IOException 
	 * 
	 */
	public void prim() throws IOException {
		int n;
		
        String cadena;
        FileReader file = new FileReader("prim.txt");
        BufferedReader buffer = new BufferedReader(file);
        
        cadena = buffer.readLine();
        
        n = Integer.parseInt(cadena);
        
        matrizPeso =  new int [n][n]; 
        
        GrafoConMatriz gra = new GrafoConMatriz(n);
        cadena = buffer.readLine();
        
        while(!(cadena = buffer.readLine()).equals("ARCOS")) {
        	gra.nuevoVertice(cadena.trim());
        }
        while((cadena = buffer.readLine())!=null) {
        	String[] result = cadena.split(",");
        	gra.nuevoArco(result[0], result[1], Integer.parseInt(result[2]));
        }
        buffer.close();       
       
        
        System.out.println("Costo del arbol minimo: "+arbolExpansionPrim(gra));
	}
	
	private static int arbolExpansionPrim(GrafoConMatriz gra){
        int longMin, menor;
        int z;
        int [] coste = new int[gra.getNumeroNodos()];
        int [] masCerca = new int[gra.getNumeroNodos()];
        boolean [] W = new boolean[gra.getNumeroNodos()];
        
        for(int i = 0; i<gra.getNumeroNodos(); i++){
            W[i] = false;
        }
        longMin = 0;
        W[0] = true;
        
        for(int i=1; i<gra.getNumeroNodos(); i++){
            coste[i] = gra.getMatrizPeso()[0][1];
            masCerca[i] = 0;
        }
        
        for(int i=1; i<gra.getNumeroNodos(); i++){
            
            menor = coste[1];
            z = 1;
            
            for(int j=2; j<gra.getNumeroNodos(); j++){
                
                if(coste[j] < menor){
                    menor = coste[j];
                    z = j;
                }
                
            }
            longMin += menor;
            System.out.println("Nodo:"+gra.getNodos().get(masCerca[z]).getNombre()+
                    " -> Nodo:"+gra.getNodos().get(z).getNombre()+"   Peso:   "+coste[z]);
            W[z] = true;
            coste[z] = GrafoConMatriz.INFINITO;
            
            for(int j = 1; j< gra.getNumeroNodos(); j++){
                if((gra.getMatrizPeso()[z][j] < coste[j]) && !W[j]){
                    coste[j] = gra.getMatrizPeso()[z][j];
                    masCerca[j] = z;
                }
            }
        }
        return longMin;
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
        
        while(!(cadena = buffer.readLine()).equals("ARCOS")) {
        	gra.nuevoVertice(cadena.trim());
        }
        while((cadena = buffer.readLine())!=null) {
        	String[] result = cadena.split(",");
        	gra.nuevoArco(result[0], result[1], Integer.parseInt(result[2]));        	
        }
        buffer.close();
        
        System.out.println("Cola de prioridad usado en Dijkstra: ");
        
        for(int nodo =0 ; nodo< n ; nodo++){
        	colaPrioridadDijkstra(gra, n , nodo);
        }
        
        
	}
    
    public void colaPrioridadDijkstra(GrafoConMatriz gra, int V , int inicio)
    {
    	int[] distancia = new int[V];
        int[] padre = new int[V];
        boolean[] visto = new boolean[V];        
                
        for (int i = 0; i < V; i++) {
            distancia[i] = 1200000000;
            padre[i] = -1;
            visto[i] = false;
        }
        
        distancia[inicio]=0;
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
        System.out.println("DESDE EL NODO : "+inicio);
         for (int x=0; x < V; x++) {
            System.out.println("AL NODO "+ x + " : "+ distancia[x]);
         }
         System.out.println();

        
    }
    
public void restricciones() throws IOException {
		
        String cadena;
        FileReader file = new FileReader("restricciones.txt");
        BufferedReader buffer = new BufferedReader(file);
        
        GrafoConMatriz grafoConMatriz = new GrafoConMatriz();
        
        cadena = buffer.readLine();
        
        while(!(cadena = buffer.readLine()).equals("IGUALDADES")) {
        	grafoConMatriz.nuevoVertice(cadena.trim());
        }
        while(!(cadena = buffer.readLine()).equals("RESTRICCIONES")) {
        	String[] result = cadena.split(",");
        	Nodo nodo1 = grafoConMatriz.obtenerNodo(result[0], grafoConMatriz);
        	Nodo nodo2 = grafoConMatriz.obtenerNodo(result[1], grafoConMatriz);
        	nodo1.addConexionRestriccion(nodo2);
        	nodo2.addConexionRestriccion(nodo1);
        }
        List<Desigualdad> restricciones = new ArrayList<>();
        while((cadena = buffer.readLine())!=null) {
        	String[] result = cadena.split(",");
        	restricciones.add(new Desigualdad(Integer.parseInt(result[0]), Integer.parseInt(result[1])));
        }
        
        buffer.close();
        
        validarRestricciones(grafoConMatriz, restricciones);
	}

	private void validarRestricciones(GrafoConMatriz grafoConMatriz, List<Desigualdad> restricciones) {
		for(Desigualdad a : restricciones) {
			Nodo comienza = grafoConMatriz.obtenerNodo(a.getA()+"", grafoConMatriz);
			List<Nodo> nueva = new  ArrayList<>();
			
			if (!nueva.contains(comienza)) {
				nueva.add(comienza);
				for (int i = 0; i < comienza.getNodos().size(); i++) {
					Nodo aa = comienza.getNodos().get(i);
					if (!nueva.contains(aa)) {
						recorridoGrafoProfundidad(aa, nueva);
					}
				}
			}
			
			System.out.printf("La restricci�n: "+a.getA()+" "+a.getB()+" -> ");
			
			Boolean bandera = true;
			for(Nodo n:nueva) {
				if(n.getNombre().equals(a.getB()+"")) {
					bandera = false;
					break;
				}
			}
			
			if(bandera) {
				System.out.printf(" Cumple");
			}else {
				System.out.printf(" Incumple");
			}
			
			System.out.println(" ");
			
		}
	}
}



/*

public void floydWarshall() throws IOException {
		int n;


        
		
        String cadena;
        FileReader file = new FileReader("warshall.txt");
        BufferedReader buffer = new BufferedReader(file);
        
        cadena = buffer.readLine();
        
        n = Integer.parseInt(cadena);

 
        
        GrafoConMatriz gra = new GrafoConMatriz(n);
        cadena = buffer.readLine();
        
        while(!(cadena = buffer.readLine()).equals("ARCOS")) {
        	gra.nuevoVertice(cadena.trim());
        }
        while((cadena = buffer.readLine())!=null) {
        	String[] result = cadena.split(",");
        	gra.nuevoArco(result[0], result[1], Integer.parseInt(result[2]));        	
        }
        buffer.close();
        
        System.out.println("Floyd Warshall: ");
        floydWarshallDinamica(gra, n);
        
        
	}
	
	public void floydWarshallDinamica(GrafoConMatriz gra, int V)
    {
        int dist[][] = new int[V][V];
        int i, j, k;
 
       
        for (i = 0; i < V; i++) {
            for (j = 0; j < V; j++) {
            	if(i == j) {
            		dist[i][j] = 0;
            	}else {
            		dist[i][j] = gra.getMatrizPeso()[i][j];
            	}
                
            }
        }

        for (k = 0; k < V; k++)
        {            
            for (i = 0; i < V; i++)
            {         
                for (j = 0; j < V; j++)
                {             
                    if (dist[i][k] + dist[k][j] < dist[i][j])
                        dist[i][j] = dist[i][k] + dist[k][j];
                }
            }
        }
 
        printSolution(dist, V);
    }
 
    public void printSolution(int dist[][], int V)
    {
        System.out.println(" Se muestran las minimas distancias entre cada par de vertices");
        for (int i=0; i<V; ++i)
        {
            for (int j=0; j<V; ++j)
            {
                if (dist[i][j]==65535)
                    System.out.print("INF ");
                else
                    System.out.print(dist[i][j]+"   ");
            }
            System.out.println();
        }
    }
    */