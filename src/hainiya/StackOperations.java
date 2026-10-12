package hainiya;

import java.util.Arrays;

/** A dynamically growing integer stack. Display order is bottom to top. */
public class StackOperations {
    private static final int DEFAULT_CAPACITY = 8;
    private int[] elements;
    private int size;

    public StackOperations() {
        this(DEFAULT_CAPACITY);
    }

    public StackOperations(int initialCapacity) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("Initial capacity must be positive.");
        }
        elements = new int[initialCapacity];
    }

    /** Pushes a value in O(1) amortized time; growth takes O(n). */
    public void push(int value) {
        if (size == elements.length) {
            elements = Arrays.copyOf(elements, Math.multiplyExact(elements.length, 2));
        }
        elements[size++] = value;
    }

    /** Removes the top value in O(1), or returns null when empty. */
    public Integer pop() {
        if (isEmpty()) {
            return null;
        }
        return elements[--size];
    }

    /** Returns the top value without removing it, or null when empty. */
    public Integer peek() {
        return isEmpty() ? null : elements[size - 1];
    }

    /** Returns a formatted snapshot in O(n), without printing or changing it. */
    public String display() {
        StringBuilder result = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                result.append(", ");
            }
            result.append(elements[i]);
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
