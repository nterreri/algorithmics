package uk.ac.ucl.ucabter.graphs;

import java.util.*;

import uk.ac.ucl.ucabter.graphs.DirectionalGraph.Edge;

/*Concrete implementation of an adjacency-list based, weighted directional 
 * graph.
 * 
 * It provides facilities to:
 * <ol>
 * <li>Calculate the length (cost) of a given route (path)</li>
 * <li>The number of possible routes between two points</li>
 * <li>The shortest route between two points</li>
 * </ol>*/
public class DirectionalGraph implements Graph {
	private HashMap<String, LinkedList<Edge>> vertices;
	
	/*Inner class defining each list element as a terminal, value pair. 
	 * The terminal is the vertex in the graph to which the edge is directed,
	 * the integer is the cost or weight associated with the edge.*/
	protected class Edge {
		protected String terminal;
		protected int weight;
		
		/*Constructs an edge towards the terminal with the given weight.
		 * The starting vertex is decided based on the list to which this
		 * edge belongs*/
		protected Edge(String terminal, int weight) {
			this.terminal = terminal;
			this.weight = weight;
		}
		
		public String getTerminal() {
			return terminal;
		}
		
		public int getWeight()  {
			return weight;
		}
	}
	
	/*Inner class providing structured definition of a path (route) within the 
	 * network, alternative to String[].*/
	protected class Path {
		protected LinkedList<Edge> edgesInOrder = new LinkedList<Edge>();
		
		protected Path() {}
		
		/*Appends an edge to the beginning of the path*/
		protected void addEdge(Edge e) {
			edgesInOrder.addFirst(e);
		}
	}
	
	/*Constructs a default instance with an initial capacity of 10 that 
	 * increases automatically*/
	public DirectionalGraph() {
		Init(10);
	}
	
	
	/*ConstructorS auxilliary method*/
	private void Init(int size) {
		vertices = new HashMap<String, LinkedList<Edge>>(size);
	}
	
	
	/*Adds a new vertex to the graph*/
	public void addVertex(String vertex) {
		vertices.put(vertex, new LinkedList<Edge>());
	}
	
	
	/*Returns a list of all vertices reachable from this vertex*/
	public LinkedList<Edge> getEdges(String vertex) {
		LinkedList<Edge> edges = vertices.get(vertex);
		return (edges == null ? null : edges);
	}
	
	
	/*Inserts an edge of specified cost (weight) from vertex start to vertex 
	 * destination*/
	public void insertEdge(String start, String destination, int cost) 
			throws GraphException{
		
		//destination vertex must exist in graph
		if(vertices.get(destination) == null) 
			throw new GraphException("destination vertex not in graph");
		
		LinkedList<Edge> edges = vertices.get(start);
		Iterator<Edge> edgePointer = edges.iterator();
		
		while(edgePointer.hasNext()) {
			Edge current = edgePointer.next();
			//edge must not already exist
			if(current.terminal.compareTo(destination) == 0)
				throw new 
				GraphException("edge already exists between vertices");
		}
		
		edges.add(new Edge(destination, cost));
	}
	
	
	/*Returns the cost (weight) of the edge between two directly connected 
	 * vertices (neighbours)  */
	public int costNeighbour(String start, String destination) 
			throws GraphException {
		int result = 0;
		boolean found = false;
		Iterator<Edge> edgePointer = vertices.get(start).iterator();
		
		//destination must be in list of vertices reachable from the start
		while(edgePointer.hasNext()) {
			Edge current = edgePointer.next();
			if(current.terminal.compareTo(destination) == 0) {
				result = current.weight;
				found = true;
			}
		}
		
		if(!found)
			throw new GraphException("no such destination vertex");
		return result;	
	}
	
	
	/*Returns the cost of a path between two vertices, will throw an exception 
	 * in case the path indicated is invalid*/
	public int cost(String[] path) throws GraphException {
		if(path.length <= 1)
			throw new GraphException("path too short");
		
		int accumulator = 0;
		for(int i = 0; i < (path.length - 1); i++) 
			accumulator += costNeighbour(path[i], path[i+1]);
		
		return accumulator;
	}
	
	
	/*Visits the graph depth-first from currentVertex parameter, returns a 
	 * record of vertices visited as a map from vertices labes to true boolean
	 * objects */
	protected Map<String, Boolean> dfTraverse(String currentVertex, 
			Map<String, Boolean> visited) {
		
		//visit action
		visited.put(currentVertex, true);
		
		Iterator<Edge> edgePointer = vertices.get(currentVertex).iterator();
		while(edgePointer.hasNext()) {
			Edge current = edgePointer.next();
			if(visited.get(current.terminal) == null)
				dfTraverse(current.terminal, visited);
		}
		
		return visited;
	}
	
	
}
