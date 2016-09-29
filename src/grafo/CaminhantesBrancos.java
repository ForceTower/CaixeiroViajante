package grafo;

import java.util.ArrayList;
import java.util.List;

import model.Aresta;
import model.Vertice;

public class CaminhantesBrancos {
	public List<List<Vertice>> caminhos = new ArrayList<>();
	public List<List<Aresta>> caminhosA = new ArrayList<>();

	public void analise(Vertice iniciar, Aresta ignorar, Grafo metodos) {
		//At� a linha 20 temos constantes
		System.out.println("------> E l� vamos n�s de novo");
		Vertice finalizar = ignorar.getDestino(iniciar); // O fato de declarar o vertice final, consome 1 vertice por causa da condi��o na linha 30.
		List<Vertice> visitados = new ArrayList<>();
		List<Aresta> caminho = new ArrayList<>();
		
		System.out.println("Ultimo: " + finalizar);
		
		horaDoShow(iniciar, finalizar, caminho, visitados, ignorar); //Continuemos daqui
	}

	//No principio, na primeirissima itera��o, chegamos aqui com (vertices - 2)
	private void horaDoShow(Vertice atual, Vertice ultimo, List<Aresta> caminho, List<Vertice> visitados, Aresta ignorar) { 
		System.out.println("Visitando: " + atual);
		visitados.add(atual); //Esta linha ir� se encarregar de fazer este vertice nao ser mais visitado, basicamente um vertice--; 
		
		if (atual.equals(ultimo)) { //Assumindo um pior caso, esta verificacao ocorre somente na ultima itera��o dessa recurs�o, ja que ele precisa passar por todos os vertices antes de chegar at� o local desejado
			System.out.println(" ------ > Chegou na ponta");
			if (visitados.size() == Vertice.todosOsVertices.size()) {
				System.out.println("Temos um caminho em potencial: ");
				System.out.println(visitados);
				System.out.println("Arestas: " + caminho);
				caminhos.add(visitados);
				caminhosA.add(caminho);
			} else {
				System.out.println("N�o satisfez a condi��o...");
				System.out.println("Visitados: " + visitados);
			}
			return; // Num bom caso, este return ir� quebrar esta recurs�o e a permuta��o deste ponto ser� finalizada, poupando execu��es. mas continuemos o pior caso
		}
		System.out.println("Arestas: " + atual.getArestas());
		
		for (Aresta a : atual.getArestas()) { //Mais uma vez, no pior dos casos este for tambem executaria (Vertice-1) vezes
			if (a.equals(ignorar)) {
				System.out.println("� a aresta cujo nome n deve ser dito");
				continue; //Se for a aresta q foi marcada como fixa de volta, ele poupa uma recursao, mas no pior causo isso n aconteceria
			}
				
			Vertice dest = a.getDestino(atual);
			System.out.println("Analisando Aresta com Destino: " + dest);
			if (visitados.contains(dest)) { //Esta parte faz algo interessante, ela faz a recursap diminuir gradativamente o numero de vertices verificadas a cada execu��o da recurs�o
				//pois, se a cada Recurs�o, um vertice diferente � colocado na listinha, em algum momento todos os vertices estar�o na listinha
				System.out.println("Ja visitado");
				continue; //Este continue ir� fazer a recurs�o ocorrer apenas aos que n�o foram visitados
			}
			
			else { //Entrar neste else significa que o algoritmo ir� visitar um local novo, e vai toma-lo como novo vertice para estudos, e ser� marcado como visitado
				System.out.println("Vamos analisar: " + dest);
				
				List<Vertice> copiaV = new ArrayList<>();
				List<Aresta> copiaC = new ArrayList<>();
				copiaV.addAll(visitados);
				copiaC.addAll(caminho);
				copiaC.add(a);
				horaDoShow(dest, ultimo, copiaC, copiaV, ignorar); //chama recursivamente esta funcao, s� que desta vez, ao entrar, ele ter� um vertice a menos como possibilidade de ser analisado
			}
				
		} //basicamente, neste for, ele faz a permutacao entre todos os vertices n�o visitados
	} //A cada vez que este metodo � chamado recursivamente, um vertice � deixado de fora da recurs�o.
	//Ou seja, este metodo ser� executado (Vertice-2) (Vertice-3) (Vertice-4) (Vertice-5) ...  at� Vertice - n == 0
	//Isso lembra o fatorial de um valor...
	//Voltemos para a classe Grafo, linha 79 onde a explica��o continua
}
