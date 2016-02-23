package uk.ac.ucl.myuclaccountname.UnionFind;

import static org.junit.Assert.*;

import org.junit.Test;

public class UnionFind3UnitTests extends UnionFind3{

	@Test
	public void find3Test() {
		UnionFind3 sets = constructSample();
		
		assertEquals(sets.parent[0], 5);	//F parent of A
		assertEquals(sets.parent[1], 0);	//A parent of B
		assertEquals(sets.parent[2], 0);	//A parent of C
		assertEquals(sets.parent[3], 5);	//F parent of D
		assertEquals(sets.parent[4], 5);	//F parent of E
		assertEquals(sets.parent[5], -1);	//F parent of F
		assertEquals(sets.parent[6], 5);	//F parent of G
		assertEquals(sets.parent[7], 0);	//A parent of H
		assertEquals(sets.parent[8], 5);	//F parent of I
		assertEquals(sets.parent[9], -1);	//J parent of J
	}
	
	private UnionFind3 constructSample() {
		UnionFind3 sets = new UnionFind3(10);
		
		sets.union(0,1); // A,B
		sets.union(2,7); // C,H
		sets.union(5,6); // F,G
		sets.union(5,8); // F,I
		sets.union(3,4); // D,E
		sets.union(0,7); // A,H
		sets.union(6,4); // G,E
		sets.union(4,7); // E,H
		
		return sets;
	}

}