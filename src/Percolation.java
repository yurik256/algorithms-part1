import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[] sites;
    private int size;
    private WeightedQuickUnionUF uf;
    private int virtualTopCell;
    private int virtualBottomCell;

    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("N should be more than 0");
        }

        uf = new WeightedQuickUnionUF(N*N + 2); // Add two additional virtual nodes
        size = N;
        sites = new boolean[N*N];

        virtualTopCell = N*N;
        virtualBottomCell = N*N + 1;

        for (int i = 1; i <= N; i++) {
            //connect top cells to virtual top node
            uf.union(getIndex(1, i), virtualTopCell);
            //connect bottom cells to virtual top node
            uf.union(getIndex(N, i), virtualBottomCell);
        }
    }

    public void open(int i, int j) {
        // open site (row i, column j) if it is not open already
        validate(i, j);
        this.sites[getIndex(i, j)] = true;

        if (isCellExisting(i+1, j) && isOpen(i+1, j)) {
            uf.union(getIndex(i+1, j), getIndex(i, j));
        }

        if (isCellExisting(i-1, j) && isOpen(i-1, j)) {
            uf.union(getIndex(i-1, j), getIndex(i, j));
        }

        if (isCellExisting(i, j-1) && isOpen(i, j-1)) {
            uf.union(getIndex(i, j-1), getIndex(i, j));
        }

        if (isCellExisting(i, j+1) && isOpen(i, j+1)) {
            uf.union(getIndex(i, j+1), getIndex(i, j));
        }
    }

    public boolean isOpen(int i, int j) {
        validate(i, j);
        return sites[getIndex(i, j)];
    }

    public boolean isFull(int i, int j) {
        validate(i, j);
        return isOpen(i, j) && uf.connected(virtualTopCell, getIndex(i, j));
    }

    public boolean percolates() {
        if (size == 1)
        {
            return isFull(1, 1);
        }
        return uf.connected(virtualTopCell, virtualBottomCell);
    }

    private int getIndex(int i, int j) {
        validate(i, j);
        return size*i-(size-j+1);
    }

    private void validate(int i, int j) {
        if (!isCellExisting(i, j))
            throw new IndexOutOfBoundsException("index is out of bounds");
    }

    private boolean isCellExisting(int i, int j) {
        return !(i <= 0 || i > size || j <= 0 || j > size);
    }

    public static void main(String[] args) {
        // test client (optional)

        Percolation percolation = new Percolation(3);
        percolation.open(1, 2);
        percolation.open(2, 2);
        percolation.open(3, 2);
        percolation.open(3, 3);

        System.out.println(percolation.percolates());
    }
}
