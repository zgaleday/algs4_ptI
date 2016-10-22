import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;


public class RandomizedQueue<Item> implements Iterable<Item> {
    
    private Item[] q = (Item[]) new Object[1];
    private int head = 0;
    private int tail = 0;
    private int size = 0;
    
    public boolean isEmpty() { return size == 0; }
    
    public int size() { return size; }
    
    public void enqueue(Item item) {
        if (item == null) { throw new NullPointerException(); }
        if (size == q.length) { resize(2 * q.length); }
        else if (tail == q.length) { tail = 0; }
        q[tail] = item;
        size += 1; 
        tail += 1;
    }
    public Item dequeue() {
        int foo;
        if (size == 0) { throw new NoSuchElementException(); }
        if (size > 0 && size == q.length / 4) { resize(q.length / 2); }
        else if (head == q.length) { head = 0; }
        if (head > tail) { 
            if (StdRandom.uniform() <= (((float) tail) / size)) {
                foo = StdRandom.uniform(tail);               
             }
            else { foo = StdRandom.uniform(head, q.length); }
        }        
        else if (tail > head) { foo = StdRandom.uniform(head, tail); }
        else { foo = head - 1; }
        Item temp = q[foo];
        q[foo] = q[head];
        q[head] = null;
        head += 1;
        size -= 1;
        return temp;                
    }
    public Item sample() {
        if (size == 0) { throw new NoSuchElementException(); }
        int foo;
        if (head > tail) {
            if (StdRandom.uniform() <= (((float) tail) / size)) {
                foo = StdRandom.uniform(tail);               
             }
            else { foo = StdRandom.uniform(head, q.length); }
        }
        else if (tail > head) { foo = StdRandom.uniform(head, tail); }
        else { foo = head - 1; }
        return q[foo];
    }
     public Iterator<Item> iterator() {
         return new RandomQueueIterator();
     }
        
    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            if (head == q.length) head = 0;
            copy[i] = q[head];
            head += 1;
            }
        head = 0;
        tail = size;
        q = copy;
    }
    private class RandomQueueIterator implements Iterator<Item> {
        private int[] range = range(size);
        private int itSize = size;
        private int iter = 0;
       
        public boolean hasNext() { return itSize != 0; }
        public void remove() { throw new UnsupportedOperationException(); }
        public Item next() { 
            if (!hasNext()) { throw new NoSuchElementException(); }
            int index = range[iter] + head;
            if (index > q.length - 1) { index = index + 1 - q.length; }
            itSize -= 1;
            Item temp = q[index];
            iter += 1;
            return temp;
         }
        private int[] range(int bigness) {
            int[] rangeArray = new int[bigness];
            for (int i = 0; i < size; i++) { rangeArray[i] = i; }
            StdRandom.shuffle(rangeArray);
            return rangeArray;
        }
    }
    
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        StdOut.println(rq.isEmpty());
        for (int i = 0; i < 100; i++) { rq.enqueue(i); }
//        for (int i = 0; i < 100; i++) {StdOut.println(rq.sample()); }
        for (Integer item : rq) { StdOut.println(item); }
        
        

    }
}