package uk.ac.ucl.myuclaccountname.UnionFind;

public class UnionFind2 extends UnionFind {
	
	public UnionFind2() {
		super();
	}
	
	public UnionFind2(int size) {
		super(size);
	}
	
	@Override
	public int find(int n) {
		if (parent[n] < 0) {
			return n;
		} 
		
		int r = n;
		while (parent[r] >= 0) {
			System.out.println("looping1");
			r = parent[r];
		}
		
		while(parent[n] >= 0) {

			System.out.println("looping2");
			n = parent[n];
			parent[n] = r;
		}
		return r;
	}

}
