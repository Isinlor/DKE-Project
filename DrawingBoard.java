import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import java.util.*;
 
class DrawingBoard extends JPanel{
 
    public final int n = 10;
 
    public final int m = 4;
    
    public static final int r = 15;
                                                
    public ArrayList<Node> nodes;
   
    public DrawingBoard(){
 
        this.nodes = new ArrayList<Node>();   
        this.generateRandomCoordinates();
    }
   
    public boolean checkAvailability(Node N){
   
        boolean available = true ;
 
        for(Node n:nodes)
            if(N.checkDistance(n.getX(), n.getY()) == false)
                available = false ;
       
        return available;
    }
 
    public void generateRandomCoordinates(){
 
        Random rand = new Random();
        int i = 0;
        
        while (i < n) {
        	
        	int x = rand.nextInt(700)+100;
            int y = rand.nextInt(700)+100;
        	
            Node N = new Node(x,y);
            
            if(checkAvailability(N)){
             
            	this.nodes.add(N);
            	i++;
            }; 
           
        }
       
    }
    
    public void drawNodes(Graphics g){
       
        Graphics2D g2d = (Graphics2D) g;
           
        g2d.setColor(Color.BLUE);
       
        for (Node n : this.nodes) {
 
            g2d.fillOval(n.getX()-r, n.getY()-r, 2*r, 2*r);
        }        
    }
    
    public void drawEdges(Graphics g){
   
        Random rand = new Random();
        Graphics2D g2d = (Graphics2D) g;
        int i = 0;
        
        g2d.setColor(Color.BLACK);
 
        while (i < m) {
                
        	int a = rand.nextInt(n); 
        	int b = rand.nextInt(n);
 
            if (a != b) {
            	
            	g2d.drawLine(nodes.get(a).getX(), nodes.get(a).getY(), nodes.get(b).getX(), nodes.get(b).getY());
            	i++;
            }     
        }
        
    }
    
    public void paintComponent(Graphics g){
   
        super.paintComponent(g);
       
        drawNodes(g);
        drawEdges(g);
        
        System.out.println();
        for(Node n:nodes)
            n.printCoordinates();
    }
   
    class Node{
   
        private int x;
        private int y;
       
        public Node(int x, int y){
       
            this.x = x;
            this.y = y;
        }
 
        public int getX(){
            return x;
        }
       
        public int getY(){
            return y;
        }
 
        public void setX(int x){
            this.x = x;
        }
       
        public void setY(int y){
            this.y = y;
        }

        public boolean checkDistance(int x, int y){
       
            double d = Math.sqrt( Math.pow((this.x-x), 2) + Math.pow((this.y-y), 2));
           
            if( d > (2*r)+30 )
                return true;
           
            return false;
        }
       
        public void printCoordinates(){
       
            System.out.println("("+x+","+y+")");
        }
       
    }
   
}