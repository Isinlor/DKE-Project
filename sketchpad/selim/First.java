import java.awt.*;
import javax.swing.*;

public class First {

    public static void main(String[] args) {


        JFrame frame = new JFrame();

        JButton button1 = new JButton("Game mode 1");
        JButton button2 = new JButton("Game mode 2");
        JButton button3 = new JButton("Game mode 3");
        JButton button4 = new JButton("SOUTH");

        JPanel panel = new JPanel();

        panel.setLayout(new BorderLayout());
        panel.add(button1, BorderLayout.WEST);
        panel.add(button2, BorderLayout.CENTER);
        panel.add(button3, BorderLayout.EAST);

        //ActionListener listener = new ClickListener();
        //button1.addActionListener(this);
        //button2.addActionListener(this);
        //button3.addActionListener(this);
        // ... action to be performed

        // private static final int FRAME_WIDTH = 800;
        //private static final int FRAME_HEIGHT = 400;

        frame.setSize(800, 400);
        frame.setTitle("Game mode");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }

}
