import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

public class RandomizedQueue<Item> /*implements Iterable<Item>*/{
    private Item[] s;
    private int tail = 0;
    private Random selekta = new Random();
    //for randomization purposes, has to be an array
    public RandomizedQueue(){
        s = (Item[]) new Object[1];
    }
/*want to put an item in the empty array spots randomly*/
    public boolean isEmpty(){
        
        return (tail ==0);
    }

    public int size(){
        return tail;
    }

    private void resize(int capacity){
        Item[] copy = (Item[]) new Object[capacity];
        for (int i= 0 ;i<tail; i++) {
            copy[i] = s[i];
        }
        s= copy;
    }

    public void enqueue(Item item){
        if(item ==null) throw new IllegalArgumentException();
        if (tail ==s.length) resize(2*s.length);
        s[tail++] = item;
    }

    public Item dequeue(){
        if(isEmpty()) throw new NoSuchElementException();
        if (tail < s.length/4) resize(s.length/2);
        int dequeueIndex = selekta.nextInt(tail);
        Item toDequeue = s[dequeueIndex];
        s[dequeueIndex] = s[--tail];
        return toDequeue;

    }

    public Item sample(){
        if(isEmpty()) throw new NoSuchElementException();
        int dig = selekta.nextInt(size());
        return s[dig];
    }

    public Iterator<Item> iterator(){
        return new RandQueueIterator();
    }
    
    private class RandQueueIterator implements Iterator<Item>{
        
        private RandQueueIterator(){
            for (int i =tail; i >0; i--){
                Item temp = s[i];
                int j = selekta.nextInt(i);
                s[i] = s[j];
                s[j] = temp;
            }
        }
        
        private int index = 0;
        
        public boolean hasNext(){
            return (index <= tail);
        }
        public void remove(){
            throw new UnsupportedOperationException();
        }
        public Item next(){
            if (index> tail){
                throw new NoSuchElementException();
            }
            Item item = s[index];
            index++;
            return item;
        }

    }

    public static void main(String[] args){
        RandomizedQueue r = new RandomizedQueue();
        r.enqueue("a");
        r.enqueue("b");
        System.out.println(r.isEmpty());
    }
}
/*
    private class RandQueIterator implements Iterator<Item>{
        private Node current = first;
        
        public boolean hasNext(){
            current != null;
        }
        public void remove() { throw new UnsupportedOperationException("will not fly");}
        public Item next(){
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
    */
