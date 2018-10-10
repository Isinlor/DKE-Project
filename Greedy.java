import java.io.*;
import java.util.*;

class Edge{

	int u;
	int v;
}

class Graph{

	int n; //number of vertices
	int m; //number of edges
	int[][] adj; //adjacency matrix

	//constructor
	Graph(int n, int m, Edge[] e){

		this.n = n;
		this.m = m;

		//create and fill the adjacency matrix
		adj = new int[n][n];

		for(int i=0;i<n;i++)
			for(int j=0;j<n;j++)
				adj[i][j]=0;

		for(int k=0;k<m;k++){

			adj[e[k].u-1][e[k].v-1]=1;
			adj[e[k].v-1][e[k].u-1]=1;
		}
	}

	int getChromatic(){

		//list of numbers used to color the graph without duplicates
		ArrayList<Integer> Chrom = new ArrayList<Integer>();

		//colors of vertices
		int[] vertexCol = new int[n];
		Arrays.fill(vertexCol,-1);

		//array which says if a certain color is available or not, all colors set to true in the beginning
		boolean availCol[] = new boolean[n];
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
			while(availCol[curCol]!=true)
				curCol++;

			//color the vertix and add it to the Chrom list if it's not already there
			vertexCol[i]=curCol;
			if(Chrom.contains(curCol)==false)
				Chrom.add(curCol);

			//reset the array
			Arrays.fill(availCol, true);
		}

		//print test
		System.out.println("Vertix | Color");
		for (int k=0; k<n; k++)
			System.out.println((k+1) + " | " + vertexCol[k]);

		//chromatic number is size of the chrom array
		return Chrom.size();
	}
}

public class Greedy{

	public final static boolean DEBUG = true;
	public final static String COMMENT = "//";

	public static void main( String args[] ){

		if( args.length < 1 ){

			System.out.println("Error! No filename specified.");
			System.exit(0);
		}

		String inputfile = args[0];

		boolean seen[] = null;

		int n = -1;
		int m = -1;

		Edge e[] = null;

		try{

			FileReader fr = new FileReader(inputfile);
			BufferedReader br = new BufferedReader(fr);

			String record = new String();

			while ((record = br.readLine()) != null){

				if( record.startsWith("//") ) continue;
				break;
			}

			if( record.startsWith("VERTICES = ") ){

				n = Integer.parseInt( record.substring(11) );
				if(DEBUG) System.out.println(COMMENT + " Number of vertices = "+n);
			}

			seen = new boolean[n+1];

			record = br.readLine();

			if( record.startsWith("EDGES = ") ){

				m = Integer.parseInt( record.substring(8) );
				if(DEBUG) System.out.println(COMMENT + " Expected number of edges = "+m);
			}

			e = new Edge[m];

			for( int d=0; d<m; d++){

				record = br.readLine();
				String data[] = record.split(" ");
				if( data.length != 2 ){

					System.out.println("Error! Malformed edge line: "+record);
					System.exit(0);
				}

				e[d] = new Edge();

				e[d].u = Integer.parseInt(data[0]);
				e[d].v = Integer.parseInt(data[1]);

				seen[ e[d].u ] = true;
				seen[ e[d].v ] = true;
			}

			String surplus = br.readLine();
			if( surplus != null ){

				if( surplus.length() >= 2 ) if(DEBUG) System.out.println(COMMENT + " Warning: there appeared to be data in your file after the last edge: '"+surplus+"'");
			}

		}

		catch (IOException ex){

			System.out.println("Error! Problem reading file "+inputfile);
			System.exit(0);
		}

		for( int x=1; x<=n; x++ ){

			if( seen[x] == false ){

				if(DEBUG) System.out.println(COMMENT + " Warning: vertex "+x+" didn't appear in any edge : it will be considered a disconnected vertex on its own.");
			}
		}

		//! At this point e[0] will be the first edge, with e[0].u referring to one endpoint and e[0].v to the othe
		//! e[m-1] will be the last edge
		//!
		//! there will be n vertices in the graph, numbered 1 to n

		//! INSERT YOUR CODE HERE!

		Graph G = new Graph(n,m,e);

		//test print adjacency matrix
		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++)
				System.out.print("|"+G.adj[i][j]+"|");

			System.out.println();
		}

		//print chromatic number
		System.out.println("The chromatic number is: "+G.getChromatic());

	}
}
