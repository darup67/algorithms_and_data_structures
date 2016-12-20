import java.util.*;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public class Board {
   
   private final int[][] blocks;  // game board
   private final int n;           // board dimensons (n x n)

   // construct a board from an n-by-n array of blocks
   // (where blocks[i][j] = block in row i, column j)
   public Board(int[][] blocks) {
      if (blocks == null) throw new NullPointerException();
      
      this.blocks = blocks.clone();
      n = blocks.length;
      
   }
   
   // board dimension n
   public int dimension() {
      return n;
   }
   
   // number of blocks out of place
   public int hamming() {
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
               
//               StdOut.println("Expected: " + expected);
//               StdOut.println("Actual: " + blocks[i][j]);
//               StdOut.println("Cols: " + cols);
//               StdOut.println("Rows: " + rows);
//               StdOut.println("Total: " + (cols + rows));
//               StdOut.println("");
               
               count += cols + rows;
               
//               StdOut.println("Count: " + count);
//               StdOut.println("");
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
      int[][] twinBlocks = blocks.clone();
      
      // pick two non-zero array elements
      int i = 0, j = 0, k = -1, l = -1;
      
      for (; i < n; i++) {
         for (; j < n; j++) {
            if (k == -1 && blocks[i][j] != 0) {
               k = i;
               l = j;
            
            } else if (blocks[i][j] != 0) break;           
         }
         if (k != -1 && blocks[i][j] != 0) break;
         else StdOut.println("i: " + i + "  j: " + j);
      }
      
      // swap procedure
      StdOut.println("i: " + i + "  j: " + j);
      StdOut.println("k: " + k + "  l: " + l);
      final int temp = twinBlocks[i][j];
      twinBlocks[i][j] = twinBlocks[k][l];
      twinBlocks[k][l] = temp;
      
      return new Board(twinBlocks);
   }
   
   // does this board equal y?
   public boolean equals(Object y) {
      if (y == this) return true;
      
      if (y == null) return false;
      
      if (y.getClass() != this.getClass()) return false;
      
      Board that = (Board) y;
      if (this.n     != that.n)     return false;
      for (int i = 0; i < n; i++)
         for (int j = 0; i < n; j++)
            if (this.blocks[i][j] != that.blocks[i][j]) return false;
      return true;
   }
   
   // all neighboring boards
//   public Iterable<Board> neighbors() {
//      
//   }
   
   // string representation of this board
   public String toString() {
      String boardStr = "\n" + n + "\n ";
      
      for (int i = 0; i < blocks.length; i++) {
         for (int j = 0; j < blocks[i].length; j++) {
            boardStr += blocks[i][j] + "  ";
         }
         
         boardStr += "\n ";
      }
      
      return boardStr;
   }

   // unit tests (not graded)
   public static void main(String[] args) {
      
      final int[][] array = {{0, 1, 3}, {4, 8, 2}, {7, 6, 5}};
      Board board = new Board(array);
//      StdOut.println(board.toString());
//      StdOut.println(board.hamming());
      StdOut.println(board.toString());
      StdOut.println(board.twin().toString());
      
//      // create initial board from file
//      In in = new In(args[0]);
//      int n = in.readInt();
//      int[][] blocks = new int[n][n];
//      for (int i = 0; i < n; i++)
//         for (int j = 0; j < n; j++)
//         blocks[i][j] = in.readInt();
//      Board initial = new Board(blocks);
//      
//      // solve the puzzle
//      Solver solver = new Solver(initial);
//      
//      // print solution to standard output
//      if (!solver.isSolvable())
//         StdOut.println("No solution possible");
//      else {
//         StdOut.println("Minimum number of moves = " + solver.moves());
//         for (Board board : solver.solution())
//            StdOut.println(board);
//      }
   }
}