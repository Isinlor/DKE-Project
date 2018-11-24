import javax.swing.*;
import java.awt.*;

public class Frame2Viewer{
    public static void main(String[] args){

        JFrame frame2 = new JFrame("Game Specification");
        frame2.setVisible(true);
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame2.setSize(700,300);
        frame2.setLocationRelativeTo(null);
        frame2.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        JPanel panel1 = new JPanel();

        panel.setLayout(new GridLayout(4, 2));


        // The components are added from left-to-right, top-to-bottom
        JLabel Label1 = new JLabel("Number of vertices for a random graph: ");       JTextField TextField1 = new JTextField("Number of vertices");
        JLabel Label2 = new JLabel("Number of edges for a random graph:  ");         JTextField TextField2 = new JTextField("Number of edges");
        //JSeparator Separator1 = new JSeparator();
        JLabel Label5 = new JLabel("Choose the file for a specific graph: ");        JTextField TextField3 = new JTextField("To do");

        panel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        TextField1.setSize(20,20);

        panel.add(Label1);      panel.add(TextField1);
        panel.add(Label2);      panel.add(TextField2);
        panel.add(Label5);      panel.add(TextField3);

        JButton Button1 = new JButton("Play");
        panel1.add(Button1);

        frame2.add(panel, BorderLayout.CENTER);
        frame2.add(panel1, BorderLayout.SOUTH);
    }
}
