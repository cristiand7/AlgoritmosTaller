package Grafo;

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

}
