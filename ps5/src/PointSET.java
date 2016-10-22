import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Queue;

public class PointSET {

    private SET<Point2D> s;
    
    public PointSET() { s = new SET<Point2D>(); }
    
    public boolean isEmpty() { return s.isEmpty(); }
    
    public int size() { return s.size(); }
    
    public boolean contains(Point2D p) { 
        if (p == null) { throw new NullPointerException(); }
        return s.contains(p);
    }
    
    public void insert(Point2D p) {
        if (p == null) { throw new NullPointerException(); }
        if (!contains(p)) { s.add(p); }
    }
        
    public void draw() {
        for (Point2D point : s) { point.draw(); }
    }
    
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) { throw new NullPointerException(); }
        Queue<Point2D> q = new Queue<Point2D>();
        if (isEmpty()) { return q; }
        if (s.min().y() > rect.ymax()) { return q; }
        else if (s.max().y() < rect.ymin()) { return q; }
        for (Point2D point : s) {
            if (rect.contains(point)) { q.enqueue(point); }
        }
        return q;
    }
    
    public Point2D nearest(Point2D p) {
        
        if (isEmpty()) { return null; }
        else if (p == null) { throw new NullPointerException(); }
        Point2D minP = null;
        double minDist = Double.POSITIVE_INFINITY;
        for (Point2D point : s) {
            double dist = p.distanceTo(point);
            if (minDist > dist) {
                minP = point;
                minDist = dist;
            }
        }
        return minP;
    }
    
    public static void main(String[] args) {
        
        Point2D a = new Point2D(1.0, 1.0);
        Point2D b = new Point2D(.88, .87);
        RectHV r = new RectHV(0.0, 1.0, 1.0, 1.0);
        PointSET ps = new PointSET();
        System.out.println(ps.isEmpty());
        ps.insert(a);
        ps.insert(b);
        System.out.println(ps.contains(a));
        ps.draw();
        Iterable<Point2D> it = ps.range(r);
        for (Point2D p : it) { System.out.println(p); }
        
        
    }
}