package uk.ac.ucl.ucabter.graphs;

/**Inner class defining each list element as a terminal, value pair. 
 * The terminal is the vertex in the graph to which the edge is directed,
 * the integer is the cost or weight associated with the edge.*/
public class WeightedEdge<V> {
	protected V terminal;
	protected int weight;

	/**Constructs an edge towards the terminal with the given weight.
	 * The starting vertex is decided based on the list to which this
	 * edge belongs*/
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