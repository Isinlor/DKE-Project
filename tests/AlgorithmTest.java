import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class AlgorithmTest {

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {

//        Edge[] edges = {new Edge(4, 2), new Edge(2, 3), new Edge(3, 4), new Edge(3, 1)};
//        GraphX graph = new GraphX(edges, 5);

//        Graph graph = Random.generate(11, (11*10) / 2);
//
//        Backtracking backtracking = new Backtracking(graph);
//        backtracking.findUpperBound();
//
//        System.out.println(graph.getUpperBound());

//        GraphX graph = FileLoader.load("/home/isinlor/Projects/DKE-Project/graphs/phase3/block3_2018_graph08.txt");
//        System.out.println(graph.getShortDescription());
//        System.out.println(graph.simplify().getShortDescription());
//        System.exit(0);

        testDirectory(args[0]);

    }

    public static void testDirectory(String directory) throws InterruptedException, ExecutionException, TimeoutException {
        File[] graphFiles = new File(directory).listFiles(new FileFilter() {
            public boolean accept(File file) {
                return file.getName().endsWith(".txt");
            }
        });

        for(File graphFile: graphFiles) {

            System.gc();
//            System.out.println(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
            System.out.print(graphFile.getName() + ":");

            Graph graph = FileLoader.load(graphFile.getAbsolutePath()).simplify();
            MaxClique maxClique = new MaxClique(graph);
            Greedy2.findUpperBound(graph);

//            System.out.println(graph.getDescendingDegreeSortedVertices());

//            if(true) continue;

            try {

                CompletableFuture.runAsync(() -> {

//                    return ExactAlgorithm.getChromaticNumber(
//                            graph.getNumberOfVertices(),
//                            graph.getEdges(),
//                            graph.getLowerBound()
//                    );

                    Backtracking backtracking = new Backtracking(graph);
                    backtracking.colorClique(maxClique.getMaxClique());
                    backtracking.findUpperBound();

//                    return graph.getUpperBound() == graph.getLowerBound() ? graph.getUpperBound() : 0;

                }).get(3, TimeUnit.SECONDS);

                System.out.print("\tL: " + graph.getLowerBound());
                System.out.print("\tU: " + graph.getUpperBound());
                System.out.print("\tC: " + (graph.getUpperBound() == graph.getLowerBound() ? graph.getUpperBound() : 0));
                System.out.println();

            } catch (TimeoutException e) {
                System.out.print("\tL: " + graph.getLowerBound());
                System.out.print("\tU: " + graph.getUpperBound());
                System.out.print("\tC: timeout");
                System.out.println();
            }
        }
    }

}