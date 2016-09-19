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
	public void testSimples1(){
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
	
	@Test
	public void testSimples2(){
		int tamanho = 37;
		String menorCaminho = "[1,4,5,3,2,6,1]";
		
		grafo.criarVertice("1");
		grafo.criarVertice("2");
		grafo.criarVertice("3");
		grafo.criarVertice("4");
		grafo.criarVertice("5");
		grafo.criarVertice("6");
		
		grafo.criarAresta("1", "4", 4);
		grafo.criarAresta("2", "3", 2);
		grafo.criarAresta("3", "5", 7);
		grafo.criarAresta("1", "5", 1);
		grafo.criarAresta("2", "6", 10);
		grafo.criarAresta("1", "6", 3);
		grafo.criarAresta("5", "4", 11);
		grafo.criarAresta("5", "4", 20);
		
		grafo.trabalhemSenhoresAlunos("1");
		assertEquals(menorCaminho, grafo.getResultado());
		assertEquals(tamanho, grafo.getTamanho());
	}
	
	@Test
	public void testGrafoDesconexo(){
		grafo.criarVertice("1");
		grafo.criarVertice("2");
		grafo.criarVertice("3");
		grafo.criarVertice("4");
		grafo.criarVertice("5");
		grafo.criarVertice("6");
		grafo.criarVertice("7");
		
		grafo.criarAresta("1", "4", 4);
		grafo.criarAresta("2", "3", 2);
		grafo.criarAresta("3", "5", 7);
		grafo.criarAresta("1", "5", 1);
		grafo.criarAresta("2", "6", 10);
		grafo.criarAresta("1", "6", 3);
		grafo.criarAresta("5", "4", 11);
		grafo.criarAresta("5", "4", 20);
		
		grafo.trabalhemSenhoresAlunos("1");
		assertNull(grafo.getResultado());	
	}
	
	@Test
	public void testComplexo(){
		int tamanho = 57;
		String menorCaminho = "[1,2,3,4,5,6,7,8,9,10,11,1]";
		
		grafo.criarVertice("1");
		grafo.criarVertice("2");
		grafo.criarVertice("3");
		grafo.criarVertice("4");
		grafo.criarVertice("5");
		grafo.criarVertice("6");
		grafo.criarVertice("7");
		grafo.criarVertice("8");
		grafo.criarVertice("9");
		grafo.criarVertice("10");
		grafo.criarVertice("11");

		grafo.criarAresta("1", "2", 4);
		grafo.criarAresta("1", "7", 3);
		grafo.criarAresta("1", "11", 5);
		grafo.criarAresta("2", "3", 12);
		grafo.criarAresta("2", "6", 3);
		grafo.criarAresta("2", "9", 6);
		grafo.criarAresta("3", "4", 7);
		grafo.criarAresta("3", "11", 6);
		grafo.criarAresta("4", "5", 5);
		grafo.criarAresta("4", "7", 4);
		grafo.criarAresta("4", "9", 7);
		grafo.criarAresta("5", "6", 3);
		grafo.criarAresta("5", "11", 3);
		grafo.criarAresta("6", "7", 4);
		grafo.criarAresta("6", "10", 5);
		grafo.criarAresta("7", "8", 6);
		grafo.criarAresta("8", "9", 5);
		grafo.criarAresta("8", "11", 4);
		grafo.criarAresta("9", "10", 2);
		grafo.criarAresta("10", "11", 4);
		
		grafo.trabalhemSenhoresAlunos("1");
		assertEquals(menorCaminho, grafo.getResultado());
		assertEquals(tamanho, grafo.getTamanho());
	}
}