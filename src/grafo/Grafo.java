package grafo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Aresta;
import model.Vertice;

public class Grafo {
	private List<Aresta> melhor = null;
	private List<List<Vertice>> resultadosVertice = new ArrayList<>();
	public int cont=0;
	private int menor = Integer.MAX_VALUE;
	private int indexOfShorter = -1;
	
	public Vertice criarVertice(String nome) {
		if(this.buscarVertice(nome)==null)
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
		resultadosVertice = new ArrayList<>();
		
		Vertice v = buscarVertice(verticeInicial);
		if (v == null) {
			System.out.println("Vertice n�o existe");
			return;
		}
		
		if (v.getArestas().size() < 2) {
			System.out.println("N�o h� como encontrar um caminho de ida e volta at� este ponto sem repetir vertices e arestas");
			return;
		}
		
		System.out.println("Quantidade de arestas: " + v.getArestas().size());
		System.out.println("Arestas que partem deste vertice: " + v.getArestas());
		
		for (Aresta t : v.getArestas()) {
			System.out.println("Tentativa com a retirada da aresta: " + t);
			CaminhantesBrancos cm = new CaminhantesBrancos();
			cm.analise(v, t, this);
			for (List<Aresta> lis : cm.caminhosA) {
				this.cont++;
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
		
		indexOfShorter = -1;
		melhor = null;
		menor = Integer.MAX_VALUE;
		
		System.out.println("\nCalculos do tamanhos...");
		for (int i = 0; i < resultados.size(); i++) {
			List<Aresta> cam = resultados.get(i);
			int tamanho = 0;
			for (Aresta aresta : cam){
				tamanho += aresta.getDistancia();
			}
			
			System.out.println("Tamanho do " + resultadosVertice.get(i) + ": " + tamanho);
			if (tamanho < menor) {
				menor = tamanho;
				indexOfShorter = i;
				melhor = cam;
			}
		}
		System.out.println("\nResultado:\n");
		if (melhor != null) {
			System.out.println("O melhor caminho � o: ");
			System.out.println(resultadosVertice.get(indexOfShorter));
			System.out.println("Com o tamanho: " + menor);
		}
		else
			System.out.println("N�o � possivel detectar um caminho de ida e volta neste grafo sem repetir vertices e arestas");
	}
	
	public String getResultado(){
		if(melhor != null)
			return this.resultadosVertice.get(this.indexOfShorter).toString().replace(" ", "");
		return null;
	}
	
	public int getTamanho(){
		return this.menor;
	}
	
	public void lerArquivo(String arquivo) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(new File(arquivo)));
		int v = Integer.parseInt(br.readLine());
		int a = Integer.parseInt(br.readLine());
		if(a < v){
			System.out.println("Este grafo n�o possui circuito");
			br.close();
			System.exit(-1);
		}
		else{
			for(String linha = br.readLine(); linha != null; linha = br.readLine()){
				String[] buffer = linha.split(" ");
				this.criarVertice(buffer[0]);
				this.criarVertice(buffer[1]);
				this.criarAresta(buffer[0], buffer[1], Integer.parseInt(buffer[2]));
			}
			System.out.println("Leitura do arquivo: [OK]");
		}
		br.close();
		this.trabalhemSenhoresAlunos("0");
	}
}
