/*
 * Olivia Doherty
 * Project 3
 * 
 * This is the parent class of all the other agent classes
 */

 
import java.awt.Graphics;
// import java.awt.Color;

public abstract class Agent {
    // initalizing our x and y
    private double x;
    private double y;
    
    public Agent(double x0, double y0){
        //constructor setting x and y
        this.x = x0;
        this.y = y0;
    }

    public double getX(){
        // get x function
        return x;
    }

    public double getY(){
        // get y function
        return y;
    }

    public void setX(double newX){
        // set x function
        this.x = newX;
    }

    public void setY(double newY){
        // set y function
        this.y = newY;
    }

    public String toString(){
        // to string
        return "(" + x + ", " + y + ")";
    }

    // looks at the individual child classes for thse functions
    public abstract void updateState( Landscape scape );

    public abstract void draw(Graphics g);



}