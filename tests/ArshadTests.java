import arshad.ArshadDemo;
import arshad.GraphOperations;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Objects;

/** Dependency-free regression tests; deliberately outside the arshad package. */
public class ArshadTests {
    private static int passed;
    private static int failed;

    /** Runs every test, prints results and exits nonzero if any test fails. */
    public static void main(String[] args) {
        test("Empty graph state", () -> {
            GraphOperations g = new GraphOperations();
            check(g.isEmpty()); equal(0, g.vertexCount()); equal(0, g.edgeCount());
        });
        test("Add vertex", () -> {
            GraphOperations g = new GraphOperations();
            check(g.addVertex(7)); check(g.containsVertex(7)); check(!g.isEmpty());
        });
        test("Duplicate vertex preserves connections", () -> {
            GraphOperations g = ArshadDemo.sampleGraph();
            check(!g.addVertex(1)); equal(6, g.vertexCount()); check(g.containsEdge(1, 2));
        });
        test("Add edge creates missing endpoints", () -> {
            GraphOperations g = new GraphOperations();
            check(g.addEdge(8, 9)); equal(2, g.vertexCount()); equal(1, g.edgeCount());
        });
        test("Duplicate edge", () -> {
            GraphOperations g = ArshadDemo.sampleGraph();
            check(!g.addEdge(1, 2)); equal(5, g.edgeCount());
        });
        test("Reversed duplicate edge", () -> {
            GraphOperations g = ArshadDemo.sampleGraph();
            check(!g.addEdge(2, 1)); equal(5, g.edgeCount());
        });
        test("Undirected adjacency", () -> {
            GraphOperations g = ArshadDemo.sampleGraph();
            check(g.containsEdge(1, 3)); check(g.containsEdge(3, 1));
        });
        test("Vertex count includes isolated vertices", () -> {
            GraphOperations g = ArshadDemo.sampleGraph();
            g.addVertex(99); equal(7, g.vertexCount()); equal(5, g.edgeCount());
        });
        test("Sample edge count", () -> equal(5, ArshadDemo.sampleGraph().edgeCount()));
        test("Empty display representation", () -> equal("Graph is empty.", new GraphOperations().toString()));
        test("Adjacency display in insertion order", () -> {
            GraphOperations g = new GraphOperations();
            g.addEdge(3, 1); g.addVertex(7);
            equal(String.join(System.lineSeparator(), "3 -> [1]", "1 -> [3]", "7 -> []"), g.toString());
        });
        test("Display prints representation", () -> {
            GraphOperations g = ArshadDemo.sampleGraph();
            PrintStream original = System.out;
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            try (PrintStream capture = new PrintStream(output)) {
                System.setOut(capture); g.display();
            } finally { System.setOut(original); }
            equal(g.toString() + System.lineSeparator(), output.toString());
        });
        test("Sample BFS", () -> equal(List.of(1, 2, 3, 4, 5, 6), ArshadDemo.sampleGraph().bfs(1)));
        test("Sample DFS", () -> equal(List.of(1, 2, 4, 5, 3, 6), ArshadDemo.sampleGraph().dfs(1)));
        test("Cycle BFS visits each vertex once", () -> {
            GraphOperations g = new GraphOperations();
            g.addEdge(1, 2); g.addEdge(2, 3); g.addEdge(3, 1);
            equal(List.of(1, 2, 3), g.bfs(1));
        });
        test("Cycle DFS follows recursive order", () -> {
            GraphOperations g = new GraphOperations();
            g.addEdge(1, 2); g.addEdge(1, 3); g.addEdge(2, 3); g.addEdge(2, 4);
            equal(List.of(1, 2, 3, 4), g.dfs(1));
        });
        test("Disconnected components remain separate", () -> {
            GraphOperations g = ArshadDemo.sampleGraph();
            g.addEdge(20, 21); equal(List.of(20, 21), g.bfs(20)); equal(List.of(20, 21), g.dfs(20));
            equal(6, g.bfs(1).size()); equal(6, g.dfs(1).size());
        });
        test("Remove edge clears both directions", () -> {
            GraphOperations g = ArshadDemo.sampleGraph();
            check(g.removeEdge(2, 1)); check(!g.containsEdge(1, 2)); check(!g.containsEdge(2, 1));
            equal(4, g.edgeCount()); equal(6, g.vertexCount());
        });
        test("Remove missing edge has no effect", () -> {
            GraphOperations g = ArshadDemo.sampleGraph();
            check(!g.removeEdge(1, 99)); check(!g.removeEdge(99, 1)); equal(5, g.edgeCount());
        });
        test("Remove vertex clears incident edges", () -> {
            GraphOperations g = ArshadDemo.sampleGraph();
            check(g.removeVertex(2)); equal(5, g.vertexCount()); equal(2, g.edgeCount());
            check(!g.containsEdge(1, 2)); check(!g.containsEdge(4, 2)); check(!g.containsEdge(5, 2));
            equal(List.of(1, 3, 6), g.bfs(1)); equal(List.of(4), g.dfs(4));
        });
        test("Remove missing vertex has no effect", () -> {
            GraphOperations g = ArshadDemo.sampleGraph();
            check(!g.removeVertex(99)); equal(6, g.vertexCount()); equal(5, g.edgeCount());
        });
        test("Missing traversal start returns empty lists", () -> {
            GraphOperations g = ArshadDemo.sampleGraph();
            equal(List.of(), g.bfs(99)); equal(List.of(), g.dfs(99)); equal(6, g.vertexCount());
        });
        test("Traversals on empty graph", () -> {
            GraphOperations g = new GraphOperations(); equal(List.of(), g.bfs(1)); equal(List.of(), g.dfs(1));
        });
        test("Single vertex traversals", () -> {
            GraphOperations g = new GraphOperations(); g.addVertex(42);
            equal(List.of(42), g.bfs(42)); equal(List.of(42), g.dfs(42));
        });
        test("Self-loop counted once and traversed once", () -> {
            GraphOperations g = new GraphOperations(); check(g.addEdge(4, 4));
            check(!g.addEdge(4, 4)); equal(1, g.edgeCount()); equal(1, g.vertexCount());
            equal(List.of(4), g.bfs(4)); equal(List.of(4), g.dfs(4)); equal("4 -> [4]", g.toString());
        });
        test("Remove self-loop once", () -> {
            GraphOperations g = new GraphOperations(); g.addEdge(4, 4);
            check(g.removeEdge(4, 4)); check(!g.removeEdge(4, 4)); equal(0, g.edgeCount()); equal(1, g.vertexCount());
        });
        test("Remove vertex with self-loop and ordinary edge", () -> {
            GraphOperations g = new GraphOperations(); g.addEdge(4, 4); g.addEdge(4, 5); g.addEdge(5, 6);
            g.removeVertex(4); equal(1, g.edgeCount()); equal(2, g.vertexCount()); equal(List.of(5, 6), g.dfs(5));
        });
        test("Repeated traversals are deterministic", () -> {
            GraphOperations g = ArshadDemo.sampleGraph();
            for (int i = 0; i < 5; i++) {
                equal(List.of(1, 2, 3, 4, 5, 6), g.bfs(1)); equal(List.of(1, 2, 4, 5, 3, 6), g.dfs(1));
            }
        });
        test("Returned traversal lists do not expose graph state", () -> {
            GraphOperations g = ArshadDemo.sampleGraph(); g.bfs(1).clear(); g.dfs(1).clear();
            equal(6, g.bfs(1).size()); equal(6, g.dfs(1).size()); equal(5, g.edgeCount());
        });
        test("Reinserted edge follows new insertion order", () -> {
            GraphOperations g = ArshadDemo.sampleGraph(); g.removeEdge(1, 2); g.addEdge(1, 2);
            equal(List.of(1, 3, 2, 6, 4, 5), g.bfs(1)); equal(List.of(1, 3, 6, 2, 4, 5), g.dfs(1));
        });
        test("Remove all vertices restores empty state", () -> {
            GraphOperations g = ArshadDemo.sampleGraph();
            for (int v = 1; v <= 6; v++) { check(g.removeVertex(v)); }
            check(g.isEmpty()); equal(0, g.edgeCount()); equal(0, g.vertexCount());
        });
        test("All integer labels supported", () -> {
            GraphOperations g = new GraphOperations(); g.addEdge(Integer.MIN_VALUE, 0); g.addEdge(0, Integer.MAX_VALUE);
            List<Integer> expected = List.of(Integer.MIN_VALUE, 0, Integer.MAX_VALUE);
            equal(expected, g.bfs(Integer.MIN_VALUE)); equal(expected, g.dfs(Integer.MIN_VALUE));
        });
        test("Long chain DFS avoids recursive stack overflow", () -> {
            GraphOperations g = new GraphOperations();
            for (int v = 0; v < 19999; v++) { g.addEdge(v, v + 1); }
            List<Integer> order = g.dfs(0); equal(20000, order.size());
            for (int v = 0; v < order.size(); v++) { equal(v, order.get(v)); }
            equal(19999, g.edgeCount());
        });
        System.out.println("\nResults: " + passed + " passed, " + failed + " failed, " + (passed + failed) + " total.");
        if (failed > 0) { System.exit(1); }
    }

    private static void test(String name, Runnable action) {
        try {
            action.run(); passed++; System.out.println("PASS: " + name);
        } catch (AssertionError | RuntimeException exception) {
            failed++; System.out.println("FAIL: " + name + " -- " + exception);
        }
    }

    private static void check(boolean condition) {
        if (!condition) { throw new AssertionError("Condition was false"); }
    }

    private static void equal(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) {
            throw new AssertionError("Expected " + expected + ", got " + actual);
        }
    }
}
