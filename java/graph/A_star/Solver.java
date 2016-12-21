import java.util.*;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public class Solver {
   
   private Node head;                           // solved node
   private MinPQ<Node> pq = new MinPQ<Node>();  // A* priority queue
   private int solutionMoves;                   // number of moves in solution
   
   // compares two boards by Manhattan priority function
   private class Node implements Comparable<Node> {
       
      private final Board board;   // board for this node
      private final Node prev;     // pointer to previous node
      private final int moves;     // number of moves taken
      private final int priority;  // computed priority
      
      // Node constructor
      public Node (Board board, Node prev, int moves) {
         this.board    = board;
         this.prev     = prev;
         this.moves    = moves;
         this.priority = board.manhattan() + moves;
      }
      
      public int compareTo(Node that) {
         if      (this.priority > that.priority) return 1;
         else if (this.priority < that.priority) return -1;
         else return 0;
      }
   }
   
   // find a solution to the initial board (using the A* algorithm)
   public Solver(Board initial) {
      if (initial == null) throw new NullPointerException();

      pq.insert(new Node(initial, null, 0));
      
      while (true) {
         final Node node = pq.delMin();
         if (node.board.isGoal()) {
            head = node;
            solutionMoves = head.moves;
            break;
         }
         
         assert node.priority >= node.prev.priority;
         
         for (Board neighbor : node.board.neighbors()) {
            if (node.prev == null)
               pq.insert(new Node(neighbor, node, 1));
            
            else if (!neighbor.equals(node.prev.board))
               pq.insert(new Node(neighbor, node, node.moves + 1));
         }
         
//         StdOut.println("================");
//         StdOut.println("Moves: " + moves);
//         StdOut.println("================");
//         for (Node solNode : pq) {
//            StdOut.println("Priority: " + solNode.priority());
//            StdOut.println("Manhattan: " + solNode.board().manhattan());
//            StdOut.println(solNode.board().toString());
//         }
//         StdOut.println();
      }
   }
   
   // is the initial board solvable?
   public boolean isSolvable() {
      return true;
   }
   
   // min number of moves to solve initial board; -1 if unsolvable
   public int moves() {
      return solutionMoves;
   }
   
   // sequence of boards in a shortest solution; null if unsolvable
   public Iterable<Board> solution() {
      ArrayDeque<Board> solution = new ArrayDeque<Board>();
      
      Node node = head;
      while (node != null) {
         solution.addFirst(node.board);
         node = node.prev;
      }
      
      return solution;
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
      StdOut.println("Hamming: " + initial.hamming());
      StdOut.println("Manhattan: " + initial.manhattan());
      
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