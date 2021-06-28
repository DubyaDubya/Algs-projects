import java.util.Iterator;
public class Bag<T extends Comparable<T>> implements Iterable<T>{
    private int N = 0;
    private T[] arr;
    
    public Bag(){
        resize(1);
    }
    
    private void resize(int capacity){
        T[] old = this.arr;
        this.arr = (T[]) (new Object[capacity]);
        for (int i = 0; i < N; i++){
            this.arr[i] = old[i];
        }
    }

    public void add(T item){
        if (N == arr.length) resize(2*N);
        arr[N++] = item;
    }

    private class BagIterator implements Iterator<T>{
        private int i = 0;
        public boolean hasNext(){
            return i < N;
        }
        public T next(){
            return arr[i++];
        }
        @Override
        public void remove(){throw new UnsupportedOperationException();}
    }
    public Iterator<T> iterator()
    {return new BagIterator();}
}
