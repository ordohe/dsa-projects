/*
 * Olivia Doherty
 * Project 3
 * 
 * This is the child class which is the social agent

 */

 
import java.awt.Graphics;
import java.awt.Color;

public class SocialAgent extends Agent {
   
    private boolean moved;
    private int radius;

    public SocialAgent(double x0, double y0, int radius){
        super( x0, y0 );
        this.radius = radius;
    }

    public void setRadius(int radius){
        this.radius = radius;
    }

    public int getRadius(){
        return radius;
    }

    public void draw(Graphics g){
        if(!moved) g.setColor(new Color(0, 0, 255));
        else g.setColor(new Color(125, 125, 255));
    
        g.fillOval((int) getX(), (int) getY(), 5, 5);
    }

    // public void updateState(Landscape scape){
    //     LinkedList<Agent> neighbors = scape.getNeighbors(getX(), getY(), radius);
    //     if (neighbors.size() < 4){
    //         while (getX() < 0 || getX() > scape.getWidth()){
    //             double dx = Math.random() * 20 - 10;
    //             setX(getX() + dx);
    //         }
    //         while (getY() < 0 || getY() > scape.getHeight()){
    //             double dy = Math.random() * 20 - 10;
    //             setY(getY() + dy);
    //         }
    //         this.moved = true;
    //     }
    //     else{
    //         this.moved = false;
    //     }
    // }

    public void updateState(Landscape scape) {
        double x0 = this.getX();
        double y0 = this.getY();
        double radius = this.getRadius();
    
        LinkedList<Agent> neighbors = scape.getNeighbors(x0, y0, radius);
    
        if (neighbors.size() < 4) {
            // move the agent randomly between -10 and 10
            double dx = (Math.random() * 20) - 10;
            double dy = (Math.random() * 20) - 10;
    
            // ensure the agent stays within the bounds of the landscape
            double newX = x0 + dx;
            double newY = y0 + dy;
            if (newX < 0) newX = 0;
            if (newX > scape.getWidth()) newX = scape.getWidth();
            if (newY < 0) newY = 0;
            if (newY > scape.getHeight()) newY = scape.getHeight();
    
            this.setX(newX);
            this.setY(newY);
            this.moved = true;
        } else {
            this.moved = false;
        }
    }

}