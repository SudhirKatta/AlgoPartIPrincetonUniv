import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdRandom;

public class Board {

    private int[] board;
    private final int boardsize;
    private int hammingPriority = 0;
    private int manhattanDist = 0;
    private int zeroLocation1DCord = -1;

    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {

        if (blocks == null)
            throw new java.lang.NullPointerException("null block");

        boardsize = blocks.length;
        board = new int[(boardsize * boardsize)];

        for (int i = 0; i < boardsize; i++) {
            for (int j = 0; j < boardsize; j++) {
                int oneD = xyTo1D(i, j);
                this.board[oneD] = blocks[i][j];

                if (zeroLocation1DCord != -1 && blocks[i][j] == 0)
                    throw new java.lang.IllegalArgumentException("Too many empty blocks");

                if (blocks[i][j] == 0)
                    zeroLocation1DCord = oneD;
            }
        }

        calcHamming();
        calcManhattan();
    }

    // board dimension n
    public int dimension() {
        return boardsize;
    }

    // number of blocks out of place
    public int hamming() {
        return hammingPriority;
    }

    private void calcHamming() {
        for (int i = 0; i < board.length; i++) {
            if (board[i] != i + 1 && board[i] != 0)
                hammingPriority++;
        }
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        return manhattanDist;
    }

    private void calcManhattan() {
        for (int i = 0; i < board.length; i++) {
            if (board[i] != i + 1 && board[i] != 0) {
                int xCord = oneDToxy(i)[0];
                int yCord = oneDToxy(i)[1];
                int expectedX = (board[i] - 1) / boardsize;
                int expectedY = (board[i] - 1) % boardsize;
                int dx = xCord - expectedX; // x-distance to expected coordinate
                int dy = yCord - expectedY; // y-distance to expected coordinate
                manhattanDist += Math.abs(dx) + Math.abs(dy);
            }
        }
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // a board that is obtained by exchanging any pair of
    // blocks
    public Board twin() {

        boolean swap = true;

        int[][] twinblocks = deepCopyBlocks();

        while (swap) {

            int oneDindex1 = StdRandom.uniform(boardsize * boardsize);
            int oneDindex2 = StdRandom.uniform(boardsize * boardsize);

            if (twinblocks[oneDToxy(oneDindex1)[0]][oneDToxy(oneDindex1)[1]] != 0
                    && twinblocks[oneDToxy(oneDindex2)[0]][oneDToxy(oneDindex2)[1]] != 0 && oneDindex1 != oneDindex2) {

                swapblocks(twinblocks, oneDToxy(oneDindex1)[0], oneDToxy(oneDindex1)[1], oneDToxy(oneDindex2)[0],
                        oneDToxy(oneDindex2)[1]);
                swap = false;
            }
        }

        return new Board(twinblocks);
    }

    // does this board equal other?
    public boolean equals(Object other) {
        if (other == this)
            return true;
        if (other == null)
            return false;
        if (other.getClass() != this.getClass())
            return false;
        Board that = (Board) other;
        if (this.dimension() != that.dimension())
            return false;
        if (this.toString().equals(that.toString()))
            return true;

        return false;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {

        Stack<Board> boardConfigs = new Stack<Board>();

        int row = oneDToxy(zeroLocation1DCord)[0];
        int col = oneDToxy(zeroLocation1DCord)[1];

        // right
        if (isValidIndices(row, col + 1)) {
            int[][] blocks = deepCopyBlocks();
            swapblocks(blocks, row, col, row, col + 1);
            boardConfigs.push(new Board(blocks));
        }

        // left
        if (isValidIndices(row, col - 1)) {
            int[][] blocks = deepCopyBlocks();
            swapblocks(blocks, row, col, row, col - 1);
            boardConfigs.push(new Board(blocks));
        }

        // top
        if (isValidIndices(row - 1, col)) {
            int[][] blocks = deepCopyBlocks();
            swapblocks(blocks, row, col, row - 1, col);
            boardConfigs.push(new Board(blocks));
        }

        // bottom
        if (isValidIndices(row + 1, col)) {
            int[][] blocks = deepCopyBlocks();
            swapblocks(blocks, row, col, row + 1, col);
            boardConfigs.push(new Board(blocks));
        }

        return boardConfigs;
    }

    private void swapblocks(int[][] blocks, int row1, int col1, int row2, int col2) {
        int tmp = blocks[row2][col2];
        blocks[row2][col2] = blocks[row1][col1];
        blocks[row1][col1] = tmp;
    }

    private int[][] deepCopyBlocks() {
        int[][] blocks = new int[boardsize][boardsize];

        for (int i = 0; i < board.length; i++) {
            blocks[oneDToxy(i)[0]][oneDToxy(i)[1]] = board[i];
        }

        return blocks;
    }

    private boolean isValidIndices(int indexi, int indexj) {
        return ((0 <= indexi && indexi < boardsize) & (0 <= indexj && indexj < boardsize));
    }

    // string representation of this board (in the
    // output format specified below)
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(boardsize + "\n");
        for (int i = 0; i < board.length; i++) {
            if (i % boardsize == 0 && i != 0)
                s.append("\n");
            s.append(String.format("%2d ", board[i]));
        }
        s.append("\n");
        return s.toString();
    }

    // Converts 2D cordinates to 1D array cordinate
    private int xyTo1D(int indexi, int indexj) {
        return (indexi * boardsize) + indexj;
    }

    // Converts 2D cordinates to 1D array cordinate
    private int[] oneDToxy(int index) {
        return new int[] { index / boardsize, index % boardsize };
    }

    // unit tests (not graded)
    public static void main(String[] args) {

        int[][] node1 = new int[3][3];
        int[][] node2 = new int[2][2];

        node1[0][0] = 1;
        node1[0][1] = 2;
        node1[0][2] = 3;
        node1[1][0] = 4;
        node1[1][1] = 0;
        node1[1][2] = 5;
        node1[2][0] = 7;
        node1[2][1] = 8;
        node1[2][2] = 6;

        /*
         * for (int i = 0; i < 3; i++) { for (int j = 0; j < 3; j++) { if ((i *
         * 3) + j + 1 == 9) break; // node1[i][j] = (i * 3) + j + 2; node2[i][j]
         * = (i * 3) + j + 1; } }
         */
        node2[0][0] = 0;
        node2[0][1] = 3;
        node2[1][0] = 1;
        node2[1][1] = 2;

        //Board board1 = new Board(node1);
        Board board2 = new Board(node2);

        // System.out.println("Hamming = " + board1.hamming() + " manhatan = " +
        // board1.manhattan() + " isGoal = " + board1.isGoal());

        // System.out.println(board1.toString() + board1.equals(board2));

        System.out.println(board2.toString() + board2.twin().toString());
        /*
         * System.out.println(board1.toString());
         * 
         * for (Board board : board1.neighbors()) {
         * System.out.println(board.toString()); }
         */
    }
}
