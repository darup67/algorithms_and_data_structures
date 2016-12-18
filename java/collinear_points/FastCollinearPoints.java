import java.util.*;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;

public class FastCollinearPoints {
   private LineSegment[] lineSegments;
   
   // finds all line segments containing 4 or more points
   public FastCollinearPoints(Point[] points) {
      if (points == null) throw new NullPointerException();
      
      ArrayList<LineSegment> lines = new ArrayList<LineSegment>();
      Point[] pointsCopy = new Point[points.length - 1];
      
      for (int i = 0; i < points.length; i++) {
         int j = 0, k = 0;
         while (j < points.length - 1) {
            if (i != k) {
               pointsCopy[j] = points[k];
               j++;
            }
            k++;
         }
         
         Arrays.sort(pointsCopy, points[i].slopeOrder());
         
         for (j = 0; j < pointsCopy.length - 2; j++) {
            if (points[i].slopeTo(pointsCopy[j]) == points[i].slopeTo(pointsCopy[j + 1]) &&
                points[i].slopeTo(pointsCopy[j]) == points[i].slopeTo(pointsCopy[j + 2])) {
            
               Point[] linePoints = {points[i], pointsCopy[j], pointsCopy[j+1], pointsCopy[j+2]};
               Arrays.sort(linePoints);
               lines.add(new LineSegment(linePoints[0], linePoints[3]));
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
      return lineSegments;
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
          StdOut.println(segment);
          segment.draw();
      }
      StdDraw.show();
   }
}
