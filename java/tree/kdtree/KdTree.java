import java.util.ArrayList;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
   private Node root;   // root pointer
   private int  n = 0;  // tree size
   
   private class Node {
      private Point2D p;             // node Point2D
      private Node    left  = null;  // left child pointer
      private Node    right = null;  // right child pointer
      
      public Node(Point2D p) {
         this.p = p;
      }
   }
   
   // construct an empty set of points
   public KdTree() {
      
   }
   
   // recursive draw implementation
   private void drawNode(Node node) {
      if (Node == null) return;
      
      StdDraw.point(node.p.x(), node.p.y());
      
      drawNode(node.left);
      drawNode(node.right);
   }
   
   // internal recursive search method for public insert() method
   private Node insertionPoint(Node node, Point2D p, int level) {
      
      // level is even (compare x coordinate)
      if (level % 2 == 0) {
         if (node.p.x() < p.x()) {
            if (node.left == null) return node;
            else return insertionPoint(node.left, p, level + 1);
         
         } else {
            return insertionPoint(node.right, p, level + 1);
         }
      
      // level is odd (compare y coordinate)
      } else {
         if (node.p.y() < p.y()) {
            return insertionPoint(node.left, p, level + 1);
         
         } else {
            return insertionPoint(node.right, p, level + 1);
         }
      }
   }
   
   // internal recursive search method for public contains() method
   private boolean isInTree(Node node, Point2D p, int level) {
      if (node == null) return false;
      if (node.p.x() == p.x() && node.p.y() == p.y()) return true;
      
      // level is even (compare x coordinate)
      if (level % 2 == 0) {
         if (node.p.x() < p.x()) {
            return isInTree(node.left, p, level + 1);
         
         } else {
            return isInTree(node.right, p, level + 1);
         }
      
      // level is odd (compare y coordinate)
      } else {
         if (node.p.y() < p.y()) {
            return isInTree(node.left, p, level + 1);
         
         } else {
            return isInTree(node.right, p, level + 1);
         }
      }
   }
   
   // is the set empty?
   public boolean isEmpty() {
      return n == 0;
   }
   
   // number of points in the set
   public int size() {
      return n;
   }
   
   // add the point to the set (if it is not already in the set)
   public void insert(Point2D p) {
      findInsertionPoint
      
      n++;
   }
   
   // does the set contain point p?
   public boolean contains(Point2D p) {
      int level = 0;
      
      return isInTree(root, p, level);
   }
   
   // draw all points to standard draw
   public void draw() {
      StdDraw.point(root.p.x(), root.p.y());
      
      drawNode(root.left);
      drawNode(root.right);
   }
   
   // all points that are inside the rectangle 
   public Iterable<Point2D> range(RectHV rect) {
      ArrayList<Point2D> pointsInRange = new ArrayList<Point2D>();
      
      
      
      return pointsInRange;
   }
   
   // a nearest neighbor in the set to point p; null if the set is empty
   public Point2D nearest(Point2D p) {
      if (isEmpty()) return null;
      
      double  nearDist = Double.POSITIVE_INFINITY;
      Point2D nearest  = null;
      
      for (Point2D q : points) {
         final double dist = q.distanceSquaredTo(p);
         
         if (dist < nearDist) {
            nearDist = dist;
            nearest = q;
         }
      }
      
      return nearest;
   }
   
   // unit testing of the methods (optional)
   public static void main(String[] args) {
      
   }
}