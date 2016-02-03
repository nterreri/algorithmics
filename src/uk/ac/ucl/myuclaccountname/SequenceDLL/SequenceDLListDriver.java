package uk.ac.ucl.myuclaccountname.SequenceDLL;

public class SequenceDLListDriver extends SequenceDLList {
	public static void main(String args[]) {
		
		try {
			/*constructor test*/
			SequenceDLList constructorTest = new SequenceDLList();
			assert constructorTest.listHead == null;
			assert constructorTest.listTail == null;
			System.out.println("Constructor test successful.");
			
			/*insertFirst test*/
			SequenceDLList insertFirst = new SequenceDLList();
			insertFirst.insertFirst(5);
			
			//list empty case: the list head == list tail, not null
			assert insertFirst.listHead != null;
			assert insertFirst.listTail != null;
			assert insertFirst.listHead == insertFirst.listTail;
			//value of head node is exactly 5:
			assert insertFirst.listHead.datum == Integer.valueOf(5);
			System.out.println("insertFirst first test successful.");
			//list not empty case:
			insertFirst.insertFirst(2.5);
			assert insertFirst.listHead != null;
			assert insertFirst.listTail != null;
			assert insertFirst.listHead != insertFirst.listTail;
			assert (double)insertFirst.listHead.datum == (2.5);
			assert insertFirst.listHead.next == insertFirst.listTail;
			assert insertFirst.listHead.previous == null;
			assert insertFirst.listTail.datum == Integer.valueOf(5);
			assert insertFirst.listTail.next == null;
			assert insertFirst.listTail.previous == insertFirst.listHead;
			System.out.println("insertFirst second test successful.");
			
			
			/*insertLast()*/
			//list empty case:
			SequenceDLList insertLast = new SequenceDLList();
			insertLast.insertLast(5);
			assert insertLast.listHead != null;
			assert insertLast.listTail != null;
			assert insertLast.listHead == insertLast.listTail;
			assert insertLast.listTail.datum == Integer.valueOf(5);
			System.out.println("insertLast first test successful.");
			//list not empty case:
			insertLast.insertLast(10);
			assert insertLast.listHead != null;
			assert insertLast.listTail != null;
			assert insertLast.listHead != insertLast.listTail;
			assert insertLast.listHead.datum == Integer.valueOf(5);
			assert insertLast.listHead.next == insertLast.listTail;
			assert insertLast.listHead.previous == null;
			assert insertLast.listTail.datum == Integer.valueOf(10);
			assert insertLast.listTail.next == null;
			assert insertLast.listTail.previous == insertLast.listHead;
			System.out.println("insertLast second test successful.");
			
			/*insert()*/
			SequenceDLList insert = new SequenceDLList();
			insert.insert('c', 0);
			insert.insert('a', 0);
			//test cases where index is 0
			assert (char)insert.listHead.datum == 'a';
			assert insert.listHead.next == insert.listTail;
			assert (char)insert.listTail.datum == 'c'; 
			assert insert.listTail.previous == insert.listHead;
			System.out.println("insert first test successful.");
			//general case:
			insert.insert('b', 1);
			assert (char)insert.listHead.datum == 'a';
			assert (char)insert.listTail.datum == 'c';
			assert (char)insert.listHead.next.datum == 'b';
			assert (char)insert.listTail.previous.datum == 'b';
			assert (char)insert.listHead.next.previous.datum == 'a';
			assert (char)insert.listHead.next.next.datum == 'c';
			assert insert.listHead.next.next == insert.listTail;
			assert insert.listTail.previous.previous == insert.listHead;
			System.out.println("insert second test successful.");
			
			/*deleteFirst()*/
			SequenceDLList df = createSampleList();
			df.deleteFirst();
			assert (char)df.listHead.datum == 'b';
			assert df.listHead.previous == null;
			System.out.println("deleteFirst test successful.");
			
			/*deleteLast()*/
			SequenceDLList dl = createSampleList();
			dl.deleteLast();
			assert (char)dl.listTail.datum == 'b';
			assert dl.listTail.next == null;
			assert dl.listTail.previous == dl.listHead;
			System.out.println("deleteLast test successful.");
			
			/*delete()*/
			SequenceDLList del = createSampleList();
			del.delete(1);
			assert (char)del.listHead.datum == 'a';
			assert del.listHead.next == del.listTail;
			assert (char)del.listTail.datum == 'c';
			assert del.listTail.previous == del.listHead;
			System.out.println("delete first test successful.");
			del = createSampleList();
			del.delete(2);
			assert (char)del.listHead.datum == 'a';
			assert del.listHead.next == del.listTail;
			assert (char)del.listTail.datum == 'b';
			assert del.listTail.next == null;
			System.out.println("delete second test successful.");
			
			/*first()*/
			SequenceDLList fir = createSampleList();
			//char storage = (char)fir.first();
			assert 'a' == (char)fir.first();
			System.out.println("first test succesful");
			
			/*last()*/
			SequenceDLList last = createSampleList();
			//storage = (char)last.last();
			assert 'c' == (char)last.last();
			System.out.println("last test succesful");
			
			/*element()*/
			SequenceDLList elem = createSampleList();
			//storage = (char)elem.element(1);
			assert 'b' == (char)elem.element(1);
			System.out.println("element test succesful");
			
			/*empty()*/
			SequenceDLList emp = new SequenceDLList();
			assert emp.empty() : "emp.empty()";
			emp.insertFirst(2);
			assert !emp.empty() : "!emp.empty()";
			System.out.println("empty test successful");
			
			/*size()*/
			SequenceDLList siz = createSampleList();
			assert siz.size() == 3;
			System.out.println("size test successful.");
			
			/*clear()*/
			SequenceDLList cl = createSampleList();
			cl.clear();
			assert cl.empty();
			System.out.println("clear test successful.");
			
			
		} catch(AssertionError e) {
			System.err.println("Assertion failure:");
			System.err.println("Assertion failed at line: " + e.getStackTrace()[0].getLineNumber());
			
		} catch(Exception e) {
			System.err.println("Exception occurred:");
			e.printStackTrace();
		}
		
		
		
		System.out.println("\n\nEnd of test");
	}
	
	public static SequenceDLList createSampleList() {
		SequenceDLList list = new SequenceDLList();
		list.insertFirst('c');
		list.insertFirst('b');
		list.insertFirst('a');
		
		return list;
	}
}
