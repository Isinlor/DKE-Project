import java.io.*;
import java.util.*;
	
	class Graph{
		
		int numberOfVertices; /** number of all vertices */
		int numberOfEdges; /** number of all edges */
		int[][] adj; /** adjacency matrix */
		int coloredVertices; /** number of colored vertices */
		ArrayList<Node> nodes = new ArrayList<Node>(); /** list of all vertices in a graph */
		ArrayList<Integer> colors = new ArrayList<Integer>(); /** array of colors */

		/**
		 * Constructor 
		 */
		Graph(int numberOfVertices, int numberOfEdges, Edge[] e){
			
			this.numberOfVertices = numberOfVertices;
			this.numberOfEdges = numberOfEdges;
				
			adj = new int[numberOfVertices+1][numberOfVertices+1];
				
			for(int i=0;i<=numberOfVertices;i++)
				for(int j=0;j<=numberOfVertices;j++)
						adj[i][j]=0;
				
			for(int k=0;k<numberOfEdges;k++){
						
				adj[e[k].u][e[k].v] = 1;
				adj[e[k].v][e[k].u] = 1;
			}
			
			coloredVertices = 0;
			
			for(int i=0; i<=numberOfVertices; i++)
				nodes.add(new Node(i));	
			
			colors.add(1);
		}
		
		/**
		 * Checks whether there's an edge between two nodes.
		 */
		public boolean isAdjacent(Node a, Node b){
		
			if( adj[a.getLabel()][b.getLabel()] == 1 )
				return true;
			
			return false;
		}
		
		/**
		 * Returns neighbours of node x.
		 */
		public ArrayList<Node> getNeighbours(Node x){ 
			
			ArrayList<Node> neighbours = new ArrayList<Node>();
			
			for(Node n : nodes)
				if(isAdjacent(n,x))
					neighbours.add(n);
					
			return neighbours; 
		}
		
		/**
		 * Checks if we can color vertex x with a specific color.
		 */
		 public boolean isAvailable(Node x, int color){
		 	 
		 	 ArrayList<Node> neighbours = getNeighbours(x);
		 	 
		 	 for(Node ngh: neighbours)
		 	 	 if(ngh.getColor()==color)
		 	 	 	return false;
		 	 
		 	 return true;
		 }
		
		/**
		 * Sets degrees of all nodes and then sorts them by the degree
		 */
		 public void setDegree(){
		 
		 	 for(Node n : nodes)
		 	 	 n.setDegree(getNeighbours(n).size());
		 	
		 	 Collections.sort(nodes, Collections.reverseOrder());
		 }
		 
		 /**
		  * Sets saturation degree of all the nodes.
		  */
		 public void setSaturationDegree(){
		 	 
		 	 ArrayList<Node> neighbours = new ArrayList<Node>();
		 	 HashSet<Integer> neighbourColors = new HashSet<Integer>(); 
		 	 
		 	 for(Node n: nodes){
		 	 
		 	 	 neighbours = getNeighbours(n);
		 	 	 neighbourColors.clear();
		 	 	 
		 	 	 for(Node ngh : neighbours)
		 	 	 	 if(ngh.getColor()!=-1)
		 	 	 	 	neighbourColors.add(ngh.getColor());
		 	
		 	 	 n.setSatDegree(neighbourColors.size());
		 	 }
		 }
		 
		 /** 
		  *Returns the node with the max saturation degree. 
		  */
		 public Node maxSaturationDegree(){
		 
		 	 Node max = nodes.get(numberOfVertices);
		 	 
		 	 for(Node n: nodes)
		 	 	 if((max.compareTo(n) == -1) && (n.getColor() == -1) )
		 	 	 	max = n;
		 	 	
		 	 return max;
		 }
		 
		 /**
		  * Graph coloring with DSATUR algorithm
		  */
		 public int DSATUR(){
		 
		 	 nodes.get(0).setColor(1);
		 	 coloredVertices++;
		 	 setSaturationDegree();
		 	 
		 	 while(coloredVertices < numberOfVertices){
		 	 
		 	 	 Node max = maxSaturationDegree();
		 	 	 boolean colored = false;
		 	 	 
		 	 	 for(int i=0; i<colors.size(); i++)
		 	 	 	 if(isAvailable(max, colors.get(i))){
		 	 	 	 
		 	 	 	 	 max.setColor(colors.get(i));
		 	 	 	 	 colored = true;
		 	 	 	 	 break;
		 	 	 	 }
		 	 	 
		 	 	 if(colored == false){
		 	 	 	 
		 	 	 	 int newColor = colors.size()+1;
		 	 	 	 
		 	 	 	 colors.add(newColor);
		 	 	 	 max.setColor(newColor);
		 	 	 }	
		 	 	 
		 	 	 coloredVertices++;
		 	 	 setSaturationDegree();
		 	 }
		 	 
		 	 return colors.size();
		 }
		 
		 /**
		  * Sets up the degrees of vertices, initial saturation degrees and executes DSATUR algorithm.
		  */
		 public void executeDSATUR(){
		 
		 	setDegree();
			setSaturationDegree();

			System.out.println("Chromatic number of the given graph is: " + DSATUR());
		 }
		 
		 /**
		  * Print the nodes
		  */
		 public void printAllNodes(){
		 
		 	 for(Node n : nodes)
		 	 	 System.out.println("Vertex	" + n.getLabel() + "	Degree: " + n.getDegree() + "	Saturation degree: " + n.getSatDegree() + "   Color: " + n.getColor());
		 	 
		 	 System.out.println();
		 }
        			
	}
	
