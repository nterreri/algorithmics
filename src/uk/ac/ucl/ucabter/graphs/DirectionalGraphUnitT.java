package uk.ac.ucl.ucabter.graphs;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Before;
import org.junit.Test;

import uk.ac.ucl.ucabter.graphs.DirectionalGraph.Edge;

public class DirectionalGraphUnitT extends DirectionalGraph {
	DirectionalGraph graph;
	
	@Before
	public void constructInstance() throws GraphException {
		//create instance
		graph = new DirectionalGraph();
		//add vertices
		graph.addVertex("A");
		graph.addVertex("B");
		graph.addVertex("C");
		graph.addVertex("D");
		graph.addVertex("E");
		graph.addVertex("F");
		//add edges
		graph.insertEdge("A", "B", 5);
		graph.insertEdge("A", "C", 10);
		graph.insertEdge("B", "C", 4);
		graph.insertEdge("B", "E", 5);
		graph.insertEdge("B", "D", 10);
		graph.insertEdge("C", "E", 1);
		graph.insertEdge("D", "F", 1);
		graph.insertEdge("D", "E", 2);
		graph.insertEdge("E", "F", 10);
		

	}
	
//	@Test
//	public void testEdgeConstructor() {
//		LinkedList<Edge>edges = graph.getEdges("A");
////		DirectionalGraph.Edge edge = new Graph.Edge("C", 5);
//		edges.add(edge);
//		assertFalse(edges.isEmpty());
//		assertEquals("C", edges.getFirst().terminal);
//	}
	
	@Test
	public void testConstructor() {
		assertNotNull(graph);
	}
	
	@Test
	public void testGetEdges() {
		assertTrue(graph.getEdges("F").isEmpty());
		//TODO check edges
	}
	
	@Test
	public void testAddVertex() {
		graph.addVertex("Z");
		assertNotNull(graph.getEdges("Z"));
	}
	
	@Test
	public void testInsertEdge() throws GraphException {
		graph.insertEdge("C", "D", 5);
		LinkedList<Edge> edges = graph.getEdges("C");
		assertFalse(edges.isEmpty());
		
		//expect D to be the second element in list of edges from D
		Iterator<Edge> pointer = edges.iterator();
		pointer.next();
		assertEquals("D", pointer.next().terminal);
	}
	
	@Test(expected=GraphException.class)
	public void testInsertEdgeExceptionInvalid() throws GraphException {

		graph.insertEdge("A", "INVALID", 5);
	}
	
	@Test(expected=GraphException.class)
	public void testInsertEdgeExceptionDuplicate() throws GraphException {

		graph.insertEdge("C", "D", 5);
		graph.insertEdge("C", "D", 5);
	}

	@Test
	public void testCostNeighbour() throws GraphException {
		assertEquals(5, graph.costNeighbour("A", "B"));
	}
	
	@Test(expected=GraphException.class)
	public void testCostNeighbourWithException() throws GraphException {
		assertEquals(5, graph.costNeighbour("A", "Z"));
	}
	
	@Test
	public void testCost() throws GraphException {
		String[] path = {"A", "B", "D"};
		assertEquals(15, graph.cost(path));
		
		String[] path2 = {"B", "D", "E", "F"};
		assertEquals(22, graph.cost(path2));

		String[] path3 = {"A", "C", "E", "F"};
		assertEquals(21, graph.cost(path3));

		String[] path4 = {"A", "B", "E", "F"};
		assertEquals(20, graph.cost(path4));
	}
	
	@Test(expected=GraphException.class)
	public void testCostNoSuchPath() throws GraphException {
		String[] path = {"A", "B", "F"};
		assertEquals(15, graph.cost(path));
	}
	
	@Test(expected=GraphException.class)
	public void testCostTooShort() throws GraphException {
		String[] path = {"A"};
		assertEquals(15, graph.cost(path));
		
	}
	
	@Test
	public void testPathAddEdge() {
		Path path = new Path();
		Edge edge = new Edge("F", 1);
		path.addEdge(edge);
		assertEquals(edge, path.edgesInOrder.getFirst());
		Edge lastEdge = new Edge("F", 2);
		path.addEdge(lastEdge);
		assertEquals(lastEdge, path.edgesInOrder.getFirst());
		assertEquals(edge, path.edgesInOrder.getLast());
	}
	
//	@Test
//	public void testPrint() {
//		Iterator<Edge> pointer = graph.getEdges("D").iterator();
//		while(pointer.hasNext())
//			System.out.print(pointer.next().terminal + " ");
//	}
}
