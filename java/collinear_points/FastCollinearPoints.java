import java.util.Arrays;
import java.util.ArrayList;
import edu.princeton.cs.algs4.In;
//import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;

public class FastCollinearPoints {
   private final Point[]       pts;           // copy of input array
   private final LineSegment[] lineSegments;  // set of lines to be drawn
   
   // finds all line segments containing 4 or more points
   public FastCollinearPoints(Point[] points) {
      if (points == null) throw new NullPointerException();
      
      pts = points.clone();
      ArrayList<Point>       endpoints = new ArrayList<Point>();
      ArrayList<Double>      slopes    = new ArrayList<Double>();
      ArrayList<LineSegment> lines     = new ArrayList<LineSegment>();
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
         
         for (j = 0; j < ptsCopy.length; j++) {
            double slopeToJ = pts[i].slopeTo(ptsCopy[j]);
            int count = 0;
            k = j;
            
            if (slopeToJ == Double.NEGATIVE_INFINITY)
               throw new IllegalArgumentException();
            
            while (k < ptsCopy.length &&
                   pts[i].compareTo(ptsCopy[k]) <= 0 &&
                   slopeToJ == pts[i].slopeTo(ptsCopy[k])) {
               
               count++;
//               StdOut.println("Count: " + count);
//               StdOut.println("Compare: " + pts[i].compareTo(ptsCopy[k]));
//               StdOut.println("");
               k++;
            }
            
            if (count >= 3) {
//               StdOut.println(pts[i].toString());
//               for (int x = 0; x < count; x++) {
//                  StdOut.println(ptsCopy[j + x].toString());
//               }
//               StdOut.println("");
               
               Point endpoint = ptsCopy[k - 1];
               boolean isDuplicate = false;
               
               for (int x = 0; x < endpoints.size(); x++) {
                  if (endpoints.get(x) == endpoint && slopes.get(x) == slopeToJ) {
                     isDuplicate = true;
                     break;
                  }
               }
               
               if (!isDuplicate) {
                  lines.add(new LineSegment(pts[i], ptsCopy[k - 1]));
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
