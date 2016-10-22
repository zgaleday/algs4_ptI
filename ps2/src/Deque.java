import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
  
    private Node first;
    private Node last;
    private int dequeSize = 0;

 public boolean isEmpty() {
     return dequeSize == 0;
 }
 
 public int size() {
     return dequeSize;
 }
 
 public void addFirst(Item item) {
     if (item == null) { throw new NullPointerException(); }
     Node oldFirst = first;
     first = new Node();
     first.item = item;
     first.previous = null;
     if (isEmpty()) {
         last = first;
         first.next = null;
     }
     else {
         oldFirst.previous = first;
         first.next = oldFirst;
     }
     dequeSize += 1;    
 }
 public void addLast(Item item) {
     if (item == null) { throw new NullPointerException(); }
     Node oldLast = last;
     last = new Node();
     last.item = item;
     last.next = null;
     if (isEmpty()) {
         first = last;
         last.previous = null;
     }
     else {
     last.previous = oldLast;
     oldLast.next = last;
     }
     dequeSize += 1;
 }
 public Item removeFirst() {
     if (dequeSize == 0) { throw new NoSuchElementException(); }
     Node oldFirst = first;
     if (dequeSize > 1) {
     first = oldFirst.next;
     first.previous = null;
     }
     else {
         first = null;
         last = null;
     }
     dequeSize -= 1;
     return oldFirst.item;
 }
 public Item removeLast() {
     if (dequeSize == 0) { throw new NoSuchElementException(); }
     Node oldLast = last;
     if (dequeSize > 1) {
         last = oldLast.previous;
         last.next = null;
     }
     else {
         first = null;
         last = null;
     }
     dequeSize -= 1;
     return oldLast.item;
  
 }
 public Iterator<Item> iterator() {
     return new DequeIterator();
 }
 
 private class DequeIterator implements Iterator<Item> {
     private Node current = first;
     public boolean hasNext() { return current != null; }
     public void remove() { throw new UnsupportedOperationException(); }
     public Item next() {
         if (current == null) { throw new NoSuchElementException(); }
         Item item = current.item;
         current = current.next;
         return item;
     }
 }
  
 private class Node {
  
     private Item item;
     private Node next;
     private Node previous;
 }
 
 public static void main(String[] args) {
//     Deque<Integer> d = new Deque<Integer>();
//     d.addFirst(0);
//     System.out.println(d.removeFirst());
//     d.addLast(2);
//     d.addLast(3);
//     System.out.println(d.removeLast());
//     System.out.println(d.removeLast());
//     
//
     }
}
