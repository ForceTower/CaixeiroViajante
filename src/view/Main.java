package view;

import java.io.IOException;

import grafo.Grafo;
import model.Aresta;
import model.Vertice;

public class Main {
	public static void main(String[] args) throws IOException {
		String dir = ".\\arquivos\\entrada.txt";
		Grafo grafo = new Grafo();
		if (args.length == 1)
			dir = args[0];		
		Vertice.todosOsVertices.clear();
		Aresta.todasAsArestas.clear();
		grafo.lerArquivo(dir);
	}
}