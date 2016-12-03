import edu.princeton.cs.algs4.*;

public class Percolation {
   private int nSquared;
   private boolean[][] openGrid;
   private boolean[][] fullGrid;
   private WeightedQuickUnionUF uf;
   
   private int xyTo1D(int row, int col) {
      return ((row - 1) * (openGrid.length - 1)) + col;
   }
   
   public Percolation(int n) {
      nSquared = n * n;
      openGrid = new boolean[n + 1][n + 1];
      fullGrid = new boolean[n + 1][n + 1];
      uf = new WeightedQuickUnionUF(nSquared + 2);
      
      // Union top row to virtual top site
      for (int i = 1; i <= n; i++) {
         uf.union(0, i);
      }
      // Union bottom row to virtual bottom site
      /*for (int i = nSquared - n + 1; i <= nSquared; i++) {
         uf.union(i, nSquared + 1);
      }*/
   }
   
   public void open(int row, int col) {
      openGrid[row][col] = true;
      
      // Union N site
      if (row - 1 >= 1              && isOpen(row - 1, col)) {
         uf.union(xyTo1D(row - 1, col), xyTo1D(row, col));
      }
      // Union S site
      if (row + 1 < openGrid.length && isOpen(row + 1, col)) {
         uf.union(xyTo1D(row + 1, col), xyTo1D(row, col));
      }
      // Union W site
      if (col - 1 >= 1              && isOpen(row, col - 1)) {
         uf.union(xyTo1D(row, col - 1), xyTo1D(row, col));
      }
      // Union E site
      if (col + 1 < openGrid.length && isOpen(row, col + 1)) {
         uf.union(xyTo1D(row, col + 1), xyTo1D(row, col));
      }
      
      if (row == 1)
         fullGrid[row][col] = true;
      /*if (row == fullGrid.length && uf.connected(0, xyTo1D(row, col)))
         fullGrid[row][col] = true;*/
      
      for (int i = 2; i < fullGrid.length; i++) {
         for (int j = 1; j < fullGrid.length; j++) {
            if (!fullGrid[i][j] && uf.connected(0, xyTo1D(i, j))) {
               fullGrid[i][j] = true;
            }
         }
      }
   }
   
   public boolean isOpen(int row, int col) {
      return openGrid[row][col];
   }
   
   public boolean isFull(int row, int col) {
      return fullGrid[row][col];
   }
   
   public boolean percolates() {
      for (int i = nSquared - openGrid.length + 2; i <= nSquared; i++) {
         if (uf.connected(0, i)) return true;
      }
      return false;
//      return uf.connected(0, nSquared + 1);
   }

   public static void main(String[] args) {
      int n = StdIn.readInt();
      Percolation perc = new Percolation(n);

      while (!StdIn.isEmpty()) {
         int p = StdIn.readInt();
         int q = StdIn.readInt();
         perc.open(p, q);
      }
   }
}