import java.util.*;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;

public class BruteCollinearPoints {
   private LineSegment[] lineSegments;
   
   // finds all line segments containing 4 points
   public BruteCollinearPoints(Point[] points) {
      if (points == null) throw new NullPointerException();
      
      ArrayList<LineSegment> lines = new ArrayList<LineSegment>();
      
      for (int i = 0; i < points.length; i++) {
         if (points[i] == null) throw new NullPointerException();
         
         for (int j = i + 1; j < points.length; j++) {
            if (points[j] == null)      throw new NullPointerException();
            if (points[i] == points[j]) throw new IllegalArgumentException();
            
            for (int k = j + 1; k < points.length; k++) {
               if (points[k] == null) throw new NullPointerException();
               
               for (int l = k + 1; l < points.length; l++) {
                  if (points[l] == null) throw new NullPointerException();
                  
                  Double slope1 = points[i].slopeTo(points[j]);
                  Double slope2 = points[i].slopeTo(points[k]);
                  Double slope3 = points[i].slopeTo(points[l]);
                  
                  if (slope1.equals(slope2) && slope1.equals(slope3)) {
//                     StdOut.println(slope1 + ", " + slope2 + ", " + slope3);
//                     StdOut.println("");
                     
                     Point[] ptArr = {points[i], points[j],
                                      points[k], points[l]};
                     Arrays.sort(ptArr);
//                     for (Point p : ptArr) StdOut.println(p.toString());
//                     StdOut.println("");
                     
                     lines.add(new LineSegment(ptArr[0], ptArr[3]));
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
      return lineSegments;
   }
   
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

