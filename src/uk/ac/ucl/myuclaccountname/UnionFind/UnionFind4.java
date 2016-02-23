package uk.ac.ucl.myuclaccountname.UnionFind;

public class UnionFind4 extends UnionFind {

	public UnionFind4() {
		super();
	}
	
	public UnionFind4(int size) {
		super(size);
	}
	
	@Override
	public int find(int n) {
		if (parent[n] < 0) {
			return n;
		} 
		
		int r = n;
		while (parent[r] >= 0) {
			r = parent[r];
		}
		
		while(parent[n] >= 0) {
			int temp = n;
			n = parent[n];
			parent[temp] = r;
		}
		return r;
	}

}
