import hainiya.QueueOperations;
import hainiya.StackOperations;

import java.util.Objects;

/** Standalone tests: no external dependencies and no -ea flag required. */
public class HainiyaTests {
    private static int passed;
    private static int failed;

    public static void main(String[] args) {
        test("Stack: empty operations", () -> {
            StackOperations stack = new StackOperations();
            equal(true, stack.isEmpty());
            equal(0, stack.size());
            equal(null, stack.pop());
            equal(null, stack.peek());
            equal("[]", stack.display());
            equal(0, stack.size());
        });
        test("Stack: push one element", () -> {
            StackOperations stack = new StackOperations();
            stack.push(10);
            equal(false, stack.isEmpty());
            equal(1, stack.size());
            equal(10, stack.peek());
            equal("[10]", stack.display());
        });
        test("Stack: multiple pushes, LIFO pop and non-destructive peek", () -> {
            StackOperations stack = new StackOperations();
            stack.push(10);
            stack.push(20);
            stack.push(30);
            equal("[10, 20, 30]", stack.display());
            equal(30, stack.peek());
            equal(3, stack.size());
            equal(30, stack.pop());
            equal(20, stack.peek());
            equal(2, stack.size());
            equal(20, stack.pop());
            equal(10, stack.pop());
            equal(true, stack.isEmpty());
            equal(null, stack.pop());
        });
        test("Stack: repeated dynamic resizing preserves all values", () -> {
            StackOperations stack = new StackOperations(1);
            for (int i = 0; i < 1000; i++) {
                stack.push(i);
            }
            equal(1000, stack.size());
            for (int i = 999; i >= 0; i--) {
                equal(i, stack.pop());
            }
            equal(0, stack.size());
            equal(null, stack.peek());
        });
        test("Stack: reuse and full integer range", () -> {
            StackOperations stack = new StackOperations(1);
            stack.push(Integer.MIN_VALUE);
            equal(Integer.MIN_VALUE, stack.pop());
            stack.push(0);
            stack.push(Integer.MAX_VALUE);
            equal(Integer.MAX_VALUE, stack.pop());
            equal(0, stack.pop());
        });
        test("Stack: rejects non-positive initial capacity", () -> {
            rejectsCapacity(() -> new StackOperations(0));
            rejectsCapacity(() -> new StackOperations(-1));
        });
        test("Queue: empty operations", () -> {
            QueueOperations queue = new QueueOperations();
            equal(true, queue.isEmpty());
            equal(0, queue.size());
            equal(null, queue.dequeue());
            equal(null, queue.peek());
            equal("[]", queue.display());
            equal(0, queue.size());
        });
        test("Queue: enqueue one element", () -> {
            QueueOperations queue = new QueueOperations();
            queue.enqueue(100);
            equal(false, queue.isEmpty());
            equal(1, queue.size());
            equal(100, queue.peek());
            equal("[100]", queue.display());
        });
        test("Queue: multiple enqueues, FIFO dequeue and non-destructive peek", () -> {
            QueueOperations queue = new QueueOperations();
            queue.enqueue(100);
            queue.enqueue(200);
            queue.enqueue(300);
            equal("[100, 200, 300]", queue.display());
            equal(100, queue.peek());
            equal(3, queue.size());
            equal(100, queue.dequeue());
            equal(200, queue.peek());
            equal(2, queue.size());
            equal(200, queue.dequeue());
            equal(300, queue.dequeue());
            equal(true, queue.isEmpty());
            equal(null, queue.dequeue());
        });
        test("Queue: circular wraparound without growth", () -> {
            QueueOperations queue = new QueueOperations(3);
            queue.enqueue(10);
            queue.enqueue(20);
            queue.enqueue(30);
            equal(10, queue.dequeue());
            equal(20, queue.dequeue());
            queue.enqueue(40);
            queue.enqueue(50);
            equal("[30, 40, 50]", queue.display());
            equal(3, queue.size());
            equal(30, queue.dequeue());
            equal(40, queue.dequeue());
            equal(50, queue.dequeue());
        });
        test("Queue: resizing a wrapped queue preserves FIFO order", () -> {
            QueueOperations queue = new QueueOperations(3);
            queue.enqueue(10);
            queue.enqueue(20);
            queue.enqueue(30);
            equal(10, queue.dequeue());
            queue.enqueue(40);
            queue.enqueue(50); // Triggers growth with a non-zero front.
            equal("[20, 30, 40, 50]", queue.display());
            equal(4, queue.size());
            for (int value = 20; value <= 50; value += 10) {
                equal(value, queue.dequeue());
            }
            equal(true, queue.isEmpty());
        });
        test("Queue: repeated dynamic resizing preserves all values", () -> {
            QueueOperations queue = new QueueOperations(1);
            for (int i = 0; i < 1000; i++) {
                queue.enqueue(i);
            }
            equal(1000, queue.size());
            for (int i = 0; i < 1000; i++) {
                equal(i, queue.dequeue());
            }
            equal(0, queue.size());
            equal(null, queue.peek());
        });
        test("Queue: repeated drain and reuse with integer boundary values", () -> {
            QueueOperations queue = new QueueOperations(2);
            for (int cycle = 0; cycle < 20; cycle++) {
                queue.enqueue(Integer.MIN_VALUE);
                queue.enqueue(Integer.MAX_VALUE);
                equal(Integer.MIN_VALUE, queue.dequeue());
                equal(Integer.MAX_VALUE, queue.dequeue());
                equal(null, queue.dequeue());
                equal(true, queue.isEmpty());
            }
            queue.enqueue(0);
            equal(0, queue.dequeue());
        });
        test("Queue: rejects non-positive initial capacity", () -> {
            rejectsCapacity(() -> new QueueOperations(0));
            rejectsCapacity(() -> new QueueOperations(-1));
        });
        System.out.printf("%nResults: %d PASS, %d FAIL%n", passed, failed);
        if (failed > 0) {
            System.exit(1);
        }
    }

    private static void equal(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) {
            throw new AssertionError("Expected " + expected + ", received " + actual);
        }
    }

    private static void rejectsCapacity(Runnable action) {
        try {
            action.run();
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new AssertionError("Expected IllegalArgumentException");
    }

    private static void test(String name, Runnable action) {
        try {
            action.run();
            passed++;
            System.out.println("PASS: " + name);
        } catch (AssertionError | RuntimeException failure) {
            failed++;
            System.out.println("FAIL: " + name + " - " + failure.getMessage());
        }
    }
}
