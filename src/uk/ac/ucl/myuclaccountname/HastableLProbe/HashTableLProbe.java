package uk.ac.ucl.myuclaccountname.HastableLProbe;

class KeyNotFoundInTableException extends Exception {
	/* Rudimentary exception - should be extended */
}

class TableOverflowException extends Exception {
	
	public TableOverflowException() {
		super();
	}
	
	public TableOverflowException(String message) {
		super(message);
	}
}

public class HashTableLProbe {

	/**
	 * Entries in the hash table arry.
	 */
	protected class Entry {
		protected String key;
		protected Object value;

		public Entry(String k, Object v) {
			key = k;
			value = v;
		}
	}

	/**
	 * The hash table is implemented by an array of entries.
	 */
	protected Entry[] entries;

	/**
	 * Compute a hash over a string and map it into a given range.
	 */
	private int h1(String key, int M) {
		int n = 0;

		for (int i=0; i < key.length(); i++) {
			n += (int)key.charAt(i);
		}

		return n % M;
	}

	/**
	 * Default constructor.
	 */
	public HashTableLProbe() {
		entries = new Entry[50];
	}

	/**
	 * Constructor for a given hash table size.
	 */
	public HashTableLProbe(int size) {
		entries = new Entry[size];
	}

	/**
	 * Map a key to a value (insert into the hash table).
	 */
	public void insert(String key, Object value)
			throws TableOverflowException {
		// Compute the hash value
		int index = h1(key, entries.length);

		// Probe linearly to find empty slot.
		int count = 0;

		while (entries[index] != null &&
				(!entries[index].key.equals("Tombstone"))
				&& count != entries.length) {
			index = (index+1) % entries.length;
			count += 1;
		}

		if (count == entries.length) {
			throw new TableOverflowException();
		} else {
			entries[index] = new Entry(key, value);
		}
	}

	/**
	 * Find the value which is mapped to a key.
	 */
	public Object retrieve(String key)
			throws KeyNotFoundInTableException {
		// Compute the hash value
		int index = h1(key, entries.length);

		// Probe linearly looking for match.
		int count = 0;

		while (entries[index] != null &&
				(!entries[index].key.equals(key))
				&& count != entries.length) {
			index = (index+1) % entries.length;
			count += 1;
		}

		if (entries[index] == null || count == entries.length) {
			throw new KeyNotFoundInTableException();
		}

		return entries[index].value;
	}

	/**
	 * Delete a mapping for a key.
	 */
	public void delete(String key)
			throws KeyNotFoundInTableException {
		// Compute the hash value
		int index = h1(key, entries.length);

		// Probe linearly looking for match.
		int count = 0;

		while (entries[index] != null &&
				(!entries[index].key.equals(key))
				&& count != entries.length) {
			index = (index+1) % entries.length;
			count += 1;
		}

		if (entries[index] == null || count == entries.length) {
			throw new KeyNotFoundInTableException();
		}

		entries[index].key = "Tombstone";
	}

	/**
	 * Delete a mapping for a key without creating a tombstone.
	 * @throws TableOverflowException 
	 */
	public void delete2(String key)
			throws KeyNotFoundInTableException, TableOverflowException {
		// Like delete, except that the entry is not replaced by a tombstone.
		// Instead, the entry will be deleted (set to null).
		// This requires a cleanup and rehashing all succeeding entries.
		
		//look for matching hash:
		int index = h1(key, entries.length);
		int count = 0;
		
		while(entries[index] != null && !entries[index].key.equals(key)
				&& count < entries.length) {
			index = (index + 1) % entries.length;
			++count;
		}
		
		//if none can be found, complain:
		if(entries[index] == null || count >= entries.length)
			throw new KeyNotFoundInTableException();
		
		//if found, delete reference:
		entries[index] = null;
		
		//chain through successive not null keys from deleted element (including
		//tombstones), peek at the next element in the succession to check if 
		//it is null:
		while(entries[(index + 1) % entries.length] != null) {
			//move pointer "down"
			index = (index + 1) % entries.length;
			
			//if entry is a tombstone, then do nothing, otherwise:
			if(!entries[index].key.equals("Tombstone")) {
				
				//create temporary entry record
				Entry temp = new Entry(entries[index].key, 
										entries[index].value);
				
				//delete reference in array
				entries[index] = null;
				
				//rehash entry from temp record
				try {
					insert(temp.key, temp.value);
				} catch(TableOverflowException e) {
					throw new TableOverflowException("Failed to rehash after "
							+ "element deletion: element was deleted but array "
							+ "became full before subsequent elements could be "
							+ "rehashed, data structure may have only been "
							+ "partially rehashed");
				}
			}
		}
	}

	/**
	 * Resize the current hash table to a different size.
	 *
	 * Resizing can be done to enlarge or to shrink the hash table. If
	 * the resized hash table cannot hold all elements of the previous
	 * one, an exception will be thrown.
	 */
	public void resize(int newSize)
			throws TableOverflowException {
		// Replaces the entries array with a new one in which all
		// old entries are inserted fresh (`rehashing').
		Entry[] rehash = new Entry[newSize];
		
		//for each entry in the table before rehashing that is neither null
		//nor a "Tombstone", insert that entry into the new array, dealing with
		//conflicts by probing linearly, and through Tombstones (so the 
		//search() method still works as intended)
		for(int i = 0; i< entries.length; i++) {
			if(entries[i] != null && !entries[i].key.equals("Tombstone")) {
				
				int j = h1(entries[i].key, newSize);
				int count = 0;
				
				while(rehash[j] != null && !rehash[j].key.equals("Tombstone")
						&& count < rehash.length) {
					j = (j + 1) % rehash.length;
					++count;
				}
				
				if(count >= rehash.length)
					throw new TableOverflowException("resize(newSize) called "
							+ "with newSize < number of elements already in "
							+ "the table.");
				else
					rehash[j] = new Entry(entries[i].key, entries[i].value);
			}
		}
		
		//replace old array with new
		entries = rehash;
	}

	/**
	 * Return a textual representation of the hash table.
	 */
	public String toString() {
		String str = "";
		for (int i = 0; i < entries.length; ++i) {
			Entry e = entries[i];
			str += i + ": ";
			if (e != null) {
				str += e.key + " " + e.value + "\n";
			} else {
				str += null + "\n";
			}
		}
		return str;
	}
}