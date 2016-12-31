import java.util.ArrayList;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class KdTree {
   private Node root = null;  // root pointer
   private int  n = 0;        // tree size
   
   private class Node {
      private Point2D p;             // node Point2D
      private RectHV  rect;          // rect of all points possibly under node
      private Node    left  = null;  // left child pointer
      private Node    right = null;  // right child pointer
      
      public Node(Point2D p, double xmin, double ymin, double xmax, double ymax) {
         this.p    = p;
         this.rect = new RectHV(xmin, ymin, xmax, ymax);
      }
   }
   
   // construct an empty set of points
   public KdTree() {
      
   }
   
   // internal recursive search method for public insert() method
   private void createNode(Node node, Point2D p, int level, double xmin, double ymin, double xmax, double ymax) {
      
      // level is even (compare x coordinate)
      if (level % 2 == 0) {
         if (p.x() < node.p.x()) {
            if (node.left == null) {
//               StdOut.println("Insert left (X)");
               node.left =             new Node(p, xmin, ymin, node.p.x(), ymax);
               
            } else {
//               StdOut.println("Traverse left (X)");
               createNode(node.left, p, level + 1, xmin, ymin, node.p.x(), ymax);
            }
         
         } else {
            if (node.right == null) {
//               StdOut.println("Insert right (X)");
               node.right =              new Node(p,node.p.x(), ymin, xmax, ymax);
               
            } else {
//               StdOut.println("Traverse right (X)");
               createNode(node.right, p, level + 1, node.p.x(), ymin, xmax, ymax);
            }
         }
      
      // level is odd (compare y coordinate)
      } else {
         if (p.y() < node.p.y()) {
            if (node.left == null) {
//               StdOut.println("Insert left (Y)");
               node.left =             new Node(p, xmin, ymin, xmax, node.p.y());
            
            } else {
//               StdOut.println("Traverse left (Y)");
               createNode(node.left, p, level + 1, xmin, ymin, xmax, node.p.y());
            }
         
         } else {
            if (node.right == null) {
//               StdOut.println("Insert right (Y)");
               node.right =             new Node(p, xmin, node.p.y(), xmax, ymax);
            
            } else {
//               StdOut.println("Traverse right (Y)");
               createNode(node.right, p, level + 1, xmin, node.p.y(), xmax, ymax);
            }
         }
      }
   }
   
   // internal recursive search method for public contains() method
   private boolean isInTree(Node node, Point2D p, int level) {
      if (node == null)     return false;
      if (p.equals(node.p)) return true;
      
      // level is even (compare x coordinate)
      if (level % 2 == 0) {
         if (p.x() < node.p.x()) return isInTree(node.left,  p, level + 1);
         else                    return isInTree(node.right, p, level + 1);
      
      // level is odd (compare y coordinate)
      } else {
         if (p.y() < node.p.y()) return isInTree(node.left,  p, level + 1);
         else                    return isInTree(node.right, p, level + 1);
      }
   }
   
   // recursive draw implementation
   private void drawNode(Node node, int level, double xmin, double ymin, double xmax, double ymax) {
      if (node == null) return;
      
      StdOut.println("Point: " + node.p.x() + ", " + node.p.y());
      StdOut.println("X: "    + xmin             + " to " + xmax
         + ", Y: " + ymin             + " to " + ymax);
      StdOut.println("Rect: " + node.rect.xmin() + " to " + node.rect.xmax()
         + ", Y: " + node.rect.ymin() + " to " + node.rect.ymax());
      StdOut.println("Level: " + level);
      StdOut.println();
      
      // level is even (vertical line)
      if (level % 2 == 0) {
         StdDraw.setPenColor(StdDraw.RED);
         StdDraw.setPenRadius();
         RectHV line = new RectHV(node.p.x(), ymin, node.p.x(), ymax);
         line.draw();
         line = null;
                  
         drawNode(node.left,  level + 1, xmin, ymin, node.p.x(), ymax);
         drawNode(node.right, level + 1, node.p.x(), ymin, xmax, ymax);
      
      // level is odd (horizontal line)
      } else {
         StdDraw.setPenColor(StdDraw.BLUE);
         StdDraw.setPenRadius();
         RectHV line = new RectHV(xmin, node.p.y(), xmax, node.p.y());
         line.draw();
         line = null;
                      
         drawNode(node.left,  level + 1, xmin, ymin, xmax, node.p.y());
         drawNode(node.right, level + 1, xmin, node.p.y(), xmax, ymax);
      }
      
      StdDraw.setPenColor(StdDraw.BLACK);
      StdDraw.setPenRadius(0.01);
      node.p.draw();
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
      if (p == null) throw new NullPointerException();
      
      if (root == null) {
         root = new Node(p, 0, 0, 1, 1);
//         StdOut.println("Insert root");
      }
      else createNode(root, p, 0, 0, 0, 1, 1);
      
      n++;
      
//      StdOut.println();
   }
   
   // does the set contain point p?
   public boolean contains(Point2D p) {
      if (p == null) throw new NullPointerException();
      
      return isInTree(root, p, 0);
   }
   
   // draw all points to standard draw
   public void draw() {
      if (root == null) return;
      
      drawNode(root, 0, 0, 0, 1, 1);
   }
   
   // all points that are inside the rectangle 
   public Iterable<Point2D> range(RectHV rect) {
      if (rect == null) throw new NullPointerException();
      
      ArrayList<Point2D> pointsInRange = new ArrayList<Point2D>();
      
      
      
      return pointsInRange;
   }
   
   // a nearest neighbor in the set to point p; null if the set is empty
   public Point2D nearest(Point2D p) {
      if (p == null) throw new NullPointerException();
      
      if (isEmpty()) return null;
      
      double  nearDist = Double.POSITIVE_INFINITY;
      Point2D nearest  = null;
      
      
      
      return nearest;
   }
   
   // unit testing of the methods (optional)
   public static void main(String[] args) {
      KdTree kdtree = new KdTree();
      
//      kdtree.insert(new Point2D(0.2, 0.2));
//      kdtree.insert(new Point2D(0.8, 0.8));
//      kdtree.insert(new Point2D(0.5, 0.5));
//      kdtree.insert(new Point2D(0.3, 0.9));
//      kdtree.insert(new Point2D(0.7, 0.1));
      
      kdtree.insert(new Point2D(0.7, 0.2));
      kdtree.insert(new Point2D(0.5, 0.4));
      kdtree.insert(new Point2D(0.2, 0.3));
      kdtree.insert(new Point2D(0.4, 0.7));
      kdtree.insert(new Point2D(0.9, 0.6));
      
//      StdOut.println(kdtree.contains(new Point2D(0.7, 0.2)));
//      StdOut.println(kdtree.contains(new Point2D(0.4, 0.7)));
//      StdOut.println(kdtree.contains(new Point2D(0.4, 0.8)));
      
      StdDraw.enableDoubleBuffering();
      StdDraw.clear();
      kdtree.draw();
      StdDraw.show();
   }
}