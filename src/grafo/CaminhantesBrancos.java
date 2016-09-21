package grafo;

import java.util.ArrayList;
import java.util.List;

import model.Aresta;
import model.Vertice;

public class CaminhantesBrancos {
	public List<List<Vertice>> caminhos = new ArrayList<>();
	public List<List<Aresta>> caminhosA = new ArrayList<>();

	public void analise(Vertice iniciar, Aresta ignorar, Grafo metodos) {
		System.out.println("------> E lá vamos nós de novo");
		Vertice finalizar = ignorar.getDestino(iniciar);
		List<Vertice> visitados = new ArrayList<>();
		List<Aresta> caminho = new ArrayList<>();
		
		System.out.println("Ultimo: " + finalizar);
		
		horaDoShow(iniciar, finalizar, caminho, visitados, ignorar);
	}

	private void horaDoShow(Vertice atual, Vertice ultimo, List<Aresta> caminho, List<Vertice> visitados, Aresta ignorar) {
		System.out.println("Visitando: " + atual);
		visitados.add(atual);
		
		if (atual.equals(ultimo)) {
			System.out.println(" ------ > Chegou na ponta");
			if (visitados.size() == Vertice.todosOsVertices.size()) {
				System.out.println("Temos um caminho em potencial: ");
				System.out.println(visitados);
				System.out.println("Arestas: " + caminho);
				caminhos.add(visitados);
				caminhosA.add(caminho);
			} else {
				System.out.println("Não satisfez a condição...");
				System.out.println("Visitados: " + visitados);
			}
			return;
		}
		
		System.out.println("Arestas: " + atual.getArestas());
		
		for (Aresta a : atual.getArestas()) {
			if (a.equals(ignorar)) {
				System.out.println("É a aresta cujo nome n deve ser dito");
				continue;
			}
				
			Vertice dest = a.getDestino(atual);
			System.out.println("Analisando Aresta com Destino: " + dest);
			if (visitados.contains(dest)) {
				System.out.println("Ja visitado");
				continue;
			}
			
			else {
				System.out.println("Vamos analisar: " + dest);
				
				List<Vertice> copiaV = new ArrayList<>();
				List<Aresta> copiaC = new ArrayList<>();
				copiaV.addAll(visitados);
				copiaC.addAll(caminho);
				copiaC.add(a);
				
				horaDoShow(dest, ultimo, copiaC, copiaV, ignorar);
			}
				
		}
	}

}
