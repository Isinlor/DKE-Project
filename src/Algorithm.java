public class Algorithm {

    static Graph graph;

    public static void main(String[] args) {

        graph = FileLoader.load(args[0]).simplify();

        SimpleColoring.color(graph);

        if(graph.hasChromaticNumber()) return;

        NeighborhoodSearch neighborhoodSearch = new NeighborhoodSearch(graph, 500);
        neighborhoodSearch.findUpperBound();

        Greedy.findUpperBound(graph);

        (new DSatur(graph)).executeDSATUR();

        neighborhoodSearch = new NeighborhoodSearch(graph, 1000);
        neighborhoodSearch.findUpperBound();

        MaxClique maxClique = new MaxClique(graph);

        if(graph.getUpperBound() - graph.getLowerBound() > 1) {
            TimedBacktracking timedBacktracking = new TimedBacktracking(graph);
            timedBacktracking.colorClique(maxClique.getMaxClique());
            timedBacktracking.findUpperBound();
        }

        Backtracking backtracking = new Backtracking(graph);
        backtracking.colorClique(maxClique.getMaxClique());
        backtracking.findUpperBound();

    }

}
