import edu.princeton.cs.algs4.*;

public class Percolation {
   private int nSquared;             // Grid size (n * n)
   private boolean[][] openGrid;     // false == closed site, true == open
   private WeightedQuickUnionUF uf;  // Union-find structure
   
   // Converts x,y grid reference into UF site reference
   private int xyTo1D(int row, int col) {
      return ((row - 1) * (openGrid.length - 1)) + col;
   }
   
   // Constructor
   public Percolation(int n) {
      nSquared = n * n;
      openGrid = new boolean[n + 1][n + 1];
      uf = new WeightedQuickUnionUF(nSquared + 2);
      
      // Union top row to virtual top site
      for (int i = 1; i <= n; i++) {
         uf.union(0, i);
      }
   }
   
   // Opens given site and connects it to adjacent open sites
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
   }
   
   // Returns true if a given site is open
   public boolean isOpen(int row, int col) {
      return openGrid[row][col];
   }
   
   // Returns true if a given site is full
   public boolean isFull(int row, int col) {
      if (openGrid[row][col] && uf.connected(0, xyTo1D(row, col)))
         return true;
      return false;
   }
   
   // Returns true if the system percolates
   public boolean percolates() {
      for (int i = nSquared - openGrid.length + 2; i <= nSquared; i++) {
         if (uf.connected(0, i)) return true;
      }
      return false;
   }

   // Main for testing (not implemented)
   public static void main(String[] args) {}
}