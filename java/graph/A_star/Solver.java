import java.util.Arrays;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public class Solver {
   
   private Board[] solution;    // solution
   private int solutionMoves;   // number of moves in solution
   
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
      
      MinPQ<Node> pq     = new MinPQ<Node>();  // Original board pq
      MinPQ<Node> pqTwin = new MinPQ<Node>();  // Twin board pq
      
      pq.insert(new Node(initial, null, 0));
      pqTwin.insert(new Node(initial.twin(), null, 0));
      
      while (true) {
         // Solution on original board
         Node node = pq.delMin();
         
         if (node.board.isGoal()) {
            solutionMoves = node.moves;
            solution = new Board[node.moves + 1];
            
            for (int i = node.moves; i >= 0; i--) {
               solution[i] = node.board;
               node = node.prev;
            }
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
      return solutionMoves == -1 ? false : true;
   }
   
   // min number of moves to solve initial board; -1 if unsolvable
   public int moves() {
      return solutionMoves;
   }
   
   // sequence of boards in a shortest solution; null if unsolvable
   public Iterable<Board> solution() {
      return solutionMoves == -1 ? null : Arrays.asList(solution);
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