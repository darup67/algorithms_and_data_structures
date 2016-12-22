import java.util.ArrayDeque;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public class Solver {
   
   private Node head;                           // solved node
   private MinPQ<Node> pq = new MinPQ<Node>();  // A* priority queue
   private int solutionMoves;                   // number of moves in solution
   private boolean isSolvable;                  // is this puzzle solvable?
   
   // compares two boards by Manhattan priority function
   private class Node implements Comparable<Node> {
       
      private final Board board;   // board for this node
      private final Node prev;     // pointer to previous node
      private final int moves;     // number of moves taken
      private final int priority;  // computed priority
      
      // Node constructor
      public Node(Board board, Node prev, int moves) {
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
      
      MinPQ<Node> pqTwin = new MinPQ<Node>();  // Twin board pq
      
      pq.insert(new Node(initial, null, 0));
      pqTwin.insert(new Node(initial.twin(), null, 0));
      
      while (true) {
         // Solution on original board
         final Node node = pq.delMin();
         
         if (node.board.isGoal()) {
            head = node;
            solutionMoves = head.moves;
            isSolvable = true;
            break;
         }
         
         for (Board neighbor : node.board.neighbors()) {
            if (node.prev == null)
               pq.insert(new Node(neighbor, node, 1));
            
            else if (!neighbor.equals(node.prev.board))
               pq.insert(new Node(neighbor, node, node.moves + 1));
         }
         
         // Solution on twin board
         final Node nodeTwin = pqTwin.delMin();
         
         if (nodeTwin.board.isGoal()) {
            solutionMoves = -1;
            isSolvable = false;
            break;
         }
         
         for (Board neighbor : nodeTwin.board.neighbors()) {
            if (nodeTwin.prev == null)
               pqTwin.insert(new Node(neighbor, nodeTwin, 1));
            
            else if (!neighbor.equals(nodeTwin.prev.board))
               pqTwin.insert(new Node(neighbor, nodeTwin, nodeTwin.moves + 1));
         }
      }
   }
   
   // is the initial board solvable?
   public boolean isSolvable() {
      return isSolvable;
   }
   
   // min number of moves to solve initial board; -1 if unsolvable
   public int moves() {
      return solutionMoves;
   }
   
   // sequence of boards in a shortest solution; null if unsolvable
   public Iterable<Board> solution() {
      if (!isSolvable) return null;
      
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