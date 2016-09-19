package grafo;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import model.Aresta;
import model.Vertice;

public class GrafoTest {
	
	Grafo grafo = new Grafo();
	
	@Before
	public void setup(){
		Aresta.todasAsArestas.clear();
		Vertice.todosOsVertices.clear();
		
	}
	@Test
	public void test1(){
		int tamanho = 11;
		String menorCaminho = "[A,D,C,B,A]";
		
		grafo.criarVertice("A");
		grafo.criarVertice("B");
		grafo.criarVertice("C");
		grafo.criarVertice("D");
		
		grafo.criarAresta("A", "B", 5);
		grafo.criarAresta("B", "C", 3);
		grafo.criarAresta("C", "D", 2);
		grafo.criarAresta("A", "D", 1);
		grafo.criarAresta("A", "C", 10);
		
		grafo.trabalhemSenhoresAlunos("A");
		
		assertEquals(menorCaminho, grafo.getResultado()); 
	}
	
}
