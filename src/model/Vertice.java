package model;

import java.util.ArrayList;
import java.util.List;

public class Vertice implements Comparable<Vertice>{
	public static List<Vertice> todosOsVertices = new ArrayList<>();
	private String nome;
	private Vertice pai;
	private boolean visitado;
	private int distancia;
	
	private List<Aresta> arestas;
	
	public Vertice(String nome) {
		this.nome = nome;
		arestas = new ArrayList<>();
		todosOsVertices.add(this);
	}

	public void addAresta(Aresta aresta) {
		arestas.add(aresta);
	}
	
	public List<Aresta> getArestas() {
		return arestas;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setPai(Vertice v) {
		pai = v;
	}
	
	public Vertice getPai() {
		return pai;
	}
	
	public void setDistancia(int t) {
		distancia = t;
	}
	
	public int getDistancia() {
		return distancia;
	}
	
	public void visitar() {
		visitado = true;
	}
	
	public void setVisita(boolean b) {
		visitado = b;
	}
	
	public boolean foiVisitado() {
		return visitado;
	}
	
	public boolean equals(Object e) {
		if (e instanceof Vertice)
			return ((Vertice)e).getNome().equals(nome);
		return false;
	}
	
	public String toString() {
		return nome;
	}

	@Override
	public int compareTo(Vertice o) {
		if (this.getDistancia() < o.getDistancia())
			return -1;
		
        else if (this.getDistancia() == o.getDistancia())
        	return 0;
        
        return 1;
	}

}