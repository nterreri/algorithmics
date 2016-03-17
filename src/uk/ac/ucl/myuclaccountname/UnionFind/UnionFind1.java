package uk.ac.ucl.myuclaccountname.UnionFind;

public class UnionFind1 extends UnionFind {

	public UnionFind1() {
		super();
	}
	
	public UnionFind1(int size) {
		super(size);
	}

	@Override
	protected int find(int n) {
		// TODO Auto-generated method stub
		return 0;
	}
	
//	@Override
//	public int find(int n) {
//		if (parent[n] < 0) {
//			return n;
//		} else {
//			int r = find(parent[n]);
//			parent[n] = r;
//			return r;
//		}
//	}
}
