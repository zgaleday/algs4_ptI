import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdDraw;


public class KdTree {
    
    private Node root;
    private int size;
    
    public KdTree() {
        root = null;
        size = 0;
    }
    
    public boolean isEmpty() { return size == 0; }
    
    public int size() { return size; }
    
    public boolean contains(Point2D p) {
        if (p == null) { throw new NullPointerException(); }
        return search(root, p, false, true);
    }
    
    private boolean search(Node root, Point2D p, boolean vert, boolean horiz) {
        boolean found;
        if (root == null) { return false; }
        else if (root.point().equals(p)) { return true; }
        if (horiz) {
            if (root.x() > p.x()) { found = search(root.left, p, !vert, !horiz); }
            else { found = search(root.right, p, !vert, !horiz); }
        }
        else {
            if (root.y() > p.y()) { found = search(root.left, p, !vert, !horiz); }
            else { found = search(root.right, p, !vert, !horiz); }
        }
        return found;
    }
    
    public void insert(Point2D p) { 
        if (p == null) { throw new NullPointerException(); }
        if (root == null) {
            root = new Node(p, false, true); 
            size += 1;
        }
        if (!contains(p)) {
            size += 1;
            insert(root, p, false, true);
        }
    }
    
    private Node insert(Node x, Point2D p, boolean vert, boolean horiz) {
        if (x == null) { 
            Node temp = new Node(p, !horiz, !vert);
            return temp;
        } 
        if (horiz) {
            if (x.x() > p.x()) { x.left = insert(x.left, p, !vert, !horiz); }
            else { x.right = insert(x.right, p, !vert, !horiz); }
        }
        else {
            if (x.y() > p.y()) { x.left = insert(x.left, p, !vert, !horiz); }
            else { x.right = insert(x.right, p, !vert, !horiz); }
        }
        return x;
    }
    
    public void draw() {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        root.point().draw();
        StdDraw.setPenRadius();
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.line(root.x(), 0.0, root.x(), 1.0);
        draw(root);
        
    }
    
    private void draw(Node x) {
        if (x.left != null) { 
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            x.left.point().draw();
            StdDraw.setPenRadius();
            if (x.left.VERTICAL) {
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.line(0.0, x.left.y(), x.x(), x.left.y()); 
            }
            else {
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.line(x.left.x(), 0.0, x.left.x(), x.y());
            }
        }
        if (x.right != null) { 
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            x.right.point().draw();
            StdDraw.setPenRadius();
            if (x.left.VERTICAL) {
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.line(x.x(), x.right.y(), 1.0, x.right.y()); 
            }
            else {
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.line(x.right.x(), x.y(), x.right.x(), 1.0);
            }
        }
        if (x.left != null) { draw(x.left); }
        if (x.right != null) { draw(x.right); }
            
            
    }
    
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) { throw new NullPointerException(); }
        Queue<Point2D> q = new Queue<Point2D>();
        if (isEmpty()) { return q; }
        range(root, rect, false, true, q);
        return q;
    }
       
    private Queue<Point2D> range(Node root, RectHV rect, boolean vert, boolean horiz, Queue<Point2D> q) {
        if (root == null) { return q; }
        else if (rect.contains(root.point())) { 
            q = range(root.left, rect, !vert, !horiz, q);
            q = range(root.right, rect, !vert, !horiz, q);
            q.enqueue(root.point());
            return q;
        }
        else if (horiz) {
            if (root.x() > rect.xmax()) { 
                q = range(root.left, rect, !vert, !horiz, q);
            }
            else if (root.x() < rect.xmin()) { 
                q = range(root.right, rect, !vert, !horiz, q);
            }
            else { 
                q = range(root.left, rect, !vert, !horiz, q);
                q = range(root.right, rect, !vert, !horiz, q);
            }
        }
        else {
            if (root.y() > rect.ymax()) { 
                q = range(root.left, rect, !vert, !horiz, q);
            }
            else if (root.y() < rect.ymin()) { 
                q = range(root.right, rect, !vert, !horiz, q);
            }
            else { 
                q = range(root.left, rect, !vert, !horiz, q);
                q = range(root.right, rect, !vert, !horiz, q);
                
            }
        }
        return q;
    }
    
    public Point2D nearest(Point2D point) {
        
        if (isEmpty()) { return null; }
        else if (point == null) { throw new NullPointerException(); }
        return nearest(root, point, false, true, root).point();
    }
    
    private Node nearest(Node x, Point2D p, boolean vert, boolean horiz, Node minNode) {
               
        if (x == null) { return minNode; }
        
        else if (x.point().equals(p)) { return x; }
        
        else if (minNode.point().equals(p)) { return minNode; }
             
        double minDist = p.distanceSquaredTo(minNode.point());
        
        if (p.distanceSquaredTo(x.point()) < minDist) {
            minNode = x;
            p.distanceSquaredTo(minNode.point());
        }
        
        
        
        if (horiz) {
            Point2D check = new Point2D(x.x(), p.y());
            if (x.x() > p.x()) { 
                minNode = nearest(x.left, p, !vert, !horiz, minNode); 
                if (p.distanceSquaredTo(check) <= minDist) 
                    minNode = nearest(x.right, p, !vert, !horiz, minNode);
            }
            
            else { 
                minNode = nearest(x.right, p, !vert, !horiz, minNode); 
                if (p.distanceSquaredTo(check) <= minDist) 
                    minNode = nearest(x.left, p, !vert, !horiz, minNode);
            }
        }
        else {
            Point2D check = new Point2D(p.x(), x.y());
            if (x.y() > p.y()) { 
                minNode = nearest(x.left, p, !vert, !horiz, minNode); 
                if (p.distanceSquaredTo(check) <= minDist) 
                    minNode = nearest(x.right, p, !vert, !horiz, minNode);
            }
            
            else { 
                minNode = nearest(x.right, p, !vert, !horiz, minNode); 
                if (p.distanceSquaredTo(check) <= minDist) 
                    minNode = nearest(x.left, p, !vert, !horiz, minNode);
            }
        }
        return minNode;
    }
    
    private class Node {
        private final Point2D p;
        private Node left;
        private Node right;
        private boolean VERTICAL;
        private boolean HORIZONTAL;
        
        public Node(Point2D p, boolean vert, boolean horiz) {
            this.p = p;
            VERTICAL = vert;
            HORIZONTAL = horiz;            
        }
        
        public Point2D point() { return this.p; }        
        public double x() { return this.p.x(); }
        public double y() { return this.p.y(); }
        
    }
    
    public static void main(String [] args) {
        
    }
}