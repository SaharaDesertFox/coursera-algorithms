import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * User: stas
 * Date: 12.02.14 1:22
 */

public class Deque<Item> implements Iterable<Item> {

    private int size;
    private Node first, last;

    public Deque() {
        size = 0;
    }

    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    private void checkAdd(Item item) {
        if (item == null) throw new NullPointerException();
    }

    private void checkRemove() {
        if (size == 0) throw new NoSuchElementException();
    }

    public void addFirst(Item item) {
        checkAdd(item);
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        if (++size == 1) {
            last = first;
        } else {
            oldFirst.prev = first;
        }
    }

    public void addLast(Item item) {
        checkAdd(item);
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.prev = oldLast;
        if (++size == 1) {
            first = last;
        } else {
            oldLast.next = last;
        }
    }

    public Item removeFirst() {
        checkRemove();
        Item item = first.item;
        first.item = null;
        first = first.next;
        if (--size > 0) {
            first.prev = null;
        } else {
            last = null;
        }
        return item;
    }

    public Item removeLast() {
        checkRemove();
        Item item = last.item;
        last.item = null;
        last = last.prev;
        if (--size > 0) {
            last.next = null;
        } else {
            first = null;
        }
        return item;
    }


    public Iterator<Item> iterator() {
        return new Iterator<Item>() {

            private Node current = first;

            public boolean hasNext() {
                return current != null;
            }

            public Item next() {
                if (!hasNext()) throw new NoSuchElementException();
                Item item = current.item;
                current = current.next;
                return item;
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    public static void main(String[] args) {

        Deque<Integer> deque = new Deque<Integer>();
        StdOut.println(deque.isEmpty());
        deque.addFirst(1);
        deque.addFirst(1);
        deque.addFirst(1);
        deque.addLast(2);
        deque.addLast(2);
        deque.addLast(2);
        System.out.println(deque.size());
        deque.removeFirst();
        deque.removeFirst();
        deque.removeFirst();
        deque.removeFirst();
        deque.removeLast();
        deque.removeLast();
        System.out.println(deque.size());
        System.out.println(deque.first);
        System.out.println(deque.last);
    }
}