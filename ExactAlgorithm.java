import java.io.*;
import java.util.*;

class ColEdge
{
    int u;
    int v;
}

public class ExactAlgorithm
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
        
        getChromaticNumber(e, n);

    }

    // numberOfVertices or upper bound
    public static int getChromaticNumber(ColEdge[] listOfEdges, int numberOfVertices) {

        for(int colors = 1; colors <= numberOfVertices; colors++) {

            if(checkAllParticions(listOfEdges, numberOfVertices, colors)) {
                if(DEBUG) System.out.println("Chromatic number: " + colors);
                return colors;
            }

            if(DEBUG) System.out.println("Lower bound: " + colors);

        }

        return numberOfVertices;

    }

    // this function attemts to find all particions of vertices in given amount of colors
    // iterating over particions is difficult, so this algorithm uses counting as an
    // easy way to list all particions even tough some will be repeating
    // assume nodes A B C D
    // you can split them into two sets by assigning 0 or 1 to each node
    // e.g.: A=0 B=0 C=0 D=0
    //       A=0 B=0 C=0 D=1
    //       A=0 B=0 C=1 D=0
    //       A=0 B=0 C=1 D=1
    // The list above is basically counting from 0 to 3 in binary
    // If you want to split them into three sets you cen just count in base 3 system
    // Then for each possible particioning/coloring we check whether it is correct
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
        int maxPartitions = (int)Math.pow(colors, numberOfVertices);

        // if(DEBUG) System.out.println("Max particions: " + maxPartitions);

        // assignes a color to each vertex
        // NOTICE! vertices are indexed from 1, i.e. there is no vertex 0
        //         so we need to ignore index 0 in this array
        int[] listOfColorsPerVertex = new int[numberOfVertices + 1];
        for(int i = 0; i < (maxPartitions - 1); i++) {

            increment(listOfColorsPerVertex, colors);

            if(isGraphColoringCorrect(listOfEdges, listOfColorsPerVertex)) {
                return true;
            }

        }

        return false;

    }

    // counting in arbitrary base
    // array of integers represents digits of a numbers
    // first we increase least significant digit, and then we do carry
    // e.g. wehn counting from 9 to 10 in decimal system we give as input
    // number = {0, 9}, base = 10
    // the lest significant digit gets incremented i.e. number = {0, 10}
    // but decimal sistem has not digit 10, so we carry 1 i.e. number = {1, 0}
    public static void increment(int[] number, int base) {

        number[(number.length - 1)]++;
        for(int i = (number.length - 1); i >= 0; i--) {

            if(number[i] > (base - 1)) {
                number[i] = 0;
                number[i - 1]++;
            } else {
                return;
            }

        }

    }

    // check wether vertices on the same edge are having the same color assigned
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

}
