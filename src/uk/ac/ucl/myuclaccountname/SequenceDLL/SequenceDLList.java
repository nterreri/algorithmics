package uk.ac.ucl.myuclaccountname.SequenceDLL;

class SequenceDLListException extends Exception {
	  SequenceDLListException() {
	    super();
	  }
	  SequenceDLListException(String s) {
	    super(s);
	  }
	}

	/**
	 * <dl>
	 * <dt>Purpose: Implementation of Sequence ADT.
	 * <dd>
	 *
	 * <dt>Description:
	 * <dd>This class is an implementation of the Sequence using an linked list as
	 * the underlying data structure. The capacity is therefore unlimited and
	 * overflow does not need to be checked.
	 * </dl>
	 *
	 * @author Danny Alexander
	 * @version $Date: 2000/01/08
	 */

	public class SequenceDLList {
	  /**
	   * Member class Node encapsulates the nodes of the linked list in
	   * which the stack is stored. Each node contains a data item and a
	   * reference to another node - the next in the linked list.
	   */
	  protected class TwoWayNode {

	    public TwoWayNode(Object o) {
	      this(o, null, null);
	    }

	    public TwoWayNode(Object o, TwoWayNode n, TwoWayNode p) {
	      datum = o;
	      next = n;
	      previous = p;
	    }

	    //The Node data structure consists of two object references.
	    //One for the datum contained in the node and the other for
	    //the next node in the list.

	    protected Object datum;
	    protected TwoWayNode next;
	    protected TwoWayNode previous;
	  }

	  //We use object references to the head and tail of the list (the head
	  //and tail of the sequence, respectively).
	  protected TwoWayNode listHead;
	  protected TwoWayNode listTail;

	  //Only require a single constructor, which sets both object
	  //references to null.
	  /**
	   * Constructs an empty sequence object.
	   */
	  public SequenceDLList() {
	    listHead = null;
	    listTail = null;
	  }

	  /**
	   * Adds a new item at the start of the sequence.
	   */
	  public void insertFirst(Object o) {
	    //There is a special case when the sequence is empty.
	    //Then the both the head and tail pointers needs to be
	    //initialised to reference the new node.
	    if (listHead == null) {
	      listHead = new TwoWayNode(o, listHead, null);
	      listTail = listHead;
	    }

	    //In the general case, we simply add a new node at the start
	    //of the list via the head pointer.
	    else {
	    	TwoWayNode newHead = new TwoWayNode(o, listHead, null);
	    	listHead.previous = newHead;
	    	listHead = listHead.next; //newHead;
	    }
	  }
	}
