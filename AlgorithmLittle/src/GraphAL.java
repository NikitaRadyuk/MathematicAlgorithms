import java.util.*;
public class GraphAL {
    private final LinkedHashMap<Integer, Integer> circuit = new LinkedHashMap<>();
    private final int[][] route;
    private final int INFINITY = -1;
    private int bottomLine = 0;
    private int n;

    public GraphAL(int[][] route) {
        this.route = route;
        n = route.length;
    }

    public void minimizeRoute() {
        castToCostMatrix();
        if (n-- == 2) {
            putLastPath();
            return;
        }

        int[] maxCoordinates = findMaxCoordinates(findAllG());
        delete(maxCoordinates[0], maxCoordinates[1]);
        circuit.put(maxCoordinates[0], maxCoordinates[1]);
        minimizeRoute();
    }

    /**
     * Deletes(changes values to INFINITY) the specified
     * row and column in the route.
     *
     * @param r row to delete.
     * @param c column to delete.
     */
    private void delete(int r, int c) {
        for (int i = 0; i < route.length; i++) {
            for (int j = 0; j < route.length; j++) {
                if (i == r || j == c) {
                    route[i][j] = INFINITY;
                }
            }
        }

        route[c][r] = INFINITY;
    }

    /**
     * Finds all G coefficients in each row and column
     * of the route.
     *
     * @return list of array with G info.
     */
    private List<int[]> findAllG() {
        List<int[]> gs = new ArrayList<>();
        for (int i = 0; i < route.length; i++) {
            for (int j = 0; j < route.length; j++) {
                if (route[i][j] == 0) {
                    gs.add(findG(i, j));
                }
            }
        }

        return gs;
    }

    /**
     * Finds a G coefficient for the value
     * with (i, j) coordinates in the route.
     *
     * @param i row number.
     * @param j column number.
     * @return an array with 3 values:
     * 1. value of G.
     * 2. row number.
     * 3. column number.
     */
    private int[] findG(int i, int j) {
        return new int[] {
                findMin(i, j, true) + findMin(j, i, false),
                i,
                j,
        };
    }

    /**
     * Finds the min value in the specified row or column.
     *
     * @param i row or column number.
     * @param k column or row number.
     * @param byRow if true - the search goes by rows.
     *              if false - the search goes by columns.
     * @return min value.
     */
    private int findMin(int i, int k, boolean byRow) {
        int min = 100000;

        for (int j = 0; j < route.length; j++) {
            if (j != k) {
                int i1 = byRow ? i : j;
                int j1 = byRow ? j : i;
                if (route[i1][j1] != INFINITY && min > route[i1][j1]) {
                    min = route[i1][j1];
                }
            }
        }

        return min;
    }

    private void castToCostMatrix() {
        subtract(true);
        subtract(false);
    }

    /**
     * Finds the min value and subtracts it.
     *
     * @param byRow if true - the search goes by rows.
     *              if false - the search goes by columns.
     */
    private void subtract(boolean byRow) {
        final int MAX = 100000;
        int[] min = new int[route.length];
        for (int i = 0; i < route.length; i++) {
            min[i] = MAX;
            for (int j = 0; j < route.length; j++) {
                int i1 = byRow ? i : j;
                int j1 = byRow ? j : i;
                if (route[i1][j1] != INFINITY && min[i] > route[i1][j1]) {
                    min[i] = route[i1][j1];
                }
            }
        }

        for (int i = 0; i < route.length; i++) {
            if (min[i] != MAX) {
                bottomLine += min[i];
                for (int j = 0; j < route.length; j++) {
                    int i1 = byRow ? i : j;
                    int j1 = byRow ? j : i;
                    if (route[i1][j1] != INFINITY) {
                        route[i1][j1] -= min[i];
                    }
                }
            }
        }
    }

    /**
     * From all G coefficients
     * searches for the maximal.
     *
     * @param gs list to search.
     * @return coordinates of the maximal.
     */
    private int[] findMaxCoordinates(List<int[]> gs) {
        int max = 0, r = 0, c = 0;
        for (int[] g : gs) {
            if (max < g[0]) {
                max = g[0];
                r = g[1];
                c = g[2];
            }
        }

        return new int[] {r, c};
    }

    /**
     * Puts the last path into G circuit,
     * when matrix dimension is equal two.
     */
    private void putLastPath() {
        for (int i = 0; i < route.length; i++) {
            for (int j = 0; j < route.length; j++) {
                if (route[i][j] == 0) {
                    circuit.put(i, j);
                    break;
                }
            }
        }
    }

    /**
     * Transforms the route in the
     * correct sequence.
     *
     * @return transformed route.
     */
    private Map<Integer, Integer> transform() {
        Map<Integer, Integer> c = new LinkedHashMap<>();
        int key = circuit.keySet().iterator().next();
        for (int i = 0; i < circuit.size(); i++) {
            int val = circuit.get(key);
            c.put(key, val);
            key = val;
        }

        return c;
    }
    public void printTrack() {
        Map<Integer, Integer> c = transform();
        Set<Integer> keys = c.keySet();
        for (int k : keys) {
            System.out.print(k + " - " + c.get(k) + ", ");
        }

        System.out.println("\nBottom Line = " + bottomLine);
    }


    public static void main(String[] args) {
        final int INFINITY = -1;
        int[][] route = {
                {INFINITY, 34, 33, 25, 28},
                {28, INFINITY, 25, 27, 22},
                {37, 28, INFINITY, 30, 31},
                {30, 29, 28, INFINITY, 34},
                {39, 38, 41, 40, INFINITY},
        };

        GraphAL graph = new GraphAL(route);
        graph.minimizeRoute();
        graph.printTrack();
    }
}
