package grafo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import debug.Debug;
import model.Aresta;
import model.Vertice;

public class Grafo {
	private List<Aresta> melhor = null;
	private List<List<Vertice>> resultadosVertice = new ArrayList<>();
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
	
	public void trabalhemSenhoresAlunos(String verticeInicial) { //O algoritmo comeca daqui...
		List<List<Aresta>> resultados = new ArrayList<>();
		resultadosVertice = new ArrayList<>();
		
		Vertice v = buscarVertice(verticeInicial); 
		if (v == null) {
			Debug.println("Vertice não existe");
			return;
		}
		
		if (v.getArestas().size() < 2) {
			Debug.println("Não há como encontrar um caminho de ida e volta até este ponto sem repetir vertices e arestas");
			return;
		}
		
		Debug.println("Quantidade de arestas: " + v.getArestas().size());
		Debug.println("Arestas que partem deste vertice: " + v.getArestas());
		
		//Ou melhor.. comeca aqui...
		//No pior caso, com um grafo completamente conectado, este for irá ser executado vertices - 1 vezes. (Ja que o vertice atual tem arestas Lenistas)
		for (Aresta t : v.getArestas()) {  //(Vertice-1)
			Debug.println("Tentativa com a retirada da aresta: " + t);
			CaminhantesBrancos cm = new CaminhantesBrancos();
			cm.analise(v, t, this); //Chama o metodo que da continuidade à analise, entre aqui para depois ler a linha 79, para evitar spoilers
			
			for (List<Aresta> lis : cm.caminhosA) {
				lis.add(t);
				resultados.add(lis);
			}
			
			for (List<Vertice> lis : cm.caminhos) {
				lis.add(v);
				resultadosVertice.add(lis);
			}
		}
		// Ou sej, ele terá executado da linha 64 (Vertice-1) * (Vertice-2) * (Vertice-3) * ....  1;
		// A ordem desse algoritmo então é O(fatorial(Vertice))
		
		Debug.println("Resultados em Arestas: ");
		Debug.println(resultados);
		Debug.println("Resultados em Vertices: ");
		Debug.println(resultadosVertice);
		indexOfShorter = -1;
		melhor = null;
		menor = Integer.MAX_VALUE;
		
		Debug.println("\nCalculos do tamanhos...");
		for (int i = 0; i < resultados.size(); i++) {//Resultados pode ter no maximo todas as permutacoes de caminhos
			List<Aresta> cam = resultados.get(i);
			int tamanho = 0;
			for (Aresta aresta : cam){//Arestas de uma permutação vai ser apenas O de (V)
				tamanho += aresta.getDistancia();
			}
			
			Debug.println("Tamanho do " + resultadosVertice.get(i) + ": " + tamanho);
			if (tamanho < menor) {
				menor = tamanho;
				indexOfShorter = i;
				melhor = cam;
			}
		}
		//Esta parte de calculos dos tamanhos tambem executa Fatorial(Vertices);
		System.out.println("\nResultado:\n");
		if (melhor != null) {
			System.out.println("O melhor caminho é o: ");
			System.out.println(resultadosVertice.get(indexOfShorter));
			System.out.println("Com o tamanho: " + menor);
		}
		else
			System.out.println("Não é possivel detectar um caminho de ida e volta neste grafo sem repetir vertices e arestas");
		
	}
	
	public void coisaDeQuintaSerie(String verticeInicial, String verticeFinal) {
		Vertice v = buscarVertice(verticeInicial);
		Vertice vf = buscarVertice(verticeFinal);
		if (v == null || vf == null) {
			Debug.println("Vertice não existe");
			return;
		}
		
		List<List<Aresta>> todasUsadas = new ArrayList<>();
		
		while (true) {
			CaminhantesBrancos cm = new CaminhantesBrancos();
			List<Aresta> caminho = cm.theRunPrep(v, vf);
			if (caminho == null) {
				System.out.println("Acabou");
				break;
			}
			
			else {
				todasUsadas.add(caminho);
				int menor = Integer.MAX_VALUE;
				for (Aresta a : caminho)
					if (a.getDistancia() < menor)
						menor = a.getDistancia();
				
				for (Aresta a : caminho) {
					a.setDistancia(a.getDistancia() - menor);
					Aresta b = a.getV2().getVirtualAresta(a);
					b.setDistancia(b.getDistancia() + menor);
				}
			}
		}
		
		CaminhantesBrancos cm = new CaminhantesBrancos();
		int fluxo = cm.getFluxoParaANovinhaDoGrau(todasUsadas);
		System.out.println("Tava a caminho do fluxo e avistei: " + fluxo);
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
			System.out.println("Este grafo não possui circuito");
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
		this.coisaDeQuintaSerie("0", "7");
	}
}
