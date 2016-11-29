import java.util.Arrays;

public class BruteCollinearPoints {

    private final Point[] copiedpoints;
    private int numberOfSegments = 0;
    private final LineSegment[] lineSegmentstmp;

    public BruteCollinearPoints(Point[] points) {
        if (points == null)
            throw new java.lang.NullPointerException("Point is null");

        this.copiedpoints = points.clone();
        Arrays.sort(this.copiedpoints);

        for (int i = 0; i < this.copiedpoints.length - 1; i++) {
            if (this.copiedpoints[i] == null)
                throw new java.lang.NullPointerException("Point is null");
            if (this.copiedpoints[i].compareTo(this.copiedpoints[i + 1]) == 0)
                throw new IllegalArgumentException("Duplicate Point");
        }

        lineSegmentstmp = new LineSegment[copiedpoints.length * copiedpoints.length];

        calculateSegments();
    }

    // the number of line segments
    public int numberOfSegments() {
        return numberOfSegments;
    }

    // the line segments
    public LineSegment[] segments() {

        return Arrays.copyOf(lineSegmentstmp, numberOfSegments);
    }

    private void calculateSegments() {

        int length = copiedpoints.length;

        for (int i = 0; i < length; i++) {
            for (int j = i + 1; j < length; j++) {
                for (int k = j + 1; k < length; k++) {
                    if (copiedpoints[i].slopeTo(copiedpoints[j]) == copiedpoints[i].slopeTo(copiedpoints[k])) {
                        for (int l = k + 1; l < length; l++) {
                            if (copiedpoints[i].slopeTo(copiedpoints[j]) == copiedpoints[i].slopeTo(copiedpoints[l])) {
                                lineSegmentstmp[numberOfSegments] = new LineSegment(copiedpoints[i], copiedpoints[l]);
                                numberOfSegments++;
                            }
                        }
                    }
                }
            }
        }

    }
}
