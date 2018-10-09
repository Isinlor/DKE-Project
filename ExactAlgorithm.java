import java.io.*;
import java.util.*;

class ColEdge
{
    int u;
    int v;
}

public class ReadGraph
{

    public final static boolean DEBUG = true;

    public final static String COMMENT = "//";

    public static void main( String args[] )
    {
        if( args.length < 1 )
        {
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
        ColEdge e[] = null;

        try 	{
            FileReader fr = new FileReader(inputfile);
            BufferedReader br = new BufferedReader(fr);

            String record = new String();

            //! THe first few lines of the file are allowed to be comments, staring with a // symbol.
            //! These comments are only allowed at the top of the file.

            //! -----------------------------------------
            while ((record = br.readLine()) != null)
            {
                if( record.startsWith("//") ) continue;
                break; // Saw a line that did not start with a comment -- time to start reading the data in!
            }

            if( record.startsWith("VERTICES = ") )
            {
                n = Integer.parseInt( record.substring(11) );
                if(DEBUG) System.out.println(COMMENT + " Number of vertices = "+n);
            }

            seen = new boolean[n+1];

            record = br.readLine();

            if( record.startsWith("EDGES = ") )
            {
                m = Integer.parseInt( record.substring(8) );
                if(DEBUG) System.out.println(COMMENT + " Expected number of edges = "+m);
            }

            e = new ColEdge[m];

            for( int d=0; d<m; d++)
            {
                if(DEBUG) System.out.println(COMMENT + " Reading edge "+(d+1));
                record = br.readLine();
                String data[] = record.split(" ");
                if( data.length != 2 )
                {
                    System.out.println("Error! Malformed edge line: "+record);
                    System.exit(0);
                }
                e[d] = new ColEdge();

                e[d].u = Integer.parseInt(data[0]);
                e[d].v = Integer.parseInt(data[1]);

                seen[ e[d].u ] = true;
                seen[ e[d].v ] = true;

                if(DEBUG) System.out.println(COMMENT + " Edge: "+ e[d].u +" "+e[d].v);

            }

            String surplus = br.readLine();
            if( surplus != null )
            {
                if( surplus.length() >= 2 ) if(DEBUG) System.out.println(COMMENT + " Warning: there appeared to be data in your file after the last edge: '"+surplus+"'");
            }

        }
        catch (IOException ex)
        {
            // catch possible io errors from readLine()
            System.out.println("Error! Problem reading file "+inputfile);
            System.exit(0);
        }

        for( int x=1; x<=n; x++ )
        {
            if( seen[x] == false )
            {
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


        int[] listOfColorsPerVertexA = {0, 1, 2, 3, 3, 1, 2}; // correct
        if(isGraphColoringCorrect(e, listOfColorsPerVertexA)) {
            System.out.println("Correct colors A!");
        }

        int[] listOfColorsPerVertexB = {0, 2, 2, 3, 3, 1, 2}; // wrong
        if(isGraphColoringCorrect(e, listOfColorsPerVertexB)) {
            System.out.println("Correct colors B!");
        }

    }

    public static boolean isGraphColoringCorrect(ColEdge[] listOfEdges, int[] listOfColorsPerVertex) {

        for(int i = 0; i < listOfEdges.length; i++) {

            ColEdge edge = listOfEdges[i];

            // check wether nodes on the same edge are having the same color assigned
            if(listOfColorsPerVertex[edge.u] == listOfColorsPerVertex[edge.v]) {
                return false;
            }

        }

        return true;

    }

    // numberOfVertices or upper bound
    public static int getChromaticNumber(ColEdge[] listOfEdges, int numberOfVertices) {

        for(int colors = 1; colors < numberOfVertices; colors++) {

            if(checkAllParticions(listOfEdges, numberOfVertices, colors)) {
                return colors;
            }

        }

        return numberOfVertices;

    }

    public static boolean checkAllParticions(ColEdge[] listOfEdges, int numberOfVertices, int colors) {

        /**
        * I can't compare different algorithms for doing this but one that comes
        * to mind immediately which is probably not bad is simply to count to
        * b^n in different bases "b" where the base depends on how many
        * partitions you want.
        *
        * So, for instance, if you want partitions of 10 things into 2 different
        * sets, enumerate all 10-digit base 2 numbers: the 0s in a given number
        * correspond to one partition and the 1s correspond to the other.
        * Similarly, for partitions of 10 things into 3 different sets,
        * enumerate all 10-digit base 3 numbers.
        */
        int maxPartitions = Math.pow(numberOfVertices, colors);

        int[] listOfColorsPerVertex;
        for(int i = 0; i < maxPartitions; i++) {

            nextlistOfColorsPerVertex(listOfColorsPerVertex, colors);

            if(isGraphColoringCorrect(listOfEdges, listOfColorsPerVertex)) {
                return true;
            }

        }

        return false;

    }

    public static void nextlistOfColorsPerVertex(int[] listOfColorsPerVertex, colors) {

        // TODO - this whole loop may be rewriten ...
        for(int i = listOfColorsPerVertex.length - 1; i <= 1; i--) {

            if(listOfColorsPerVertex[i] < colors - 1) {

                listOfColorsPerVertex[i]++;
                return;

            } else {

                // TODO

            }


        }

    }

}
