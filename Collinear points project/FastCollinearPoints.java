import java.util.Comparator;
import java.util.Random;

public class FastCollinearPoints {
    /*have a collection of Points. want to find every maximal line segment
    which connects 4 on more points.*/
    Point[] pointArray;
    Bag<LineSegment> segments;
    public FastCollinearPoints(Point[] pointArray){
        this.pointArray = pointArray;
    }

    private void sortByY(){
        Sorter.threeWayQS(pointArray);
    }

    private void sortBySlope(Point importantPoint){
        Comparator<Point> slopeFromLowest = importantPoint.slopeOrder();
        Sorter.threeWayQS(pointArray, slopeFromLowest);
    }
    
    private void segmentSearch(){
        int i = 0;
        int same = 0;
        Point[] endpoints = new Point[2];
        while (i < pointArray.length){
            if(pointArray[i] == pointArray[same]){
                if (endpoints[0] == null || endpoints[0].compareTo(pointArray[i]) > 0){
                    endpoints[0] = pointArray[i];
                }
                if (endpoints[1] == null || endpoints[1].compareTo(pointArray[i]) < 0){
                    endpoints[1] = pointArray[i];
                }
            }
            else if (i - same >= 4){
                segments.add(new LineSegment(endpoints[0],endpoints[1]));
                endpoints[0] = null;
                endpoints[1] = null;
            }
            i++;
        }
        if (i - same >= 4){
            segments.add(new LineSegment(endpoints[0],endpoints[1]));
        }
    }

    

    public static void main(String[] args){
        Random r = new Random();
        Point[] pointies = new Point[50];
        for (int i = 0; i < pointies.length; i++){
            pointies[i] = new Point(r.nextInt(11), r.nextInt(11));
        }
        
        FastCollinearPoints runIt =  new FastCollinearPoints(pointies);
        runIt.sortByY();
        runIt.sortBySlope(runIt.pointArray[0]);
        for(int i = 0; i <pointies.length; i++){
            double s = pointies[0].slopeTo(pointies[i]);
            System.out.print(runIt.pointArray[i] + " " + s);
        }
    }
}
