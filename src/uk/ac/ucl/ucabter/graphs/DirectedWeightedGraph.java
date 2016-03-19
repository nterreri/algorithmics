package uk.ac.ucl.ucabter.graphs;

import java.util.*;

import uk.ac.ucl.ucabter.graphs.DirectedWeightedGraph.Edge;

/**Concrete implementation of an adjacency-list based, weighted directed 
 * graph<p>
 * 
 * The implementation is inspired by 
 * C. Shaffer, 2013 (Java Version, rev. 3.2.0.10), <em>Data Structures & 
 * Algorithm Analysis</em>, available 
 * here: http://people.cs.vt.edu/~shaffer/Book/<p>
 * 
 * Many thanks also to Jens Krinke ( http://www0.cs.ucl.ac.uk/people/J.Krinke.html ),
 * who taught the author about data structures and algorithms.<p>
 * 
 * The current implementation uses HashMap, HashSet and LinkedList.<p>
 * 
 * It provides facilities to:
 * <ol>
 * <li>Calculate the length (cost) of a given route (path)</li>
 * <li>The number of possible routes between two points</li>
 * <li>The shortest route between two points</li>
 * </ol><p>*/
public class DirectedWeightedGraph<V> {
	
	/**Record of vertices and reachable from each*/
	protected Map<V, List<Edge>> vertices;
	/**Record of visited vertices (used in traversal and shortest path)*/
	protected Set<V> mark;
	
	/**Inner class defining each list element as a terminal, value pair. 
	 * The terminal is the vertex in the graph to which the edge is directed,
	 * the integer is the cost or weight associated with the edge.*/
	protected class Edge {
		protected V terminal;
		protected int weight;
		
		/**Constructs an edge towards the terminal with the given weight.
		 * The starting vertex is decided based on the list to which this
		 * edge belongs*/
		protected Edge(V terminal, int weight) {
			this.terminal = terminal;
			this.weight = weight;
		}
		
