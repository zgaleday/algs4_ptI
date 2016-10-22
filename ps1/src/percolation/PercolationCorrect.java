import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class PercolationCorrect {
    
    private WeightedQuickUnionUF uf1;
    private WeightedQuickUnionUF uf2;
    private int sites;
    private boolean[] state;
    private int size;
    
    public PercolationCorrect(int N) {    
        if (N < 1) {
            throw new IllegalArgumentException("N must be a pos. int");
        }
        uf1 = new WeightedQuickUnionUF(N * N + 2);
        uf2 = new WeightedQuickUnionUF(N * N + 1);
        state = new boolean[N*N];
        sites = N;
        size = N*N+2;
    }
    
    public void open(int i, int j) {
        if (i < 1 || i > sites || j < 1 || j > sites) {
            throw new IndexOutOfBoundsException("Invalid Index");
        }
        int index = (i - 1) * sites + (j - 1); 
        if (!state[index]) {
            
            state[index] = true;
            if (j != 1 && state[index - 1]) {
                uf1.union(index, index-1);
                uf2.union(index, index-1);
            }
            if (j != sites && state[index + 1]) {
                uf1.union(index, index+1);
                uf2.union(index, index+1);
            }
            if (i != 1 && state[index-sites]) {
                uf1.union(index, index-sites);
                uf2.union(index, index-sites);
            }
            if (i != sites && state[index+sites]) {
                uf1.union(index, index+sites);
                uf2.union(index, index+sites);
            }
            if (i == 1) {
                uf1.union(index, size - 2);
                uf2.union(index, size-2);
            }
            if (i == sites) {
                uf1.union(index, size - 1);
            }
        }
    }
    
    
    public boolean isOpen(int i, int j) {
        if (i < 1 || i > sites || j < 1 || j > sites) {
            throw new IndexOutOfBoundsException("Invalid Index");
        }
        int index = (i - 1) * sites + (j - 1);
        return state[index];
    }
    
    public boolean isFull(int i, int j) {
        if (i < 1 || i > sites || j < 1 || j > sites) {
            throw new IndexOutOfBoundsException("Invalid Index");
        }
        int index = (i - 1) * sites + (j - 1);
        return uf2.connected(index, size -2);
    }
    public boolean percolates() {
        return uf1.connected(size - 2, size - 1);
    }
    
    public static void main(String[] args) {
    }
        
    
}


