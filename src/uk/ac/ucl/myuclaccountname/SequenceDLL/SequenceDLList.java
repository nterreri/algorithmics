package uk.ac.ucl.myuclaccountname.SequenceDLL;

class SequenceDLListException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6544264935948053277L;
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
 * <dd>This class is an implementation of the Sequence using an <strong>double</strong>linked list as
 * the underlying data structure. The capacity is therefore unlimited and
 * overflow does not need to be checked.
 * </dl>
 *
 * Based on: Danny Alexander, 2000/01/08
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
			listHead.previous = new TwoWayNode(o, listHead, null);
			listHead = listHead.previous; //newHead;
		}
	}

	/**
	 * Adds a new item at the end of the sequence.
	 */
	public void insertLast(Object o) {
		//There is a special case when the sequence is empty.
		//Then the both the head and tail pointers needs to be
		//initialised to reference the new node.
		if (listHead == null) {
			listHead = new TwoWayNode(o, listHead, null);
			listTail = listHead;
		}

		//In the general case, we simply add a new node to the end
		//of the list via the tail pointer.
		else {
			//TwoWayNode newTail = new TwoWayNode(o, null, listTail);
			//listTail.next = newTail;
			listTail.next = new TwoWayNode(o, null, listTail);
			listTail = listTail.next; //newTail;
		}
	}

	/**
	 * Adds a new item at a specified position in the sequence.
	 */
	public void insert(Object o, int index) throws SequenceDLListException {
		//Check the index is positive.
		if (index < 0) {
			throw new SequenceDLListException("Indexed Element out of Range");
		}

		//There is a special case when the sequence is empty.
		//Then the both the head and tail pointers needs to be
		//initialised to reference the new node.
		if (listHead == null) {
			if (index == 0) {
				insertFirst(o);
			} else {
				throw new SequenceDLListException("Indexed element is out of range");
			}
		}

		//There is another special case for insertion at the head of
		//the sequence.
		else if (index == 0) {
			insertFirst(o);
		}

		//In the general case, we need to chain down the linked list
		//from the head until we find the location for the new
		//list node. If we reach the end of the list before finding
		//the specified location, we know that the given index was out
		//of range and throw an exception.
		else {
			TwoWayNode nodePointer = listHead;
			int i = 1;
			while (i < index) {
				nodePointer = nodePointer.next;
				i += 1;
				if (nodePointer == null) {
					throw new SequenceDLListException("Indexed Element out of Range");
				}
			}

			//Now we've found the node before the position of the
			//new one, so we 'hook in' the new Node.

			/*//would be more elegant, but does not work where nodePointer.next is null:
	      //nodePointer.next = nodePointer.next.previous = new TwoWayNode(0, nodePointer.next, nodePointer);
			 */
			//Create new node, its pointers wired properly
			TwoWayNode newNode = new TwoWayNode(o, nodePointer.next, nodePointer);
			//Wire nodePointer.next previous pointer to the new node, unless nodePointer.next
			//points to nothing (nothing has no "previous" property)
			if(nodePointer != listTail) //equivalently: (nodePointer.next != null)
				nodePointer.next.previous = newNode;
			//Wire nodePointer's "next" pointer to the new node
			nodePointer.next = newNode;

			//Finally we need to check that the tail pointer is
			//correct. Another special case occurs if the new
			//node was inserted at the end, in which case, we need
			//to update the tail pointer.
			if (nodePointer == listTail) {
				listTail = listTail.next;
			}
		}
	}

	/**
	 * Removes the item at the start of the sequence.
	 */
	public void deleteFirst() throws SequenceDLListException {
		//Check there is something in the sequence to delete.
		if (listHead == null) {
			throw new SequenceDLListException("Sequence Underflow");
		}

		//There is a special case when there is just one item in the
		//sequence. Both pointers then need to be reset to null.
		if (listHead.next == null) {
			listHead = null;
			listTail = null;
		}

		//In the general case, we just unlink the first node of the
		//list, and unlink the next node "previous" pointer from the previous head pointer.
		else {
			listHead = listHead.next;
			listHead.previous = null;
		}
	}

	/**
	 * Removes the item at the end of the sequence.
	 */
	public void deleteLast() throws SequenceDLListException {
		//Check there is something in the sequence to delete.
		if (listHead == null) {
			throw new SequenceDLListException("Sequence Underflow");
		}

		//There is a special case when there is just one item in the
		//sequence. Both pointers then need to be reset to null.
		if (listHead.next == null) {
			listHead = null;
			listTail = null;
		}

		//In the general case, we need to 
		//reset the forward link of the second to last element to null.
		//And shift the tail pointer the second to last element
		else {
			//Unlink the last node and reset the tail pointer.
			listTail.previous.next = null;
			listTail = listTail.previous;
		}

	}

	/**
	 * Removes the item at the specified position in the sequence.
	 */
	public void delete(int index) throws SequenceDLListException {
		//Check there is something in the sequence to delete.
		if (listHead == null) {
			throw new SequenceDLListException("Sequence Underflow");
		}

		//Check the index is positive.
		if (index < 0) {
			throw new SequenceDLListException("Indexed Element out of Range");
		}

		//There is a special case when there is just one item in the
		//sequence. Both pointers then need to be reset to null.
		if (listHead.next == null) {
			if (index == 0) {
				listHead = null;
				listTail = null;
			} else {
				throw new SequenceDLListException("Indexed element is out of range.");
			}
		}

		//There is also a special case when the first element has to
		//be removed.

		else if (index == 0) {
			deleteFirst();
		}

		//In the general case, we need to chain down the list to find
		//the node in the indexed position.
		else {
			TwoWayNode nodePointer = listHead;
			int i = 1;
			while (i < index) {
				nodePointer = nodePointer.next;
				i += 1;
				if (nodePointer.next == null) {
					throw new SequenceDLListException("Indexed Element out of Range");
				}

			}

			//Unlink the node and reset the tail pointer if that
			//node was the last one.
			if (nodePointer.next == listTail) {
				listTail = nodePointer;
			} else {
				//If the next node is not the last, 
				//unlink the "previous" pointer looking in to the node to be deleted:
				nodePointer.next.next.previous = nodePointer;
			}
			//Unhook the "next" pointer looking in to the node to be deleted:
			nodePointer.next = nodePointer.next.next;
		}
	}

	/**
	 * Returns the item at the start of the sequence.
	 */
	public Object first() throws SequenceDLListException {
		if (listHead != null) {
			return listHead.datum;
		} else {
			throw new SequenceDLListException("Indexed Element out of Range");
		}
	}

	/**
	 * Returns the item at the end of the sequence.
	 */
	public Object last() throws SequenceDLListException {
		if (listTail != null) {
			return listTail.datum;
		} else {
			throw new SequenceDLListException("Indexed Element out of Range");
		}
	}

	/**
	 * Returns the item at the specified position in the sequence.
	 */
	public Object element(int index) throws SequenceDLListException {
		//Check the index is positive.
		if (index < 0) {
			throw new SequenceDLListException("Indexed Element out of Range");
		}

		//We need to chain down the list until we reach the indexed
		//position

		TwoWayNode nodePointer = listHead;
		int i = 0;
		while (i < index) {
			if (nodePointer.next == null) {
				throw new SequenceDLListException("Indexed Element out of Range");
			} else {
				nodePointer = nodePointer.next;
				i += 1;
			}
		}

		return nodePointer.datum;
	}

	/**
	 * Tests whether there are any items in the sequence.
	 */
	public boolean empty() {
		return (listHead == null);
	}

	/**
	 * Returns the number of items in the sequence.
	 */
	public int size() {
		//Chain down the list counting the elements

		TwoWayNode nodePointer = listHead;
		int size = 0;
		while (nodePointer != null) {
			size += 1;
			nodePointer = nodePointer.next;
		}
		return size;
	}

	/**
	 * Empties the sequence.
	 */
	public void clear() {
		listHead = null;
		listTail = null;
	}

}


