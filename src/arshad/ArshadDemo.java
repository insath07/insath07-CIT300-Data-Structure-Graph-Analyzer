package arshad;

import java.util.Scanner;

/** Interactive console demonstration of A.L.M. Arshad's graph module. */
public class ArshadDemo {
    /** Runs the menu until Exit or end of input. */
    public static void main(String[] args) {
        GraphOperations graph = new GraphOperations();
        System.out.println("CIT300 \u2013 DATA STRUCTURE & GRAPH ANALYZER");
        System.out.println("A.L.M. ARSHAD \u2013 GRAPH, BFS & DFS DEMONSTRATION");
        System.out.println("Use integer vertex labels. Adding edges creates missing vertices.");
        System.out.println("Duplicates have no effect; self-loops count as one edge.");
        try (Scanner input = new Scanner(System.in)) {
            while (true) {
                printMenu();
                Integer choice = readInteger(input, "Choose an option (1-10): ");
                if (choice == null) {
                    break;
                }
                if (choice == 10) {
                    System.out.println("Thank you. Goodbye!");
                    return;
                }
                switch (choice) {
                    case 1, 3 -> {
                        Integer vertex = readInteger(input, "Vertex: ");
                        if (vertex == null) { return; }
                        boolean changed = choice == 1
                                ? graph.addVertex(vertex) : graph.removeVertex(vertex);
                        System.out.println(changed ? "Vertex " + (choice == 1 ? "added." : "removed.")
                                : (choice == 1 ? "Vertex already exists." : "Vertex does not exist."));
                    }
                    case 2, 4 -> {
                        Integer source = readInteger(input, "Source vertex: ");
                        if (source == null) { return; }
                        Integer destination = readInteger(input, "Destination vertex: ");
                        if (destination == null) { return; }
                        boolean changed = choice == 2 ? graph.addEdge(source, destination)
                                : graph.removeEdge(source, destination);
                        System.out.println(changed ? "Edge " + (choice == 2 ? "added." : "removed.")
                                : (choice == 2 ? "Edge already exists." : "Edge does not exist."));
                    }
                    case 5 -> graph.display();
                    case 6, 7 -> {
                        Integer start = readInteger(input, "Starting vertex: ");
                        if (start == null) { return; }
                        if (!graph.containsVertex(start)) {
                            System.out.println("Starting vertex does not exist. Add it or load the sample graph.");
                        } else {
                            System.out.println(choice == 6 ? "BFS: " + graph.bfs(start)
                                    : "DFS: " + graph.dfs(start));
                        }
                    }
                    case 8 -> {
                        System.out.println("Vertices: " + graph.vertexCount() + ", undirected edges: " + graph.edgeCount());
                        System.out.println("Empty: " + graph.isEmpty());
                        System.out.println("Adjacency list storage: O(V + E).");
                        System.out.println("BFS (queue) / DFS (stack): O(V + E) time, O(V) auxiliary space.");
                        System.out.println("Traversals visit only the component reachable from the start.");
                        System.out.println("Add/check vertex or edge, remove edge: expected O(1).");
                        System.out.println("Remove vertex: expected O(d(v)); display: O(V + E).");
                        System.out.println("Vertex count, edge count and empty check: O(1).");
                    }
                    case 9 -> {
                        graph = sampleGraph();
                        System.out.println("Sample loaded (replaces the current graph).");
                        graph.display();
                        System.out.println("Try BFS / DFS starting at vertex 1.");
                    }
                    default -> System.out.println("Please choose a menu option from 1 to 10.");
                }
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n1. Add Vertex\n2. Add Edge\n3. Remove Vertex\n4. Remove Edge"
                + "\n5. Display Graph\n6. Run Breadth-First Search\n7. Run Depth-First Search"
                + "\n8. Graph Information and Time Complexity\n9. Load Sample Graph\n10. Exit");
    }

    private static Integer readInteger(Scanner input, String prompt) {
        while (true) {
            System.out.print(prompt);
            if (!input.hasNextLine()) {
                System.out.println("\nInput ended. Goodbye!");
                return null;
            }
            try {
                return Integer.valueOf(input.nextLine().trim());
            } catch (NumberFormatException exception) {
                System.out.println("Enter a whole number from -2147483648 to 2147483647.");
            }
        }
    }

    /** Creates the six-vertex sample in the assignment's insertion order. */
    public static GraphOperations sampleGraph() {
        GraphOperations graph = new GraphOperations();
        for (int vertex = 1; vertex <= 6; vertex++) {
            graph.addVertex(vertex);
        }
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 4);
        graph.addEdge(2, 5);
        graph.addEdge(3, 6);
        return graph;
    }
}
