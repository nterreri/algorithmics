package uk.ac.ucl.ucabter.graphs;

import java.util.*;

import uk.ac.ucl.ucabter.graphs.DirectionalGraph.Edge;

/*Concrete implementation of an adjacency-list based, weighted directional 
 * graph. The implementation is adapted from lecture notes delivered by 
 * Jens Kirke at UCL and a book on data structures by C. Shaffer, available 
 * here: http://people.cs.vt.edu/~shaffer/Book/
 * 
 * It provides facilities to:
 * <ol>
 * <li>Calculate the length (cost) of a given route (path)</li>
 * <li>The number of possible routes between two points</li>
 * <li>The shortest route between two points</li>
 * </ol>*/
public class DirectionalGraph implements Graph {
	protected HashMap<String, LinkedList<Edge>> vertices;//record of vertices
	protected Map<String, Boolean> mark;//record of visited bits
	
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
	
		@Override
		public String toString() {
			return this.terminal.toString();
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
	public LinkedList<Edge> edges(String vertex) {
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
	protected void dfTraverse(String currentVertex, 
			Map<String, Boolean> visited) {
		
		//visit action
		//addEdge to path (from where? return it to what?)
		visited.put(currentVertex, true);
		
		Iterator<Edge> edgePointer = vertices.get(currentVertex).iterator();
		while(edgePointer.hasNext()) {
			Edge current = edgePointer.next();
			if(visited.get(current.terminal) == null)
				dfTraverse(current.terminal, visited);
		}
		
	}
	
	/*Traverses the graph from the argument starting vertex*/
	public void doTraversal(String start) {
		//initialize mark record, record of visited vertices
		mark = new HashMap<String, Boolean>(vertices.size());
		dfTraverse(start, mark);
	}

	/*Computes number of paths from start to destination that meet the limit 
	 * condition. Conditions are allowed to be either EXACT or LESSTHAN, 
	 * meaning only values that exactly match the limit parameter or are less 
	 * than the limit parameter, respectively, will be counted. Since the graph
	 * is directional but not acyclical (allows cycles).<p>
	 * 
	 * Such conditions are necessary in order to allow graph traversals to 
	 * revisit vertices after they have been visited (allows for cyclical 
	 * routes, that visit the same vertex multiple times) without looping 
	 * indefinitely. */
	public int pathsTo(String start, String destination, int limit, 
			Conditions c) throws GraphException {
		if(vertices.get(start) == null || vertices.get(destination) == null)
			throw new GraphException("No such vertex");
		
		switch(c) {
		case LESSTHAN:
			return pathsToLessThan(start, destination, limit - 1);
		case EXACT:
			return pathsToExact(start, destination, limit - 1);
		default:
			return 0;
		}
	}
	
	/*Computes recursively all available paths from start to destination that 
	 * take less than or exactly the limit parameter number of junctures*/
	protected int pathsToLessThan(String start, String destination, int limit) {
		//each recursion step uses its own local accumulator
		int accumulator = 0;

		//iterate through available edges from current node
		for(Edge edge : edges(start)) {
			
			//stop if no of junctures is strictly larger than limit
			if(limit < 0)
				break;
			
			//increase path No accumulator if destination is reached
			if(edge.terminal == destination) 
				++accumulator;
			
			//recurse over next available non-terminal edge
			else if(!edges(edge.terminal).isEmpty())
				accumulator += 
				pathsToLessThan(edge.terminal, destination, limit - 1);
		}

		return accumulator;
	}
	
	/*Computes recursively all available paths from start vertex to destination
	 * that exactly match the limit on the number of junctures taken*/
	protected int pathsToExact(String start, String destination, int limit) {
		//each recursion step uses its own local accumulator
		int accumulator = 0;
		
		//iterate through available edges from current node
		for(Edge edge : edges(start)) {
			
			//stop if no of junctures is strictly larger than limit
			if(limit < 0)
				break;
			
			//increase path No accumulator if destination is reached AND No of
			//junctures matches condition
			if(limit == 0 && edge.terminal == destination) 
				++accumulator;
			
			//recurse over next available non-terminal edge
			else if(!edges(edge.terminal).isEmpty())
				accumulator += 
				pathsToExact(edge.terminal, destination, limit - 1);
		}
		
		return accumulator;
	}
	
	/*Calculates the length of the shortest path between two vertices in the 
	 * graph*/
	public int shortestPath(String start, String destination) {
		
		return 0;
	}
}
