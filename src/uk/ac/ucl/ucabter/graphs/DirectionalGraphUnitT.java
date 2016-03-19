package uk.ac.ucl.ucabter.graphs;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Before;
import org.junit.Test;

import uk.ac.ucl.ucabter.graphs.DirectionalGraph.Edge;

public class DirectionalGraphUnitT extends DirectionalGraph {
	DirectionalGraph acyclicalGraph;
	DirectionalGraph generalGraph;
	
	@Before
	public void constructInstance() throws GraphException {
		//create instance
		acyclicalGraph = new DirectionalGraph();
		//add vertices
		acyclicalGraph.addVertex("A");
		acyclicalGraph.addVertex("B");
		acyclicalGraph.addVertex("C");
		acyclicalGraph.addVertex("D");
		acyclicalGraph.addVertex("E");
		acyclicalGraph.addVertex("F");//size = 7
		//add edges
		acyclicalGraph.insertEdge("A", "B", 5);
		acyclicalGraph.insertEdge("A", "C", 10);
		acyclicalGraph.insertEdge("B", "C", 4);
		acyclicalGraph.insertEdge("B", "E", 5);
		acyclicalGraph.insertEdge("B", "D", 10);
		acyclicalGraph.insertEdge("C", "E", 1);
		acyclicalGraph.insertEdge("D", "F", 1);
		acyclicalGraph.insertEdge("D", "E", 2);
		acyclicalGraph.insertEdge("E", "F", 10);
		
		//create other instance
		generalGraph = new DirectionalGraph();
		//add vertices
		generalGraph.addVertex("A");
		generalGraph.addVertex("B");
		generalGraph.addVertex("C");
		generalGraph.addVertex("D");
		generalGraph.addVertex("E");//size = 6
		//add edges
		generalGraph.insertEdge("A", "B", 5);
		generalGraph.insertEdge("B", "C", 4);
		generalGraph.insertEdge("C", "D", 7);
		generalGraph.insertEdge("D", "C", 8);
		generalGraph.insertEdge("D", "E", 6);
		generalGraph.insertEdge("A", "D", 5);
		generalGraph.insertEdge("C", "E", 2);
		generalGraph.insertEdge("E", "B", 3);
		generalGraph.insertEdge("A", "E", 7);
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
		assertNotNull(acyclicalGraph);
	}
	
	@Test
	public void testGetEdges() {
		assertTrue(acyclicalGraph.edges("F").isEmpty());
		//TODO check edges
	}
	
	@Test
	public void testAddVertex() {
		acyclicalGraph.addVertex("Z");
		assertNotNull(acyclicalGraph.edges("Z"));
	}
	
	@Test
	public void testInsertEdge() throws GraphException {
		acyclicalGraph.insertEdge("C", "D", 5);
		LinkedList<Edge> edges = acyclicalGraph.edges("C");
		assertFalse(edges.isEmpty());
		
		//expect D to be the second element in list of edges from D
		Iterator<Edge> pointer = edges.iterator();
		pointer.next();
		assertEquals("D", pointer.next().terminal);
	}
	
	@Test(expected=GraphException.class)
	public void testInsertEdgeExceptionInvalid() throws GraphException {

		acyclicalGraph.insertEdge("A", "INVALID", 5);
	}
	
	@Test(expected=GraphException.class)
	public void testInsertEdgeExceptionDuplicate() throws GraphException {

		acyclicalGraph.insertEdge("C", "D", 5);
		acyclicalGraph.insertEdge("C", "D", 5);
	}

	@Test
	public void testCostNeighbour() throws GraphException {
		assertEquals(5, acyclicalGraph.costNeighbour("A", "B"));
	}
	
	@Test(expected=GraphException.class)
	public void testCostNeighbourWithException() throws GraphException {
		assertEquals(5, acyclicalGraph.costNeighbour("A", "Z"));
	}
	
	@Test
	public void testCost() throws GraphException {
		String[] path = {"A", "B", "D"};
		assertEquals(15, acyclicalGraph.cost(path));
		
		String[] path2 = {"B", "D", "E", "F"};
		assertEquals(22, acyclicalGraph.cost(path2));

		String[] path3 = {"A", "C", "E", "F"};
		assertEquals(21, acyclicalGraph.cost(path3));

		String[] path4 = {"A", "B", "E", "F"};
		assertEquals(20, acyclicalGraph.cost(path4));
	}
	
	@Test(expected=GraphException.class)
	public void testCostNoSuchPath() throws GraphException {
		String[] path = {"A", "B", "F"};
		assertEquals(15, acyclicalGraph.cost(path));
	}
	
	@Test(expected=GraphException.class)
	public void testCostTooShort() throws GraphException {
		String[] path = {"A"};
		assertEquals(15, acyclicalGraph.cost(path));
		
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
	
	@Test
	public void testDFTraverse() {
		Set<String> visited = new HashSet<String>();
		
		acyclicalGraph.dfTraverse("A", visited);
		assertTrue(visited.contains("A"));
		assertTrue(visited.contains("B"));
		assertTrue(visited.contains("C"));
		assertTrue(visited.contains("D"));
		assertTrue(visited.contains("E"));
		assertTrue(visited.contains("F"));

		visited = new HashSet<String>();
		acyclicalGraph.dfTraverse("D", visited);
		assertFalse(visited.contains("A"));
		assertFalse(visited.contains("B"));
		assertFalse(visited.contains("C"));
		assertTrue(visited.contains("D"));
		assertTrue(visited.contains("E"));
		assertTrue(visited.contains("F"));
		
	}

	@Test
	public void testPathsToLessThan() throws GraphException {
		assertEquals(5, acyclicalGraph.pathsTo("A", "F", 10, Conditions.LESSTHAN));
		assertEquals(0, acyclicalGraph.pathsTo("A", "F", 1, Conditions.LESSTHAN));
		
		assertEquals(2, generalGraph.pathsTo("C", "C", 3, Conditions.LESSTHAN));
		
	}
	
	@Test
	public void testPathsToExact() throws GraphException {
		assertEquals(3, acyclicalGraph.pathsTo("A", "F", 3, Conditions.EXACT));
		assertEquals(3, generalGraph.pathsTo("A", "C", 4, Conditions.EXACT));
	}
	
	@Test(expected=GraphException.class)
	public void testPathsToException() throws GraphException {
		generalGraph.pathsTo("INVALID", "INVALID", -50 , Conditions.EXACT);
	}
	
	@Test
	public void testShortestPath() throws GraphException {
		assertEquals(16, acyclicalGraph.shortestPath("A", "F"));
		
		assertEquals(9, generalGraph.shortestPath("A", "C"));
		assertEquals(9, generalGraph.shortestPath("B", "B"));
	}
	
//	@Test
//	public void testPrint() {
//		Iterator<Edge> pointer = graph.getEdges("D").iterator();
//		while(pointer.hasNext())
//			System.out.print(pointer.next().terminal + " ");
//	}
}
