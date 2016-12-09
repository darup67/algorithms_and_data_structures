import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
   private Node first    = null;
   private Node last     = null;
   private int  size     = 0;
   
   // linked list Node class
   private class Node {
      private Item item;
      private Node next = null;
      private Node prev = null;
      
      public Node(Item item) { this.item = item; }
   }
   
   // deque Iterator class
   private class ListIterator implements Iterator<Item> {
      private Node current = first;
      
      // is there another item in the deque?
      public boolean hasNext() { return current != null; }
      
      // unsupported
      public void remove() { throw new UnsupportedOperationException(); }
      
      // returns next item, if it exists
      public Item next() {
         if (current == null) throw new NoSuchElementException();
         
         Item item = current.item;
         current   = current.next;
         return item;
      }
   }
   
   // construct an empty deque
   public Deque() { 
      // can't imagine what needs to be done here ...
   }
   
   // is the deque empty?
   public boolean isEmpty() {
      return (size == 0);
   }
   
   // return the number of items on the deque
   public int size() {
      return size;
   }   
   
   // add the item to the front
   public void addFirst(Item item) {
      if (item == null) throw new NullPointerException();
      
      Node newNode = new Node(item);
      if (first == null) {
         first = newNode;
         last = newNode;
      } else {
         newNode.next = first;
         first.prev = newNode;
         first = newNode;
      }
      
      size++;
   }

   // add the item to the end
   public void addLast(Item item) {
      if (item == null) throw new NullPointerException();
      
      Node newNode = new Node(item);
      if (first == null) {
         first = newNode;
         last = newNode;
      } else {
         newNode.prev = last;
         last.next = newNode;
         last = newNode;
      }
      
      size++;
   }        
   
   // remove and return the item from the front
   public Item removeFirst() {
      if (first == null) throw new NoSuchElementException();
      
      Item item = first.item;
      
      if (first.next != null) {
         first = first.next;
         first.prev = null;
      } else {
         first = null;
         last = null;
      }
      
      size--;
      return item;
   }
   
   // remove and return the item from the end
   public Item removeLast() {
      if (last == null) throw new NoSuchElementException();
      
      Item item = last.item;
      
      if (last.prev != null) {
         last = last.prev;
         last.next = null;
      } else {
         first = null;
         last = null;
      }
      
      size--;
      return item;
   }
   
   // return an iterator over items in order from front to end
   public Iterator<Item> iterator() {
      return new ListIterator();
   }
   
   // unit testing
   public static void main(String[] args) { 
      Deque<Integer> deque = new Deque<Integer>();
      
      int[] testArr = {5, 4, 3, 2, 1};
      for (int e : testArr) deque.addFirst(e);
      for (int e : deque) StdOut.println(e);
      StdOut.println("Size: " + deque.size());
      StdOut.println("");
      
      int[] testArrTwo = {6, 7, 8, 9, 10};
      for (int e : testArrTwo) deque.addLast(e);
      for (int e : deque) StdOut.println(e);
      StdOut.println("Size: " + deque.size());
      StdOut.println("");
      
      StdOut.println(deque.removeFirst());
      StdOut.println(deque.removeFirst());
      StdOut.println(deque.removeFirst());
      StdOut.println(deque.removeLast());
      StdOut.println(deque.removeLast());
      StdOut.println(deque.removeLast());
      StdOut.println("");
      for (int e : deque) StdOut.println(e);
      StdOut.println("Size: " + deque.size());
   }
}
