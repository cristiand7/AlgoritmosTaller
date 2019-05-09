package Grafo;

public class Arista {

	String vertice1;
	String vertice2;
	int costo;
	
	public Arista(String vertice1,String vertice2,int costo)
	{
		this.vertice1=vertice1;
		this.vertice2=vertice2;
		this.costo=costo;

	}
	
	public int getCosto() {
			return costo;
			
	}

	public void setCosto(int costo) {
		this.costo = costo;
	}

	public String getVertice1() {
		return vertice1;
	}

	public void setVertice1(String vertice1) {
		this.vertice1 = vertice1;
	}

	public String getVertice2() {
		return vertice2;
	}

	public void setVertice2(String vertice2) {
		this.vertice2 = vertice2;
	}
	

}
