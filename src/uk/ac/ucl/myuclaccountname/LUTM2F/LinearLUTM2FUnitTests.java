package uk.ac.ucl.myuclaccountname.LUTM2F;

import static org.junit.Assert.*;

import org.junit.Test;

public class LinearLUTM2FUnitTests extends LinearLUTM2F {

	@Test
	public void constructorTest1() {
		LinearLUTM2F instance = new LinearLUTM2F();
		
		assertTrue(instance.seq.empty());
	}
	
	@Test
	public void constructorTest2() {
		LinearLUTM2F instance = new LinearLUTM2F(22);

		assertTrue(instance.seq.empty());
		assertFalse(instance.seq.size() == 22);
	}
	
	@Test
	public void insertTest() throws SequenceListException {
		LinearLUTM2F instance = new LinearLUTM2F();
		
		instance.insert("test", "testvalue");
		assertTrue(instance.seq.size() == 1);
		assertTrue(instance.seq.first() == instance.seq.last());
		
		instance.insert("test2", "testvalue");
		assertTrue(instance.seq.size() == 2);
		assertTrue(instance.seq.first() != instance.seq.last());
	}
	
	@Test
	public void linearSearchTest() {
		LinearLUTM2F instance = createSomeLUT();
		
		assertTrue(instance.linearSearch(new Key("c")) >= 0);
		assertFalse(instance.linearSearch(new Key("Not a valid key")) >= 0);
	}
	
	@Test
	public void removeTest() throws KeyNotFoundInTableException, SequenceListException {
		LinearLUTM2F instance = createSomeLUT();
		
		//int random = ((int) Math.random() * 10) % 4;
		
		instance.remove("c");
		for(int i = 0; i < instance.seq.size(); i++){
			Entry currentEntry = (Entry)instance.seq.element(i);
			assertFalse(currentEntry.key.equals("c"));
		}	
	}
	
	@Test
	public void retrieveTestBasic() throws KeyNotFoundInTableException {
		LinearLUTM2F instance = createSomeLUT();
		
		assertTrue((int)instance.retrieve("b") == 'b');
	}
	
	@Test
	public void updateTestBasic() throws KeyNotFoundInTableException {
		LinearLUTM2F instance = createSomeLUT();
		
		instance.update("b", (int)'B');
		instance.update("c", (int)'C');
		
		assertTrue((int)instance.retrieve("c") == 'C');
		assertFalse((int)instance.retrieve("a") == 'B');
	}
	
	@Test
	public void retrieveTestM2F() throws KeyNotFoundInTableException {
		LinearLUTM2F instance = createSomeLUT();
		
		assertTrue((int)instance.retrieve("b") == 'b');
		assertTrue(instance.keyAt(0).equals(new Key("b")));
		assertTrue(instance.keyAt(1).equals(new Key("a")));
		assertTrue(instance.keyAt(2).equals(new Key("c")));
		assertTrue(instance.keyAt(3).equals(new Key("d")));
		
		assertFalse(instance.keyAt(1).equals(new Key("b")));
	}
	
	@Test
	public void updateTestM2F() throws KeyNotFoundInTableException {
		LinearLUTM2F instance = createSomeLUT();
		
		instance.update("b", (int)'B');
		instance.update("c", (int)'C');
		
		assertTrue(instance.keyAt(0).equals(new Key("c")));
		assertTrue(instance.keyAt(1).equals(new Key("b")));
		assertTrue(instance.keyAt(2).equals(new Key("a")));
		assertTrue(instance.keyAt(3).equals(new Key("d")));
	}
	
	@Test
	public void toStringTest() {
		LinearLUTM2F instance = createSomeLUT();
		
		String expected = "a:" + (int)'a' + ", " 
						+ "b:" + (int)'b' + ", " 
						+ "c:" + (int)'c' + ", " 
						+ "d:" + (int)'d' + ", ";
		assertTrue(expected.equals(instance.toString()));
	}
	
	private LinearLUTM2F createSomeLUT() {
		LinearLUTM2F instance = new LinearLUTM2F();
		
		instance.insert( "d", (int)'d');
		instance.insert( "c", (int)'c');
		instance.insert( "b", (int)'b');
		instance.insert( "a", (int)'a');
		
		return instance;
	}
	
}
