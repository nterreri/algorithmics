package uk.ac.ucl.myuclaccountname.UnionFind;

public abstract class UnionFind {
	protected int [] parent;
	
	public UnionFind() {
		final int DEFAULTSIZE = 10;
		parent = new int[DEFAULTSIZE];
		
		for(int i = 0; i < DEFAULTSIZE; i++)
			parent[i] = -1;
	}
	
	public UnionFind(int size) {
		parent = new int[size];
		
		for(int i = 0; i < size; i++)
			parent[i] = -1;
	}

	public boolean equivalent(int a, int b) {
		int root1 = find(a);
		int root2 = find(b);
		return root1 == root2;
	}

	public void union(int a, int b) {
		int root1 = find(a);
		int root2 = find(b);
		if (root1 != root2) {
			parent[root2] = root1;
		}
	}

	protected abstract int find(int n);
}


