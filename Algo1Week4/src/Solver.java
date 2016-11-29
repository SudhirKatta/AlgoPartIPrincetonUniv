import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

    private Node goal;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null)
            throw new java.lang.NullPointerException("null block");
         
        final Board initConfig = initial;
        solve(initConfig);
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return goal != null;
    }

    private void solve(Board initial) {

        MinPQ<Node> minPQ = new MinPQ<Node>();
        minPQ.insert(new Node(initial, null));
        Node minNode = minPQ.delMin();
        
        MinPQ<Node> minPQTwin = new MinPQ<Node>();
        minPQTwin.insert(new Node(initial.twin(), null));
        Node minNodeTwin = minPQTwin.delMin();

        while (!minNode.board.isGoal() && !minNodeTwin.board.isGoal()) {

            for (Board board : minNode.board.neighbors()) {
                if (minNode.previousNode == null || !board.equals(minNode.previousNode.board))
                    minPQ.insert(new Node(board, minNode));
            }
            minNode = minPQ.delMin();

            for (Board boardtwin : minNodeTwin.board.neighbors()) {
                if (minNodeTwin.previousNode == null || !boardtwin.equals(minNodeTwin.previousNode.board))
                    minPQTwin.insert(new Node(boardtwin, minNodeTwin));
            }
            minNodeTwin = minPQTwin.delMin();
        }

        if (minNode.board.isGoal()) {
            goal = minNode;
        }
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return goal == null ? -1 : goal.numMoves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable())
            return null;
        Stack<Board> solution = new Stack<Board>();
        for (Node s = goal; s != null; s = s.previousNode)
            solution.push(s.board);
        return solution;
    }

    private class Node implements Comparable<Node> {

        private Board board;
        private Node previousNode;
        private int numMoves;
        private int priority;

        public Node(Board currentboard, Node previousNode) {
            this.board = currentboard;
            this.previousNode = previousNode;

            if (this.previousNode == null)
                numMoves = 0;
            else
                numMoves = this.previousNode.numMoves + 1;

            priority = currentboard.manhattan() + this.numMoves;
        }

        @Override
        public int compareTo(Node o) {
            return this.priority - o.priority;
        }
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {

        int[][] blocks = new int[3][3];

        blocks[0][0] = 1;
        blocks[0][1] = 2;
        blocks[0][2] = 3;
        blocks[1][0] = 0;
        blocks[1][1] = 7;
        blocks[1][2] = 6;
        blocks[2][0] = 5;
        blocks[2][1] = 4;
        blocks[2][2] = 8;
        /*
         * blocks[0][0] = 1; blocks[0][1] = 2; blocks[0][2] = 3; blocks[1][0] =
         * 4; blocks[1][1] = 5; blocks[1][2] = 6; blocks[2][0] = 8; blocks[2][1]
         * = 7; blocks[2][2] = 0;
         */

        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }

    }
}
