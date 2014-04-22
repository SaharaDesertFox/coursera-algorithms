/**
 * User: stas
 * Date: 07.04.14 0:37
 */

public class Board {

    private int N;
    private int[][] blocks;
    private int man;
    private int ham;

    // construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        this.blocks = new int[blocks.length][blocks.length];
        for (int i = 0; i < blocks.length; i++)
            System.arraycopy(blocks[i], 0, this.blocks[i], 0, blocks[i].length);
        init();
    }

    private Board() {
    }

    private Board init() {
        this.N = this.blocks.length;
        this.man = manhattanInternal();
        this.ham = hammingInternal();
        return this;
    }

    // board dimension N
    public int dimension() {
        return N;
    }

    // number of blocks out of place
    public int hamming() {
        return ham;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        return man;
    }

    private int hammingInternal() {
        int res = 0;
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                int val = blocks[i][j];
                if (val == 0) continue;
                int expected = (i * N + j + 1);
                if (expected != val) res++;
            }
        }
        return res;
    }

    private int manhattanInternal() {
        int res = 0;
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                int block = blocks[i][j];
                if (block == 0) continue;
                int iExpected = (block - 1) / N;
                int jExpected = (block - 1) % N;
                int m = Math.abs(i - iExpected) + Math.abs(j - jExpected);
                res += m;
            }
        }
        return res;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return manhattan() == 0;
    }

    // a board obtained by exchanging two adjacent blocks in the same row
    public Board twin() {
        int[][] newBlocks = copyBlocks();
        out:
        for (int i = 0; i < newBlocks.length; i++) {
            for (int j = 0, k = j + 1; k < newBlocks.length; j++, k++) {
                if (newBlocks[i][j] != 0 && newBlocks[i][k] != 0) {
                    int t = newBlocks[i][j];
                    newBlocks[i][j] = newBlocks[i][k];
                    newBlocks[i][k] = t;
                    break out;
                }
            }
        }
        return new Board(newBlocks);
    }

    private int[][] copyBlocks() {
        int[][] newBlocks = new int[N][N];
        for (int i = 0; i < blocks.length; i++)
            System.arraycopy(blocks[i], 0, newBlocks[i], 0, blocks[i].length);
        return newBlocks;
    }

    private Board copyWithExchange(int i, int j, int i2, int j2) {
        int[][] newBlocks = copyBlocks();
        int t = newBlocks[i][j];
        newBlocks[i][j] = newBlocks[i2][j2];
        newBlocks[i2][j2] = t;
        Board board = new Board();
        board.blocks = newBlocks;
        return board.init();
    }


    // all neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> neighbors = new Stack<Board>();
        int i, j = 0;
        a:
        for (i = 0; i < N; i++) {
            for (j = 0; j < N; j++) {
                if (blocks[i][j] == 0) break a;
            }
        }

        if (i - 1 >= 0) neighbors.push(copyWithExchange(i, j, i - 1, j));
        if (i + 1 < N) neighbors.push(copyWithExchange(i, j, i + 1, j));
        if (j - 1 >= 0) neighbors.push(copyWithExchange(i, j, i, j - 1));
        if (j + 1 < N) neighbors.push(copyWithExchange(i, j, i, j + 1));

        return neighbors;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (!(y instanceof Board)) return false;
        Board b = (Board) y;
        if (this == b) return true;
        if (this.N == b.N) {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (blocks[i][j] != b.blocks[i][j]) return false;
                }
            }
            return true;
        }
        return false;
    }

    // string representation of the board (in the output format specified below)
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(N).append("\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

}