import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    
    private WeightedQuickUnionUF uf1;
    private int sites;
    private int size;
    private byte[] state;
    private boolean percolates;
    
    public Percolation(int N) {    
        if (N < 1) {
            throw new IllegalArgumentException("N must be a pos. int");
        }
        uf1 = new WeightedQuickUnionUF(N * N + 1);
        state = new byte[N*N/3 + 1];
        sites = N;
        size = N*N+1;
    }
    
    public void open(int i, int j) {
        if (i < 1 || i > sites || j < 1 || j > sites) {
            throw new IndexOutOfBoundsException("Invalid Index");
        }
        int index = (i - 1) * sites + (j - 1); 
        if (findBits(i, j) == 0) {
            
            boolean [] temp = new boolean[2];
            changeBits(i,j, 1, 1);
            if (j != 1 && findBits(i, j - 1) != 0) {
                byte foo = findBitsIndex(uf1.find(index - 1));
                uf1.union(index, index-1);
                if (foo == 2) {
                    temp[0] = true;
                    changeBits(i, j, 1, 0);
                }
                else if (foo == 1) {
                    temp[1] = true;
                    changeBits(i, j, 0, 1);
                    } 
            }
            
            if (j != sites && findBits(i, j + 1) != 0) {
                //System.out.println(findBits(i, j + 1));
                //System.out.println(uf1.connected(index, size - 1));
                byte foo = findBitsIndex(uf1.find(index + 1));
                uf1.union(index, index+1);
                if (foo == 2) {
                    temp[0] = true;
                    changeBits(i, j, 1, 0);
                }
                else if (foo == 1) {
                    temp[1] = true;
                    changeBits(i, j, 0, 1);
                    }
            }
            if (i != 1 && findBits(i - 1, j) != 0) {
                byte foo = findBitsIndex(uf1.find(index - sites));
                uf1.union(index, index-sites);
                if (foo == 2) {
                    temp[0] = true;
                    changeBits(i, j, 1, 0);
                }
                else if (foo == 1) {
                    temp[1] = true;
                    changeBits(i, j, 0, 1);
                    }
            }
            if (i != sites && findBits(i + 1, j) != 0) {
                byte foo = findBitsIndex(uf1.find(index + sites));
                uf1.union(index, index+sites);
                if (foo == 2) {
                    temp[0] = true;
                    changeBits(i, j, 1, 0);
                }
                else if (foo == 1) {
                    temp[1] = true;
                    changeBits(i, j, 0, 1);
                    }
            }
            if (i == 1) {
                uf1.union(index, size - 1);
                changeBits(i, j, 1, 0);
            }
            else if (i == sites) {
                changeBits(i, j, 0, 1);
            }

            if (temp[0] == true && temp[1] == true) {
                percolates = true;
            }
       }
    }
    
    
    public boolean isOpen(int i, int j) {
        if (i < 1 || i > sites || j < 1 || j > sites) {
            throw new IndexOutOfBoundsException("Invalid Index");
        }
        if (findBits(i, j) != 0) {
            return true;
        }
        return false;
    }
    
    public boolean isFull(int i, int j) {
        if (i < 1 || i > sites || j < 1 || j > sites) {
            throw new IndexOutOfBoundsException("Invalid Index");
        }
        int index = (i - 1) * sites + (j - 1);
        return uf1.connected(index, size - 1);
    }
    public boolean percolates() {
        return percolates;
    }
    
    private byte findBits(int i, int j) {
        int index = (i - 1) * sites + (j - 1);
        return ((byte) (((byte) (state[index / 3] >> (shiftVal(index)))) & 3));
    }
    private byte findBitsIndex(int index) {
        return ((byte) (((byte) (state[index / 3] >> (shiftVal(index)))) & 3));
    }
    private int checkByte(int i, int j) {
        int index = (i - 1) * sites + (j - 1);
        return state[ index / 3]; 
    }
    private void changeBits(int i, int j, int hi, int lo) {
        int index = (i - 1) * sites + (j - 1);
        int pos = index / 3;
        if (hi == 1) {
            state[pos] |= (1 << (shiftVal(index) + 1));
        }
        else if (hi == 0) {
            state[pos] &= ~((1 << (shiftVal(index) + 1)));
        }
        if (lo == 1) {
            state[pos] |= ((byte) (1 << (shiftVal(index))));
        }
        else if (lo == 0) {
            state[pos] &= ~(1 << (shiftVal(index)));
        }
    }
    
    private int shiftVal(int index) {
        if (index % 3 == 0) {
            return 4;
        }
        else if (index % 3 == 1) {
            return 2;
        }
        else {
            return 0;
        }
        
    }
    
    private void updateState(int i, int j) {
        
    }
    
    public static void main(String[] args) {

    }
        
    
}