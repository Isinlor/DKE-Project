import java.util.*;

public class Greedy {

	public static void findUpperBound(Graph graph) {

		class GraphX {

			int n; //number of vertices
			int m; //number of edges
			int[][] adj; //adjacency matrix

			//constructor
			GraphX(int n, int m, Edge[] e){

				this.n = n;
				this.m = m;

				//create and fill the adjacency matrix
				adj = new int[n][n];

				for(int i=0;i<n;i++)
					for(int j=0;j<n;j++)
						adj[i][j]=0;

				for(int k=0;k<m;k++){

					adj[e[k].from-1][e[k].to-1]=1;
					adj[e[k].to-1][e[k].from-1]=1;
				}
			}

			int getUpperBound(){

				//list of numbers used to color the graph without duplicates
				ArrayList<Integer> Chrom = new ArrayList<Integer>();

				//colors of vertices
				int[] vertexCol = new int[n];
				Arrays.fill(vertexCol,-1);

				//array which says if a certain color is available or not, all colors set to true in the beginning
				boolean[] availCol = new boolean[n];
				Arrays.fill(availCol,true);

				//color first vertix with first color
				vertexCol[0]=0;

				//go through all the vertices
				for(int i=1; i<n; i++){

					//go through all of the neighbours and set their colors to unavailable
					for(int j=0; j<n; j++)
						if((adj[i][j]==1)&&(vertexCol[j]!=-1))
							availCol[vertexCol[j]] = false;

					//search for the first available color
					int curCol = 0;
					while(!availCol[curCol])
						curCol++;

					//color the vertix and add it to the Chrom list if it's not already there
					vertexCol[i]=curCol;
					if(!Chrom.contains(curCol))
						Chrom.add(curCol);

					//reset the array
					Arrays.fill(availCol, true);
				}

				//chromatic number is size of the chrom array
				return Chrom.size();
			}
		}

		GraphX G = new GraphX(
				graph.getNumberOfVertices(),
				graph.getNumberOfEdges(),
				graph.getEdges()
		);

//		System.out.println("Greedy start...");
		int upperBound = G.getUpperBound();
//		System.out.println("Greedy: " + upperBound);

		graph.addUpperBound(upperBound);

	}
}
