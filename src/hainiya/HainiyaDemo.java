package hainiya;

import java.util.Scanner;

/** Console demonstration; input and output are separate from the structures. */
public class HainiyaDemo {
    private static final String BORDER = "+--------------------------------------------------+";
    private final StackOperations stack = new StackOperations();
    private final QueueOperations queue = new QueueOperations();
    private final Scanner input;

    public HainiyaDemo(Scanner input) {
        this.input = input;
    }

    public static void main(String[] args) {
        new HainiyaDemo(new Scanner(System.in)).run();
    }

    private static void row(String text) {
        System.out.printf("| %-48s |%n", text);
    }

    private static void menu(String title, String... options) {
        System.out.println();
        System.out.println(BORDER);
        row(title);
        System.out.println(BORDER);
        for (String option : options) {
            row(option);
        }
        System.out.println(BORDER);
    }

    /** Null means end of input; malformed or out-of-range integers are retried. */
    private Integer readInteger(String prompt) {
        while (true) {
            System.out.print(prompt);
            if (!input.hasNextLine()) {
                System.out.println("\nInput ended. Goodbye.");
                return null;
            }
            try {
                return Integer.valueOf(input.nextLine().trim());
            } catch (NumberFormatException exception) {
                System.out.println("ERROR: Enter a valid 32-bit integer.");
            }
        }
    }

    private void run() {
        while (true) {
            menu("CIT300 - DATA STRUCTURES AND ALGORITHMS",
                    "N.D.F. HAINIYA - STACK AND QUEUE DEMONSTRATION",
                    "1. Stack Operations", "2. Queue Operations",
                    "3. Display Both Structures", "4. Exit");
            Integer choice = readInteger("Enter your choice: ");
            if (choice == null) {
                return;
            }
            switch (choice) {
                case 1:
                    if (!stackMenu()) {
                        return;
                    }
                    break;
                case 2:
                    if (!queueMenu()) {
                        return;
                    }
                    break;
                case 3:
                    System.out.println("Stack (bottom -> top): " + stack.display());
                    System.out.println("Queue (front -> rear): " + queue.display());
                    break;
                case 4:
                    System.out.println("Goodbye.");
                    return;
                default:
                    System.out.println("ERROR: Choose an option from 1 to 4.");
            }
        }
    }

    private boolean stackMenu() {
        while (true) {
            menu("STACK OPERATIONS - LIFO", "1. Push", "2. Pop", "3. Peek",
                    "4. Display", "5. Back");
            Integer choice = readInteger("Enter your choice: ");
            if (choice == null) {
                return false;
            }
            switch (choice) {
                case 1:
                    Integer value = readInteger("Enter value to push: ");
                    if (value == null) {
                        return false;
                    }
                    stack.push(value);
                    System.out.println("SUCCESS: Pushed " + value + ".");
                    break;
                case 2:
                    showResult("Popped", stack.pop(), "Stack");
                    break;
                case 3:
                    showResult("Top", stack.peek(), "Stack");
                    break;
                case 4:
                    System.out.println("Stack (bottom -> top): " + stack.display());
                    break;
                case 5:
                    return true;
                default:
                    System.out.println("ERROR: Choose an option from 1 to 5.");
            }
        }
    }

    private boolean queueMenu() {
        while (true) {
            menu("QUEUE OPERATIONS - FIFO", "1. Enqueue", "2. Dequeue", "3. Peek",
                    "4. Display", "5. Back");
            Integer choice = readInteger("Enter your choice: ");
            if (choice == null) {
                return false;
            }
            switch (choice) {
                case 1:
                    Integer value = readInteger("Enter value to enqueue: ");
                    if (value == null) {
                        return false;
                    }
                    queue.enqueue(value);
                    System.out.println("SUCCESS: Enqueued " + value + ".");
                    break;
                case 2:
                    showResult("Dequeued", queue.dequeue(), "Queue");
                    break;
                case 3:
                    showResult("Front", queue.peek(), "Queue");
                    break;
                case 4:
                    System.out.println("Queue (front -> rear): " + queue.display());
                    break;
                case 5:
                    return true;
                default:
                    System.out.println("ERROR: Choose an option from 1 to 5.");
            }
        }
    }

    private static void showResult(String operation, Integer value, String structure) {
        if (value == null) {
            System.out.println("ERROR: " + structure + " is empty.");
        } else {
            System.out.println("SUCCESS: " + operation + ": " + value);
        }
    }
}
