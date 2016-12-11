import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
   private Item[] array = (Item[]) new Object[16];
   private int size = 0;
   
   // RandomizedQueue Iterator class
   private class ArrayIterator implements Iterator<Item> {
      private int[] indexArr  = createIndexArr();
      private int   currIndex = 0;
      
      private int[] createIndexArr() {
         int[] temp = new int[size];
         
         for (int i = 0; i < size; i++) {
            temp[i] = i;
         }
         
         StdRandom.shuffle(temp);
         return temp;
      }
      
      // is there another item in the deque?
      public boolean hasNext() { return currIndex < size; }
      
      // unsupported
      public void remove() { throw new UnsupportedOperationException(); }
      
      // returns next item, if it exists
      public Item next() {
         if (currIndex == size) throw new NoSuchElementException();
         
         return array[indexArr[currIndex++]];
      }
   }

   // construct an empty randomized queue
   public RandomizedQueue() {
      // can't imagine what needs to be done here ...
   }
      
   // is the queue empty?
   public boolean isEmpty() {
      return size == 0;
   }
   
   // return the number of items on the queue
   public int size() {
      return size;
   }   
   
   // add the item
   public void enqueue(Item item) {
      if (item == null) throw new NullPointerException();
      
      array[size++] = item;
   }

   // remove and return a random item
   public Item dequeue() {
      if (size == 0) throw new NoSuchElementException();
      
      Item item = array[--size];
      array[size] = null;
      return item;
   }
   
   // return (but do not remove) a random item
   public Item sample() {
      if (size == 0) throw new NoSuchElementException();
      
      return array[size - 1];
   }
   
   // return an independent iterator over items in random order
   public Iterator<Item> iterator() {
      return new ArrayIterator();
   }
   
   // unit testing
   public static void main(String[] args) { 
      RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
      
      rq.enqueue(37);
      rq.enqueue(58);
      rq.enqueue(14);
      rq.enqueue(94);
      rq.enqueue(43);
      rq.enqueue(76);
         
      StdOut.println(rq.dequeue());
      StdOut.println(rq.dequeue());
      StdOut.println("");
      
      for (int x : rq)
         StdOut.println(x);
   }
}