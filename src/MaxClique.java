import java.util.HashSet;
import java.util.Set;

class MaxClique {

    private Graph graph;
    private Set<Integer> maxClique = new HashSet<>();
    private int maxCliqueDegree;

    MaxClique(Graph graph) {
        this.graph = graph;
        executeBronKerbosch();
    }

    public Set<Integer> getMaxClique() {
        return maxClique;
    }

    private void executeBronKerbosch() {

        HashSet<Integer> X = new HashSet<Integer>();
        HashSet<Integer> R = new HashSet<Integer>();
        HashSet<Integer> P = new HashSet<Integer>(graph.getVertices());

        BronKerbosch(R, P, X);

    }

    /**
     * Bron Kerbosch algorithm - version without a pivot
     */
    private void BronKerbosch(Set<Integer> R, Set<Integer> P, Set<Integer> X) {

        if ((P.size() == 0) && (X.size() == 0)) {
            addClique(R);
            return;
        }

        HashSet<Integer> P2 = new HashSet<Integer>(P);
        P2.removeAll(getNeighbours(getPivotVertex(P, X)));

        for (Integer n : P2) {

            R.add(n);
            BronKerbosch(R, Intersection(P, getNeighbours(n)), Intersection(X, getNeighbours(n)));
            R.remove(n);
            P.remove(n);
            X.add(n);

        }
    }

    private int getPivotVertex(Set<Integer> P, Set<Integer> X) {
        int pivotVertex = 0;
        int pivotVertexDegree = 0;
        for (Integer n: P) {
            if(graph.getDegree(n) > pivotVertexDegree) {
                pivotVertex = n;
            }
        }
        for (Integer n: X) {
            if(graph.getDegree(n) > pivotVertexDegree) {
                pivotVertex = n;
            }
        }
        return pivotVertex;
    }

    /**
     * Intersection of two sets
     */
    private Set<Integer> Intersection(Set<Integer> S1, Set<Integer> S2) {
        HashSet<Integer> S3 = new HashSet<Integer>(S1);
        S3.retainAll(S2);
        return S3;
    }

    private Set<Integer> getNeighbours(int x) {
        return graph.getNeighbours(x);
    }

    private void addClique(Set<Integer> clique) {
        if(Thread.interrupted()) throw new RuntimeException();
        graph.addLowerBound(clique.size());
        if(clique.size() > maxClique.size()) {
            setMaxClique(clique);
        } else if(clique.size() == maxClique.size()) {
            if(getDegree(clique) > maxCliqueDegree) {
                setMaxClique(clique);
            }
        }
    }

    private void setMaxClique(Set<Integer> clique) {
        maxClique = new HashSet<>();
        maxClique.addAll(clique);
        maxCliqueDegree = getDegree(clique);
    }

    private int getDegree(Set<Integer> clique) {
        int degree = 0;
        for (int vertex: clique) {
            degree += graph.getDegree(vertex);
        }
        return degree;
    }

}