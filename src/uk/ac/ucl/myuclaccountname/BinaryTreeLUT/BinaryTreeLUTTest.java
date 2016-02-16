package uk.ac.ucl.myuclaccountname.BinaryTreeLUT;

import static org.junit.Assert.*;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

import org.hamcrest.core.IsInstanceOf;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class BinaryTreeLUTTest extends BinaryTreeLUT {



	@Test
	public void findRightmostTest() throws KeyNotFoundInTableException {
		BinaryTreeLUT instance = createSampleInstance();

		//System.out.println(instance.findRightmost(instance.root.right).kvPair.key.toString());


		assertTrue(instance.findRightmost(instance.root).kvPair.
				key.equals(new Key("A")));//expect A

		assertTrue(instance.findRightmost(
				instance.getFromTree(new Key("L"), instance.root)).kvPair.
				key.equals(new Key("A")));//expect A
	}

	@Test
	public void removeLeftTest() throws KeyNotFoundInTableException {
		BinaryTreeLUT instance = createSampleInstance();

		instance.remove("M");

		//L has N to its left:
		assertTrue(instance.root.right.right.left.kvPair.key.equals(new Key("N")));
	}

	@Test
	public void removeRightTest() throws KeyNotFoundInTableException {
		BinaryTreeLUT instance = createSampleInstance();

		instance.remove("O");
		
		//P has L to its right:
		assertTrue(instance.root.right.kvPair.key.equals(new Key("L")));
	}

	@Test 
	public void removeGeneralTest() throws KeyNotFoundInTableException {
		BinaryTreeLUT instance = createSampleInstance();

		//ACT
		instance.remove("L");

		//NOTE: After moving the rightmost node to another position, the order of 
		//the elements in the tree is compromised: therefore getFromTree is
		//no longer reliable:
		//assertNotNull(instance.getFromTree(new Key("I"), instance.root).right);
		//It thinks that I is to the left of A, when it is to the right

		//A is where L was
		assertTrue(instance.root.right.right.kvPair.key.equals(new Key("M")));

		//verify that A is no longer where it was
		assertFalse(instance.root.right.right.left.kvPair.key.equals(new Key("M")));

		//verify that N is still attached to M
		assertTrue(instance.root.right.right.left.kvPair.key.equals(new Key("N")));
	}

	@Test 
	public void removeGeneralRootNodeTest() throws KeyNotFoundInTableException {
		BinaryTreeLUT instance = createSampleInstance();

		//ACT
		instance.remove("P");

		//NOTE: After moving the rightmost node to another position, the order of 
		//the elements in the tree is compromised: therefore getFromTree is
		//no longer reliable, see note in removeGeneralTest() above

		//Q is where P was
		assertTrue(instance.root.kvPair.key.equals(new Key("Q")));

		//verify that Q is no longer where it was
		assertNull(instance.root.left);

		//Verify that right subtree unaltered
		assertTrue(instance.root.right.kvPair.key.equals(new Key("O")));

	}

	@Test
	public void removeGeneralRightnodeTest() throws KeyNotFoundInTableException {
		BinaryTreeLUT instance = createSampleInstance();


		System.out.println("Original:\n" + instance.toString());
		//ACT
		instance.remove("G");
		System.out.println(instance.toString());
		//NOTE: After moving the rightmost node to another position, the order of 
		//the elements in the tree is compromised: therefore getFromTree is
		//no longer reliable:
		//assertNotNull(instance.getFromTree(new Key("I"), instance.root).right);
		//It thinks that I is to the left of A, when it is to the right

		//H is where G was
		assertTrue(instance.root.right.right.right.right.kvPair.key.equals(new Key("H")));

		//verify that H is no longer where it was
		assertNull(instance.root.right.right.right.right.left);

		
	}

	private BinaryTreeLUT createSampleInstance() {
		//ArrayBlockingQueue<Integer> keysAndData = new ArrayBlockingQueue<Integer>(20);
		ArrayBlockingQueue<String> keysAndData = 
				new ArrayBlockingQueue<String>(20);
		BinaryTreeLUT result = new BinaryTreeLUT();

		keysAndData.add("P");
		keysAndData.add("Q");
		keysAndData.add("O");
		keysAndData.add("L");
		keysAndData.add("M");
		keysAndData.add("N");
		keysAndData.add("I");
		keysAndData.add("G");
		keysAndData.add("H");
		keysAndData.add("A");
		keysAndData.add("D");
		keysAndData.add("E");
		keysAndData.add("F");
		keysAndData.add("C");
		keysAndData.add("B");


		while(keysAndData.size() > 0)
			result.insert(keysAndData.peek()/*.toString()*/, keysAndData.poll());

		//System.out.println(result.toString());
		return result;
	}

}
