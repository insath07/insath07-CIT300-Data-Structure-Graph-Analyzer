package hainiya;

/** A dynamically growing circular integer queue. Display order is front to rear. */
public class QueueOperations {
    private static final int DEFAULT_CAPACITY = 8;
    private int[] elements;
    private int front;
    // rear is the next insertion position, including when the queue is full.
    private int rear;
    private int size;

    public QueueOperations() {
        this(DEFAULT_CAPACITY);
    }

    public QueueOperations(int initialCapacity) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("Initial capacity must be positive.");
        }
        elements = new int[initialCapacity];
    }

    /** Enqueues in O(1) amortized time; growth takes O(n). */
    public void enqueue(int value) {
        if (size == elements.length) {
            grow();
        }
        elements[rear] = value;
        rear = (rear + 1) % elements.length;
        size++;
    }

    private void grow() {
        int[] expanded = new int[Math.multiplyExact(elements.length, 2)];
        int index = front;
        for (int i = 0; i < size; i++) {
            expanded[i] = elements[index];
            index = (index + 1) % elements.length;
        }
        elements = expanded;
        front = 0;
        rear = size;
    }

    /** Removes the front value in O(1), or returns null when empty. */
    public Integer dequeue() {
        if (isEmpty()) {
            return null;
        }
        int value = elements[front];
        front = (front + 1) % elements.length;
        size--;
        return value;
    }

    /** Returns the front value without removing it, or null when empty. */
    public Integer peek() {
        return isEmpty() ? null : elements[front];
    }

    /** Returns a formatted FIFO snapshot in O(n), without printing. */
    public String display() {
        StringBuilder result = new StringBuilder("[");
        int index = front;
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                result.append(", ");
            }
            result.append(elements[index]);
            index = (index + 1) % elements.length;
        }
        return result.append(']').toString();
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }
}
