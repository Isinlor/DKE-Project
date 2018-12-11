import java.io.*;
import java.util.*;
	
	class Node{
	
		private int label;
		
		public Node(int label){
		
			this.label = label;
		}
		
		public int getLabel(){
		
			return this.label;
		}
		
		public void setLabel(int label){
		
			this.label = label;
		}	
		
	}
	
	/**
	 * Finding a maximal clique and a maximal independent set problem are complementary: 
	   a clique in graph is an independent set in the complement graph of G and vice versa.
	   This program lists all of the maximal independent sets of a graph by taking the complement of 
	   a graph and using Bron Kerbosch algorithm to list all maximal cliques in it.
	 */
	
	class Graph{
		
		int numberOfVertices; //number of vertices
		int m; //number of edges
		int[][] adj; //adjacency matrix
		ArrayList<Node> N = new ArrayList<Node>();
		ArrayList<Set<Node>> maxCliqueList = new ArrayList<Set<Node>>();
		
		/**
		 * Constructor
		 */
		Graph(int numberOfVertices, int m, Edge[] e){
			
			this.numberOfVertices = numberOfVertices;
			this.m = m;
				
			adj = new int[numberOfVertices][numberOfVertices];
				
			for(int i=0;i<numberOfVertices;i++)
				for(int j=0;j<numberOfVertices;j++){
				
					if(i==j)
						adj[i][j]=0;
					else 
						adj[i][j] = 1;
				}
				
			for(int k=0;k<m;k++){
						
				adj[e[k].u-1][e[k].v-1]=0;
				adj[e[k].v-1][e[k].u-1]=0;
			}
			
			for(int i=0; i<numberOfVertices; i++){
			
				Node n = new Node(i+1);
				N.add(n);
			}
		}
		
		/**
		 * Check whether there's an edge between two nodes
		 */
		public boolean isAdjacent(Node a, Node b){
		
			if( adj[a.getLabel()-1][b.getLabel()-1] == 1 )
				return true;
			
			return false;
		}
		
		/**
		 * Returns neighbours of node x 
		 */
		public ArrayList<Node> getNeighbours(Node x){ 
			
			ArrayList<Node> neighbours = new ArrayList<Node>();
			
			for(Node n : N )
				if(isAdjacent(n,x))
					neighbours.add(n);
					
			return neighbours; 
		}
    
		/**
		 * Intersection of two sets
		 */
		public ArrayList<Node> Intersection(ArrayList<Node> S1, ArrayList<Node> S2){ 
			
			ArrayList<Node> S3 = new ArrayList<Node>(S1); 
			S3.retainAll(S2); 
			return S3; 
		}
		 
		/**
		 * Bron Kerbosch algorithm - version without a pivot 
		 */
		public void BronKerbosch(ArrayList<Node> R, ArrayList<Node> P, ArrayList<Node> X){ 
			
			if ((P.size()==0) && (X.size()==0)){
				
				processMaxClique(R); 
				return; 
			} 
			
			ArrayList<Node> P1 = new ArrayList<Node>(P);
        
			for(Node n : P){
				
				R.add(n);
				BronKerbosch(R, Intersection(P1, getNeighbours(n)), Intersection(X, getNeighbours(n)));
				R.remove(n);
				P1.remove(n);
				X.add(n);
			}
        } 

        /**
         *Proccess a maximal clique and add it to the list of all maximal cliques
         */
        public void processMaxClique(ArrayList<Node> R){ 
        	
        	Set<Node> maxClique = new HashSet<Node>();
        	
        	for(Node n : R)
        		maxClique.add(n);
        		 
        		maxCliqueList.add(maxClique);
        }
        
        /**
         * Print a list of all maximal cliques in a set 
         */
        public void printMaxCliques(){
        
        	System.out.println("  ------Maximal Independent Clique--------"); 
        	
        	for(Set<Node> maxClique: maxCliqueList){
        	
        		System.out.print("[");
        		
        		for(Node n: maxClique)
        			System.out.print(" " + n.getLabel() + " ");
        		
        		System.out.println("]");
        	
        	}
        	
        }
        
        public int getLowerBound(){
        
        	int lowerBound = 1;
        	
        	for(Set<Node> maxClique: maxCliqueList)
        		if(maxClique.size()>lowerBound)
        			lowerBound = maxClique.size();
        		
        	return lowerBound;
        }
        
        public void executeBronKerbosch(){
        
        	ArrayList<Node> X = new ArrayList<Node>(); 
        	ArrayList<Node> R = new ArrayList<Node>(); 
        	ArrayList<Node> P = new ArrayList<Node>(N);
        	
        	BronKerbosch(R, P, X); 
        	printMaxCliques();
        }
        			
	}
	
public class BronKerbosch{
		
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
        	
        	G.executeBronKerbosch(); 
		}
}

	
	


    


    