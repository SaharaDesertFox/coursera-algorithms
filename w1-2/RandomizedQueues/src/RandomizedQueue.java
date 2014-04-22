import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * User: stas
 * Date: 12.02.14 1:23
 */

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] items;
    private int size;

    public RandomizedQueue() {
        items = (Item[]) new Object[2];
    }


    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    private void resize(int capacity) {
        assert capacity >= size;
        Item[] temp = (Item[]) new Object[capacity];
        System.arraycopy(items, 0, temp, 0, size);
        items = temp;
    }

    public void enqueue(Item item) {
        if (item == null) throw new NullPointerException();
        if (size == items.length) resize(2 * size);
        if (isEmpty()) {
            items[size++] = item;
        } else {
            int index = StdRandom.uniform(size + 1);
            if (index == size) {
                items[index] = item;
            } else {
                Item newLast = items[index];
                items[size] = newLast;
                items[index] = item;
            }
            size++;
        }
    }

    // delete and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();
        Item item = items[size - 1];
        items[size - 1] = null;  // to avoid loitering
        size--;
        // shrink size of array if necessary
        if (size > 0 && size == items.length / 4) resize(items.length / 2);
        return item;
    }

    // return (but do not delete) a random item
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();
        int index = StdRandom.uniform(size);
        return items[index];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedIterator();
    }

    private class RandomizedIterator implements Iterator<Item> {

        private Item[] randomItems;
        private int index;

        RandomizedIterator() {
            index = size;
            randomItems = (Item[]) new Object[size];
            System.arraycopy(items, 0, randomItems, 0, size);
            StdRandom.shuffle(randomItems);
        }

        public boolean hasNext() {
            return index > 0;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return randomItems[--index];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {

        Deque<Integer> deque = new Deque<Integer>();
        deque.isEmpty();
    }
}