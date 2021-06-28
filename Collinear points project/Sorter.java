import java.util.Comparator;
public final class Sorter{

    //exchange method
    private static <T>void exch(T[] a, int b, int c){
        T temp = a[b];
        a[b] = a[c];
        a[c] = temp;
    }

    //shuffle method
    private static <T>void shuffle(T[] a){
        for (int i = 0; i <a.length; i++){
            int randIndex = (int) (Math.random()*(a.length - i) +i);
            System.out.print(randIndex);
            T temp = a[i];
            a[i] = a[randIndex];
            a[randIndex] = temp;
        }
    }


    private static <T extends Comparable<T>>void threeWayQSHelper(T[] a, int lo, int hi){
        if (hi <= lo) return; 
        int i = lo;
        int lt = lo;
        int gt = hi;
        T v= a[lo];
        while(gt>= i){
            int cmp = a[i].compareTo(v);
            if (cmp < 0) exch(a, i++, lt++);
            else if (cmp >0) exch(a, i, gt--);
            else i++;
        }
        threeWayQSHelper(a, lo, lt-1);
        threeWayQSHelper(a, gt+1, hi);
    }

    private static <T>void threeWayQSHelper(T[] a, int lo, int hi, Comparator<T> c){
        if (hi<= lo) return;
        int lt = lo, gt = hi, i = lo;
        T v = a[lo];
        while(i <= gt){
            int cmp = c.compare(a[i],v);
            if (cmp < 0) exch(a, i++, lt++);
            else if (cmp >0) exch(a, i, gt--);
            else i++;
        }
        threeWayQSHelper(a, lo, lt -1, c);
        threeWayQSHelper(a, gt + 1, hi, c);
    }

    public static <T extends Comparable<T>>void threeWayQS(T[] a){
        /*implement a qs to sort by default*/
        shuffle(a);
        threeWayQSHelper(a,0, a.length-1);
    }

    public static <T extends Comparable<T>>void threeWayQS(T[] a, Comparator<T> c){
        /*implement a qs to sort by default*/
        shuffle(a);
        threeWayQSHelper(a,0, a.length-1, c);
    }

    public static void main(String[] args){
        
    }
}