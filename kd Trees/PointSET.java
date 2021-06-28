/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdOut;

import java.util.TreeSet;

public class PointSET {
    private TreeSet<Point2D> tree;

    public PointSET() {
        this.tree = new TreeSet<Point2D>();
    }

    public boolean isEmpty() {
        return tree.isEmpty();
    }

    public int size() {
        return tree.size();
    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        tree.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return tree.contains(p);
    }

    public void draw() {
        for (Point2D s : tree) {
            s.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        Queue<Point2D> q = new Queue<>();
        for (Point2D s : tree) {
            if (s.x() >= rect.xmin() && s.x() <= rect.xmax()
                    && s.y() >= rect.ymin() && s.y() <= rect.ymax())
                q.enqueue(s);
        }
        return q;
    }

    public Point2D nearest(Point2D p) {
        if (this.isEmpty()) return null;
        Point2D close = new Point2D(0, 0);
        double dist = Double.POSITIVE_INFINITY;
        for (Point2D s : tree) {
            if (dist > p.distanceSquaredTo(s)) {
                close = s;
                dist = p.distanceSquaredTo(s);
            }
        }
        return close;
    }

    public static void main(String[] args) {
        PointSET points = new PointSET();
        Point2D lo = new Point2D(0.6, 0.4);
        points.insert(lo);
        points.insert(new Point2D(0.8, 0.4));
        points.insert(new Point2D(0.6, 0.3));
        points.insert(new Point2D(0.4, 0.2));
        StdOut.print(points.nearest(new Point2D(0, 0)));
    }
}
