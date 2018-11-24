import javax.swing.*;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class Frame1Viewer {
    public static void main(String[] args) {

        JFrame frame = new JFrame();

        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setTitle("Game mode");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        JButton button1 = new JButton("Game mode 1");
        JButton button2 = new JButton("Game mode 2");
        JButton button3 = new JButton("Game mode 3");

        frame.setLayout(null);

        button1.setSize(FRAME_WIDTH/3,FRAME_HEIGHT-28);
        button1.setLocation(0,4);
        frame.add(button1);
        ActionListener listener1 = new ClickListenerFrame1();
        button1.addActionListener(listener1);

        button2.setSize(FRAME_WIDTH/3,FRAME_HEIGHT-28);
        button2.setLocation(300,4);
        frame.add(button2);
        ActionListener listener2 = new ClickListenerFrame1();
        button2.addActionListener(listener2);

        button3.setSize(FRAME_WIDTH/3,FRAME_HEIGHT-28);
        button3.setLocation(600,4);
        frame.add(button3);
        ActionListener listener3 = new ClickListenerFrame1();
        button3.addActionListener(listener3);
    }

    private static final int FRAME_WIDTH = 900;
    private static final int FRAME_HEIGHT = 300;
}
