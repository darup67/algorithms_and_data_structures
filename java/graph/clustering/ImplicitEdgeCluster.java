import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public class ImplicitEdgeCluster {
   
   // check if two vertices are >= 2 Hamming dist apart
   private static boolean isClose(int x, int y) {
      return Integer.bitCount(x ^ y) < 3 ? true : false;
   }
   
   // return max k for input graph k-clustering of min-spacing 3
   public static void main(String[] args) {
      In in         = new In(args[0]);
      int n         = in.readInt();
      int labelSize = in.readInt();
      
      int[]                nodeList = new int[n];
      WeightedQuickUnionUF uf       = new WeightedQuickUnionUF(n);
      int                  count    = n;

      // create nodeList from file
      for (int i = 0; i < n; i++) {
         boolean[] bits = new boolean[32];
         int bitVector  = 0;  // int packed data format for bit vector
         
         for (int j = 32 - labelSize; j < 32; j++)
            bits[j] = in.readBoolean();
         
         for (int j = 0; j < 32; j++) {
            bitVector = (bitVector << 1) + (bits[j] ? 1 : 0);
         }
         
         nodeList[i] = bitVector;
      }
      
      // iterate through all nC2 vertex pairs
      for (int i = 0; i < n; i++) {
         for (int j = i + 1; j < n; j++) {
            
            // if the two vertices are within 2 dist and not connected
            if (isClose(nodeList[i], nodeList[j]) && !uf.connected(i, j)) {
               uf.union(i, j);
               count--;
            }
         }
      }
      
      StdOut.println(count);
   }
}