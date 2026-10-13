package harshad;

import java.util.Scanner;

/** Standalone demonstration for H.M. Harshad's assigned module. */
public class HarshadDemo {
    private final Scanner scanner = new Scanner(System.in);
    private final LinkedListOperations list = new LinkedListOperations();

    public static void main(String[] args) { new HarshadDemo().run(); }

    private void run() {
        while (true) {
            System.out.println("\n+---------------------------------------------------+");
            System.out.println("| CIT300 - DATA STRUCTURE & GRAPH ANALYZER          |");
            System.out.println("| H.M. HARSHAD - LINKED LIST DEMONSTRATION          |");
            System.out.println("+---------------------------------------------------+");
            System.out.println("| 1. Insert value                                   |");
            System.out.println("| 2. Delete value                                   |");
            System.out.println("| 3. Search value                                   |");
            System.out.println("| 4. Display linked list                            |");
            System.out.println("| 5. Linked list explanation / complexity           |");
            System.out.println("| 6. Exit                                           |");
            System.out.println("+---------------------------------------------------+");
            Integer choice = readInt("Enter your choice: ");
            if (choice == null) { System.out.println("Input ended. Goodbye!"); return; }
            switch (choice) {
                case 1: {
                    Integer value = readInt("Value to insert: ");
                    if (value == null) return;
                    list.insert(value);
                    System.out.println("[SUCCESS] Inserted " + value + " at the end.");
                    break;
                }
                case 2: {
                    Integer value = readInt("Value to delete: ");
                    if (value == null) return;
                    System.out.println(list.delete(value) ? "[SUCCESS] First occurrence deleted." : "[INFO] Value not found.");
                    break;
                }
                case 3: {
                    Integer value = readInt("Value to search: ");
                    if (value == null) return;
                    int index = list.search(value);
                    System.out.println(index < 0 ? "[INFO] Value not found." : "[FOUND] Value at index " + index + " (zero-based).");
                    break;
                }
                case 4: list.display(); break;
                case 5:
                    System.out.println("Singly linked list: each node holds an integer and a next reference.");
                    System.out.println("Insert at tail: O(1). Search: O(n). Delete by value: O(n).");
                    System.out.println("Display: O(n). Extra storage for n nodes: O(n).");
                    break;
                case 6: System.out.println("Goodbye!"); return;
                default: System.out.println("[ERROR] Choose an option from 1 to 6.");
            }
        }
    }

    /** Read integers safely; null indicates end of input. */
    private Integer readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            if (!scanner.hasNextLine()) return null;
            String line = scanner.nextLine().trim();
            try { return Integer.valueOf(line); }
            catch (NumberFormatException ex) { System.out.println("[ERROR] Please enter a valid integer."); }
        }
    }
}
