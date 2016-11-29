import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private int gridSize, trials;
    private double[] openSitesTrial;

    public PercolationStats(int n, int trials) {
        // perform trials independent experiments on an n-by-n grid
        if (n <= 0) {
            throw new java.lang.IllegalArgumentException("grid size n less than one");
        }
        this.gridSize = n;

        if (trials <= 0) {
            throw new java.lang.IllegalArgumentException("number of trials less than one");
        }
        this.trials = trials;
        openSitesTrial = new double[trials];
    }

    public double mean() {

        while (trials != 0) {
            Percolation perc = new Percolation(gridSize);
            while (!perc.percolates()) {
                int row = StdRandom.uniform(gridSize + 1);
                int col = StdRandom.uniform(gridSize + 1);
                if (row != 0 && col != 0) {
                    if (!perc.isOpen(row, col)) {
                        perc.open(row, col);
                        openSitesTrial[trials - 1]++;
                    }
                }
            }
            openSitesTrial[trials - 1] = openSitesTrial[trials - 1] / (gridSize * gridSize);
            trials--;
        }
        return StdStats.mean(openSitesTrial);
    }

    public double stddev() {
        // sample standard deviation of percolation threshold
        this.mean();
        return StdStats.stddev(openSitesTrial);
    }

    public double confidenceLo() {
        // low endpoint of 95% confidence interval
        this.stddev();
        return this.mean() - ((1.96 * this.stddev()) / Math.sqrt(gridSize));
    }

    public double confidenceHi() {
        // high endpoint of 95% confidence interval
        stddev();
        return this.mean() + ((1.96 * this.stddev()) / Math.sqrt(gridSize));
    }

    
    public static void main(String[] args) {

        // PercolationStats percolationStats = new
        // PercolationStats(Integer.parseInt(args[0]),
        // Integer.parseInt(args[1]));
        PercolationStats percolationStats = new PercolationStats(200, 100);
        System.out.println("mean " + percolationStats.mean());
        System.out.println("stddev " + percolationStats.stddev());
        System.out.println(
                "95% confidence interval " + percolationStats.confidenceLo() + " , " + percolationStats.confidenceHi());
    }
 }