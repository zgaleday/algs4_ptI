import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;




public class PercolationStats {
    
    private int rows;
    private int trials;
    private int sites;
    private double[] results;
    
    public PercolationStats(int N, int T) {
        
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("N or T out of range");
        }
            
        rows = N;
        trials = T;
        sites = N*N;
        results = new double[T];
        
        for (int i = 0; i < trials; i++) {
            Percolation perc = new Percolation(N);
            double count = 0;
            while (!perc.percolates()) {
                int p = StdRandom.uniform(1, rows+1); 
                int q = StdRandom.uniform(1, rows+1);
                if (!perc.isOpen(p, q)) {
                    perc.open(p, q);
                    count += 1;
                }
            }
            results[i] = count / sites;
        }
    }
    
    public double mean() {
        return StdStats.mean(results);
    }
    
    public double stddev() {
        return StdStats.stddev(results);
    }
    
    public double confidenceLo() {
        return (mean() - (1.96*stddev()/Math.sqrt(((double) trials))));
    }
    
    public double confidenceHi() {
        return (mean() + (1.96*stddev()/Math.sqrt(((double) trials))));
    }
    
    public static void main(String[] args) {
    
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats percStat = new PercolationStats(N, T);
        StdOut.printf("mean                    =%f\n", percStat.mean());
        StdOut.printf("stddev                  =%f\n", percStat.stddev());
        StdOut.printf("95%% confidence interval  =%f, %f\n",
                      percStat.confidenceLo(), percStat.confidenceHi());
        
    }
}
    
    
    