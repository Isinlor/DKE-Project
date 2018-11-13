import java.util.Scanner;

public class GenerateGraph {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        int vertices, edges;

        do {

            //System.out.print("How many vertices: ");
            vertices = input.nextInt();

            //System.out.print("How many edges: ");
            edges = input.nextInt();

        } while(vertices > edges);

        System.out.println("VERTICES = " + vertices);
        System.out.println("EDGES = " + edges);

        int[][] graph = makeGraph(vertices, edges);

        for(int x = 1; x < vertices; x++) {
            for(int y = x; y < vertices; y++) {
                if(graph[x][y] != 0) System.out.println(x + " " + y);
            }
        }

    }

    public static int[][] makeGraph(int vertices, int edges) {

        int[][] graph = new int[vertices + 1][vertices + 1];

        while(edges > 0) {

            int x = (int)(Math.random() * vertices) + 1;
            int y = (int)(Math.random() * vertices) + 1;

            //System.out.println(x + " " + y);

            if(x > y && graph[x][y] == 0) {
                System.out.println(x + " " + y);
                graph[x][y] = 1;
                edges--;
            }

        }

        return graph;

    }

}
