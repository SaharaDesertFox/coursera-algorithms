import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * User: stas
 * Date: 07.04.14 0:37
 */

public class Solver {

    private int moves;
    private MinPQ<Node> pq;
    private MinPQ<Node> pqTwin;
    private List<Board> solution;
    private boolean solvable;
    private boolean work = true;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial.isGoal()) {
            solution = new ArrayList<Board>(1);
            solution.add(initial);
            moves = 0;
            solvable = true;
            return;
        }
        moves = 1;

        final Board twin = initial.twin();
        final Node root = new Node(initial, null);
        final Node rootTwin = new Node(twin, null);

        Comparator<Node> comparator = new Comparator<Node>() {
            public int compare(Node n1, Node n2) {
                int man = n1.board.manhattan() - n2.board.manhattan();
                int diffM = man + (n1.length()) - (n2.length());
                if (diffM != 0) return diffM;
                else return man;
            }
        };
        pq = new MinPQ<Node>(comparator);
        pqTwin = new MinPQ<Node>(comparator);

        new Thread(new Runnable() {
            public void run() {
                Node node = addNeighborsTwin(rootTwin, twin.neighbors());
                while (!node.board.isGoal() && work) {
                    node = addNeighborsTwin(node, node.board.neighbors());
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (node.board.isGoal()) {
                    solvable = false;
                    work = false;
                }
            }
        }).start();


        Node node = addNeighbors(root, initial.neighbors());
        while (!node.board.isGoal() && work) {
            node = addNeighbors(node, node.board.neighbors());
        }
        if (node.board.isGoal()) {
            solvable = true;
            work = false;
            solution = node.getPath();
            Collections.reverse(solution);
        }
    }

    private Node addNeighbors(Node current, Iterable<Board> neighbors) {
        for (Board board : neighbors) {
            if (current.parentNode != null && current.parentNode.board.equals(board)) {
                continue;
            }
            Node node = new Node(board, current);
            if (node.board.isGoal()) return node;
            pq.insert(node);
        }
        Node minNode = pq.delMin();
        int minBoardMoves = minNode.length();
        moves = minBoardMoves + 1;
        return minNode;
    }

    private Node addNeighborsTwin(Node current, Iterable<Board> neighbors) {
        for (Board board : neighbors) {
            if (current.parentNode != null && current.parentNode.board.equals(board)) {
                continue;
            }
            Node node = new Node(board, current);
            if (node.board.isGoal()) return node;
            pqTwin.insert(node);
        }
        return pqTwin.delMin();
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if no solution
    public int moves() {
        if (isSolvable()) return moves;
        else return -1;
    }

    // sequence of boards in a shortest solution; null if no solution
    public Iterable<Board> solution() {
        if (isSolvable()) return solution;
        else return null;
    }

    private static class Node {
        private Board board;
        private Node parentNode;
        private int length;

        Node(Board board, Node parentNode) {
            this.board = board;
            this.parentNode = parentNode;
            length = 0;
            Node parent = parentNode;
            while (parent != null) {
                parent = parent.parentNode;
                length++;
            }
        }

        int length() {
            return length;
        }

        List<Board> getPath() {
            List<Board> path = new ArrayList<Board>();
            path.add(board);
            Node parent = parentNode;
            while (parent != null) {
                path.add(parent.board);
                parent = parent.parentNode;
            }
            return path;
        }
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
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
