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

		//creating and fill the adjacency matrix
		adj = new int[n][n];

		for(int i=0;i<n;i++)
			for(int j=0;j<n;j++)
				adj[i][j]=0;

		for(int k=0;k<m;k++){

			adj[e[k].u-1][e[k].v-1]=1;
			adj[e[k].v-1][e[k].u-1]=1;
		}
	}

	void sortByDegree(int[] v, Edge[] e){

		//calculate degrees of all the vertices and store them in array
		int[] deg = new int[n];

		for(int l=0;l<m;l++){

			deg[e[l].u-1]++;
			deg[e[l].v-1]++;
		}

		//print degrees
		System.out.println("VERTIX  |  DEGREE");
		for(int p=0;p<n;p++)
		System.out.println((p+1) + "  |  " + deg[p]);

		//print array before sorting
		System.out.println(Arrays.toString(v));

		//sort the array in descending order by the vertix degree
		for(int i=0; i<v.length-1; i++){
			for(int j=i+1; j<v.length; j++){

				if(deg[i]<deg[j]){
					int tmp=v[i];
					v[i]=v[j];
					v[j]=tmp;
				}
			}
		}

		//print array after sorting
		System.out.println(Arrays.toString(v));
	}

	int getChromatic(int[] v, Edge[] e){

		//array of used colors without duplicates
		ArrayList<Integer> Chrom = new ArrayList<Integer>();

		//sort the array
		sortByDegree(v,e);

		//create array of where we'll store the vertix colors and initialize it to one
		int[] vertexCol = new int[n];
		Arrays.fill(vertexCol,-1);

		//array which keeps track of which colors are available, intialize all of them to true
		boolean availCol[] = new boolean[n];
		Arrays.fill(availCol,true);

		//set the first vertix in array
		vertexCol[v[0]-1]=0;

		//go through all vertices
		for(int i=1; i<n; i++){

			//set the neighbours' colors to not available
			for(int j=0; j<n; j++)
			if((adj[v[i]-1][v[j]-1]==1)&&(vertexCol[v[j]-1]!=-1))
				availCol[vertexCol[v[j]-1]] = false;

			//find the first color which is available
			int curCol = 0;
			while(availCol[curCol]!=true)
				curCol++;

			//color the vertix with it and add it to array of colors used if it's not already there
			vertexCol[v[i]-1]=curCol;
			if(Chrom.contains(curCol)==false)
				Chrom.add(curCol);

			//reset the availability array for next iteration
			Arrays.fill(availCol, true);
		}

		//print colors and vertices
		System.out.println("Vertix | Color");
		for (int k=0; k<n; k++)
			System.out.println((k+1) + " | " + vertexCol[v[k]-1]);

		//Chrom is a set of all colors used but without duplicates so its size is a chromatic number
		return Chrom.size();
	}
}

public class WelshPowell{

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
		int[] v=new int[n];

		//array of vertices in the graph
		for(int i=0;i<n;i++)
		v[i]=i+1;

		//print adjacency matrix
		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++)
			System.out.print("|"+G.adj[i][j]+"|");

			System.out.println();
		}

		//print sort

		G.sortByDegree(v,e);

		System.out.println("The chromatic number is approximately: "+G.getChromatic(v,e));
	}

}
