/**
 * User: stas
 * Date: 12.02.14 1:23
 */

public class Subset {

    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        int size = Integer.parseInt(args[0]);

        while (!StdIn.isEmpty()) {
            String val = StdIn.readString();
            queue.enqueue(val);
        }

        while (size > 0) {
            StdOut.println(queue.dequeue());
            size--;
        }

    }

}
