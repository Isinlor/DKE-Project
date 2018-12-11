import java.io.*;
import java.util.*;

public class Backtracking {

    static boolean DEBUG = false;

	public static int getChromaticNumber(int numberOfVertices, int numberOfEdges, Edge[] edges) {

        class Node {

            int color;

            public Node(int color) {
                this.color = color;
            }

            public int getLabel() {
                return color;
            }

            public int getDegree() {
                return color;
            }

        }

        class Graph {

            int numberOfVertices; //number of vertices
            int numberOfEdges; //number of edges
            int[][] adj; //adjacency matrix
            ArrayList<Node> nodes = new ArrayList<Node>();
            ArrayList<Set<Node>> maxCliqueList = new ArrayList<Set<Node>>();
            int colors[];


            /**
             * Constructor
             */
            Graph(int numberOfVertices, int numberOfEdges, Edge[] e) {

                this.numberOfVertices = numberOfVertices;
                this.numberOfEdges = numberOfEdges;

                adj = new int[numberOfVertices + 1][numberOfVertices + 1];

                for (int i = 0; i <= numberOfVertices; i++)
                    for (int j = 0; j <= numberOfVertices; j++)
                        adj[i][j] = 0;

                for (int k = 0; k < numberOfEdges; k++) {

                    adj[e[k].to][e[k].from] = 1;
                    adj[e[k].from][e[k].to] = 1;
                }

                for (int i = 0; i <= numberOfVertices; i++)
                    nodes.add(new Node(i));

                colors = new int[numberOfVertices + 1];
                for (int c = 0; c <= numberOfVertices; c++)
                    colors[c] = 0;
            }

            /**
             * Check whether there's an edge between two nodes
             */
            public boolean isAdjacent(Node a, Node b) {

                if (adj[a.getLabel()][b.getLabel()] == 1)
                    return true;

                return false;
            }

            /**
             * Returns neighbours of node x
             */
            public ArrayList<Node> getNeighbours(Node x) {

                ArrayList<Node> neighbours = new ArrayList<Node>();

                for (Node n : nodes)
                    if (isAdjacent(n, x))
                        neighbours.add(n);

                return neighbours;
            }


            /**
             * Check if we can color vertex x with a specific color
             */
            public boolean isAvailable(Node x, int color) {

                for (Node n : nodes)
                    if (isAdjacent(n, x) && (colors[n.getLabel()] == color))
                        return false;

                return true;
            }

            /**
             * Checks if a graph is complete
             */
            public boolean isComplete() {

                boolean check = true;

                for (Node n : nodes)
                    if (n.getDegree() != (numberOfVertices - 1))
                        check = false;

                return check;
            }

            /**
             * Check if graph is a star
             */

            public boolean isStar() {

                int counter1 = 0, counter2 = 0;

                for (Node n : nodes) {

                    if (n.getDegree() == 1)
                        counter1++;
                    else if (n.getDegree() == (numberOfVertices - 1))
                        counter2++;
                }

                if ((counter1 == (numberOfVertices - 1)) && (counter2 == 1))
                    return true;

                return false;
            }

            /**
             * Intersection of two sets
             */
            public ArrayList<Node> Intersection(ArrayList<Node> S1, ArrayList<Node> S2) {

                ArrayList<Node> S3 = new ArrayList<Node>(S1);
                S3.retainAll(S2);
                return S3;
            }

            /**
             * Bron Kerbosch algorithm - version without a pivot
             */
            public void BronKerbosch(ArrayList<Node> R, ArrayList<Node> P, ArrayList<Node> X) {

                if ((P.size() == 0) && (X.size() == 0)) {

                    processMaxClique(R);
                    return;
                }

                ArrayList<Node> P1 = new ArrayList<Node>(P);

                for (Node n : P) {

                    R.add(n);
                    BronKerbosch(R, Intersection(P1, getNeighbours(n)), Intersection(X, getNeighbours(n)));
                    R.remove(n);
                    P1.remove(n);
                    X.add(n);
                }
            }

            /**
             * Proccess a maximal clique and add it to the list of all maximal cliques
             */
            public void processMaxClique(ArrayList<Node> R) {

                Set<Node> maxClique = new HashSet<Node>();

                for (Node n : R)
                    maxClique.add(n);

                maxCliqueList.add(maxClique);
            }

            public void executeBronKerbosch() {

                ArrayList<Node> X = new ArrayList<Node>();
                ArrayList<Node> R = new ArrayList<Node>();
                ArrayList<Node> P = new ArrayList<Node>(nodes);

                BronKerbosch(R, P, X);
            }

            public int getLowerBound() {

                int lowerBound = 1;

                for (Set<Node> maxClique : maxCliqueList)
                    if (maxClique.size() > lowerBound)
                        lowerBound = maxClique.size();

                return lowerBound;
            }

            /**
             * Backtracking algorithm
             */
            public boolean Backtracking(int m, Node x) {

                if (x.getLabel() == numberOfVertices)
                    return true;

                for (int col = 1; col <= m; col++) {

                    if (isAvailable(x, col)) {

                        colors[x.getLabel()] = col;
                        if (Backtracking(m, nodes.get(nodes.indexOf(x) + 1)))
                            return true;
                    }

                    colors[x.getLabel()] = 0;
                }

                return false;
            }

            /**
             * Execute backtracking
             */
            public boolean executeBacktracking(int m) {

                boolean found = false;

                if (Backtracking(m, new Node(1)) == false) {

                    System.out.println("The solution doesn't exist");

                } else {

                    found = true;
                }

                return found;
            }

            /**
             * Get chromatic number
             */
            public int getChromaticNumber() {

                if (isComplete())
                    return numberOfVertices;
                if (isStar())
                    return 2;

                executeBronKerbosch();
                int i = getLowerBound();

                while (i <= numberOfVertices) {

                    boolean colored = executeBacktracking(i);

                    if(DEBUG) {
                        if (colored) {
                            System.out.println("Solution");
                            for (Node n : nodes)
                                System.out.println("Node " + n.getLabel() + " Color " + colors[n.getLabel()]);
                        } else {
                            System.out.println("No solution for " + i);
                        }
                    }

                    if(colored) {
                        break;
                    }

                    i++;
                }

                return i;
            }

        }

        //! At this point e[0] will be the first edge, with e[0].to referring to one endpoint and e[0].from to the other
        //! e[1] will be the second edge...
        //! (and so on)
        //! e[m-1] will be the last edge
        //!
        //! there will be n vertices in the graph, numbered 1 to n

        //! INSERT YOUR CODE HERE!
        Graph G = new Graph(numberOfVertices, numberOfEdges, edges);

        int x = G.getChromaticNumber();
        System.out.println("The chromatic number is: " + x);

        return x;

    }
}