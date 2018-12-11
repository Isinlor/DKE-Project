import java.io.*;
import java.util.*;

public class FileLoader {

    public final static boolean DEBUG = false;

    public final static String COMMENT = "//";

    public static Graph load(String pathToFileWithGraph) {

        try {

            boolean seen[] = null;

            //! n is the number of vertices in the graph
            int n = -1;

            //! m is the number of edges in the graph
            int m = -1;

            //! e will contain the edges of the graph
            Edge e[] = null;


            FileReader fr = new FileReader(pathToFileWithGraph);
            BufferedReader br = new BufferedReader(fr);

            String record = new String();

            //! THe first few lines of the file are allowed to be comments, staring with a // symbol.
            //! These comments are only allowed at the top of the file.

            //! -----------------------------------------
            while ((record = br.readLine()) != null) {
                if (record.startsWith("//")) continue;
                break; // Saw a line that did not start with a comment -- time to start reading the data in!
            }

            if (record.startsWith("VERTICES = ")) {
                n = Integer.parseInt(record.substring(11));
                if (DEBUG) System.out.println(COMMENT + " Number of vertices = " + n);
            }

            seen = new boolean[n + 1];

            record = br.readLine();

            if (record.startsWith("EDGES = ")) {
                m = Integer.parseInt(record.substring(8));
                if (DEBUG) System.out.println(COMMENT + " Expected number of edges = " + m);
            }

            e = new Edge[m];

            for (int d = 0; d < m; d++) {

                if (DEBUG) System.out.println(COMMENT + " Reading edge " + (d + 1));

                record = br.readLine();
                String data[] = record.split(" ");

                if (data.length != 2) {
                    System.out.println("Error! Malformed edge line: " + record);
                    System.exit(0);
                }

                e[d] = new Edge(
                        Integer.parseInt(data[0]),
                        Integer.parseInt(data[1])
                );
                seen[e[d].from] = true;
                seen[e[d].to] = true;

                if (DEBUG) System.out.println(COMMENT + " Edge: " + e[d].from + " " + e[d].to);

            }

            String surplus = br.readLine();
            if (surplus != null) {
                if (surplus.length() >= 2) if (DEBUG)
                    System.out.println(COMMENT + " Warning: there appeared to be data in your file after the last edge: '" + surplus + "'");
            }

            for (int x = 1; x <= n; x++) {
                if (seen[x] == false) {
                    if (DEBUG)
                        System.out.println(COMMENT + " Warning: vertex " + x + " didn't appear in any edge : it will be considered a disconnected vertex on its own.");
                }
            }

            return new Graph(e, n);

        } catch (WrongGraphSpecificationException ex) {
            throw new WrongGraphSpecificationException(
                    "Problem reading file " + pathToFileWithGraph + "\n" + ex.getMessage(), ex
            );
        } catch (Exception ex) {
            throw new WrongGraphSpecificationException("Problem reading file " + pathToFileWithGraph, ex);
        }

    }

}
