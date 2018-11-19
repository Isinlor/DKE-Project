import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import java.util.*;
 
//I basically copy pasted this part from the internet because i have no fucking idea what i'm doing
//since we still haven't done literally anything connected to the graphics in class ;(
public class BasicShapesEx extends JFrame {
 
    public BasicShapesEx(){
       
        initUI();
    }
   
    private void initUI(){
   
        add(new DrawingBoard());
       
        setTitle("Random Graph");
        setSize(960, 960);      
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
 
    public static void main(String[] args) {
 
        EventQueue.invokeLater(new Runnable() {
       
            @Override
            public void run() {
                BasicShapesEx ex = new BasicShapesEx();
                ex.setVisible(true);
            }
        });
    }
}