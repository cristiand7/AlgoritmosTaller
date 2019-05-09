package Grafo;

import java.util.ArrayList;
import java.util.List;

public class GrafoListVertices extends Grafo {

    List<Nodo> nodos = new ArrayList<>();

    @Override
    public Nodo buscarNodo(int a) {
        for (Nodo nodo : nodos) {
            if (nodo.getNumeroNodo() == a) {

                return nodo;
            }
        }
        return null;
    }

    @Override
    public void addNodo(Nodo nodoA) {
        nodos.add(nodoA);
    }

    @Override
    public void nuevoVertice(String nombre) {
        // TODO Auto-generated method stub
        Nodo n =new Nodo(nombre);
        n.setNumeroNodo(nodos.size());
       addNodo(n);
    }

    @Override
    public int numVertice(String nombre) {
        for (Nodo nodo : nodos) {
            if (nodo.getNombre().equals(nombre)) {
                return nodo.getNodos().size();
            }
        }
        return 0;
    }

    @Override
    public int pesoArco(String a, String b) {
        for (Nodo nodo : nodos) {
            if (nodo.getNombre().equals(a)) {
                for (Nodo n : nodo.getNodos()) {
                    if (n.getNombre().equals(b)) {
                        return (int) n.getObjeto();
                    }
                }
            }
        }

        return 65535;
    }

    @Override
    public int nuevoArco(String a, String b, int peso) {
        Nodo n = new Nodo(b);
        n.setObjeto(peso);
        for (Nodo nodo : nodos) {
            if (nodo.getNombre().equals(a)) {
                nodo.addConexion(n, null);
            }
        }
        return peso;
    }

    @Override
    public List<Nodo> getNodos() {
        return nodos;
    }

    @Override
    public void setNodos(List<Nodo> nodos) {
        this.nodos = nodos;
    }

    @Override
    public int getNumeroNodos() {
        return nodos.size();
    }

    @Override
    public void setNumeroNodos(int numeroNodos) {

    }
}
