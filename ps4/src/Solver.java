import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.Comparator;
import edu.princeton.cs.algs4.Stack;


public class Solver {
    
    private final MinPQ<Node> initPQ;
    private final MinPQ<Node> twinPQ;
    private final Board start;
    private Node lastNode;
      
    public Solver(Board initial) {
        if (initial == null) { throw new NullPointerException(); }
        this.initPQ = new MinPQ<Node>(new ByManhattan());
        this.twinPQ = new MinPQ<Node>(new ByManhattan());
        this.start = initial;
        this.lastNode = null;
        Node init = new Node(initial, null);
        Node twin = new Node(initial.twin(), null);
        insertNeighbors(initPQ, init);
        insertNeighbors(twinPQ, twin);
        aStar(initPQ, twinPQ, init, twin);
    }
    public boolean isSolvable() { return this.lastNode != null; }
    
    public int moves() { 
        if (!isSolvable()) { return -1; }
        else { return this.lastNode.moves(); }
    }
    
    public Iterable<Board> solution() { 
        if (!isSolvable()) { return null; }
        
        Stack<Board> s = new Stack<Board>();
        Node current = this.lastNode;
        while (current.board() != start) {
            s.push(current.board());
            current = current.previous();
        }
        s.push(this.start);
        return s;
    }
    
    private static class ByManhattan implements Comparator<Node> {
        public int compare(Node v, Node w) {
            int vPriority = v.board().manhattan() + v.moves();
            int wPriority = w.board().manhattan() + w.moves();
            return vPriority - wPriority;
        }
    }
    
    private static class ByHamming implements Comparator<Node> {
        public int compare(Node v, Node w) {
            int vPriority = v.board().hamming() + v.moves();
            int wPriority = w.board().hamming() + w.moves();
            return vPriority - wPriority;
        }
    }
    
    private class Node {
        private Board board;
        private Node previous;
        private int moves;
        
        public Node(Board board, Node previous) {
            this.board = board;
            this.previous = previous;
            if (previous != null) { this.moves = previous.moves() + 1; }
        }
        public int moves() { return this.moves; }
        
        public Board board() { return this.board; }
        
        public Node previous() { return this.previous; }
    }
    
    private void insertNeighbors(MinPQ<Node> pq, Node current) {
        for (Board board : current.board().neighbors()) {
            if (current.previous() == null) {
                Node temp = new Node(board, current);
                pq.insert(temp);
            }  
            else if (!board.equals(current.previous().board())) {
                Node temp = new Node(board, current);
                pq.insert(temp);
            }
        }
    }
    
    private void aStar(MinPQ<Node> pqInit, MinPQ<Node> pqTwin, Node initial, Node twin) {
        int iter = 1;
        Node current = initial;
        Node twinCurrent = twin;
        
        while (!current.board().isGoal() && !twinCurrent.board().isGoal()) {
            if ((iter & 1) == 1) {
                current = pqInit.delMin();
                insertNeighbors(pqInit, current);
            }
            else {
                twinCurrent = pqTwin.delMin();
                insertNeighbors(pqTwin, twinCurrent);
                    
                
            }
            iter += 1;
        }
        if ((iter & 1) == 0 | iter == 1) { 
            this.lastNode = current;
        }
        
       
    }
    
    public static void main(String[] args) {

    // create initial board from file
    In in = new In(args[0]);
    int N = in.readInt();
    int[][] blocks = new int[N][N];
    for (int i = 0; i < N; i++)
        for (int j = 0; j < N; j++)
            blocks[i][j] = in.readInt();
    Board initial = new Board(blocks);
    StdOut.println(initial);
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