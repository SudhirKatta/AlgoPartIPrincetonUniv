import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class KdTree {
    private Node root;
    private int size;

    // construct an empty set of points
    public KdTree() {
        root = null;
        size = 0;
    }

    // is the set empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException(" argument point is null");

        put(root, p, true);
    }

    private void put(Node node, Point2D p, boolean vertical) {

        if (node == null) {
            root = new Node(p, new RectHV(0, 0, 1, 1));
            size++;
            return;
        }

        if (node.point.equals(p))
            return;

        if (vertical) {
            // p1 < p2 is -1 , p1 = p2 is 0, p1 > p2 is 1
            if (Point2D.X_ORDER.compare(p, node.point) < 0) {
                if (node.left == null) {
                    node.left = new Node(p, new RectHV(node.rect.xmin(), node.rect.ymin(), node.point.x(), node.rect.ymax()));
                    size++;
                    return;
                }
                put(node.left, p, !vertical);
            } else if (Point2D.X_ORDER.compare(p, node.point) >= 0) {
                if (node.right == null) {
                    node.right = new Node(p, new RectHV(node.point.x(), node.rect.ymin(), node.rect.xmax(), node.rect.ymax()));
                    size++;
                    return;
                }
                put(node.right, p, !vertical);
            }
        } else {
            // p1 < p2 is -1 , p1 = p2 is 0, p1 > p2 is 1
            if (Point2D.Y_ORDER.compare(p, node.point) < 0) {
                if (node.left == null) {
                    node.left = new Node(p, new RectHV(node.rect.xmin(), node.rect.ymin(), node.rect.xmax(), node.point.y()));
                    size++;
                    return;
                }
                put(node.left, p, !vertical);
            } else if (Point2D.Y_ORDER.compare(p, node.point) >= 0) {
                if (node.right == null) {
                    node.right = new Node(p, new RectHV(node.rect.xmin(), node.point.y(), node.rect.xmax(), node.rect.ymax()));
                    size++;
                    return;
                }
                put(node.right, p, !vertical);
            }
        }
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("argument to contains() is null");
        return get(root, p, true) != null;
    }

    private Point2D get(Node node, Point2D p, boolean vertical) {
        if (node == null) {
            return null;
        }

        if (node.point.equals(p))
            return p;

        if (vertical) {
            // p1 < p2 is -1 , p1 = p2 is 0, p1 > p2 is 1
            if (Point2D.X_ORDER.compare(p, node.point) < 0) {
                if (node.left == null) {
                    return null;
                }
                return get(node.left, p, !vertical);
            } else if (Point2D.X_ORDER.compare(p, node.point) >= 0) {
                if (node.right == null) {
                    return null;
                }
                return get(node.right, p, !vertical);
            }
        } else {
            // p1 < p2 is -1 , p1 = p2 is 0, p1 > p2 is 1
            if (Point2D.Y_ORDER.compare(p, node.point) < 0) {
                if (node.left == null) {
                    return null;
                }
                return get(node.left, p, !vertical);
            } else if (Point2D.Y_ORDER.compare(p, node.point) >= 0) {
                if (node.right == null) {
                    return null;
                }
                return get(node.right, p, !vertical);
            }
        }
        return null;
    }

    // draw all points to standard draw
    public void draw() {
        draw(root, true);
    }

    private void draw(Node node, boolean vertical) {
        if (node == null)
            return;

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        StdDraw.point(node.point.x(), node.point.y());
        // StdDraw.text(node.point.x(), node.point.y(), node.point.toString());
        StdDraw.setPenRadius();

        if (vertical) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(node.point.x(), node.rect.ymin(), node.point.x(), node.rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(node.rect.xmin(), node.point.y(), node.rect.xmax(), node.point.y());
        }

        draw(node.left, !vertical);
        draw(node.right, !vertical);
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        SET<Point2D> points = new SET<Point2D>();
        rangeSearch(root, rect, points);
        return points;
    }

    private void rangeSearch(Node node, RectHV rect, SET<Point2D> points) {
        if (node == null) {
            return;
        }

        if (rect.intersects(node.rect)) {
            if (rect.contains(node.point)) {
                points.add(node.point);
            }
            rangeSearch(node.right, rect, points);
            rangeSearch(node.left, rect, points);
        }
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (root == null || p == null)
            return null;
        else
            return nearest(root, p, root.point, true);
    }

    private Point2D nearest(Node node, Point2D p, Point2D nearestpoint, boolean vertical) {
        if (node == null)
            return nearestpoint;

        if (p.distanceSquaredTo(node.point) < p.distanceSquaredTo(nearestpoint))
            nearestpoint = node.point;

        // choose the side that contains the query point first
        if (vertical) {
            if (node.point.x() < p.x()) {
                nearestpoint = nearest(node.right, p, nearestpoint, !vertical);
                if (node.left != null && (nearestpoint.distanceSquaredTo(p) > node.left.rect.distanceSquaredTo(p)))
                    nearestpoint = nearest(node.left, p, nearestpoint, !vertical);
            } else {
                nearestpoint = nearest(node.left, p, nearestpoint, !vertical);
                if (node.right != null && (nearestpoint.distanceSquaredTo(p) > node.right.rect.distanceSquaredTo(p)))
                    nearestpoint = nearest(node.right, p, nearestpoint, !vertical);
            }
        } else {
            if (node.point.y() < p.y()) {
                nearestpoint = nearest(node.right, p, nearestpoint, !vertical);
                if (node.left != null && (nearestpoint.distanceSquaredTo(p) > node.left.rect.distanceSquaredTo(p)))
                    nearestpoint = nearest(node.left, p, nearestpoint, !vertical);
            } else {
                nearestpoint = nearest(node.left, p, nearestpoint, !vertical);
                if (node.right != null && (nearestpoint.distanceSquaredTo(p) > node.right.rect.distanceSquaredTo(p)))
                    nearestpoint = nearest(node.right, p, nearestpoint, !vertical);
            }
        }
        return nearestpoint;
    }

    private static class Node {
        private Point2D point; // the point
        // the axis-aligned rectangle corresponding to this node
        private RectHV rect;
        private Node left; // the left/bottom subtree
        private Node right; // the right/top subtree

        Node(Point2D point, RectHV rect) {
            this.point = point;
            if (rect == null)
                this.rect = new RectHV(0, 0, 1, 1);
            else
                this.rect = rect;
        }
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {

        KdTree kdtree = new KdTree();
        /*
         * In in = new
         * In("/home/skatta/workspace/Algo1Week5/kdtree/circle10.txt"); RectHV
         * rect = new RectHV(0.0, 0.0, 1.0, 1.0);
         * StdDraw.enableDoubleBuffering(); StdDraw.setXscale(-0.5, 1.5);
         * StdDraw.setYscale(-.5, 1.5); StdDraw.rectangle(0.5, 0.5, 0.5, 0.5);
         * StdDraw.show();
         * 
         * while (!in.isEmpty()) { Point2D p = new Point2D(in.readDouble(),
         * in.readDouble()); if (rect.contains(p)) kdtree.insert(p); }
         * kdtree.draw(); StdDraw.show();
         * //System.out.println(kdtree.contains(new Point2D(0.966572
         * ,0.504387)));
         */

        kdtree.insert(new Point2D(0.7, 0.2));
        kdtree.insert(new Point2D(0.5, 0.4));
        kdtree.insert(new Point2D(0.2, 0.3));
        kdtree.insert(new Point2D(0.4, 0.7));
        kdtree.insert(new Point2D(0.9, 0.6));

        StdDraw.setXscale(-0.1, 1.1);
        StdDraw.setYscale(-0.1, 1.1);
        kdtree.draw();
        StdDraw.rectangle(0.5, 0.5, 0.5, 0.5);

        double x = 0.1;
        double y = 0.5;
        Point2D nearestP = kdtree.nearest(new Point2D(x, y));
        StdOut.println("Nearest point: " + nearestP.toString());
        StdDraw.setPenColor(StdDraw.GREEN);
        StdDraw.setPenRadius(0.01);
        StdDraw.point(x, y);
        StdDraw.point(nearestP.x(), nearestP.y());
        StdDraw.setPenColor(StdDraw.MAGENTA);
        StdDraw.setPenRadius();
        StdDraw.line(x, y, nearestP.x(), nearestP.y());
        StdDraw.show();

        // for (Point2D point : kdtree.range(new RectHV(0, 0, 0.4, 0.7)))
        // StdOut.println("In Range: " + point.toString());

    }
}