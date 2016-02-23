package uk.ac.ucl.myuclaccountname.UnionFind;

public class UnionFind3 extends UnionFind {

	public UnionFind3() {
		super();
	}
	
	public UnionFind3(int size) {
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
		
		for(int i = 0; i < parent.length; i++) {
			System.out.println("looping2");

			parent[i] = r;
		}
		
		return r;
	}

}
