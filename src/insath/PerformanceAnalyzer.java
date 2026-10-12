package insath;

import integration.ConsoleUI;
import java.util.Arrays;

/** Insath: compare the two searches using a common integer dataset. */
public class PerformanceAnalyzer {
    public static void compare(int[] values, int target) {
        int[] sorted = SearchingOperations.sortedCopy(values);
        SearchingOperations.Result linear = SearchingOperations.linear(values, target);
        SearchingOperations.Result binary = SearchingOperations.binary(sorted, target);
        ConsoleUI.title("SEARCH PERFORMANCE COMPARISON");
        System.out.println("Original array: " + Arrays.toString(values));
        System.out.println("Sorted copy   : " + Arrays.toString(sorted));
        System.out.println("Target        : " + target);
        System.out.println("Algorithm       | Result       | Steps       | Time(ns)     | Complexity");
        System.out.println("--------------------------------------------------------------------------");
        ConsoleUI.result("Linear Search", linear, "O(n)");
        ConsoleUI.result("Binary Search", binary, "O(log n)");
        System.out.println("\nCOMPARISON CHART (number of examined elements)");
        int max = Math.max(linear.comparisons, binary.comparisons);
        ConsoleUI.bar("Linear", linear.comparisons, max);
        ConsoleUI.bar("Binary", binary.comparisons, max);
        System.out.println("Note: Binary Search uses a sorted copy. Its index is in that sorted copy.");
        System.out.println("Binary Search timing excludes copying and sorting the data.");
        System.out.println("Graph performance comparison is pending Arshad's Graph module.");
        System.out.println("Timing is one JVM run, not a reliable benchmark by itself.");
    }
}
