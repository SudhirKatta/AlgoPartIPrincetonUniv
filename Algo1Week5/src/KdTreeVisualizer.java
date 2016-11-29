
/******************************************************************************
 *  Compilation:  javac KdTreeVisualizer.java
 *  Execution:    java KdTreeVisualizer
 *  Dependencies: KdTree.java
 *
 *  Add the points that the user clicks in the standard draw window
 *  to a kd-tree and draw the resulting kd-tree.
 *
 ******************************************************************************/
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class KdTreeVisualizer {

    public static void main(String[] args) {
        In in = new In("/home/skatta/workspace/Algo1Week5/kdtree/circle10.txt");

        RectHV rect = new RectHV(0.0, 0.0, 1.0, 1.0);
        StdDraw.enableDoubleBuffering();
        KdTree kdtree = new KdTree();
        StdDraw.setXscale(-.1, 1.1);
        StdDraw.setYscale(-.1, 1.1);
        StdDraw.rectangle(0.5, 0.5, 0.5, 0.5);
        StdDraw.show();

        /*
        while (!in.isEmpty()) {
            //double x = Math.floor(in.readDouble() * 100) / 100;
            //double y = Math.floor(in.readDouble() * 100) / 100;
            Point2D p = new Point2D(in.readDouble(), in.readDouble());
            if (rect.contains(p))
                kdtree.insert(p);
        }
        kdtree.draw();
        StdDraw.show();
        */        

        while (true) {
            if (StdDraw.mousePressed()) {
                double x = Math.floor(StdDraw.mouseX() * 10) / 10;
                double y = Math.floor(StdDraw.mouseY() * 10) / 10;
                StdOut.printf("%8.6f %8.6f\n", x, y);
                Point2D p = new Point2D(x, y);
                if (rect.contains(p)) {
                    StdOut.printf("%8.6f %8.6f\n", x, y);
                    kdtree.insert(p);
                    StdDraw.clear();
                    kdtree.draw();
                    StdDraw.rectangle(0.5, 0.5, 0.5, 0.5);
                    StdDraw.show();
                }
            }
            StdDraw.pause(50);
        }
    }
}