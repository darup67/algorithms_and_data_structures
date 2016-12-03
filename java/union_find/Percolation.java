import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
   private int n;                        // Grid dimension
   private int nSquared;                 // Grid size (n * n)
   private boolean[][] openGrid;         // false == closed site, true == open
   private WeightedQuickUnionUF uf;      // Union-find for percolates
   private WeightedQuickUnionUF ufFull;  // Union-find for isFull
   
   // Converts x,y grid reference into UF site reference
   private int xyTo1D(int row, int col) {
      return ((row - 1) * (openGrid.length - 1)) + col;
   } 
   
   // Throws exception if row, col indices are out of range
   private void validate(int row, int col) {
      if (row < 1 || col < 1 || row > n || col > n)
         throw new IndexOutOfBoundsException("row/col index out of bounds");
   }
   
   // Constructor
   public Percolation(int n) {
      if (n < 1) throw new IllegalArgumentException("error: n less than 1");
      
      this.n = n;
      nSquared = n * n;
      openGrid = new boolean[n + 1][n + 1];
      uf     = new WeightedQuickUnionUF(nSquared + 2);
      ufFull = new WeightedQuickUnionUF(nSquared + 2);
      
      // Union top row to virtual top site
      for (int i = 1; i <= n; i++) {
         uf.union(0, i);
         ufFull.union(0, i);
      }
      // Union bottom row to virtual bottom site
      for (int i = nSquared - n + 1; i <= nSquared; i++) {
         uf.union(i, nSquared + 1);
      }
   }
   
   // Opens given site and connects it to adjacent open sites
   public void open(int row, int col) {
      validate(row, col);
      openGrid[row][col] = true;
      
      // Union N site
      if (row - 1 >= 1 && isOpen(row - 1, col)) {
         uf.union(xyTo1D(row - 1, col), xyTo1D(row, col));
         ufFull.union(xyTo1D(row - 1, col), xyTo1D(row, col));
      }
      // Union S site
      if (row + 1 <= n && isOpen(row + 1, col)) {
         uf.union(xyTo1D(row + 1, col), xyTo1D(row, col));
         ufFull.union(xyTo1D(row + 1, col), xyTo1D(row, col));
      }
      // Union W site
      if (col - 1 >= 1 && isOpen(row, col - 1)) {
         uf.union(xyTo1D(row, col - 1), xyTo1D(row, col));
         ufFull.union(xyTo1D(row, col - 1), xyTo1D(row, col));
      }
      // Union E site
      if (col + 1 <= n && isOpen(row, col + 1)) {
         uf.union(xyTo1D(row, col + 1), xyTo1D(row, col));
         ufFull.union(xyTo1D(row, col + 1), xyTo1D(row, col));
      }
   }
   
   // Returns true if a given site is open
   public boolean isOpen(int row, int col) {
      validate(row, col);
      return openGrid[row][col];
   }
   
   // Returns true if a given site is full
   public boolean isFull(int row, int col) {
      validate(row, col);
      if (openGrid[row][col] && ufFull.connected(0, xyTo1D(row, col)))
         return true;
      return false;
   }
   
   // Returns true if the system percolates
   public boolean percolates() {
      if (n == 1) return isOpen(1, 1);
      return uf.connected(0, nSquared + 1);
   }

   // Main for testing (not implemented)
   public static void main(String[] args) {}
}