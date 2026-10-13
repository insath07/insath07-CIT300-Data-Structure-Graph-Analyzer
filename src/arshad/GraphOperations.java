package arshad;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Undirected, unweighted graph with insertion-ordered adjacency lists.
 * Duplicate vertices and edges have no effect. Self-loops are allowed and count
 * as one edge. Adding an edge creates either endpoint if it does not exist.
 * This mutable class is intended for single-threaded use.
 */
public class GraphOperations {
    private final Map<Integer, Set<Integer>> adjacency = new LinkedHashMap<>();
    private int edges;

    /** Adds a vertex; returns false when it already exists. */
    public boolean addVertex(int vertex) {
        if (adjacency.containsKey(vertex)) {
            return false;
        }
        adjacency.put(vertex, new LinkedHashSet<>());
        return true;
    }

    /** Adds an undirected edge and missing endpoints; returns false for duplicates. */
    public boolean addEdge(int source, int destination) {
        addVertex(source);
        addVertex(destination);
        if (!adjacency.get(source).add(destination)) {
            return false;
        }
        adjacency.get(destination).add(source);
        edges++;
        return true;
    }

    /** Removes a vertex and all incident edges; returns false if absent. */
    public boolean removeVertex(int vertex) {
        Set<Integer> neighbors = adjacency.remove(vertex);
        if (neighbors == null) {
            return false;
        }
        // Each neighbor represents one incident edge, including a self-loop.
        edges -= neighbors.size();
        for (int neighbor : neighbors) {
            if (neighbor != vertex) {
                adjacency.get(neighbor).remove(vertex);
            }
        }
        return true;
    }

    /** Removes both directions of an edge; returns false if absent. */
    public boolean removeEdge(int source, int destination) {
        if (!containsEdge(source, destination)) {
            return false;
        }
        adjacency.get(source).remove(destination);
        adjacency.get(destination).remove(source);
        edges--;
        return true;
    }

    /** Returns whether this vertex exists. */
    public boolean containsVertex(int vertex) {
        return adjacency.containsKey(vertex);
    }

    /** Returns whether this edge exists (in either direction). */
    public boolean containsEdge(int source, int destination) {
        Set<Integer> neighbors = adjacency.get(source);
        return neighbors != null && neighbors.contains(destination);
    }

    /** Returns the number of vertices, including isolated vertices. */
    public int vertexCount() {
        return adjacency.size();
    }

    /** Returns the number of undirected edges; a self-loop counts once. */
    public int edgeCount() {
        return edges;
    }

    /** Returns true when there are no vertices. */
    public boolean isEmpty() {
        return adjacency.isEmpty();
    }

    /**
     * Returns level-order traversal of the component reachable from start.
     * A missing start returns an empty list. Marks vertices when enqueued so
     * cycles cannot enqueue a vertex repeatedly. Expected O(Vr + Er) time and
     * O(Vr) auxiliary space, where r denotes the reachable component.
     */
    public List<Integer> bfs(int start) {
        List<Integer> order = new ArrayList<>();
        if (!containsVertex(start)) {
            return order;
        }
        Set<Integer> visited = new LinkedHashSet<>();
        Deque<Integer> queue = new ArrayDeque<>();
        visited.add(start);
        queue.addLast(start);
        while (!queue.isEmpty()) {
            int vertex = queue.removeFirst();
            order.add(vertex);
            for (int neighbor : adjacency.get(vertex)) {
                if (visited.add(neighbor)) {
                    queue.addLast(neighbor);
                }
            }
        }
        return order;
    }

    /**
     * Returns depth-first traversal in recursive insertion order using an
     * explicit stack, avoiding Java call-stack limits. A missing start returns
     * an empty list. Only the reachable component is visited. Expected
     * O(Vr + Er) time and O(Vr) auxiliary space.
     */
    public List<Integer> dfs(int start) {
        List<Integer> order = new ArrayList<>();
        if (!containsVertex(start)) {
            return order;
        }
        Set<Integer> visited = new LinkedHashSet<>();
        Deque<Iterator<Integer>> stack = new ArrayDeque<>();
        visited.add(start);
        order.add(start);
        stack.push(adjacency.get(start).iterator());
        while (!stack.isEmpty()) {
            Iterator<Integer> neighbors = stack.peek();
            if (!neighbors.hasNext()) {
                stack.pop(); // Finished this vertex: backtrack to its parent.
            } else {
                int neighbor = neighbors.next();
                if (visited.add(neighbor)) {
                    order.add(neighbor);
                    stack.push(adjacency.get(neighbor).iterator());
                }
            }
        }
        return order;
    }

    /** Prints the insertion-ordered adjacency list to standard output. */
    public void display() {
        System.out.println(this);
    }

    /** Returns one adjacency list per line, or a clear empty-graph message. */
    @Override
    public String toString() {
        if (isEmpty()) {
            return "Graph is empty.";
        }
        StringBuilder result = new StringBuilder();
        for (Map.Entry<Integer, Set<Integer>> entry : adjacency.entrySet()) {
            if (result.length() > 0) {
                result.append(System.lineSeparator());
            }
            result.append(entry.getKey()).append(" -> ").append(entry.getValue());
        }
        return result.toString();
    }
}
