import java.util.TreeSet;
import java.util.ArrayList;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class PointSET {
   private TreeSet<Point2D> points;
   
   // construct an empty set of points
   public PointSET() {
      points = new TreeSet<Point2D>();
   }
   
   // is the set empty?
   public boolean isEmpty() {
      return points.isEmpty();
   }
   
   // number of points in the set
   public int size() {
      return points.size();
   }
   
   // add the point to the set (if it is not already in the set)
   public void insert(Point2D p) {
      if (p == null) throw new NullPointerException();
      
      points.add(p);
   }
   
   // does the set contain point p?
   public boolean contains(Point2D p) {
      if (p == null) throw new NullPointerException();
      
      return points.contains(p);
   }
   
   // draw all points to standard draw
   public void draw() {
      for (Point2D p : points) p.draw();
   }
   
   // all points that are inside the rectangle 
   public Iterable<Point2D> range(RectHV rect) {
      if (rect == null) throw new NullPointerException();
      
      ArrayList<Point2D> pointsInRange = new ArrayList<Point2D>();
      
      for (Point2D p : points) {
         if (rect.contains(p)) pointsInRange.add(p);
      }
      
      return pointsInRange;
   }
   
   // a nearest neighbor in the set to point p; null if the set is empty
   public Point2D nearest(Point2D p) {
      if (p == null) throw new NullPointerException();
      
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