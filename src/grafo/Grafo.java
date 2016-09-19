package grafo;

import java.util.ArrayList;
import java.util.List;

import model.Aresta;
import model.Vertice;

public class Grafo {
	private List<Aresta> melhor = null;
	private List<List<Vertice>> resultadosVertice = new ArrayList<>();
	
	private int menor = Integer.MAX_VALUE;
	private int indexOfShorter = -1;
	
	public Vertice criarVertice(String nome) {
		if (!Vertice.todosOsVertices.contains(new Vertice(nome)))
			return new Vertice(nome);
		
		return null;
	}
	
	public Aresta criarAresta(String v1, String v2, int distancia) {
		if (!Aresta.todasAsArestas.contains(new Aresta(buscarVertice(v1), buscarVertice(v2), distancia)))
			return new Aresta(buscarVertice(v1), buscarVertice(v2), distancia);
		
		return null;
	}
	
	public Vertice buscarVertice(String nome) {
		for (Vertice v : Vertice.todosOsVertices)
			if (v.getNome().equals(nome)) return v;
		
		return null;
	}
	
	public void aplicarDistanciaTodosVertices(int valor) {
		for (Vertice v : Vertice.todosOsVertices)
			v.setDistancia(valor);
	}
	
	public void trabalhemSenhoresAlunos(String verticeInicial) {
		List<List<Aresta>> resultados = new ArrayList<>();
		//List<List<Vertice>> resultadosVertice = new ArrayList<>();
		resultadosVertice = new ArrayList<>();
		
		Vertice v = buscarVertice(verticeInicial);
		if (v == null) {
			System.out.println("Vertice não existe");
			return;
		}
		
		if (v.getArestas().size() < 2) {
			System.out.println("Não há como encontrar um caminho de ida e volta até este ponto sem repetir vertices e arestas");
			return;
		}
		
		System.out.println("Quantidade de arestas: " + v.getArestas().size());
		System.out.println("Arestas que partem deste vertice: " + v.getArestas());
		
		for (Aresta t : v.getArestas()) {
			System.out.println("Tentativa com a retirada da aresta: " + t);
			CaminhantesBrancos cm = new CaminhantesBrancos();
			cm.analise(v, t, this);
			for (List<Aresta> lis : cm.caminhosA) {
				lis.add(t);
				resultados.add(lis);
			}
			
			for (List<Vertice> lis : cm.caminhos) {
				lis.add(v);
				resultadosVertice.add(lis);
			}
		}
		
		System.out.println("Resultados em Arestas: ");
		System.out.println(resultados);
		System.out.println("Resultados em Vertices: ");
		System.out.println(resultadosVertice);
		
		//int indexOfShorter = -1;
		indexOfShorter = -1;
		
		//List<Aresta> melhor = null;
		melhor = null;
		//int menor = Integer.MAX_VALUE;
		menor = Integer.MAX_VALUE;
		
		
		System.out.println("\nCalculos do tamanhos...");
		for (int i = 0; i < resultados.size(); i++) {
			List<Aresta> cam = resultados.get(i);
			int tamanho = 0;
			
			for (Aresta aresta : cam)
				tamanho += aresta.getDistancia();
			
			System.out.println("Tamanho do " + resultadosVertice.get(i) + ": " + tamanho);
			if (tamanho < menor) {
				menor = tamanho;
				indexOfShorter = i;
				melhor = cam;
			}
		}
		System.out.println("\nResultado:\n");
		if (melhor != null) {
			System.out.println("O melhor caminho é o: ");
			System.out.println(resultadosVertice.get(indexOfShorter));
			System.out.println("Com o tamanho: " + menor);
		}
		else
			System.out.println("Não é possivel detectar um caminho de ida e volta neste grafo sem repetir vertices e arestas");
	}
	
	public String getResultado(){
		if(melhor != null)
			return this.resultadosVertice.get(this.indexOfShorter).toString().replace(" ", "");
		return null;
	}
	
	public int getTamanho(){
		return this.menor;
	}
	
	public static void main(String[] args) {
		Grafo gf = new Grafo();
		/*
		0 1 10
		1 2 10
		2 3 10
		3 0 10
		0 2 1
		1 3 1
		*/
		gf.criarVertice("0");
		gf.criarVertice("1");
		gf.criarVertice("2");
		gf.criarVertice("3");
		
		
		gf.criarAresta("0", "1", 10);
		gf.criarAresta("1", "2", 10);
		gf.criarAresta("2", "3", 10);
		gf.criarAresta("3", "0", 10);
		gf.criarAresta("0", "2", 1);
		gf.criarAresta("1", "3", 1);
		
		gf.trabalhemSenhoresAlunos("0");
		
		Aresta.todasAsArestas.clear();
		Vertice.todosOsVertices.clear();
		
		Grafo grafo = new Grafo();
		
		grafo.criarVertice("1");
		grafo.criarVertice("2");
		grafo.criarVertice("3");
		grafo.criarVertice("4");
		grafo.criarVertice("5");
		grafo.criarVertice("6");
		grafo.buscarVertice("7");
		
		grafo.criarAresta("1", "4", 4);
		grafo.criarAresta("2", "3", 2);
		grafo.criarAresta("3", "5", 7);
		grafo.criarAresta("1", "5", 1);
		grafo.criarAresta("2", "6", 10);
		grafo.criarAresta("1", "6", 3);
		grafo.criarAresta("5", "4", 11);
		grafo.criarAresta("5", "4", 20);
		
		grafo.trabalhemSenhoresAlunos("1");
		//System.out.println(gf.getTamanho());
		//System.out.println(gf.getResultado());
		
		
		/*
		gf.criarVertice("A");
		gf.criarVertice("B");
		gf.criarVertice("C");
		gf.criarVertice("D");
		gf.criarVertice("E");
		
		gf.criarAresta("A", "B", 2);
		gf.criarAresta("A", "C", 3);
		gf.criarAresta("A", "D", 1);
		gf.criarAresta("A", "E", 0);
		gf.criarAresta("B", "E", 2);
		gf.criarAresta("E", "D", 2);
		gf.criarAresta("B", "C", 1);
		gf.criarAresta("C", "D", 2);
		
		gf.trabalhemSenhoresAlunos("A");*/
	}

}
