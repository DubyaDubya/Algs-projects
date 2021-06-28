public class MinPQ<T extends Comparable<T>> {
    private T[] nodes;
    private int N = 0;
    public MinPQ(){
        nodes = (T[]) new Comparable[2];
        
    }

    public void add(T item){
        if(N== nodes.length-1) resize(2*(N+1));
        nodes[++N] = item;
        swim(N);
    }

    public T min(){
        return nodes[1];
    }

    public T delMin(){
        T min = nodes[1];
        exch(1,N--);
        nodes[N+1] = null;
        sink(1);
        if(N== (nodes.length/4)){
            resize(N+1);
        }
        return min;
    }
    private void exch(int i, int j){
        T temp = nodes[i];
        nodes[i] = nodes[j];
        nodes[j] = temp;
    }

    private void swim(int k){
        while (k > 1 && greater(k/2, k)){
            exch(k, k/2);
            k = k/2;
        }
    }

    private void sink(int k){
        while (2*k <=N){
            int j = 2*k;
            if (j < N && greater(j, j+1)) j++;
            if (!greater(k, j)) break;
            exch(j,k);
            k = j;
        }
    }

    private void resize(int capacity){
        T[] temp = (T[]) new Comparable[capacity];
        for (int i = 1; i <= N; i++){
            temp[i] = nodes[i];
        }
        nodes = temp;
    }

    private boolean greater(int a, int b){
        return (nodes[a].compareTo(nodes[b]) >0);
    }
    public String toString(){
        StringBuilder s = new StringBuilder();
        for(int i =1; i < nodes.length; i++){
            s.append(nodes[i] + " ");
        }
        return s.toString();
    }
}
