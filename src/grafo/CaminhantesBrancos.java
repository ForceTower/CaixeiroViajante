package grafo;

import java.util.ArrayList;
import java.util.List;

import debug.Debug;
import model.Aresta;
import model.Vertice;

public class CaminhantesBrancos {
	public List<List<Vertice>> caminhos = new ArrayList<>();
	public List<List<Aresta>> caminhosA = new ArrayList<>();

	public void analise(Vertice iniciar, Aresta ignorar, Grafo metodos) {
		//Até a linha 20 temos constantes
		Debug.println("------> E lá vamos nós de novo");
		Vertice finalizar = ignorar.getDestino(iniciar); // O fato de declarar o vertice final, consome 1 vertice por causa da condição na linha 30.
		List<Vertice> visitados = new ArrayList<>();
		List<Aresta> caminho = new ArrayList<>();
		
		Debug.println("Ultimo: " + finalizar);
		
		horaDoShow(iniciar, finalizar, caminho, visitados, ignorar); //Continuemos daqui
	}

	//No principio, na primeirissima iteração, chegamos aqui com (vertices - 2)
	private void horaDoShow(Vertice atual, Vertice ultimo, List<Aresta> caminho, List<Vertice> visitados, Aresta ignorar) { 
		Debug.println("Visitando: " + atual);
		visitados.add(atual); //Esta linha irá se encarregar de fazer este vertice nao ser mais visitado, basicamente um vertice--; 
		
		if (atual.equals(ultimo)) { //Assumindo um pior caso, esta verificacao ocorre somente na ultima iteração dessa recursão, ja que ele precisa passar por todos os vertices antes de chegar até o local desejado
			Debug.println(" ------ > Chegou na ponta");
			if (visitados.size() == Vertice.todosOsVertices.size()) {
				Debug.println("Temos um caminho em potencial: ");
				Debug.println(visitados);
				Debug.println("Arestas: " + caminho);
				caminhos.add(visitados);
				caminhosA.add(caminho);
			} else {
				Debug.println("Não satisfez a condição...");
				Debug.println("Visitados: " + visitados);
			}
			return; // Num bom caso, este return irá quebrar esta recursão e a permutação deste ponto será finalizada, poupando execuções. mas continuemos o pior caso
		}
		Debug.println("Arestas: " + atual.getArestas());
		
		for (Aresta a : atual.getArestas()) { //Mais uma vez, no pior dos casos este for tambem executaria (Vertice-1) vezes
			if (a.equals(ignorar)) {
				Debug.println("É a aresta cujo nome n deve ser dito");
				continue; //Se for a aresta q foi marcada como fixa de volta, ele poupa uma recursao, mas no pior causo isso n aconteceria
			}
				
			Vertice dest = a.getDestino(atual);
			Debug.println("Analisando Aresta com Destino: " + dest);
			if (visitados.contains(dest)) { //Esta parte faz algo interessante, ela faz a recursap diminuir gradativamente o numero de vertices verificadas a cada execução da recursão
				//pois, se a cada Recursão, um vertice diferente é colocado na listinha, em algum momento todos os vertices estarão na listinha
				Debug.println("Ja visitado");
				continue; //Este continue irá fazer a recursão ocorrer apenas aos que não foram visitados
			}
			
			else { //Entrar neste else significa que o algoritmo irá visitar um local novo, e vai toma-lo como novo vertice para estudos, e será marcado como visitado
				Debug.println("Vamos analisar: " + dest);
				
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
	
	public List<Aresta> theRunPrep(Vertice v, Vertice vf) {
		List<Aresta> caminho = new ArrayList<>();
		List<Vertice> visitados = new ArrayList<>();
		
		return theRun(v, vf, caminho, visitados);
	}

	public List<Aresta> theRun(Vertice v, Vertice vf, List<Aresta> caminho, List<Vertice> visitados) {
		visitados.add(v);
		
		if (v.equals(vf)) {
			System.out.println("got then!");
			return caminho;
		}
		
		
		for (Aresta a : v.getArestas()) {
			if (a.getDistancia() != 0 && !a.getDestino(v).equals(visitados)) {
				List<Vertice> copiaV = new ArrayList<>();
				List<Aresta> copiaC = new ArrayList<>();
				copiaV.addAll(visitados);
				copiaC.addAll(caminho);
				copiaC.add(a);
				List<Aresta> c = theRun(a.getDestino(v), vf, copiaC, copiaV);
				if (c != null) {
					System.out.println("Returning a path");
					return c;
				}
			}
		}
		System.out.println("Dead end");
		return null;
	}

	public int getFluxoParaANovinhaDoGrau(List<List<Aresta>> todasUsadas) {
		List<Aresta> semRepetir = new ArrayList<>();
		for (List<Aresta> l : todasUsadas) {
			for (Aresta a : l) {
				if (!semRepetir.contains(a) && !a.isVirtual()) {
					semRepetir.add(a);
				}
			}
		}
		
		System.out.println("Arestas usadas: " + semRepetir);
		
		int acumulador = 0;
		for (Aresta n : semRepetir)
			acumulador += n.getDistancia();
		return acumulador;
	}
}
