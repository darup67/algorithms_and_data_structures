import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Subset {
   // check array for item
   private static boolean isInArray(int[] array, int value) {
      for (int x : array) {
         if (value == x) return true;
      }
      return false;
   }
   
   // Subset test
   public static void main(String[] args) {
      RandomizedQueue<String> rq = new RandomizedQueue<String>();
      String[] strInput = StdIn.readAllStrings();
      int      k        = Integer.parseInt(args[0]);
      int[]    indexArr = new int[k];
      
      int j = 0;
      while (j < k) {
         int rand = StdRandom.uniform(1, strInput.length + 1);
         if (!isInArray(indexArr, rand)) {
            indexArr[j] = rand;
            j++;
         }
      }
      
      for (int x : indexArr) {
         rq.enqueue(strInput[x - 1]);
      }
      
      for (int i = 0; i < k; i++) {
         StdOut.println(rq.dequeue());
      }
   }
}