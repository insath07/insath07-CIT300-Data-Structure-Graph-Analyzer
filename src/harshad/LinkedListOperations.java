package harshad;

import java.util.ArrayList;
import java.util.List;

/** Singly linked list of integers. All indexes are zero-based. */
public class LinkedListOperations {
    private static final class Node {
        int value;
        Node next;
        Node(int value) { this.value = value; }
    }

    private Node head;
    private Node tail;
    private int size;

    /** Append an item at the end of the linked list in O(1) time. */
    public void insert(int value) {
        Node node = new Node(value);
        if (tail == null) head = node;
        else tail.next = node;
        tail = node;
        size++;
    }

    /** Delete the first occurrence of value. Returns false if not present. */
    public boolean delete(int value) {
        Node previous = null;
        Node current = head;
        while (current != null) {
            if (current.value == value) {
                if (previous == null) head = current.next;
                else previous.next = current.next;
                if (current == tail) tail = previous;
                size--;
                return true;
            }
            previous = current;
            current = current.next;
        }
        return false;
    }

    /** Index of first match, or -1 if the value does not exist. */
    public int search(int value) {
        int index = 0;
        for (Node current = head; current != null; current = current.next) {
            if (current.value == value) return index;
            index++;
        }
        return -1;
    }

    public int size() { return size; }
    public boolean isEmpty() { return size == 0; }

    /** Independent copy of values; callers cannot modify the linked list. */
    public List<Integer> toList() {
        List<Integer> result = new ArrayList<>();
        for (Node current = head; current != null; current = current.next) result.add(current.value);
        return result;
    }

    /** Visualize actual linked-list structure. */
    public String diagram() {
        if (isEmpty()) return "HEAD -> null (empty list)";
        StringBuilder out = new StringBuilder("HEAD -> ");
        for (Node current = head; current != null; current = current.next) {
            out.append('[').append(current.value).append("] -> ");
        }
        return out.append("null").toString();
    }

    public void display() {
        System.out.println("Linked List (" + size + " nodes): " + diagram());
        System.out.println("Values: " + toList());
    }
}
