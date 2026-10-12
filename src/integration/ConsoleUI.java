package integration;

import insath.SearchingOperations;

/** Insath: reusable ASCII console tables and headings (no external libraries). */
public class ConsoleUI {
    private static final String LINE = "+------------------------------------------------------------+";
    public static void title(String name) {
        System.out.println("\n" + LINE);
        System.out.printf("| %-58s |%n", name);
        System.out.println(LINE);
    }
    public static void message(String kind, String message) {
        System.out.printf("[%s] %s%n", kind, message);
    }
    public static void printArray(int[] values) {
        title("ARRAY DATA");
        if (values.length == 0) { System.out.println("(empty)"); return; }
        System.out.printf("%-12s %s%n", "INDEX", "VALUE");
        System.out.println("------------------------");
        for (int i = 0; i < values.length; i++) System.out.printf("%-12d %d%n", i, values[i]);
    }
    public static void result(String name, SearchingOperations.Result r, String complexity) {
        System.out.printf("%-15s | %-12s | %5d steps | %10d ns | %s%n",
            name, r.index == -1 ? "Not found" : "Index " + r.index,
            r.comparisons, r.nanoseconds, complexity);
    }
    public static void bar(String name, int steps, int maximum) {
        int width = maximum <= 0 ? 0 : (int) Math.round(30.0 * steps / maximum);
        System.out.printf("%-15s | %-30s | %d%n", name, "#".repeat(width), steps);
    }
}
