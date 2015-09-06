import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {
    private double[] fractions;

    public PercolationStats(int N, int T) {

        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("Illegal arguments");
        }

        fractions = new double[T];
        int lowerRange = 1;
        int upperRange = N+1;

        Percolation percolation;
        for (int i = 0; i < T; i++) {
            percolation = new Percolation(N);
            int openedSites = 0;
            while (!percolation.percolates()) {
                int p = StdRandom.uniform(lowerRange, upperRange);
                int q = StdRandom.uniform(lowerRange, upperRange);
                if (!percolation.isOpen(p, q)) {
                    percolation.open(p, q);
                    openedSites++;
                }
            }

            double fraction = ((double) openedSites)/(N*N);
            fractions[i] = fraction;
        }
    }

    public double mean() {
        return StdStats.mean(fractions);
    }

    public double stddev() {
        return StdStats.stddev(fractions);
    }

    public double confidenceLo() {
        return mean() - 1.96 * stddev()/(Math.sqrt(fractions.length));
    }

    public double confidenceHi() {
        return mean() + 1.96 * stddev()/(Math.sqrt(fractions.length));
    }

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);

        Stopwatch sw = new Stopwatch();
        PercolationStats ps = new PercolationStats(N, T);
        System.out.println("Elapsed time: " + sw.elapsedTime());

        String confidence = ps.confidenceLo() + ", " + ps.confidenceHi();
        StdOut.println("mean                    = " + ps.mean());
        StdOut.println("stddev                  = " + ps.stddev());
        StdOut.println("95% confidence interval = " + confidence);
    }
}
