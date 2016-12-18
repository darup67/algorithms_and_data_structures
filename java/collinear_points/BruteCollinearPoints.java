import java.util.Arrays;
import java.util.ArrayList;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;

public class BruteCollinearPoints {
   private final Point[]       pts;           // copy of input array
   private final LineSegment[] lineSegments;  // set of lines to be drawn
   
   // finds all line segments containing 4 points
   public BruteCollinearPoints(Point[] points) {
      if (points == null) throw new NullPointerException();
      
      pts = points.clone();
      ArrayList<LineSegment> lines = new ArrayList<LineSegment>();
      
      Arrays.sort(pts);
      
      for (int i = 0; i < pts.length; i++) {
         if (pts[i] == null) throw new NullPointerException();
         
         for (int j = i + 1; j < pts.length; j++) {
            if (pts[j] == null)
               throw new NullPointerException();
            
            if (pts[i].slopeTo(pts[j]) == Double.NEGATIVE_INFINITY)
               throw new IllegalArgumentException();
            
            for (int k = j + 1; k < pts.length; k++) {
               if (pts[k] == null) throw new NullPointerException();
               
               for (int l = k + 1; l < pts.length; l++) {
                  if (pts[l] == null) throw new NullPointerException();
                  
                  double slope1 = pts[i].slopeTo(pts[j]);
                  double slope2 = pts[i].slopeTo(pts[k]);
                  double slope3 = pts[i].slopeTo(pts[l]);
                  
                  if (slope1 == slope2 && slope1 == slope3) {            
//                     StdOut.println(pts[i].toString());
//                     StdOut.println(pts[j].toString());
//                     StdOut.println(pts[k].toString());
//                     StdOut.println(pts[l].toString());
//                     StdOut.println("");
                     lines.add(new LineSegment(pts[i], pts[l]));
                  }
               }
            }
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
      BruteCollinearPoints collinear = new BruteCollinearPoints(points);
      for (LineSegment segment : collinear.segments()) {
          StdOut.println(segment);
          segment.draw();
      }
      StdDraw.show();
   }
}
