import java.util.Arrays;

public class FastCollinearPoints {
    private Point[] points;
    private int numberOfSegments = 0;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {

        Arrays.sort(points);
      
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) {
                throw new IllegalArgumentException("Duplicate Point");
            }
        }
        
        this.points = points.clone();
    }

    // the number of line segments
    public int numberOfSegments() {
        return numberOfSegments;
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] lineSegmentstmp = new LineSegment[points.length];
        Point[] foundLineSegment = new Point[points.length+1];
        int index = 0;
        for (int i = 0; i < points.length; i++) {
            Arrays.sort(points);
            Arrays.sort(points, points[i].slopeOrder());
            Point minPoint = points[0];
            Point maxPoint = points[0];
            double currentSlope = Double.NEGATIVE_INFINITY;
            int numColinearPoints = 0;

            for (int j = 1; j < points.length; j++) {

                if (currentSlope != Double.NEGATIVE_INFINITY) {
                    if (currentSlope == points[0].slopeTo(points[j])) {

                        if (minPoint.compareTo(points[j]) == 1)
                            minPoint = points[j];

                        if (maxPoint.compareTo(points[j]) == -1)
                            maxPoint = points[j];

                        numColinearPoints++;
                    }
                } else {
                    currentSlope = points[0].slopeTo(points[j]);
                    if (minPoint.compareTo(points[j]) == 1)
                        minPoint = points[j];

                    if (maxPoint.compareTo(points[j]) == -1)
                        maxPoint = points[j];

                    numColinearPoints = 2;
                }

                if (currentSlope != Double.NEGATIVE_INFINITY) {
                    if (numColinearPoints == points.length || currentSlope != points[0].slopeTo(points[j])
                            || j == points.length - 1) {

                        if (numColinearPoints >= 4 && (minPoint.compareTo(maxPoint) != 0)) {
                            if (!isfoundLineSegment(foundLineSegment, minPoint, maxPoint)) {
                                foundLineSegment[index++] = minPoint;
                                foundLineSegment[index++] = maxPoint;
                                lineSegmentstmp[numberOfSegments++] = new LineSegment(minPoint, maxPoint);
                            }
                        }

                        if (currentSlope != points[0].slopeTo(points[j])) {
                            minPoint = points[0];
                            maxPoint = points[0];

                            if (minPoint.compareTo(points[j]) == 1)
                                minPoint = points[j];

                            if (maxPoint.compareTo(points[j]) == -1)
                                maxPoint = points[j];

                            currentSlope = points[0].slopeTo(points[j]);
                            numColinearPoints = 2;
                        }
                    }
                }
            }
        }

        LineSegment[] lineSegments = new LineSegment[numberOfSegments];
        for (int m = 0; m < numberOfSegments; m++) {
            lineSegments[m] = lineSegmentstmp[m];
        }
        return lineSegments;
    }

    private boolean isfoundLineSegment(Point[] foundLineSegment, Point minPoint, Point maxPoint) {
        for (int i = 0; i < foundLineSegment.length - 1; i = i + 2) {
            if (foundLineSegment[i] != null && foundLineSegment[i + 1] != null) {
                if (foundLineSegment[i].compareTo(minPoint) == 0 && foundLineSegment[i + 1].compareTo(maxPoint) == 0)
                    return true;
            }
        }
        return false;
    }
}
