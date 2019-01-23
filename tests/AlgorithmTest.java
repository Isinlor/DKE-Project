import java.io.File;
import java.io.FileFilter;
import java.util.*;
import java.util.concurrent.*;

public class AlgorithmTest {

    static Graph graph;

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {

        testDirectory(args[0]);

        System.exit(1);

    }

    public static void testDirectory(String directory) throws InterruptedException, ExecutionException, TimeoutException {

        Graph.PRINT_BOUNDS = false;

        File[] graphFiles = new File(directory).listFiles(new FileFilter() {
            public boolean accept(File file) {
                return file.getName().endsWith(".col") || file.getName().endsWith(".txt");
            }
        });

        if(graphFiles == null) {
            System.out.println("Empty directory " + directory);
            return;
        }

        Arrays.sort(graphFiles);

        int longestNameLength = 9;
        for (File graphFile: graphFiles) {
            longestNameLength = Math.max(longestNameLength, graphFile.getName().length());
        }

        System.out.println(padRight("File name", longestNameLength) + "\tV\t\tE\t\tD\t\tL\t\tU\t\tC\tT");

        for(File graphFile: graphFiles) {

            graph = FileLoader.load(graphFile.getAbsolutePath());

            System.gc();
            System.out.print(padRight(graphFile.getName(), longestNameLength));
            System.out.print("\t" + padRight(format(graph.getNumberOfVertices()), 5));
            System.out.print("\t" + padRight(format(graph.getNumberOfEdges()), 5));
            System.out.print("\t" + padRight(graph.getDensity(), 5));

            long time = System.currentTimeMillis();

            ExecutorService executorService = Executors.newSingleThreadExecutor();
            Future task = executorService.submit(() -> {

                String[] args = new String[1];
                args[0] = graphFile.getAbsolutePath();
                Algorithm.main(args);

            });

            int timeout = 120;

            try {

                task.get(timeout, TimeUnit.SECONDS);

            } catch (TimeoutException|RuntimeException e) {
                // no op
            } finally {

                executorService.shutdownNow();
                executorService.awaitTermination(10, TimeUnit.SECONDS);

                graph = Algorithm.graph;

                if(graph == null) {
                    System.out.println();
                    continue;
                }

                System.out.print("\t" + padRight(format(graph.getLowerBound()), 5));
                System.out.print("\t" + padRight(format(graph.getUpperBound()), 5));
                System.out.print("\t" + (graph.hasChromaticNumber() ? graph.getUpperBound() : "-"));
                long runningTime = System.currentTimeMillis() - time;
                double runningTimeInSeconds = round(runningTime / 1000.0);
                runningTimeInSeconds = runningTimeInSeconds > timeout ? (double)timeout : runningTimeInSeconds;
                System.out.print("\t" + runningTimeInSeconds);
                System.out.println();

            }

        }

    }

    public static double round(double number) {
        return Math.round(number * 10.0) / 10.0;
    }

    /**
     * https://stackoverflow.com/questions/388461/how-can-i-pad-a-string-in-java/391978#391978
     */
    public static String padRight(Object s, int n) {
        return String.format("%1$-" + n + "s", s);
    }

    /**
     * Source: https://stackoverflow.com/questions/4753251/how-to-go-about-formatting-1200-to-1-2k-in-java
     */
    private static final NavigableMap<Long, String> suffixes = new TreeMap<>();
    static {
        suffixes.put(1_000L, "k");
        suffixes.put(1_000_000L, "M");
        suffixes.put(1_000_000_000L, "G");
        suffixes.put(1_000_000_000_000L, "T");
        suffixes.put(1_000_000_000_000_000L, "P");
        suffixes.put(1_000_000_000_000_000_000L, "E");
    }

    public static String format(long value) {
        //Long.MIN_VALUE == -Long.MIN_VALUE so we need an adjustment here
        if (value == Long.MIN_VALUE) return format(Long.MIN_VALUE + 1);
        if (value < 0) return "-" + format(-value);
        if (value < 1000) return Long.toString(value); //deal with easy case

        Map.Entry<Long, String> e = suffixes.floorEntry(value);
        Long divideBy = e.getKey();
        String suffix = e.getValue();

        long truncated = value / (divideBy / 10); //the number part of the output times 10
        boolean hasDecimal = truncated < 100 && (truncated / 10d) != (truncated / 10);
        return hasDecimal ? (truncated / 10d) + suffix : (truncated / 10) + suffix;
    }

}