package Grafo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public abstract class Grafo implements GrafoI {

    abstract public Nodo buscarNodo(int a);

    abstract public void addNodo(Nodo nodoA);

    abstract public void nuevoVertice(String nombre);

    abstract public int numVertice(String nombre);

    abstract public int pesoArco(String a, String b);

    abstract public int nuevoArco(String a, String b, int peso);

    abstract public List<Nodo> getNodos();

    abstract public void setNodos(List<Nodo> nodos);

    abstract public int getNumeroNodos();

    abstract public void setNumeroNodos(int numeroNodos);

    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        return super.equals(obj);
    }

    @Override
    protected void finalize() throws Throwable {
        // TODO Auto-generated method stub
        super.finalize();
    }

    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        return super.hashCode();
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return super.toString();
    }
    
    /**
     * Algoritmo Floy Warshall
     * @throws IOException 
     */
    public void floydWarshallGeneral() throws IOException {
        int n;

        String cadena;
        FileReader file = new FileReader("warshall.txt");
        BufferedReader buffer = new BufferedReader(file);

        cadena = buffer.readLine();

        n = Integer.parseInt(cadena);

        cadena = buffer.readLine();

        while (!(cadena = buffer.readLine()).equals("ARCOS")) {
            nuevoVertice(cadena.trim());
        }
        while ((cadena = buffer.readLine()) != null) {
            String[] result = cadena.split(",");
            nuevoArco(result[0], result[1], Integer.parseInt(result[2]));
        }
        buffer.close();

        System.out.println("Floyd Warshall: ");
        floydWarshallDinamica(n);

    }

    public void floydWarshallDinamica(int V) {
        int dist[][] = new int[V][V];
        int i, j, k;

        for (i = 0; i < V; i++) {
            for (j = 0; j < V; j++) {
                if (i == j) {
                    dist[i][j] = 0;
                } else {
                    Nodo a = buscarNodo(i);
                    Nodo b = buscarNodo(j);
                    int peso = pesoArco(a.getNombre(), b.getNombre());
                    dist[i][j] = peso;//  [i][j];
                }

            }
        }

        for (k = 0; k < V; k++) {
            for (i = 0; i < V; i++) {
                for (j = 0; j < V; j++) {
                    if (dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                    }
                }
            }
        }

        printSolution(dist, V);
    }

    public void printSolution(int dist[][], int V) {
        System.out.println(" Se muestran las minimas distancias entre cada par de vertices");
        for (int i = 0; i < V; ++i) {
            for (int j = 0; j < V; ++j) {
                if (dist[i][j] == 65535) {
                    System.out.print("INF ");
                } else {
                    System.out.print(dist[i][j] + "   ");
                }
            }
            System.out.println();
        }
    }

    public void ReadGraphFromFIle(String archivo) throws IOException {
        String cadena;
        FileReader file = new FileReader(archivo);
        BufferedReader buffer = new BufferedReader(file);

        cadena = buffer.readLine();

        while (!(cadena = buffer.readLine()).equals("ARCOS")) {
            nuevoVertice(cadena.trim());
        }
        while (!(cadena = buffer.readLine()).equals("COMIENZA")) {
            String[] result = cadena.split(",");
            nuevoArco(result[0], result[1], Integer.parseInt(result[2]));
        }
        buffer.close();

    }

    
   
    /**
     * Algoritmo BFS (anchura) 
     * 
     * @param s
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public void BFS(int s) throws FileNotFoundException, IOException {
        ReadGraphFromFIle("anchura.txt");
        boolean visitados[] = new boolean[4];
        LinkedList<Integer> cola = new LinkedList<Integer>();
        visitados[s] = true;
        cola.add(s);

        while (cola.size() != 0) {
            s = cola.poll();
            System.out.print(s + " ");
            for (Nodo nodo : this.getNodos()) {
                int n = Integer.parseInt(nodo.getNombre());
                if (!visitados[n]) {
                    visitados[n] = true;
                    cola.add(n);
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
    
         
    
	public  void dijkstraGeneral() throws IOException {
                String cadena;
                FileReader file = new FileReader("dijkstra.txt");
                BufferedReader buffer = new BufferedReader(file);

                cadena = buffer.readLine();
                while (!(cadena = buffer.readLine()).equals("ARCOS")) {
                    nuevoVertice(cadena);
                }
                while (!(cadena = buffer.readLine()).equals("COMIENZA")) {
                    String[] result = cadena.split(",");
                     nuevoArco(result[0], result[1], Integer.parseInt(result[2]));
                }
                cadena = buffer.readLine();
                int num=numVertice(cadena);        
                Nodo n=buscarNodo(num);
                dijkstraGeneral(n);

                buffer.close();
        }
        private  void dijkstraGeneral( Nodo inicial) {
		inicial.setDistancia(0);

		List<Nodo> nodosAgregados = new ArrayList<>();
		List<Nodo> nodosNoAgregados = new ArrayList<>();

		nodosNoAgregados.add(inicial);

		while (nodosNoAgregados.size() != 0) {
			Nodo NodoActual = getMenorDistanciaNodoG(nodosNoAgregados);
			nodosNoAgregados.remove(NodoActual);
			for (Nodo conexionActual : NodoActual.getNodos()) {
				int conexionPeso = (int)conexionActual.getObjeto();
				if (!nodosAgregados.contains(conexionActual)) {
					MinimaDistanciaG(conexionActual,(double) conexionPeso, NodoActual);
					if (!nodosNoAgregados.contains(conexionActual)) {
						nodosNoAgregados.add(conexionActual);
					}
				}
			}
			nodosAgregados.add(NodoActual);
			System.out.println("Nombre " + NodoActual.getNombre());
		}
		
	}

	private  Nodo getMenorDistanciaNodoG(List<Nodo> nodosNoAgregados) {
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

	private  void MinimaDistanciaG(Nodo nodoEvaluar, Double peso, Nodo nodoActual) {
		Double nodoActualDistancia = nodoActual.getDistancia();
		if (nodoActualDistancia + peso < nodoEvaluar.getDistancia()) {
			nodoEvaluar.setDistancia(nodoActualDistancia + peso);
			List<Nodo> menorDistancia = nodoActual.getMenorDistancia();
			menorDistancia.add(nodoActual);
			nodoEvaluar.setMenorDistancia(menorDistancia);
		}
	}

 
    
}
