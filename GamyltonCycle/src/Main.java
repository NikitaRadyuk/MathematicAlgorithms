import java.awt.font.GraphicAttribute;

class GamyltonCycle {
    static int V = 5;
    int path[];

    boolean isSafe(int v, int graph[][], int path[], int pos) {
        /* Проверяем, является ли эта вершина смежной
         по отношению к ранее добавленной.*/
        if (graph[path[pos - 1]][v] == 0){
            return false;
        }



        /* Проверяем, не была ли эта вершина уже включена */
        for (int i = 0; i < pos; i++)
            if (path[i] == v)
                return false;

        return true;
    }

    boolean isNormGraph(int graph[][]){
        for(int i = 0; i < V; i++) {
            if(isSingle(i, graph)) return false;
        }
        return true;
    }

    boolean isSingle(int v, int graph[][]) {
        for(int i = 0; i < V; i++) {
            if(graph[v][i] != 0 || graph[i][v] != 0) return false;
        }
        return true;
    }

    boolean hamCycleUtil(int graph[][], int path[], int pos) {
        /* если все вершины включены в гамильтоновый цикл */
        if (pos == V) {
            // если есть ребро из последней включенной вершины в первую вершину
            if (graph[path[pos - 1]][path[0]] == 1)
                return true;
            else
                return false;
        }

        // пробуем разные вершине в качестве следующей точки цикла
        for (int v = 1; v < V; v++) {
            /* Проверяем, можно ли добавить вершину в Гамильтонов цикл */
            if (isSafe(v, graph, path, pos)) {
                path[pos] = v;
                //рекурсия для построения оставшейся части пути
                if (hamCycleUtil(graph, path, pos + 1) == true)
                    return true;

                /*если текущая вершина не привела к решению, то уменьшаем путь*/
                path[pos] = -1;
            }
        }

        return false;
    }

    int hamCycle(int graph[][]) {
        path = new int[V];
        for (int i = 0; i < V; i++)
            path[i] = -1;


        path[0] = 0;
        if (hamCycleUtil(graph, path, 1) == false) {
            System.out.println("" +
                    "Solution does not exist");
            return 0;
        }


        printSolution(path);
        return 1;
    }

    void printSolution(int path[]) {
        for (int i = 0; i < V; i++)
            System.out.print(" " + path[i] + " ");

        System.out.println(" " + path[0] + " ");
    }

    public static void main(String args[]) {
        GamyltonCycle gam =
                new GamyltonCycle();
        /* (0)--(1)--(2)
            |   / \   |
            |  /   \  |
            | /     \ |
           (3)------(4)    */
        int graph1[][] = {
                {0, 1, 0, 1, 0},
                {1, 0, 1, 1, 1},
                {0, 1, 0, 0, 1},
                {1, 1, 0, 0, 1},
                {0, 1, 1, 1, 0},
        };

        if(gam.isNormGraph(graph1)) {
            gam.hamCycle(graph1);
        }
        else System.out.println("not exist");

        /* (0)--(1)--(2)
            |   / \   |
            |  /   \  |
            | /     \ |
           (3)      (4)    */
        int graph2[][] = {
                {0, 1, 0, 1, 0},
                {1, 0, 1, 1, 1},
                {0, 1, 0, 0, 1},
                {1, 1, 0, 0, 0},
                {0, 1, 1, 0, 0},
        };


        if(gam.isNormGraph(graph2)) {
            gam.hamCycle(graph2);
        }
        else System.out.println("not exist");

        V = 6;
        int graph3[][] = {
                {0, 1, 0, 1, 0, 0},
                {1, 0, 1, 1, 1, 0},
                {0, 1, 0, 0, 1, 0},
                {1, 1, 0, 0, 1, 0},
                {0, 1, 1, 1, 0, 0},
                {0, 0, 0, 0, 0, 0}
        };

        if(gam.isNormGraph(graph3)) {
            gam.hamCycle(graph3);
        }
        else System.out.println("not exist");
    }
}