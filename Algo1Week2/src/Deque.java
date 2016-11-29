import java.util.Iterator;
import java.util.NoSuchElementException;

// import edu.princeton.cs.algs4.StdIn;
// import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {

    private Node<Item> first;
    private Node<Item> last;
    private int dequeSize;

    private static class Node<Item> {
        private Item item;
        private Node<Item> previous;
        private Node<Item> next;
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        dequeSize = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return dequeSize == 0;
    }

    // return the number of items on the deque
    public int size() {
        return dequeSize;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null)
            throw new NullPointerException("Can not add null item");

        Node<Item> oldfirst = first;
        first = new Node<Item>();

        first.item = item;
        first.next = oldfirst;

        if (isEmpty())
            last = first;
        else
            oldfirst.previous = first;

        dequeSize++;
    }

    // add the item to the end
    public void addLast(Item item) {
        if (item == null)
            throw new NullPointerException("Can not add null item");

        Node<Item> oldlast = last;

        last = new Node<Item>();
        last.item = item;


        if (isEmpty())
            first = last;
        else {
            oldlast.next = last;
            last.previous = oldlast;
        }
        dequeSize++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException("Queue underflow");

        Item item = first.item;
        first = first.next;

        dequeSize--;

        if (isEmpty()) {
            first = null;
            last = null;
        } else
            first.previous = null;

        return item;
    }

    // remove and return the item from the end
    public Item removeLast() {
        if (isEmpty())
            throw new NoSuchElementException("Queue underflow");

        Item item = last.item;
        last = last.previous;

        dequeSize--;

        if (isEmpty()) {
            first = null;
            last = null;
        } else
            last.next = null;

        return item;
    }

    // return an iterator over items in order from front to end
    @Override
    public Iterator<Item> iterator() {
        return new ListIterator<Item>(first);
    }

    private class ListIterator<Item> implements Iterator<Item> {
        private Node<Item> current;

        ListIterator(Node<Item> first) {
            current = first;
        }

        @Override
        public boolean hasNext() {
            return (current != null);
        }

        @Override
        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();

            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /*
    // unit testing
    public static void main(String[] args) {
        Deque<Integer> testdeque = new Deque<Integer>();
        //testdeque.addLast(6);
        // testdeque.removeFirst();
        //testdeque.addFirst(3);
        //testdeque.addFirst(4);
        //testdeque.addLast(1);
        //testdeque.removeFirst();
        // testdeque.removeLast();
        //testdeque.removeLast();

        int i = 2000;
        while(i !=0)
        {
            testdeque.addLast(200);
            testdeque.addFirst(400);
            testdeque.removeFirst();
            testdeque.removeLast();
            testdeque.addFirst(1);
            testdeque.addFirst(5);
            testdeque.removeFirst();
            testdeque.removeLast();
            testdeque.addLast(200);
            testdeque.addFirst(40);

            testdeque.addFirst(1001);
            testdeque.addFirst(500);
            testdeque.removeFirst();
            testdeque.removeLast();
           
            testdeque.addFirst(1);
            testdeque.addFirst(5);
            testdeque.removeFirst();
            testdeque.removeLast();
            i--;
        }
        
        //StdOut.println("deque size: " + testdeque.size());

        Iterator itr = testdeque.iterator();
        
        while (itr.hasNext()) {
            Object element = itr.next();
            System.out.print(element + " -> ");
        }
        // System.out.println();

    } 
*/
}
