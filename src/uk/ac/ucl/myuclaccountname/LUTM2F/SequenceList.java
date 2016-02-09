package uk.ac.ucl.myuclaccountname.LUTM2F;

class SequenceListException extends Exception {
  SequenceListException() {
    super();
  }
  SequenceListException(String s) {
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

public class SequenceList {
  /**
   * Member class Node encapsulates the nodes of the linked list in
   * which the stack is stored. Each node contains a data item and a
   * reference to another node - the next in the linked list.
   */
  protected class Node {

    public Node(Object o) {
      this(o, null);
    }

    public Node(Object o, Node n) {
      datum = o;
      next = n;
    }

    //The Node data structure consists of two object references.
    //One for the datum contained in the node and the other for
    //the next node in the list.

    protected Object datum;
    protected Node next;
  }

  //We use object references to the head and tail of the list (the head
  //and tail of the sequence, respectively).
  private Node listHead;
  private Node listTail;

  //Only require a single constructor, which sets both object
  //references to null.
  /**
   * Constructs an empty sequence object.
   */
  public SequenceList() {
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
      listHead = new Node(o, listHead);
      listTail = listHead;
    }

    //In the general case, we simply add a new node at the start
    //of the list via the head pointer.
    else {
      listHead = new Node(o, listHead);
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
      listHead = new Node(o, listHead);
      listTail = listHead;
    }

    //In the general case, we simply add a new node to the end
    //of the list via the tail pointer.
    else {
      listTail.next = new Node(o, listTail.next);
      listTail = listTail.next;
    }
  }

  /**
   * Adds a new item at a specified position in the sequence.
   */
  public void insert(Object o, int index) throws SequenceListException {
    //Check the index is positive.
    if (index < 0) {
      throw new SequenceListException("Indexed Element out of Range");
    }

    //There is a special case when the sequence is empty.
    //Then the both the head and tail pointers needs to be
    //initialised to reference the new node.
    if (listHead == null) {
      if (index == 0) {
        listHead = new Node(o, listHead);
        listTail = listHead;
      } else {
        throw new SequenceListException("Indexed element is out of range");
      }
    }

    //There is another special case for insertion at the head of
    //the sequence.
    else if (index == 0) {
      listHead = new Node(o, listHead);
    }

    //In the general case, we need to chain down the linked list
    //from the head until we find the location for the new
    //list node. If we reach the end of the list before finding
    //the specified location, we know that the given index was out
    //of range and throw an exception.
    else {
      Node nodePointer = listHead;
      int i = 1;
      while (i < index) {
        nodePointer = nodePointer.next;
        i += 1;
        if (nodePointer == null) {
          throw new SequenceListException("Indexed Element out of Range");
        }
      }

      //Now we've found the node before the position of the
      //new one, so we 'hook in' the new Node.

      nodePointer.next = new Node(o, nodePointer.next);

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
  public void deleteFirst() throws SequenceListException {
    //Check there is something in the sequence to delete.
    if (listHead == null) {
      throw new SequenceListException("Sequence Underflow");
    }

    //There is a special case when there is just one item in the
    //sequence. Both pointers then need to be reset to null.
    if (listHead.next == null) {
      listHead = null;
      listTail = null;
    }

    //In the general case, we just unlink the first node of the
    //list.
    else {
      listHead = listHead.next;
    }
  }

  /**
   * Removes the item at the end of the sequence.
   */
  public void deleteLast() throws SequenceListException {
    //Check there is something in the sequence to delete.
    if (listHead == null) {
      throw new SequenceListException("Sequence Underflow");
    }

    //There is a special case when there is just one item in the
    //sequence. Both pointers then need to be reset to null.
    if (listHead.next == null) {
      listHead = null;
      listTail = null;
    }

    //In the general case, we need to chain all the way down the
    //list in order to reset the link of the second to last
    //element to null.
    else {
      Node nodePointer = listHead;
      while (nodePointer.next != listTail) {
        nodePointer = nodePointer.next;
      }

      //Unlink the last node and reset the tail pointer.
      nodePointer.next = null;
      listTail = nodePointer;
    }
  }

  /**
   * Removes the item at the specified position in the sequence.
   */
  public void delete(int index) throws SequenceListException {
    //Check there is something in the sequence to delete.
    if (listHead == null) {
      throw new SequenceListException("Sequence Underflow");
    }

    //Check the index is positive.
    if (index < 0) {
      throw new SequenceListException("Indexed Element out of Range");
    }

    //There is a special case when there is just one item in the
    //sequence. Both pointers then need to be reset to null.
    if (listHead.next == null) {
      if (index == 0) {
        listHead = null;
        listTail = null;
      } else {
        throw new SequenceListException("Indexed element is out of range.");
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
      Node nodePointer = listHead;
      int i = 1;
      while (i < index) {
        nodePointer = nodePointer.next;
        i += 1;
        if (nodePointer.next == null) {
          throw new SequenceListException("Indexed Element out of Range");
        }

      }

      //Unlink the node and reset the tail pointer if that
      //node was the last one.
      if (nodePointer.next == listTail) {
        listTail = nodePointer;
      }
      nodePointer.next = nodePointer.next.next;
    }
  }

  /**
   * Returns the item at the start of the sequence.
   */
  public Object first() throws SequenceListException {
    if (listHead != null) {
      return listHead.datum;
    } else {
      throw new SequenceListException("Indexed Element out of Range");
    }
  }

  /**
   * Returns the item at the end of the sequence.
   */
  public Object last() throws SequenceListException {
    if (listTail != null) {
      return listTail.datum;
    } else {
      throw new SequenceListException("Indexed Element out of Range");
    }
  }

  /**
   * Returns the item at the specified position in the sequence.
   */
  public Object element(int index) throws SequenceListException {
    //Check the index is positive.
    if (index < 0) {
      throw new SequenceListException("Indexed Element out of Range");
    }

    //We need to chain down the list until we reach the indexed
    //position

    Node nodePointer = listHead;
    int i = 0;
    while (i < index) {
      if (nodePointer.next == null) {
        throw new SequenceListException("Indexed Element out of Range");
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

    Node nodePointer = listHead;
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
