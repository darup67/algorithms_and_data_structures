import edu.princeton.cs.algs4.*;

public class Percolation {
   private boolean[][] grid;
   
   private int xyTo1D(int row, int col) {
      return ((row - 1) * (grid.length - 1)) + col;
   }
   
   private void outputGrid() {
      for (int i = 1; i < grid.length; i++) {
         String line = i + ": ";
         for (int j = 1; j < grid[i].length; j++) {
            line += (grid[i][j] ? " open " : "closed") + " ";
         }
         StdOut.println(line);
      }
   }
   
   public Percolation(int n) {
      int nSquared = n * n;
      
      grid = new boolean[n + 1][n + 1];
      WeightedQuickUnionUF uf = new WeightedQuickUnionUF(nSquared + 2);
      
      // Union top row to virtual top site
      for (int i = 1; i <= n; i++) {
         uf.union(0, i);
      }
      StdOut.println(uf.connected(0,1));
      
      // Union bottom row to virtual bottom site
      for (int i = nSquared - n + 1; i <= nSquared; i++) {
         uf.union(i, nSquared + 1);
      }
      StdOut.println(uf.connected(nSquared, nSquared + 1));
      
      outputGrid();
   }
   
   public void open(int row, int col) {
      grid[row][col] = true;
      outputGrid();
   }
   
   public boolean isOpen(int row, int col) {
      return grid[row][col];
   }
   
   public boolean isFull(int row, int col) {
      return !grid[row][col];
   }
   
   public boolean percolates() {
      return true;
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