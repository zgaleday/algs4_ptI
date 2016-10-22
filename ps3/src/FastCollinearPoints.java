import java.util.Arrays;

public class FastCollinearPoints {
    
    private LineSegment[] segments;
    private int iter;
    
    
    public FastCollinearPoints(Point[] points) {
        
        segments = new LineSegment[points.length * points.length];
        if (points == null) throw new NullPointerException();
        if (points[points.length - 1] == null) { throw new NullPointerException(); }
        checkData(points);
        Point[] copy = Arrays.copyOfRange(points, 0, points.length);
        for (int i = 0; i < copy.length; i++) {
            Point sortPoint = points[i];
            Arrays.sort(copy, sortPoint.slopeOrder());
            
            if (copy.length > 3 && sortPoint.slopeTo(copy[1]) == sortPoint.slopeTo(copy[copy.length - 1])) {
                segments[iter] = new LineSegment(minPoint(copy, sortPoint, 1, copy.length), 
                                                 maxPoint(copy, sortPoint, 1, copy.length)); 
                iter += 1;
                break;
            }
            int lo = 0;
            int hi = lo + 2;
            boolean test = false;
                           
            while (hi < points.length) {
                if (sortPoint.slopeTo(copy[lo]) == sortPoint.slopeTo(copy[hi]) &&
                    sortPoint.slopeTo(copy[lo]) != Double.NEGATIVE_INFINITY) {
                    hi += 1;
                    test = true;
                }
                else if (test) {
                    if (sortPoint.compareTo(minPoint(copy, sortPoint, lo, hi)) == 0) {
                        segments[iter] = new LineSegment(minPoint(copy, sortPoint, lo, hi),
                                                         maxPoint(copy, sortPoint, lo, hi));
                        iter += 1;
                    }
                    test = false;
                    lo = hi;
                    hi = lo + 2;
                }
                else { 
                    lo += 1; 
                    hi += 1; 
                }
                        
            }
            if (test) {
                test = false;
                if (sortPoint.compareTo(minPoint(copy, sortPoint, lo, hi)) == 0) {
                    segments[iter] = new LineSegment(minPoint(copy, sortPoint, lo, hi),
                                                     maxPoint(copy, sortPoint, lo, hi));
                    iter += 1;
                    }
                    
            }
            lo = 0;
            hi = lo + 2;
        } }

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
    
    private Point maxPoint(Point[] points, Point sortPoint, int lo, int hi) {
        Point[] sortPoints = new Point[hi-lo + 1];
        sortPoints[0] = sortPoint;
        for (int i = 0; i < hi - lo; i++) { sortPoints[i + 1] = points[lo + i]; }
        Arrays.sort(sortPoints);
        return sortPoints[sortPoints.length - 1];
    }
    
     private Point minPoint(Point[] points, Point sortPoint, int lo, int hi) {
        Point[] sortPoints = new Point[hi-lo + 1];
        sortPoints[0] = sortPoint;
        for (int i = 0; i < hi - lo; i++) { sortPoints[i + 1] = points[lo + i]; }
        Arrays.sort(sortPoints);
        return sortPoints[0];
     }
}