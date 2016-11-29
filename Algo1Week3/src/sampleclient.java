import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class sampleclient {

    public static void main(String[] args) {

        // read the n points from a file

        //In in = new In(args[0]);
        In in = new In("/home/skatta/workspace/Algo1Week3/collinear/input8.txt");
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
        StdDraw.setXscale(-1000, 32768);
        StdDraw.setYscale(-1000, 32768);

/*        
        Point[] points = new Point[10];
        points[0] = new Point(1, 2);
        points[1] = new Point(2, 3);
        points[2] = new Point(3, 4);
        points[3] = new Point(4, 5);
        points[4] = new Point(5, 6);
        points[5] = new Point(0, 4);
        points[6] = new Point(1, 3);
        points[7] = new Point(2, 2);
        points[8] = new Point(4, 0);
        points[9] = new Point(3, 1);
*/
        
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setPenRadius(0.01);
        // draw the points
        StdDraw.enableDoubleBuffering();
        //StdDraw.setXscale(-1, 10);
        //StdDraw.setYscale(-1, 10);
        for (Point p : points) {
            p.draw();
        }

        StdDraw.show();
        StdDraw.setPenRadius(0.002);
        StdDraw.setPenColor(StdDraw.BLUE);
        // print and draw the line segments
        //BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        FastCollinearPoints collinear = new FastCollinearPoints(points);

        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
        System.out.println("done");
    }
}
