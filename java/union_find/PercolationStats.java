import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
   private double[] results;  // percolation threshold as open site percentage
   
   // perform trials independent experiments on an n-by-n grid
   public PercolationStats(int n, int trials) {
      if (n < 1 || trials < 1)
         throw new IllegalArgumentException("n or trials less than 1");
      
      int[] results = new int[n];
      
      for (int i = 0; i < trials; i++) {
         Percolation perc = new Percolation(n);
         int openings = 0;
         
         while (!perc.percolates()) {
            perc.open(StdRandom.uniform(1, n), StdRandom.uniform(1, n));
            openings++;
         }
         
         results[i] = openings / n;  // implicit conversion to double
      }
   }
   
   // sample mean of percolation threshold
   public double mean() {
      return StdStats.mean(results);
   }
   
   // sample standard deviation of percolation threshold
   public double stddev() {
      return StdStats.stddev(results);
   }
   
   // low  endpoint of 95% confidence interval
   public double confidenceLo() {
      return mean() - ((1.96 * stddev()) / Math.sqrt(results.length));
   }
   
   // high endpoint of 95% confidence interval
   public double confidenceHi() {
      return mean() + ((1.96 * stddev()) / Math.sqrt(results.length));
   }

   // test client
   public static void main(String[] args) {
      int n = StdIn.readInt();
      int trials = StdIn.readInt();
     
      /*PercolationStats stats = new PercolationStats(n, trials);
      StdOut.println("mean                    = " + stats.mean());
      StdOut.println("stddev                  = " + stats.stddev());
      StdOut.println("95% confidence interval = " + stats.confidenceLo()
                        + ", " + stats.confidenceHi());*/
      
      Percolation perc = new Percolation(n);
      int openings = 0;
      
      while (!perc.percolates()) {
         int rand1 = StdRandom.uniform(1, n + 1);
         int rand2 = StdRandom.uniform(1, n + 1);
         
         if (!perc.isOpen(rand1, rand2)) {
            perc.open(rand1, rand2);
            openings++;
            StdOut.println(rand1 + " " + rand2);
         }
      }
      
      StdOut.println(openings);
   }
}