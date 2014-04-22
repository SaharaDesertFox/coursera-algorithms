/**
 * User: stas
 * Date: 09.02.14 15:55
 */

public class Percolation {

    private byte[] id;
    private int N;
    private WeightedQuickUnionUF uf, ufTop;
    private int iTop, iBottom;

    // create N-by-N grid, with all sites blocked
    public Percolation(int N) {
        this.N = N;
        iTop = N * N;
        iBottom = iTop + 1;
        uf = new WeightedQuickUnionUF(N * N + 2);
        ufTop = new WeightedQuickUnionUF(N * N + 1);
        id = new byte[N*N];
        for (int i = 0; i < id.length; i++) {
            id[i] = 0;
        }
    }

    private int index(int i, int j) {
        return N * (i - 1) + (j - 1);
    }

    private void checkBounds(int i, int j) {
        if (i < 1 || i > N || j < 1 || j > N) {
            throw new IndexOutOfBoundsException();
        }
    }

    // open site (row i, column j) if it is not already
    public void open(int i, int j) {
        checkBounds(i, j);
        if (isOpen(i, j)) return;
        int index = index(i, j);
        id[index] = 1;
        if (i > 1 && isOpen(i - 1, j)) {
            uf.union(index(i - 1, j), index);
            ufTop.union(index(i - 1, j), index);
        }
        if (i < N && isOpen(i + 1, j)) {
            uf.union(index(i + 1, j), index);
            ufTop.union(index(i + 1, j), index);
        }
        if (j > 1 && isOpen(i, j - 1)) {
            uf.union(index(i, j - 1), index);
            ufTop.union(index(i, j - 1), index);
        }
        if (j < N && isOpen(i, j + 1)) {
            uf.union(index(i, j + 1), index);
            ufTop.union(index(i, j + 1), index);
        }


        // virtual top connect
        if (i == 1) {
            uf.union(index, iTop);
            ufTop.union(index, iTop);
        }
        if (i == N) {
            uf.union(index, iBottom);
        }
    }

    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        checkBounds(i, j);
        return id[index(i, j)] == 1;
    }

    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        checkBounds(i, j);
        return isOpen(i, j) && ufTop.connected(index(i, j), iTop);
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.connected(iTop, iBottom);
    }
}
