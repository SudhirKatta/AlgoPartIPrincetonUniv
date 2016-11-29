import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int gridSize;
    private int[] oneDArray;
    private WeightedQuickUnionUF weightedQuickUnionUF;
    private WeightedQuickUnionUF weightedQuickUnionUFTop;

    // Constructor
    public Percolation(int n) {

        if (n <= 0)
            throw new java.lang.IllegalArgumentException("grid size n less than one");
        this.gridSize = n;

        int arraysize1D = (gridSize * gridSize) + 2;
        weightedQuickUnionUF = new WeightedQuickUnionUF(arraysize1D);
        weightedQuickUnionUFTop = new WeightedQuickUnionUF(arraysize1D);

        oneDArray = new int[arraysize1D];
        for (int i = 1; i < oneDArray.length; i++) {
            oneDArray[i] = 0;
        }
        oneDArray[0] = 1;
        oneDArray[arraysize1D - 1] = 1;
    }

    // Checks index i and index j and throws exception
    private void checkForIndicesException(int indexi, int indexj) {
        // System.out.println("indexi :" + indexi + " indexj :" + indexj +
        // "\n");
        if (indexi <= 0 || indexi > gridSize)
            throw new IndexOutOfBoundsException("row index i out of bounds");
        if (indexj <= 0 || indexj > gridSize)
            throw new IndexOutOfBoundsException("row index j out of bounds");
    }

    private boolean isValidIndices(int indexi, int indexj) {
        return ((0 < indexi && indexi <= gridSize) & (0 < indexj && indexj <= gridSize));
    }

    // Converts 2D cordinates to 1D array cordinate
    private int xyTo1D(int indexi, int indexj) {
        return ((indexi - 1) * gridSize) + indexj;
    }

    // open site (row i, column j) if it is not open already
    public void open(int rowi, int columnj) {
        checkForIndicesException(rowi, columnj);

        // Centre
        if (!isOpen(rowi, columnj)) {
            int thissite1D = xyTo1D(rowi, columnj);
            oneDArray[thissite1D] = 1;
            connectToVirtualSite(rowi, columnj);

            // Top
            if (isValidIndices(rowi - 1, columnj)) {
                if (isOpen(rowi - 1, columnj)) {
                    weightedQuickUnionUF.union(thissite1D, xyTo1D(rowi - 1, columnj));
                    weightedQuickUnionUFTop.union(thissite1D, xyTo1D(rowi - 1, columnj));
                    connectToVirtualSite(rowi - 1, columnj);
                }
            }

            // Down
            if (isValidIndices(rowi + 1, columnj)) {
                if (isOpen(rowi + 1, columnj)) {
                    weightedQuickUnionUF.union(thissite1D, xyTo1D(rowi + 1, columnj));
                    weightedQuickUnionUFTop.union(thissite1D, xyTo1D(rowi + 1, columnj));
                    connectToVirtualSite(rowi + 1, columnj);
                }
            }

            // Right
            if (isValidIndices(rowi, columnj + 1)) {
                if (isOpen(rowi, columnj + 1)) {
                    weightedQuickUnionUF.union(thissite1D, xyTo1D(rowi, columnj + 1));
                    weightedQuickUnionUFTop.union(thissite1D, xyTo1D(rowi, columnj + 1));
                }
            }

            // Left
            if (isValidIndices(rowi, columnj - 1)) {
                if (isOpen(rowi, columnj - 1)) {
                    weightedQuickUnionUF.union(thissite1D, xyTo1D(rowi, columnj - 1));
                    weightedQuickUnionUFTop.union(thissite1D, xyTo1D(rowi, columnj - 1));
                }
            }
        }
    }

    // If the open site od top row or bottom row connect to virtual site
    private void connectToVirtualSite(int rowi, int columnj) {
        // If the site is top row or bottom row connect to virtual site
        if (rowi == 1) {
            weightedQuickUnionUF.union(0, xyTo1D(rowi, columnj));
            weightedQuickUnionUFTop.union(0, xyTo1D(rowi, columnj));
        }

        if (rowi == gridSize) {
            weightedQuickUnionUF.union((gridSize * gridSize) + 1, xyTo1D(rowi, columnj));
        }
    }

    // is site (row i, column j) open?
    public boolean isOpen(int indexi, int indexj) {
        checkForIndicesException(indexi, indexj);
        return (oneDArray[xyTo1D(indexi, indexj)] == 1);
    }

    // is site (row i, column j) full?
    public boolean isFull(int indexi, int indexj) {
        checkForIndicesException(indexi, indexj);
        return (weightedQuickUnionUF.connected(0, xyTo1D(indexi, indexj))
                && weightedQuickUnionUFTop.connected(0, xyTo1D(indexi, indexj)));
    }

    // does the system percolate?
    public boolean percolates() {
        return weightedQuickUnionUF.connected(0, (gridSize * gridSize) + 1);
    }

    public static void main(String[] args) {
        try {
            In in = new In("/home/skatta/workspace/Algo1Week1/percolation/input2.txt");
            int n = in.readInt();

            Percolation perc = new Percolation(n);
            while (!in.isEmpty()) {
                int i = in.readInt();
                int j = in.readInt();
                perc.open(i, j);
            }
            System.out.println("percolates :" + perc.percolates());
        } catch (Exception e) {
            System.out.println(e);
        }
    }
     
}
