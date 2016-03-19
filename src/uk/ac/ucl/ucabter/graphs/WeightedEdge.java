package uk.ac.ucl.ucabter.graphs;

/**A terminal, value pair.<p> 
 * The terminal is the vertex in the graph to which the edge is connected 
 * (and/or directed), the integer is the cost or weight associated with the edge.*/
public class WeightedEdge<V> {
	protected V terminal;
	protected int weight;

	/**Constructs an edge to the terminal with the given weight.*/
	protected WeightedEdge(V terminal, int weight) {
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