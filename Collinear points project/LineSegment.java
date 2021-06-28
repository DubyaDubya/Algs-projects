import javax.sound.sampled.Line;

public class LineSegment implements Comparable<LineSegment> {
    private final Point p;
    private final Point q;
    public LineSegment(Point p, Point q){
        if (p == null || q == null) {
            throw new NullPointerException("argument is null");
        }
        this.p = p;
        this.q = q;
    }
    public int compareTo(LineSegment that){
        if (this.q.compareTo(that.q) <0 ) return -1;
        else if (this.q.compareTo(that.q) >0 ) return +1;
        else if (this.p.compareTo(that.p) < 0) return -1;
        else if (this.p.compareTo(that.p) >0 ) return +1;
        else return 0;
    }
}
