import java.util.Comparator;

public class Point implements Comparable<Point>{
    private final int x;
    private final int y;

    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    public String toString(){
        return ("(" + this.x + "," + y + ")");
    }

    public int compareTo(Point that){
        if (this.y < that.y) return -1;
        else if (this.y > that.y) return +1;
        else if (this.x < that.x) return -1;
        else if (this.x > that.x) return +1;
        else return 0;
    }

    public double slopeTo(Point that){
        if (this.compareTo(that) == 0) return Double.NEGATIVE_INFINITY;
        else if (this.y == that.y) return +0;
        else if (this.x == that.x) return Double.POSITIVE_INFINITY;
        else return (double) (that.y - this.y) / ((double) (that.x - this.x));
    }
    private class BySlope implements Comparator<Point>{
        Point dis;
        BySlope(Point dis){
            this.dis = dis;
        }
        public int compare(Point one, Point two){
            if (dis.slopeTo(one) > dis.slopeTo(two)) return +1;
            else if (dis.slopeTo(one) < dis.slopeTo(two)) return -1;
            else return 0;
        }
    }

    public Comparator<Point> slopeOrder(){
        return new BySlope(this);
    }

    public static void main(String[] args){
        Point first = new Point(2,2);
        Point second = new Point(1,2);
        Point origin = new Point(0,0);
        System.out.println("The first point made is: "+ first);
        System.out.println("The second point made is: " + second);
        System.out.println("The point with the highest slope from the origin is"
         +origin.slopeOrder().compare(new Point(1,1),new Point(1,0)));
    }
}