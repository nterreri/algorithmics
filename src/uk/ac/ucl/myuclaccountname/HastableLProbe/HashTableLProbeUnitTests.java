package uk.ac.ucl.myuclaccountname.HastableLProbe;

import static org.junit.Assert.*;

import org.junit.Test;

public class HashTableLProbeUnitTests extends HashTableLProbe{

	@Test
	public void resizeTest() {
		HashTableLProbe table = null;

		try {
			table = constructTable();
		} catch (TableOverflowException e) {
			fail("Failed to construct test table");
		}

		try {
			table.resize(10);
		} catch (TableOverflowException e) {
			fail("Unexpected exception thrown when resizing.");
		}

		//postconditions: table.entries.lenght is 10
		//postconditions: all elements from the original table are in the new
		assertTrue(table.entries.length == 10);

		//using high level method retrieve()
		try {
			assertEquals(new Integer(41), table.retrieve("Priscilla"));
			assertEquals(new Integer(34), table.retrieve("Travis"));
			assertEquals(new Integer(28), table.retrieve("Samuel"));
			assertEquals(new Integer(39), table.retrieve("Helena"));
			assertEquals(new Integer(14), table.retrieve("Andrew"));
			assertEquals(new Integer(24), table.retrieve("Kay"));
			assertEquals(new Integer(67), table.retrieve("John"));
		} catch (KeyNotFoundInTableException e) {
			fail("Could not find expected key in table after rehash");
		}

		//attempting to shrink too much should generate an exception:
		try {
			table.resize(1);
		} catch (TableOverflowException e) {
			assertEquals(e.getMessage(), "resize(newSize) called "
					+ "with newSize < number of elements already in "
					+ "the table.");
		}
	}
	
	@Test
	public void resizeAdvancedTest() {
		HashTableLProbe table = null;

		try {
			table = constructTableAdvanced();
		} catch (TableOverflowException | KeyNotFoundInTableException e) {
			fail("Failed to construct test table");
		}

		try {
			table.resize(10);
		} catch (TableOverflowException e) {
			fail("Unexpected exception thrown when resizing.");
		}

		//postconditions: table.entries.lenght is 10
		//postconditions: all elements from the original table are in the new
		assertTrue(table.entries.length == 10);

		//using high level method retrieve()
		try {
			assertEquals(new Integer(41), table.retrieve("Priscilla"));
			assertEquals(new Integer(34), table.retrieve("Travis"));
			assertEquals(new Integer(28), table.retrieve("Samuel"));
			assertEquals(new Integer(39), table.retrieve("Helena"));
			assertEquals(new Integer(14), table.retrieve("Andrew"));
			assertEquals(new Integer(24), table.retrieve("Kay"));
			assertEquals(new Integer(67), table.retrieve("John"));
		} catch (KeyNotFoundInTableException e) {
			fail("Could not find expected key in table after rehash");
		}

		//attempting to shrink too much should generate an exception:
		try {
			table.resize(1);
		} catch (TableOverflowException e) {
			assertEquals(e.getMessage(), "resize(newSize) called "
					+ "with newSize < number of elements already in "
					+ "the table.");
		}
	}

	@Test
	public void delete2Test() {
		HashTableLProbe table = null;

		try {
			table = constructTable();
		} catch (TableOverflowException e) {
			fail("Failed to construct test table");
		}
		
		int tombstonesBefore = countTombstones(table.entries);
		int tombstonesAfter;
		
		//act
		try {
			table.delete2("Kay");
		} catch (KeyNotFoundInTableException e) {
			fail("could not find expected key for deletion in the array");
		} catch (TableOverflowException e) {
			fail(e.getMessage());
		}

		//postcondition: the target key cannot be found
		//postcondition: all other keys can still be found
		//postcondition: #no of tombstones <= previous no of tombstones
		try {
			table.retrieve("Kay");
		} catch (KeyNotFoundInTableException e) {
			System.err.println("KeyNotFoundInTableException: unable to find key");
			assertEquals(e.getMessage(), null);
		}

		//using high level method retrieve()
		try {
			assertEquals(new Integer(41), table.retrieve("Priscilla"));
			assertEquals(new Integer(34), table.retrieve("Travis"));
			assertEquals(new Integer(28), table.retrieve("Samuel"));
			assertEquals(new Integer(39), table.retrieve("Helena"));
			assertEquals(new Integer(14), table.retrieve("Andrew"));
			//assertEquals(new Integer(24), table.retrieve("Kay"));
			assertEquals(new Integer(67), table.retrieve("John"));
		} catch (KeyNotFoundInTableException e) {
			fail("Could not find expected key in table after rehash");
		}
		
		tombstonesAfter = countTombstones(table.entries);
		
		assertTrue(tombstonesAfter <= tombstonesBefore);
	}
	
