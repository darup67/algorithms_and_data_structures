import java.util.*;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;

public class FastCollinearPoints {
   private LineSegment[] lineSegments;
   
   // datatype of previously built lines with slope and endpoint
//   private class PointSlope {
//      private final Point endpoint;
//      private final double slope;
//      
//      public PointSlope(Point endpoint, double slope) {
//         this.endpoint = endpoint;
//         this.slope = slope;
//      }
//      
//      @Override
//      public boolean equals(PointSlope that) {
//         return that.endpoint == this.endpoint && that.slope == this.slope;
//      }
//      
//      @Override
//      public int hashCode() {
//         return 
//      }
//   }
   
   // finds all line segments containing 4 or more points
   public FastCollinearPoints(Point[] points) {
      if (points == null) throw new NullPointerException();
      
      ArrayList<Point>       endpoints = new ArrayList<Point>();
      ArrayList<Double>      slopes    = new ArrayList<Double>();
      ArrayList<LineSegment> lines     = new ArrayList<LineSegment>();
      Point[] pointsCopy = new Point[points.length - 1];
      
      Arrays.sort(points);
      
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
         
         for (j = 0; j < pointsCopy.length; j++) {
            int count = 0;
            k = j;
            
            double slopeToJ = points[i].slopeTo(pointsCopy[j]);
            
            while (k < pointsCopy.length &&
                   points[i].compareTo(pointsCopy[k]) <= 0 &&
                   slopeToJ == points[i].slopeTo(pointsCopy[k])) {
               
               count++;
//               StdOut.println("Count: " + count);
//               StdOut.println("Compare: " + points[i].compareTo(pointsCopy[k]));
//               StdOut.println("");
               k++;
            }
            
            if (count >= 3) {
//               StdOut.println(points[i].toString());
//               for (int x = 0; x < count; x++) {
//                  StdOut.println(pointsCopy[j + x].toString());
//               }
//               StdOut.println("");
               
               Point endpoint = pointsCopy[k - 1];
               boolean isDuplicate = false;
               
               for (int x = 0; x < endpoints.size(); x++) {
                  if (endpoints.get(x) == endpoint && slopes.get(x) == slopeToJ) {
                     isDuplicate = true;
                  }
               }
               
               if (!isDuplicate) {
                  lines.add(new LineSegment(points[i], pointsCopy[k - 1]));
                  endpoints.add(endpoint);
                  slopes.add(slopeToJ);
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
