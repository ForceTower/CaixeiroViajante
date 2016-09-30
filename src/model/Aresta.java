package model;

import java.util.ArrayList;
import java.util.List;

public class Aresta {
	public static List<Aresta> todasAsArestas = new ArrayList<>();
	private Vertice v1;
	private Vertice v2;
	private int distancia;
	private boolean visitada;
	private boolean virtual;
	
	public Aresta(Vertice v1, Vertice v2, int distancia) {
		this.v1 = v1;
		this.v2 = v2;
		this.distancia = distancia;
		v1.addAresta(this);
		virtual = false;
		//v2.addAresta(this);
		todasAsArestas.add(this);
	}
	
	public Vertice getV1() {
		return v1;
	}
	
	public void setV1(Vertice v1) {
		this.v1 = v1;
	}
	
	public Vertice getV2() {
		return v2;
	}
	
	public void setV2(Vertice v2) {
		this.v2 = v2;
	}
	
	public int getDistancia() {
		return distancia;
	}
	
	public void setDistancia(int distancia) {
		this.distancia = distancia;
	}
	
	public void visitar() {
		visitada = true;
	}
	
	public void setVisita(boolean b) {
		visitada = b;
	}
	
	public boolean foiVisitada() {
		return visitada;
	}
	
	public Vertice getDestino(Vertice v) {
		return v2;
		//return v.equals(v1) ? v2 : v1;
	}
	
	public boolean equals(Object e) {
		if (e instanceof Aresta) {
			Aresta o = (Aresta)e;
			return o.getV1().equals(v1) && o.getV2().equals(v2);
			//return ((o.getV1().equals(v1) && o.getV2().equals(v2)) || o.getV1().equals(v2) && o.getV2().equals(v1)) && o.getDistancia() == distancia;
		}
		return false;
	}
	
	public String toString() {
		return "[" + v1 + " " + v2 + "]"; 
	}
	
	public void setVirtual(boolean b) {
		virtual = b;
	}

	public boolean isVirtual() {
		return virtual;
	}

}
