import java.util.Arrays;
import java.util.ArrayList;
import edu.princeton.cs.algs4.In;
// import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;

public class FastCollinearPoints {
   private final Point[]       pts;           // copy of input array
   private final LineSegment[] lineSegments;  // set of lines to be drawn
   
   // finds all line segments containing 4 or more points
   public FastCollinearPoints(Point[] points) {
      if (points == null) throw new NullPointerException();
      
      ArrayList<LineSegment> lines = new ArrayList<LineSegment>();
      
      pts = points.clone();
      Point[] ptsCopy = new Point[pts.length - 1];
      
      Arrays.sort(pts);
      
      for (int i = 0; i < pts.length; i++) {
         int j = 0, k = 0;
         while (j < pts.length - 1) {
            if (i != k) {
               ptsCopy[j] = pts[k];
               j++;
            }
            k++;
         }
         
         Arrays.sort(ptsCopy, pts[i].slopeOrder());
         
         if (pts[i].slopeTo(ptsCopy[0]) == Double.NEGATIVE_INFINITY)
               throw new IllegalArgumentException();
         
         for (j = 0; j < ptsCopy.length - 1;) {  // No ++ here, see end of loop
            if (pts[i].slopeTo(ptsCopy[j + 1]) == Double.NEGATIVE_INFINITY)
               throw new IllegalArgumentException();
            
            final double slopeToJ = pts[i].slopeTo(ptsCopy[j]);
            int count = 2;
            k = j + 1;
            
//            StdOut.println(pts[i].toString());
//            StdOut.println(ptsCopy[j].toString());
            
            // While there are points left, points are in order, have same slope
            while (k < ptsCopy.length &&
                   ptsCopy[j].compareTo(ptsCopy[k]) <= 0 &&
                   slopeToJ == pts[i].slopeTo(ptsCopy[k])) {
//               StdOut.println(ptsCopy[k].toString());
               count++;
               k++;
            }
            
//            StdOut.println(count);
//            StdOut.println("");
            
            // If there are 4 or more contiguous same-slope points
            // and pts[i] is the first point in natural order
            // (k - 1 is the last valid index of the while loop)
            if (count >= 4 && pts[i].compareTo(ptsCopy[j]) <= 0) {
//               StdOut.println("New line!");
//               StdOut.println("");
//               StdOut.println("");
               
               lines.add(new LineSegment(pts[i], ptsCopy[k - 1]));
            }
            
            // Jump past this set of contiguous same-slope points to next point
            // Serves as loop incrementer
            j = k;
         }
      }
      
      lineSegments = lines.toArray(new LineSegment[lines.size()]);
   }
   
   // the number of line segments
   public int numberOfSegments() {
      return lineSegments.length;
   }
   
   // the line segments
   public LineSegment[] segments() {
      return lineSegments.clone();
   }
   
   // test client
   public static void main(String[] args) {
      // read the n points from a file
      In in = new In(args[0]);
      int n = in.readInt();
      Point[] points = new Point[n];
      for (int i = 0; i < n; i++) {
          int x = in.readInt();
          int y = in.readInt();
          points[i] = new Point(x, y);
      }

      // draw the points
      StdDraw.enableDoubleBuffering();
      StdDraw.setXscale(0, 32768);
      StdDraw.setYscale(0, 32768);
      for (Point p : points) {
          p.draw();
      }
      StdDraw.show();

      // print and draw the line segments
      FastCollinearPoints collinear = new FastCollinearPoints(points);
      for (LineSegment segment : collinear.segments()) {
//          StdOut.println(segment);
          segment.draw();
      }
      StdDraw.show();
   }
}
