import java.util.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Board {
   
   private final int[][] blocks;  // game board
   private final int n;           // board dimensons (n x n)
   private int hamming   = -1;    // computed hamming dist
   private int manhattan = -1;    // computed manhattan dist
   
   // swap two array positions and return result
   private int[][] swap(int x1, int y1, int x2, int y2) {
      final int[][] array = new int[n][n];
      for (int i = 0; i < n; i++)
         for (int j = 0; j < n; j++)
            array[i][j] = blocks[i][j];
      
      final int temp = array[x1][y1];
      array[x1][y1] = array[x2][y2];
      array[x2][y2] = temp;
      
      return array;
   }

   // construct a board from an n-by-n array of blocks
   // (where blocks[i][j] = block in row i, column j)
   public Board(int[][] blocks) {
      if (blocks == null) throw new NullPointerException();

      this.n = blocks.length;
      this.blocks = new int[n][n];
      
      for (int i = 0; i < n; i++)
         for (int j = 0; j < n; j++)
            this.blocks[i][j] = blocks[i][j];
      
      this.hamming = this.hamming();
      this.manhattan = this.manhattan();
   }
   
   // board dimension n
   public int dimension() {
      return n;
   }
   
   // number of blocks out of place
   public int hamming() {
      if (hamming != -1) return hamming;
      
      int count = 0;
      
      for (int i = 0; i < blocks.length; i++) {
         for (int j = 0; j < blocks[i].length; j++) {
            if (blocks[i][j] != 0) {
               final int expected = (i * n) + j + 1;
               if (blocks[i][j] != expected) count++;
            }
         }
      }
      
      return count;
   }
   
   // sum of Manhattan distances between blocks and goal
   public int manhattan() {
      if (manhattan != -1) return manhattan;
      
      int count = 0;
      
      for (int i = 0; i < blocks.length; i++) {
         for (int j = 0; j < blocks[i].length; j++) {
            if (blocks[i][j] != 0) {
               final int expected = (i * n) + j + 1;
               final int actual   = blocks[i][j];
               
               final int expMod = expected % n;
               final int actMod = actual   % n;
               final boolean isExpModZero = expMod == 0;
               final boolean isActModZero = actMod == 0;
               
               final int expCol = isExpModZero ? 3 : expMod;
               final int actCol = isActModZero ? 3 : actMod;
               final int cols = Math.abs(expCol - actCol);
               
               final int expRow = isExpModZero ? (expected / n) - 1 : expected / n;
               final int actRow = isActModZero ? (actual   / n) - 1 : actual   / n;
               final int rows = Math.abs(expRow - actRow);
               
               count += cols + rows;
            }
         }
      }
      
      return count;
   }
   
   // is this board the goal board?
   public boolean isGoal() {
      return this.hamming() == 0;
   }
   
   // a board that is obtained by exchanging any pair of blocks
   public Board twin() {
      // pick two non-zero array elements
      int i = 0, j = 0, k = -1, l = -1;
      
      for (i = 0; i < n; i++) {
         for (j = 0; j < n; j++) {
            if (k == -1 && blocks[i][j] != 0) {
               k = i;
               l = j;
               
            } else if (blocks[i][j] != 0) break;
         }
         if (j < n && k != -1 && blocks[i][j] != 0) break;
      }
      
      // swap elements and create new Board from the new array
      return new Board(swap(i, j, k, l));
   }
   
   // does this board equal y?
   public boolean equals(Object y) {
      if (y == this) return true;
      
      if (y == null) return false;
      
      if (y.getClass() != this.getClass()) return false;
      
      Board that = (Board) y;
      if (this.n != that.n) return false;
      for (int i = 0; i < n; i++)
         for (int j = 0; j < n; j++)
            if (this.blocks[i][j] != that.blocks[i][j]) return false;
      return true;
   }
   
   // all neighboring boards
   public Iterable<Board> neighbors() {
      Stack<Board> stack = new Stack<Board>();
      
      // find empty node
      for (int i = 0; i < n; i++) {
         for (int j = 0; j < n; j++) {
            if (blocks[i][j] == 0) {
               
               // swap N node and push result to stack, if exists
               if (i - 1 >= 0) {
                  stack.push(new Board(swap(i - 1, j, i, j)));
               }
               
               // swap S node and push result to stack, if exists
               if (i + 1 < n) {
                  stack.push(new Board(swap(i + 1, j, i, j)));
               }
               
               // swap W node and push result to stack, if exists
               if (j - 1 >= 0) {
                  stack.push(new Board(swap(i, j - 1, i, j)));
               }
               
               // swap E node and push result to stack, if exists
               if (j + 1 < n) {
                  stack.push(new Board(swap(i, j + 1, i, j)));
               }
               
               break;
            }
         }
         if (!stack.empty()) break;
      }
      
      return stack;
   }
   
   // string representation of this board
   public String toString() {
      StringBuilder s = new StringBuilder();
      
      s.append(n + "\n");
      for (int i = 0; i < n; i++) {
         for (int j = 0; j < n; j++) {
            s.append(String.format("%2d ", blocks[i][j]));
         }
         s.append("\n");
      }
      
      return s.toString();
   }

   // unit tests (not graded)
   public static void main(String[] args) {
      final int[][] array = {{1, 5, 3}, {4, 8, 2}, {7, 6, 0}};
      Board board = new Board(array);
      StdOut.println(board.toString());
      for (Board b : board.neighbors()) StdOut.println(b.toString());
   }
}