public class DSATUR{
		
	public final static boolean DEBUG = true;
		
	public final static String COMMENT = "//";
		
	public static void main( String args[] ){
		
		if( args.length < 1 ){
			
			System.out.println("Error! No filename specified.");
			System.exit(0);
		}
		
		String inputfile = args[0];
			
		boolean seen[] = null;
			
		//! n is the number of vertices in the graph
		int n = -1;
			
		//! m is the number of edges in the graph
		int m = -1;
			
		//! e will contain the edges of the graph
		Edge e[] = null;
			
		try { 
		
			FileReader fr = new FileReader(inputfile);
			BufferedReader br = new BufferedReader(fr);

			String record = new String();
					
			//! THe first few lines of the file are allowed to be comments, staring with a // symbol.
			//! These comments are only allowed at the top of the file.
					
			//! -----------------------------------------
			while ((record = br.readLine()) != null){
				
				if( record.startsWith("//") ) continue;
				break; // Saw a line that did not start with a comment -- time to start reading the data in!
			}
	
			if( record.startsWith("VERTICES = ") ){
						
				n = Integer.parseInt( record.substring(11) );					
			}

			seen = new boolean[n+1];	
						
			record = br.readLine();
					
			if( record.startsWith("EDGES = ") ){
				
				m = Integer.parseInt( record.substring(8) );					
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
				
		        // catch possible io errors from readLine()
			    System.out.println("Error! Problem reading file "+inputfile);
				System.exit(0);
			}

			for( int x=1; x<=n; x++ ){
				
				if( seen[x] == false ){
					
					if(DEBUG) System.out.println(COMMENT + " Warning: vertex "+x+" didn't appear in any edge : it will be considered a disconnected vertex on its own.");
				}
			}
			//! At this point e[0] will be the first edge, with e[0].u referring to one endpoint and e[0].v to the other
			//! e[1] will be the second edge...
			//! (and so on)
			//! e[m-1] will be the last edge
			//! 
			//! there will be n vertices in the graph, numbered 1 to n

			//! INSERT YOUR CODE HERE!
			Graph G = new Graph(n,m,e);
        	
			G.executeDSATUR();
		}
}

	
	


    


    
