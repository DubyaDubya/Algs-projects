/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;

public class KdTree {
    private static final boolean X = true;
    private static final boolean Y = false;
    private Node root;

    private class Node {
        private Node left, right;
        private Point2D point;
        private int count;

        private Node(Point2D p) {
            point = p;
            count = 1;
        }
    }

    public boolean isEmpty() {
        return (root == null);
    }

    public boolean contains(Point2D p) {
        Node w = root;
        boolean searching = X;
        while (w != null) {
            if (searching == X) {
                if (p.x() > w.point.x()) {
                    w = w.right;
                    searching = Y;
                }
                else if (p.x() < w.point.x()) {
                    w = w.left;
                    searching = Y;
                }
                else if (p.y() != w.point.y()) {
                    w = w.left;
                    searching = Y;
                }
                else return true;
            }
            else {
                if (p.y() > w.point.y()) {
                    w = w.right;
                    searching = X;
                }
                else if (p.y() < w.point.y()) {
                    w = w.left;
                    searching = X;
                }
                else if (p.x() != w.point.x()) {
                    w = w.left;
                    searching = X;
                }
                else return true;
            }
        }
        return false;
    }

    public void insert(Point2D p) {
        root = insert(root, p, X);
    }

    private Node insert(Node x, Point2D p, boolean axis) {
        if (x == null) return new Node(p);
        if (axis == X) {
            if (p.x() > x.point.x()) x.right = insert(x.right, p, Y);
            else if (p.x() < x.point.x()) x.left = insert(x.left, p, Y);
            else if (p.y() != x.point.y()) x.left = insert(x.left, p, Y);
            else x.point = p;
        }
        else {
            if (p.y() > x.point.y()) x.right = insert(x.right, p, X);
            else if (p.y() < x.point.y()) x.left = insert(x.left, p, X);
            else if (p.x() != x.point.x()) x.left = insert(x.left, p, X);
            else x.point = p;
        }
        x.count = 1;
        if (x.left != null) x.count += x.left.count;
        if (x.right != null) x.count += x.right.count;
        return x;
    }

    public int size() {
        return root.count;
    }

    public Iterable<Point2D> range(RectHV rect) {
        Queue<Point2D> q = new Queue<>();
        range(root, rect, X, q);
        return q;
    }

    private void range(Node a, RectHV rect, boolean axis, Queue<Point2D> q) {
        if (a == null) return;
        if (rect.contains(a.point)) q.enqueue(a.point);
        if (axis == X) {
            if (a.point.x() >= rect.xmin()) range(a.left, rect, Y, q);
            if (a.point.x() < rect.xmax()) range(a.right, rect, Y, q);
        }
        else {
            if (a.point.y() >= rect.ymin()) range(a.left, rect, X, q);
            if (a.point.y() < rect.ymax()) range(a.right, rect, X, q);
        }
    }

    public Point2D nearest(Point2D p) {
        return nearest(root, p, null, X, new RectHV(0, 0, 1, 1));
    }

    /*
    check if a point is closer than the previous closest point. if so set it to be the new closest point
    if the point being searched for is on one side of the partition, check that side of the partition first.
    figure out how to keep track of a rectangle's borders.
    have xmin, xmax, ymin, ymax
     */
    private Point2D nearest(Node a, Point2D p, Point2D previous, boolean axis, RectHV r) {
        Point2D closest;
        if (a == null) return previous;
        if (previous == null || a.point.distanceSquaredTo(p) < previous.distanceSquaredTo(p)) {
            closest = a.point;
        }
        else closest = previous;
        if (axis) {
            if (p.x() > a.point.x()) {
                RectHV b = new RectHV(r.xmin(), r.ymin(), a.point.x(), r.ymax());
                closest = nearest(a.right, p, closest, Y,
                                  new RectHV(a.point.x(), r.ymin(), r.xmax(), r.ymax()));
                if (b.distanceSquaredTo(p) < closest.distanceSquaredTo(p))
                    closest = nearest(a.left, p, closest, Y, b);
            }
            else {
                RectHV b = new RectHV(a.point.x(), r.ymin(), r.xmax(), r.ymax());
                closest = nearest(a.left, p, closest, Y,
                                  new RectHV(r.xmin(), r.ymin(), a.point.x(), r.ymax()));
                if (b.distanceSquaredTo(p) < closest.distanceSquaredTo(p))
                    closest = nearest(a.right, p, closest, Y, b);
            }
        }
        else {
            if (p.y() > a.point.y()) {
                RectHV b = new RectHV(r.xmin(), r.ymin(), r.xmin(), a.point.y());
                closest = nearest(a.right, p, closest, X,
                                  new RectHV(r.xmin(), a.point.y(), r.xmax(), r.ymax()));
                if (b.distanceSquaredTo(p) < closest.distanceSquaredTo(p))
                    closest = nearest(a.left, p, closest, X, b);
            }
            else {
                RectHV b = new RectHV(r.xmin(), a.point.y(), r.xmax(), r.ymax());
                closest = nearest(a.left, p, closest, X,
                                  new RectHV(r.xmin(), r.ymin(), r.xmax(), a.point.y()));
                if (b.distanceSquaredTo(p) < closest.distanceSquaredTo(p))
                    closest = nearest(a.right, p, closest, X, b);
            }
        }
        return closest;
    }

    public static void main(String[] args) {
        KdTree oak = new KdTree();
        for (int i = 0; i < 10; i++) {
            Point2D a = new Point2D(Math.random(), Math.random());
            System.out.println(a);
            oak.insert(a);
        }
        System.out.println();
        System.out.println(oak.nearest(new Point2D(.5, .5)));

    }
}
