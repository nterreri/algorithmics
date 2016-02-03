package uk.ac.ucl.myuclaccountname.SequenceDLL;

public class SequenceDLListTest {
  public static void main(String[] args) {
    SequenceDLList myList = new SequenceDLList();
    for (int i=0; i<26; i++) {
      myList.insertFirst(new Integer(i));
    }
    while(!myList.empty())
      try {
        System.out.println(myList.first());
        myList.deleteFirst();
      } catch (SequenceDLListException e) {
        System.out.println(e);
      }
  }
}
