import java.util.Arrays;

public class BruteCollinearPoints {
    
    private LineSegment[] segments;
    private int iter;
    
    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new NullPointerException();
        
        if (points[points.length - 1] == null) { throw new NullPointerException(); }
        checkData(points);
        segments = new LineSegment[points.length * points.length];
        if (points.length > 3) {
        for (int i = 0; i <= points.length - 4; i++) {
            for (int j = i + 1; j <= points.length - 3; j++) {
                for (int k = j + 1; k <= points.length - 2; k++) {
                    for (int l = k + 1; l <= points.length - 1; l++) {
                       if (checkSlopes(points, i, j, k, l)) {
                            segments[iter] = new LineSegment(minPoint(points, i, j, k, l), maxPoint(points, i, j, k, l));
                            iter += 1;
                        }
    } } } } }  
    }
    
    public int numberOfSegments() { return iter; }
    
    public LineSegment[] segments() {
        LineSegment[] propLength = new LineSegment[iter];
        for (int i = 0; i < iter; i++) { propLength[i] = segments[i]; }
        return propLength;
    }
    
    private void checkData(Point[] points) {
        Point[] copy = Arrays.copyOfRange(points, 0, points.length);
        Arrays.sort(copy);
        for (int i = 0; i < points.length - 1; i++) {
            if (copy[i].compareTo(copy[i + 1]) == 0) 
            { throw new IllegalArgumentException(); }
            else if (copy[i] == null) { throw new NullPointerException(); }
        }  
        
    }
    private Point maxPoint(Point[] points, int a, int b, int c, int d) {
        Point[] sortPoints = new Point[4];
        sortPoints[0] = points[a]; 
        sortPoints[1] = points[b];
        sortPoints[2] = points[c]; 
        sortPoints[3] = points[d];
        Arrays.sort(sortPoints);
        return sortPoints[3];
    }
    
     private Point minPoint(Point[] points, int a, int b, int c, int d) {
        Point[] sortPoints = new Point[4];
        sortPoints[0] = points[a]; 
        sortPoints[1] = points[b];
        sortPoints[2] = points[c]; 
        sortPoints[3] = points[d];
        Arrays.sort(sortPoints);
        return sortPoints[0];
    }
    
    private boolean checkSlopes(Point[] points, int a, int b, int c, int d) {
        return points[a].slopeTo(points[b]) == points[a].slopeTo(points[c]) &&
            points[a].slopeTo(points[b]) == points[a].slopeTo(points[d]);
    }
    
}