		public V getTerminal() {
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
		
	/**Constructs a default instance with an initial capacity of 10 that 
	 * increases automatically*/
	public DirectedWeightedGraph() {
		Init(10);
	}
	
	/**ConstructorS auxilliary method*/
	public void Init(int capacity) {
		InitHashMap(capacity);
	}
	
	private void InitHashMap(int capacity) {
		vertices = new HashMap<V, List<Edge>>(capacity);
	}
		
	/**Adds a new vertex to the graph*/
	public void addVertex(V vertex) {
		addVertexLinkedList(vertex);
	}
	
	private void addVertexLinkedList(V vertex) {
		vertices.put(vertex, new LinkedList<Edge>());
	}
	
	/**Returns a list of all vertices reachable from this vertex*/
	public List<Edge> edges(V vertex) {
		List<Edge> edges = vertices.get(vertex);
		return (edges == null ? null : edges);
	}
	
	/**Inserts an edge of specified cost (weight) from vertex start to vertex 
	 * destination*/
	public void insertEdge(V start, V destination, int cost) 
			throws GraphException{
		
		//destination vertex must exist in graph
		if(vertices.get(destination) == null) 
			throw new GraphException("destination vertex not in graph");
		
		List<Edge> edges = vertices.get(start);
		Iterator<Edge> edgePointer = edges.iterator();
		
		while(edgePointer.hasNext()) {
			Edge current = edgePointer.next();
			//edge must not already exist
			if(current.terminal == destination)
				throw new 
				GraphException("edge already exists between vertices");
		}
		
		edges.add(new Edge(destination, cost));
	}
	
	/**Returns the cost (weight) of the edge between two directly connected 
	 * vertices (neighbours)  */
	public int costNeighbour(V start, V destination) 
			throws GraphException {
		int result = 0;
		boolean found = false;
		Iterator<Edge> edgePointer = vertices.get(start).iterator();
		
		//destination must be in list of vertices reachable from the start
		while(edgePointer.hasNext()) {
			Edge current = edgePointer.next();
			if(current.terminal == destination) {
				result = current.weight;
				found = true;
			}
		}
		
		if(!found)
			throw new GraphException("no such destination vertex");
		return result;	
	}
		
	/**Returns the cost of a path between two vertices, will throw an exception 
	 * in case the path indicated is invalid*/
	public int cost(V[] path) throws GraphException {
		if(path.length <= 1)
			throw new GraphException("path too short");
		
		int accumulator = 0;
		for(int i = 0; i < (path.length - 1); i++) 
			accumulator += costNeighbour(path[i], path[i+1]);
		
		return accumulator;
	}
	
	/**Visits the graph depth-first from currentVertex parameter, returns a 
	 * record of vertices visited as a map from vertices labes to true boolean
	 * objects */
	protected void dfTraverse(V currentVertex, 
			Set<V> visited) {
		
		//visit action
		visited.add(currentVertex);
		
		Iterator<Edge> edgePointer = vertices.get(currentVertex).iterator();
		while(edgePointer.hasNext()) {
			Edge current = edgePointer.next();
			if(!visited.contains(current.terminal))
				dfTraverse(current.terminal, visited);
		}
		
	}
	
	/**Traverses the graph from the argument starting vertex*/
	public void doTraversal(V start) {
		//initialize mark record, record of visited vertices
		mark = new HashSet<V>(vertices.size() + 1, 1.0f);
		dfTraverse(start, mark);
	}

	/**Computes number of paths from start to destination that meet the limit 
	 * condition. Conditions are allowed to be either EXACT or LESSTHAN, 
	 * meaning only values that exactly match the limit parameter or are less 
	 * than the limit parameter, respectively, will be counted. Since the graph
	 * is directional but not acyclical (allows cycles).<p>
	 * 
	 * Such conditions are necessary in order to allow graph traversals to 
	 * revisit vertices after they have been visited (allows for cyclical 
	 * routes, that visit the same vertex multiple times) without looping 
	 * indefinitely. */
	public int pathsTo(V start, V destination, int limit, 
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
	
	/**Computes recursively all available paths from start to destination that 
	 * take less than or exactly the limit parameter number of junctures*/
	protected int pathsToLessThan(V start, V destination, int limit) {
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
	
	/**Computes recursively all available paths from start vertex to destination
	 * that exactly match the limit on the number of junctures taken*/
	protected int pathsToExact(V start, V destination, int limit) {
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
	
	/**Calculates the length of the shortest path between two vertices in the 
	 * graph*/
	public int shortestPath(V start, V destination) 
			throws GraphException {
		
		return dijkstraShortestPath(start, destination);
	}
	
	/**Implementation of Dijkstra's algorithm based on Map to Lists graph 
	 * structure. Delegated to by shortestPath() interface method*/
	private int dijkstraShortestPath(V start, V destination)
			throws GraphException {
		//reserve memory to store distances from start to each vertex, and to
		//keep track of which vertices have already been explored/exhausted/
		//visited
		mark = new HashSet<V>(vertices.size() + 1, 1.0f);
		Set<V> verticesSet = vertices.keySet();
		
		//initialize record of all distances
		//setting the initial capacity to 1 more than the size of the vertex map
		//setting load factor to 100% in order to ensure the hashmap will never
		//be rehashed while it exists 
		// see https://docs.oracle.com/javase/8/docs/api/java/util/HashMap.html
		HashMap<V, Integer> distances = 
				new HashMap<V, Integer>(vertices.size() + 1, 1.0f);
		
		//Stage 1: initialize all distances based on immediate accessiblity 
		//from start (i.e. from weight of edge between start and vertex, 
		//if any)
		
		//Initialize all distances to positive infinity
		for(V vertex : verticesSet) 
			distances.put(vertex, Integer.MAX_VALUE);
		//then compute actual distances from starting vertex to immediately
		//accessible vertices
		for(Edge edge : vertices.get(start)) {
			int cost = costNeighbour(start, edge.terminal);
			distances.put(edge.terminal, cost);
		}
		
		mark.add(start);
		V vertexPointer = start;
		
		//outer loop for stages 2 and 3
		for(int i = 0; i < vertices.size(); i++) {
			//set minimum distance variable to positive infinity
			int min = Integer.MAX_VALUE; 
			
			//Stage 2: determine next vertex closest to starting vertex 
			//(shortest path from startingVertex), from the current vertex 
			//pointer
			for(V vertex : verticesSet) {
				int distanceRecord = distances.get(vertex);
				//if the vertex has not been visited, 
				if(!mark.contains(vertex) && distanceRecord < min) {
					vertexPointer = vertex;//update pointer
					min = distanceRecord;//update smallest distance record for
					//target
				}
			}
			
			mark.add(vertexPointer);
			
			//Stage 3: update distances if element is reachable from current
			//vertex pointer and path from current vertex pointer to element is
			//shorter than recorded distance between start and element
			//That is, if there is a shorter path to the pointer element than
			//the one currently recorded
			for(Edge edge : vertices.get(vertexPointer)) {
				int cost = costNeighbour(vertexPointer, edge.terminal);
				int costFromStartingVertex = cost + min;
				if(costFromStartingVertex < distances.get(edge.terminal))
					distances.put(edge.terminal, costFromStartingVertex);
			}
			
		}
		
		return distances.get(destination);
	}
}
