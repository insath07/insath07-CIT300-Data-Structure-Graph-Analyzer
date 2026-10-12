package insath;

import integration.ConsoleUI;
import java.util.Arrays;

/** Insath: resizable integer array and its basic operations. */
public class ArrayOperations {
    private int[] data = new int[4];
    private int size = 0;

    public void insert(int value) {
        if (size == data.length) data = Arrays.copyOf(data, data.length * 2);
        data[size++] = value;
    }

    public boolean delete(int value) {
        int index = search(value);
        if (index == -1) return false;
        System.arraycopy(data, index + 1, data, index, size - index - 1);
        size--;
        return true;
    }

    public int search(int value) {
        for (int i = 0; i < size; i++) {
            if (data[i] == value) return i;
        }
        return -1;
    }

    public int[] toArray() { return Arrays.copyOf(data, size); }
    public int size() { return size; }
    public void display() { ConsoleUI.printArray(toArray()); }
}
