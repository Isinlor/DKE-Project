import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.plaf.metal.MetalLookAndFeel;

/**
 * This is the main class that allows to run the program.
 *
 * It allows to switch between graph specification and display graph screens.
 *
 * Author: Tomek
 */
public class Run {

    static JFrame window;
    static Container contentPane;

    public static void main(String[] args) throws Exception {

        setLookAndFeel();

        // Frame and content pane explanation:
        // https://docs.oracle.com/javase/tutorial/uiswing/components/toplevel.html

        window = new JFrame();
        contentPane = window.getContentPane();

        // exit after clicking close button
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        displaySpecificationScreen();

        // make display visible
        window.setVisible(true);

    }

    public static void displaySpecificationScreen() {
        GameTimer.reset();
        contentPane.removeAll();
        contentPane.add(new SpecificationScreen());
        window.pack();
    }

    public static void displayGraphScreen(GameState state) {
        GameTimer.reset();
        contentPane.removeAll();
        contentPane.add(new GraphScreen(state));
        window.pack();
    }

    public static void setLookAndFeel() throws Exception {
        UIManager.setLookAndFeel(MetalLookAndFeel.class.getCanonicalName());
    }

}
