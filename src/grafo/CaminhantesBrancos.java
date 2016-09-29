package grafo;

import java.util.ArrayList;
import java.util.List;

import model.Aresta;
import model.Vertice;

public class CaminhantesBrancos {
	public List<List<Vertice>> caminhos = new ArrayList<>();
	public List<List<Aresta>> caminhosA = new ArrayList<>();

	public void analise(Vertice iniciar, Aresta ignorar, Grafo metodos) {
		//Até a linha 20 temos constantes
		System.out.println("------> E lá vamos nós de novo");
		Vertice finalizar = ignorar.getDestino(iniciar); // O fato de declarar o vertice final, consome 1 vertice por causa da condição na linha 30.
		List<Vertice> visitados = new ArrayList<>();
		List<Aresta> caminho = new ArrayList<>();
		
		System.out.println("Ultimo: " + finalizar);
		
		horaDoShow(iniciar, finalizar, caminho, visitados, ignorar); //Continuemos daqui
	}

	//No principio, na primeirissima iteração, chegamos aqui com (vertices - 2)
	private void horaDoShow(Vertice atual, Vertice ultimo, List<Aresta> caminho, List<Vertice> visitados, Aresta ignorar) { 
		System.out.println("Visitando: " + atual);
		visitados.add(atual); //Esta linha irá se encarregar de fazer este vertice nao ser mais visitado, basicamente um vertice--; 
		
		if (atual.equals(ultimo)) { //Assumindo um pior caso, esta verificacao ocorre somente na ultima iteração dessa recursão, ja que ele precisa passar por todos os vertices antes de chegar até o local desejado
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
			return; // Num bom caso, este return irá quebrar esta recursão e a permutação deste ponto será finalizada, poupando execuções. mas continuemos o pior caso
		}
		System.out.println("Arestas: " + atual.getArestas());
		
		for (Aresta a : atual.getArestas()) { //Mais uma vez, no pior dos casos este for tambem executaria (Vertice-1) vezes
			if (a.equals(ignorar)) {
				System.out.println("É a aresta cujo nome n deve ser dito");
				continue; //Se for a aresta q foi marcada como fixa de volta, ele poupa uma recursao, mas no pior causo isso n aconteceria
			}
				
			Vertice dest = a.getDestino(atual);
			System.out.println("Analisando Aresta com Destino: " + dest);
			if (visitados.contains(dest)) { //Esta parte faz algo interessante, ela faz a recursap diminuir gradativamente o numero de vertices verificadas a cada execução da recursão
				//pois, se a cada Recursão, um vertice diferente é colocado na listinha, em algum momento todos os vertices estarão na listinha
				System.out.println("Ja visitado");
				continue; //Este continue irá fazer a recursão ocorrer apenas aos que não foram visitados
			}
			
			else { //Entrar neste else significa que o algoritmo irá visitar um local novo, e vai toma-lo como novo vertice para estudos, e será marcado como visitado
				System.out.println("Vamos analisar: " + dest);
				
				List<Vertice> copiaV = new ArrayList<>();
				List<Aresta> copiaC = new ArrayList<>();
				copiaV.addAll(visitados);
				copiaC.addAll(caminho);
				copiaC.add(a);
				horaDoShow(dest, ultimo, copiaC, copiaV, ignorar); //chama recursivamente esta funcao, só que desta vez, ao entrar, ele terá um vertice a menos como possibilidade de ser analisado
			}
				
		} //basicamente, neste for, ele faz a permutacao entre todos os vertices não visitados
	} //A cada vez que este metodo é chamado recursivamente, um vertice é deixado de fora da recursão.
	//Ou seja, este metodo será executado (Vertice-2) (Vertice-3) (Vertice-4) (Vertice-5) ...  até Vertice - n == 0
	//Isso lembra o fatorial de um valor...
	//Voltemos para a classe Grafo, linha 79 onde a explicação continua
}
