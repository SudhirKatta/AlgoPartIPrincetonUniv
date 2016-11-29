import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

/******************************************************************************
 * Course: Algorithms I Author: Robert Sedgewick and Kevin Wyane Assignment:
 * Week 3 -- Points Author: Nov 2016 Files: BruteCollinearPoints.java
 ******************************************************************************/

public class FastCollinearPoints {
    private final Point[] copiedpoints;
    private int numberOfSegments = 0;
    private final LineSegment[] lineSegments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null)
            throw new java.lang.NullPointerException("Point is null");

        this.copiedpoints = points.clone();
        Arrays.sort(this.copiedpoints);

        for (int i = 0; i < this.copiedpoints.length - 1; i++) {
            if (this.copiedpoints[i] == null)
                throw new java.lang.NullPointerException("Point is null");
            if (this.copiedpoints[i].compareTo(this.copiedpoints[i + 1]) == 0) {
                throw new IllegalArgumentException("Duplicate Point");
            }
        }

        lineSegments = new LineSegment[copiedpoints.length * copiedpoints.length];

        calculateSegments();
    }

    public int numberOfSegments() { // the number of line segments
        return numberOfSegments;
    }

    public LineSegment[] segments() { // the line segments
        return Arrays.copyOf(lineSegments, numberOfSegments);
    }

    private void calculateSegments() { // the line segments
        double prevslope = 0.0;
        Point[][] pointArray = new Point[copiedpoints.length * copiedpoints.length][2];
        Point[] pbyslope = copiedpoints.clone();

        for (int i = 0; i < copiedpoints.length; i++) {
            LinkedList<Point> colinearPoints = new LinkedList<Point>();
            Arrays.sort(pbyslope, copiedpoints[i].slopeOrder());
            for (int j = 0; j < pbyslope.length; j++) {

                if (copiedpoints[i].compareTo(pbyslope[j]) == 0)
                    continue;

                if (prevslope != copiedpoints[i].slopeTo(pbyslope[j])) {
                    if (colinearPoints.size() >= 3) {
                        colinearPoints.add(copiedpoints[i]);
                        Collections.sort(colinearPoints);
                        pointArray = addSegment(pointArray, colinearPoints.getFirst(), colinearPoints.getLast());
                    }
                    colinearPoints.clear();
                }
                colinearPoints.add(pbyslope[j]);
                prevslope = copiedpoints[i].slopeTo(pbyslope[j]);
            }

            if (colinearPoints.size() >= 3) {
                colinearPoints.add(copiedpoints[i]);
                Collections.sort(colinearPoints);
                pointArray = addSegment(pointArray, colinearPoints.getFirst(), colinearPoints.getLast());
            }
        }

        for (int i = 0; i < numberOfSegments; i++) {
            lineSegments[i] = new LineSegment(pointArray[i][0], pointArray[i][1]);
        }
    }

    private Point[][] addSegment(Point[][] pointArray, Point min, Point max) {
        boolean add = true;
        for (int i = 0; i < pointArray.length; i++) {
            if (pointArray[i][0] != null)
                if (pointArray[i][0] == min && pointArray[i][1] == max) {
                    add = false;
                    break;
                }
        }

        if (add) {
            pointArray[numberOfSegments][0] = min;
            pointArray[numberOfSegments][1] = max;
            numberOfSegments++;
        }
        return pointArray;
    }
}
