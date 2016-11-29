import java.util.Iterator;
import java.util.NoSuchElementException;

// import edu.princeton.cs.algs4.StdIn;
// import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int qlastIndex;
    private Item[] qArray;

    // construct an empty deque

    public RandomizedQueue() {
        qlastIndex = 0;
        qArray = (Item[]) new Object[2];
    }

    // is the deque empty?
    public boolean isEmpty() {
        return qArray[0] == null;
    }

    // return the number of items on the deque
    public int size() {
        return qlastIndex;
    }

    private void resizeqarray(int capacity) {
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < qlastIndex; i++) {
            temp[i] = qArray[i];
        }
        qArray = temp;
        temp = null;
    }

    // add the item to the front
    public void enqueue(Item item) {
        if (item == null)
            throw new NullPointerException("Can not add null item");

        if (qlastIndex == qArray.length - 1)
            resizeqarray(2 * qArray.length);

        qArray[qlastIndex] = item;
        qlastIndex++;
    }

    // remove and return the item from the end
    public Item dequeue() {
        if (isEmpty())
            throw new NoSuchElementException();

        int randomIndex = StdRandom.uniform(qlastIndex);
        Item item = qArray[randomIndex];
        qArray[randomIndex] = qArray[qlastIndex - 1];
        qArray[qlastIndex - 1] = null;
        qlastIndex--;

        if ((qlastIndex) > 0 && (qlastIndex) == (qArray.length) / 4)
            resizeqarray(qArray.length / 2);

        return item;
    }

    public Item sample() {
        if (isEmpty())
            throw new NoSuchElementException();

        return qArray[StdRandom.uniform(qlastIndex)];
    }

    // return an iterator over items in order from front to end
    @Override
    public Iterator<Item> iterator() {
        return new ArrayIterator<Item>();
    }

    private class ArrayIterator<Item> implements Iterator<Item> {

        private int index;
        private Item[] itrqArray;

        ArrayIterator() {
                index = 0;
                itrqArray = (Item[]) new Object[qArray.length];
                itrqArray = (Item[]) qArray;
                if (qlastIndex > 0)
                StdRandom.shuffle(itrqArray, 0, qlastIndex - 1);
        }

        @Override
        public boolean hasNext() {
            return (index < qlastIndex);
        }

        @Override
        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();

            Item item = itrqArray[index];
            index++;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
/*
    // unit testing 
    public  static void main(String[] args) {
        RandomizedQueue<Integer> randqueue = new RandomizedQueue<Integer>();
        int i = 100;
        while (i != 0) {
            randqueue.enqueue(StdRandom.uniform(i));
            randqueue.dequeue();
            randqueue.enqueue(StdRandom.uniform(i));
            randqueue.enqueue(StdRandom.uniform(i));
            randqueue.enqueue(StdRandom.uniform(i));
            randqueue.dequeue();
            randqueue.dequeue();
            randqueue.dequeue();
            i--;
        }
        System.out.println("Rand queue size: " + randqueue.size());

        System.out.println("rand queue size: " + randqueue.size());

        Iterator itr1 = randqueue.iterator();

        while (itr1.hasNext()) {
            Object element = itr1.next();
            System.out.print(element + " -> ");
        }
        System.out.println();

        Iterator itr2 = randqueue.iterator();

        while (itr2.hasNext()) {
            Object element = itr2.next();
            System.out.print(element + " -> ");
        }
        System.out.println();
        System.out.print(randqueue.sample());
    }*/
}
