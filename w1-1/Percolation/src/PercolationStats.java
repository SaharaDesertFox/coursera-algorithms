/**
 * User: stas
 * Date: 09.02.14 15:55
 */

public class PercolationStats {

    private int T;
    private double[] fraction;

    // perform T independent computational experiments on an N-by-N grid
    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0) throw new IllegalArgumentException();
        this.T = T;
        fraction = new double[T];

        int t = 0, size = N * N;
        while (t < T) {
            Percolation p = new Percolation(N);
            int count = 0;
            while (!p.percolates()) {
                int i = StdRandom.uniform(N) + 1;
                int j = StdRandom.uniform(N) + 1;
                if (!p.isOpen(i, j)) {
                    p.open(i, j);
                    count++;
                }
            }
            fraction[t] = ((double) count) / size;
            t++;
        }
        StdOut.println("mean                    = " + mean());
        StdOut.println("stddev                  = " + stddev());
        StdOut.println("95% confidence interval = " + confidenceLo() + ", " + confidenceHi());
    }

    // sample mean of percolation threshold
    public double mean() {
        double sum = 0;
        for (int i = 0; i < T; i++) {
            sum += fraction[i];
        }
        return sum / T;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        double mean = mean();
        double sum = 0;
        for (int i = 0; i < T; i++) {
            sum += (fraction[i] - mean) * (fraction[i] - mean);
        }
        return Math.sqrt(sum / (T - 1));
    }

    // returns lower bound of the 95% confidence interval
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(T);
    }

    // returns upper bound of the 95% confidence interval
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(T);
    }

    // test client, described below
    public static void main(String[] args) {
        int N = 200, T = 100;
        if (args.length == 2) {
            N = Integer.parseInt(args[0]);
            T = Integer.parseInt(args[1]);
        }
        new PercolationStats(N, T);
    }

}
