import java.util.ArrayList;

public class Board {
    
    private final int[][] blocks;
    private final int N;
    
    public Board(int[][] blocks) { 
        this.N = blocks[0].length;
        this.blocks = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.blocks[i][j] = blocks[i][j];
            }
        }
    }
    
    public int dimension() { return this.N; }
    
    public int hamming() {
        int correct = 1;
        int hamming = 0;
        for (int i = 0; i < this.N; i++) {
            for (int j = 0; j < this.N; j++) {
                if (this.blocks[i][j] != correct && this.blocks[i][j] != 0) { 
                    hamming += 1;
                }
                correct += 1;
            }
        }
        return hamming;
    }
    
    public int manhattan() {
        int manhattan = 0;
        int correct = 1;
        for (int i = 0; i < this.N; i++) {
            for (int j = 0; j < this.N; j++) {
                if (this.blocks[i][j] == correct) { correct += 1; }
                else if (blocks[i][j] != 0) {
                    int row = (blocks[i][j] - 1) / this.N;
                    int col = (blocks[i][j] - 1) % this.N;
                    manhattan += Math.abs(i - row);
                    manhattan += Math.abs(j - col);
                    correct += 1;
                }
                else {
                    correct += 1;
                }
            }
        }
        return manhattan;
    }
    
    public boolean isGoal() { 
        if (this != null) { return hamming() == 0; }
        else { return false; }
    }
    
    public Board twin() {
        int[][] copy = copyBlocks();            
        if (copy[0][0] != 0) {
            if (copy[0][1] != 0) { moveBlock(copy, 0, 0, 0, 1); }
            else { moveBlock(copy, 0, 0, 1, 0); }
        }
        else { moveBlock(copy, 1, 0, 1, 1); }
        return new Board(copy);
    }
    
    public boolean equals(Object y) {
        if (y == this) { return true; }
        
        if (y == null) { return false; }
        
        if (y.getClass() != this.getClass()) { return false; }
        
        Board that = (Board) y;
        if (this.dimension() != that.dimension()) { return false; }
        for (int i = 0; i < this.N; i++) {
            for (int j = 0; j < this.N; j++) {
                if (this.blocks[i][j] != that.blocks[i][j]) { return false; }
            }
        }
        return true;
    }
    
    public Iterable<Board> neighbors() {
        ArrayList<Board> neighbors = new ArrayList<Board>();
        int iter = 0;
        for (int i = 0; i < this.N; i++) {
            for (int j = 0; j < this.N; j++) {
                if (this.blocks[i][j] == 0) {
                    if (i != this.N - 1) {
                        int[][] copy = copyBlocks();
                        moveBlock(copy, i, j, i + 1, j);
                        neighbors.add(new Board(copy));
                    }
                    if (i != 0) {
                        int[][] copy = copyBlocks();
                        moveBlock(copy, i, j, i - 1, j);
                        neighbors.add(new Board(copy));
                    }
                    if (j != this.N - 1) {
                        int[][] copy = copyBlocks();
                        moveBlock(copy, i, j, i, j + 1);
                        neighbors.add(new Board(copy));
                    }
                    if (j != 0) {
                        int[][] copy = copyBlocks();
                        moveBlock(copy, i, j, i, j - 1);
                        neighbors.add(new Board(copy));
                    }
                }
                
            }
        }
        return neighbors;
    }
    
    public String toString() { 
        StringBuffer results = new StringBuffer();
        String sep = " ";
        results.append(this.N).append("\n");
        int maximalLen = String.valueOf(this.N * this.N - 1).length();
        
        for (int i = 0; i < this.N; i++) {
            for (int j = 0; j < this.N; j++) {
                int blockLen = String.valueOf(this.blocks[i][j]).length();
                if (maximalLen - blockLen == 0) {
                    results.append(sep).append(this.blocks[i][j]);
                }
                else if (maximalLen - blockLen == 1) {
                    results.append(sep).append(sep).append(this.blocks[i][j]);
                }
                else if (maximalLen - blockLen == 1) {
                    results.append(sep).append(sep).append(this.blocks[i][j]);
                }
                else if (maximalLen - blockLen == 2) {
                    results.append(sep).append(sep).append(sep).append(this.blocks[i][j]);
                }
                else if (maximalLen - blockLen == 3) {
                    results.append(sep).append(sep);
                    results.append(sep).append(sep).append(this.blocks[i][j]);
                }
                else if (maximalLen - blockLen == 4) {
                    results.append(sep).append(sep).append(sep);
                    results.append(sep).append(sep).append(this.blocks[i][j]);
                }
            }
            results.append("\n");
        }
        return results.toString();
    } 
    
    private int[][] copyBlocks() {
        int[][] copy = new int[this.N][];
        for (int i = 0; i < this.N; i++) {
            copy[i] = new int[this.N];
            System.arraycopy(this.blocks[i], 0, copy[i], 0, this.N);
        }
        return copy;
    }
    
    private void moveBlock(int[][] copy, int fromI, int fromJ, int toI, int toJ) {
        int temp = copy[fromI][fromJ];
        copy[fromI][fromJ] = copy [toI][toJ];
        copy[toI][toJ] = temp;
    }
    
    
    public static void main(String[] args) {
        int[][] blocks = new int[][]{{1, 2}, {3, 0}};
        Board b = new Board(blocks);
        System.out.println(b);
        Iterable<Board> list = b.neighbors();
        for (Board board: list) {
            System.out.println(board);
        }
        
    }
    
}   
      
    