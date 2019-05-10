package Grafo;

import java.io.IOException;
import java.util.List;

public interface GrafoI {

	public Nodo buscarNodo(int a);
	public void addNodo(Nodo nodoA) ;
	public void nuevoVertice(String nombre);
	public int numVertice(String nombre) ;
	public int pesoArco(String a, String b);
	public int nuevoArco(String a, String b, int peso);
	public List<Nodo> getNodos() ;
	public void setNodos(List<Nodo> nodos);
	public int getNumeroNodos() ;
	public void setNumeroNodos(int numeroNodos);
       public void dijkstra() throws IOException;
       public void floydWarshall() throws IOException ;
       public void BFS(int s) throws  IOException ;
       public void prim() throws IOException ;
       public void recorridoGrafoProfundidad(Nodo v, List<Nodo> nueva) ;
}
