import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class PointSET {

    private SET<Point2D> pset;

    // construct an empty set of points
    public PointSET() {
        pset = new SET<Point2D>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return pset.isEmpty();
    }

    // number of points in the set
    public int size() {
        return pset.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null)
            throw new java.lang.NullPointerException("null point");
        pset.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null)
            throw new java.lang.NullPointerException("null point");
        return pset.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D p : pset) {
            StdDraw.point(p.x(), p.y());
            // StdDraw.text(p.x(), p.y(), p.toString());
        }
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {

        Stack<Point2D> rangePoints = new Stack<Point2D>();

        for (Point2D point : pset) {
            if (rect.contains(point))
                rangePoints.push(point);
        }

        return rangePoints;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new java.lang.NullPointerException("null point");

        if (pset.isEmpty()) {
            return null;
        }

        Point2D nearestpoint = null;
        double mindist = Double.MAX_VALUE;

        for (Point2D point : pset) {

            if (p.distanceTo(point) < mindist) {
                mindist = p.distanceTo(point);
                nearestpoint = point;
            }
        }

        return nearestpoint;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {

        PointSET pset = new PointSET();
        Point2D p = new Point2D(0.2, 0.3);
        RectHV rect = new RectHV(0.2, 0.2, 0.6, 0.6);
        pset.insert(p);
        for (int i = 0; i < 1000; i++)
            pset.insert(new Point2D(StdRandom.uniform(), StdRandom.uniform()));

        rect.draw();
        StdDraw.circle(p.x(), p.y(), p.distanceTo(pset.nearest(p)));
        pset.draw();
        // StdDraw.show(0);
        StdOut.println("Nearest to " + p.toString() + " = " + pset.nearest(p));

        for (Point2D point : pset.range(rect))
            StdOut.println("In Range: " + point.toString());

    }
}