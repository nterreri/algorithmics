package uk.ac.ucl.myuclaccountname.SequenceDLL;

public class SequenceDLListDriver extends SequenceDLList {
	public static void main(String args[]) {
		
		try {
			/*constructor test*/
			SequenceDLList constructorTest = new SequenceDLList();
			System.out.println("Constructor test: " + constructorTest.listHead);
			
			/*insertFirst test*/
			SequenceDLList insertFirst = new SequenceDLList();
			insertFirst.insertFirst(5);
			
			//list empty case: the list head == list tail, not null
			assert insertFirst.listHead != null;
			assert insertFirst.listTail != null;
			assert insertFirst.listHead == insertFirst.listTail;
			//value of head node is exactly 5:
			assert insertFirst.listHead.datum == Integer.valueOf(5);
			
			System.out.println("insertFirst test successful.\n");
			
			
			insertFirst.insertFirst(2.5);
			//list not empty case:
			System.out.println("Insert first (into non-empty list): \n" +
								"Head: datum should be 5, is " + insertFirst.listHead.datum +
								"Tail:" + insertFirst.listTail.datum);
			
		} catch(AssertionError e) {
			System.err.println("Assertion failure:\n");
			e.printStackTrace();
		}
		
		
		
		System.out.println("\n\nEnd of test");
	}
	
	
}
