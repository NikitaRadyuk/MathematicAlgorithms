import java.util.*;

public abstract class Graph {
    protected final LinkedHashMap<Integer, List<Integer>> edges = new LinkedHashMap<>();
    protected final List<Integer> visited = new ArrayList<>();
    protected int edgeNumber;

    public Graph() {}
    public Graph(int vertices) {
        for (int i = 0; i < vertices; i++) {
            this.edges.put(i, new LinkedList<>());
        }
    }

    public void addEdges(int[][] adjacencyMatrix) {
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            for (int j = 0; j < adjacencyMatrix.length; j++) {
                if (adjacencyMatrix[i][j] == 1) {
                    this.edges.get(i).add(j);
                    edgeNumber++;
                }
            }
        }

        edgeNumber /= 2;
    }

    public abstract void printTrack();
}