	@Test
	public void delete2AdvancedTest() {
		HashTableLProbe table = null;

		try {
			table = constructTableAdvanced();
		} catch (TableOverflowException | KeyNotFoundInTableException e) {
			fail("Failed to construct test table");
		}
		
		int tombstonesBefore = countTombstones(table.entries);
		int tombstonesAfter;
		
		//act
		try {
			table.delete2("Kay");
		} catch (KeyNotFoundInTableException e) {
			fail("could not find expected key for deletion in the array");
		} catch (TableOverflowException e) {
			fail(e.getMessage());
		}

		//postcondition: the target key cannot be found
		//postcondition: all other keys can still be found
		//postcondition: #no of tombstones <= previous no of tombstones
		try {
			table.retrieve("Kay");
		} catch (KeyNotFoundInTableException e) {
			System.err.println("KeyNotFoundInTableException: unable to find key");
			assertEquals(e.getMessage(), null);
		}

		//using high level method retrieve()
		try {
			assertEquals(new Integer(41), table.retrieve("Priscilla"));
			assertEquals(new Integer(34), table.retrieve("Travis"));
			assertEquals(new Integer(28), table.retrieve("Samuel"));
			assertEquals(new Integer(39), table.retrieve("Helena"));
			assertEquals(new Integer(14), table.retrieve("Andrew"));
			//assertEquals(new Integer(24), table.retrieve("Kay"));
			assertEquals(new Integer(67), table.retrieve("John"));
		} catch (KeyNotFoundInTableException e) {
			fail("Could not find expected key in table after rehash");
		}
		
		tombstonesAfter = countTombstones(table.entries);
		
		assertTrue(tombstonesAfter <= tombstonesBefore);
	}

	private HashTableLProbe constructTable() throws TableOverflowException {
		HashTableLProbe table = new HashTableLProbe();

		table.insert("Priscilla", new Integer(41));
		table.insert("Travis", new Integer(34));
		table.insert("Samuel", new Integer(28));
		table.insert("Helena", new Integer(39));
		table.insert("Andrew", new Integer(14));
		table.insert("Kay", new Integer(24));
		table.insert("John", new Integer(67));

		return table;
	}
	
	private HashTableLProbe constructTableAdvanced() throws TableOverflowException, KeyNotFoundInTableException {
		HashTableLProbe table = new HashTableLProbe(10);

	      table.insert("Priscilla", new Integer(41));
	      table.insert("Travis", new Integer(34));
	      table.insert("Samuel", new Integer(28));
	      table.insert("Helena", new Integer(39));
	      table.insert("Andrew", new Integer(14));
	      table.insert("Kay", new Integer(24));
	      table.insert("John", new Integer(67));

	      table.delete("Travis");
	      table.delete("John");

	      table.insert("Dani", new Integer(15));
	      table.insert("John", new Integer(67));
	      table.insert("Travis", new Integer(34));

	      table.insert("Bob", new Integer(52));

		return table;
	}
	
	private int countTombstones(Entry[] entries) {
		
		int tombstones = 0;
		
		for(int i = 0; i < entries.length; i++) {
			if(entries[i] != null && entries[i].key.equals("Tombstone"))
				++tombstones;
		}
		
		return tombstones;
	}
}
