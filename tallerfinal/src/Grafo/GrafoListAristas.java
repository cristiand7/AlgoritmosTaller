package Grafo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GrafoListAristas extends Grafo {

    private List<Nodo> nodos = new ArrayList<>();
    private int numeroNodos;
    List<Arista> aristas;

    public GrafoListAristas() {
        aristas = new ArrayList<>();
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

    @Override
    public int pesoArco(String a, String b) {
        int costo = 0;

        for (int i = 0; i < aristas.size(); i++) {
            if (aristas.get(i).getVertice1().equals(a)) {
                if (aristas.get(i).getVertice2().equals(b)) {
                    costo = aristas.get(i).getCosto();
                    return costo;
                }

            }
        }
        return 65535;

    }

    @Override
     public Nodo buscarNodo(String a) {
        for (Nodo nodo : nodos) {
            if (nodo.getNombre().equals(a)) {

                return nodo;
            }
        }
        return null;
    }
    @Override
    public int nuevoArco(String a, String b, int peso) {
        aristas.add(new Arista(a, b, peso));
        aristas.add(new Arista(b, a, peso));

        return peso;
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

    public void eliminarArista(String origen, String destino) throws LimiteException {

        for (int i = 0; i < aristas.size(); i++) {

            if (aristas.get(i).getVertice1() == origen) {
                for (int j = 0; j < aristas.size(); j++) {
                    if (aristas.get(i).getVertice2() == destino) {
                        aristas.remove(i);

                    }
                }
            }

        }
    }

    public List sucesores(String vertice) {
        List<String> res = new ArrayList<>();

        for (int i = 0; i < getNumeroNodos(); i++) {
            if ((aristas.get(i).getVertice1() == vertice && aristas.get(i).getCosto() != 0) || (aristas.get(i).getVertice2() == vertice && aristas.get(i).getCosto() != 0)) {
                if (aristas.get(i).getVertice1() == vertice) {
                    res.add(aristas.get(i).getVertice2());
                } else {
                    res.add(aristas.get(i).getVertice1());
                }

            }

        }
        return res;
    }
    
      /**
     *
     * @param g
     * @param aVerificar
     * @param terminal
     * @param N
     * @return
     */
    public boolean HayCiclo(Grafo g, Arista aVerificar, Nodo terminal, String N) {
        List<Nodo> aux = terminal.getNodos();
        if (aux.size() == 0) {
            return false;
        }
        if (terminal.existConexxion(aVerificar.getVertice1()) != -1) {
            return true;
        }
        for (int i = 0; i < aux.size(); i++) {
            Nodo nodo = aux.get(i);
            if (nodo.getNombre().equals(N)
                    == false) {
                if (HayCiclo(g, aVerificar, g.buscarNodo(nodo.getNumeroNodo()), terminal.getNombre())) {
                    return true;
                }
            }
        }
        return false;
    }

    public void Kruskal() throws IOException {
        
          String cadena;
        FileReader file = new FileReader("kruskal.txt");
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
        int num = numVertice(cadena);
        Nodo n = buscarNodo(num);
        
        buffer.close();
        List<Arista> L=((List) ((ArrayList) aristas).clone());;
        while (L.size() != 0) {
            Arista pro = L.get(0);
            
            if (HayCiclo(this, pro, new Nodo(), pro.getVertice2()) == false) {
                nuevoArco(pro.getVertice1(), pro.getVertice2(), pro.getCosto());
                
                    System.out.println("recorrido "+pro.getVertice1() + " a "+ pro.getVertice2());
            }

            L.remove(pro);
        }

       
    }
}

