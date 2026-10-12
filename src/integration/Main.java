package integration;

import insath.ArrayOperations;
import insath.SearchingOperations;
import insath.PerformanceAnalyzer;
import java.util.Arrays;
import java.util.Scanner;

/** Insath's menu and integration foundation. Other team modules are intentionally not included. */
public class Main {
    private final Scanner scanner = new Scanner(System.in);
    private final ArrayOperations array = new ArrayOperations();

    public static void main(String[] args) { new Main().run(); }

    private void run() {
        while (true) {
            ConsoleUI.title("CIT300 - DATA STRUCTURE & GRAPH ANALYZER");
            System.out.println(" 1. Array Operations             [Insath]");
            System.out.println(" 2. Stack Operations             [Hainiya - pending]");
            System.out.println(" 3. Queue Operations             [Hainiya - pending]");
            System.out.println(" 4. Linked List Operations       [Harshad - pending]");
            System.out.println(" 5. Searching Operations         [Insath]");
            System.out.println(" 6. Graph Operations             [Arshad - pending]");
            System.out.println(" 7. Performance Comparison       [Insath]");
            System.out.println(" 8. Display All Results          [Integration pending]");
            System.out.println(" 9. Exit");
            int option = readInt("Enter your choice: ");
            switch (option) {
                case 1: arrayMenu(); break;
                case 5: searchMenu(); break;
                case 7: PerformanceAnalyzer.compare(array.toArray(), readInt("Target to compare: ")); break;
                case 8: array.display(); ConsoleUI.message("INFO", "Other modules will be shown after integration."); break;
                case 9: ConsoleUI.message("EXIT", "Goodbye!"); return;
                case 2: case 3: case 4: case 6:
                    ConsoleUI.message("PENDING", "This module belongs to another member and has not been integrated yet."); break;
                default: ConsoleUI.message("ERROR", "Choose an option from 1 to 9.");
            }
        }
    }

    private void arrayMenu() {
        while (true) {
            ConsoleUI.title("ARRAY OPERATIONS");
            System.out.println("1. Insert  2. Delete  3. Search  4. Display  5. Back");
            switch (readInt("Option: ")) {
                case 1: array.insert(readInt("Integer value: ")); ConsoleUI.message("OK", "Value inserted."); break;
                case 2: ConsoleUI.message("RESULT", array.delete(readInt("Value to delete: ")) ? "Deleted." : "Value not found."); break;
                case 3: int index = array.search(readInt("Value to search: "));
                    ConsoleUI.message("RESULT", index == -1 ? "Not found." : "Found at index " + index); break;
                case 4: array.display(); break;
                case 5: return;
                default: ConsoleUI.message("ERROR", "Choose an option from 1 to 5.");
            }
        }
    }

    private void searchMenu() {
        while (true) {
            ConsoleUI.title("SEARCHING OPERATIONS");
            System.out.println("1. Linear Search  2. Binary Search  3. Display Array  4. Back");
            switch (readInt("Option: ")) {
                case 1: ConsoleUI.result("Linear Search", SearchingOperations.linear(array.toArray(), readInt("Target: ")), "O(n)"); break;
                case 2:
                    int[] sorted = SearchingOperations.sortedCopy(array.toArray());
                    System.out.println("Sorted data: " + Arrays.toString(sorted));
                    ConsoleUI.result("Binary Search", SearchingOperations.binary(sorted, readInt("Target: ")), "O(log n)"); break;
                case 3: array.display(); break;
                case 4: return;
                default: ConsoleUI.message("ERROR", "Choose an option from 1 to 4.");
            }
        }
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            if (!scanner.hasNextLine()) {
                ConsoleUI.message("EXIT", "Input closed.");
                System.exit(0);
            }
            String line = scanner.nextLine().trim();
            try { return Integer.parseInt(line); }
            catch (NumberFormatException error) {
                ConsoleUI.message("ERROR", "Please enter a whole number, e.g. 10 or 25. Student IDs and names are not integer values.");
            }
        }
    }
}
