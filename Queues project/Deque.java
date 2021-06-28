import java.util.Iterator;

public class Deque<Item> implements Iterable<Item>{
    private Node first;
    private Node last;
    private int size;
    public Deque(){
        first = null;
        last = null;
        size = 0;
    }
    
    
    private class Node{
        Item item;
        Node next;
        Node previous;
    }

    public boolean isEmpty(){
        return (this.first == null || this.last == null);
    }

    public int size(){
        return this.size;
    }

    public void addFirst(Item item){
        this.size++;
        Node oldFirst = this.first;
        this.first = new Node();
        this.first.item = item;
        if(isEmpty()){
            this.last = this.first;
        } else{
            this.first.next= oldFirst;
            oldFirst.previous = this.first;
        }
    }

    public void addLast(Item item){
        this.size++;
        Node oldLast = this.last;
        this.last = new Node();
        this.last.item = item;
        if(isEmpty()){
            this.first = this.last;
        }else{
            this.last.previous =oldLast;
            oldLast.next = this.last;
        }
    }

    public Item removeFirst(){
        this.size--;
        Item it = this.first.item;
        this.first = this.first.next;
        if (!isEmpty()){
            this.first.previous = null;
            this.last = null;
        }
        return it;
    }

    public Item removeLast(){
        this.size--;
        Item it = this.last.item;
        this.last = this.last.previous;
        if (!isEmpty()){
            this.last.next = null;
            this.first = null;
        }
        return it;
    }
    public Iterator<Item> iterator(){
        return new ListIterator();
    }
    private class ListIterator implements Iterator<Item>{
        private Node current = first;
        public boolean hasNext(){
            return (current != null);
        }
        public void remove() { throw new UnsupportedOperationException("will not fly");}
        public Item next(){
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args){
        Deque<String> newIsh = new Deque<String>();
        newIsh.addFirst("hello");
        newIsh.addFirst("say");
        newIsh.addLast("to my");
        newIsh.addLast("Little");
        newIsh.addLast("friend");

        Iterator it = newIsh.iterator();
        System.out.println();
        while(it.hasNext()){
            System.out.print(it.next() +" ");
        }
    }
}