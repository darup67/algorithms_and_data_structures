import java.util.*;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public class Solver {
   
   private final Board initial;                                         // starting position
   private MinPQ<Board> pq = new MinPQ<Board>(new Solver.BoardOrder()); // A* priority queue
   private int moves = 0;                                               // number of moves made
   
   // compares two boards by Manhattan priority function
   private static class BoardOrder implements Comparator<Board> {
       
       // compare method demanded by Comparator interface
       public int compare(Board b1, Board b2) {
          if (b1.manhattan() > b2.manhattan()) return 1;
          else if (b1.manhattan() < b2.manhattan()) return -1;
          else return 0;
       }
    }
   
   // find a solution to the initial board (using the A* algorithm)
   public Solver(Board initial) {
      if (initial == null) throw new NullPointerException();

      boolean solved = false;
      this.initial = initial;
      
      pq.insert(initial);
      
      while (!solved) {
         pq.insert(
      }
   }
   
   // is the initial board solvable?
   public boolean isSolvable() {
      return true;
   }
   
   // min number of moves to solve initial board; -1 if unsolvable
   public int moves() {
      return initial.manhattan();
   }
   
   // sequence of boards in a shortest solution; null if unsolvable
   public Iterable<Board> solution() {
      ArrayList<Board> boardList = new ArrayList<Board>();
      
      while (true) {
         final Board newBoard = pq.delMin();
         boardList.add(newBoard);
         if (newBoard.isGoal()) break;
      }
      
      return boardList;
   }
   
   // solve a slider puzzle (given below)
   public static void main(String[] args) {
      // create initial board from file
      In in = new In(args[0]);
      int n = in.readInt();
      int[][] blocks = new int[n][n];
      for (int i = 0; i < n; i++)
         for (int j = 0; j < n; j++)
         blocks[i][j] = in.readInt();
      Board initial = new Board(blocks);
      
      // solve the puzzle
      Solver solver = new Solver(initial);
      
      // print solution to standard output
      if (!solver.isSolvable())
         StdOut.println("No solution possible");
      else {
         StdOut.println("Minimum number of moves = " + solver.moves());
         for (Board board : solver.solution())
            StdOut.println(board);
      }
   }
}