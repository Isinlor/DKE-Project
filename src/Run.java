import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Run {

    final static public int WINDOW_WIDTH = 1500;
    final static public int WINDOW_HEIGHT = 1000;

    public static void main(String[] args) {

        // Frame and content pane explanation:
        // https://docs.oracle.com/javase/tutorial/uiswing/components/toplevel.html

        JFrame window = new JFrame();
        Container contentPane = window.getContentPane();

        // exit after clicking close button
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // define size of content pane; allows to avoid issues with accounting
        // for title bar (the thing with close button)
        contentPane.setPreferredSize(new Dimension(
            WINDOW_WIDTH, WINDOW_HEIGHT
        ));

        // Graph graph = Random.generate(4, 3);
        Graph graph = FileLoader.load("../graphs/custom/graph.txt");

        contentPane.add(new GraphScreen(graph));

        // Sets windows size so that everything inside have at least preferred size
        // https://stackoverflow.com/a/22982334/893222
        window.pack();

        // make display visible
        window.setVisible(true);

    }

}
