import java.awt.*;
import javax.swing.*;

public class Second {

    public static void main(String[] args){

            JFrame frame = new JFrame("Game Specification");
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(300,400);
            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(4, 2));


            // The components are added from left-to-right, top-to-bottom
            JLabel Label1 = new JLabel("Number of vertices for a random graph: ");       JTextField TextField1 = new JTextField("Number of vertices");
            JLabel Label2 = new JLabel("Number of edges for a random graph:  ");         JTextField TextField2 = new JTextField("Number of edges");
            //JSeparator Separator1 = new JSeparator();
            JLabel Label5 = new JLabel("Choose the file for a specific graph: ");        JTextField TextField3 = new JTextField("To do");
            JButton Button1 = new JButton("Play");

            panel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);


            panel.add(Label1);      panel.add(TextField1);
            panel.add(Label2);      panel.add(TextField2);

            panel.add(Label5);      panel.add(TextField3);
            panel.add(Button1);
            frame.add(panel);
    }

}
