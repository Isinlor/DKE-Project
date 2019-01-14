import java.util.*;

/**
 * This class allows to compute exact chromatic number.
 *
 * It is based on Bron Kerbosch algorithm in order to compute lower bound.
 * Based on lower the algorithm tries to color the graph with more and more colors using backtracking algorithm.
 *
 * Author: Anna (see: sketchpad/anna/backtracking.java for original version)
 * Integration: Tomek
 */
public class ExactAlgorithm {

    /**
     * @param numberOfVertices
     * @param edges
     * @return
     */
	public static int getChromaticNumber(int numberOfVertices, Edge[] edges, int lowerBound) {

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

            HashMap<Integer, ArrayList<Node>> adjacentNodes = new HashMap<>();


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

                for (int i = 1; i <= numberOfVertices; i++)
                    nodes.add(new Node(i));

                colors = new int[numberOfVertices + 1];
                for (int c = 0; c <= numberOfVertices; c++)
                    colors[c] = 0;

                for(Node nodeA: nodes) {

                    ArrayList<Node> adjacentToA = new ArrayList<>();

                    for(Node nodeB: nodes) {
                        if(nodeA == nodeB) continue;

                        if(isAdjacent(nodeA, nodeB)) {
                            adjacentToA.add(nodeB);
                        }

                    }

                    adjacentNodes.put(nodeA.getLabel(), adjacentToA);

                }

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
             * Check if we can color vertex x with a specific color
             */
            public boolean isAvailable(Node x, int color) {

                for (Node n : adjacentNodes.get(x.getLabel()))
                    if (colors[n.getLabel()] == color)
                        return false;

                return true;
            }

            long iterations = 0;
            long maxIterations = 10000000;

            /**
             * ExactAlgorithm algorithm
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
//                     System.out.println("LOWER BOUND = " + m);
                } else {
                    found = true;
                }

                iterations = 0;

                return found;
            }

            /**
             * Get chromatic number
             */
            public int getChromaticNumber(int lowerBound) {

                int i = lowerBound;
                while (i <= numberOfVertices) {

                    boolean colored = executeBacktracking(i);

                    if(colored) {
                        break;
                    }

                    i++;
                }

                return i;
            }

        }

        Graph G = new Graph(numberOfVertices, edges.length, edges);

        return G.getChromaticNumber(lowerBound);

    }
}