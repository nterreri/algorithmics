package uk.ac.ucl.ucabter.graphs;

import java.util.Map;

/*Concrete implementation*/
public class DirectionalGraph<E, V> implements Graph<E, V> {
	
	protected Map<String, V> vertices; //record of all vertices
	protected Map<V, E> adjacencyMatryx; //record of connections between vertices
	
	@Override
	public void Init(int n) {
		// TODO Auto-generated method stub
		
	}
	
	

}
