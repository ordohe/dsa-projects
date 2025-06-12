/*
 * Olivia Doherty
 * Project 3
 * 
 * This is the landscape class which creates the visual for the agents
 */

 
import java.awt.Graphics;


public class Landscape {
    
    private int width;
    private int height;
    private LinkedList<Agent> agents;

    public Landscape(int w, int h){
        this.width = w;
        this.height = h;
        this.agents = new LinkedList<Agent>();
    }

    public int getHeight(){
        return this.height;
    }

    public int getWidth(){
        return this.width;
    }

    public void addAgent( Agent a ){
        agents.add(a);
    }

    public String toString(){
        return "Landscape with " + agents.size() + "agents.";
    }

    public LinkedList<Agent> getNeighbors(double x0, double y0, double radius){
        LinkedList<Agent> neighbors = new LinkedList<>();
        for (Agent agent : agents){
            double distance = Math.sqrt(Math.pow(x0 - agent.getX(), 2) + Math.pow(y0 - agent.getY(), 2));
            if (distance <= radius) {
                neighbors.add(agent);
        }
        }
        return neighbors;
    }

    public void draw(Graphics g){
        for (Agent agent : this.agents){
            agent.draw(g);
        }
    }

    public void updateAgents(){
        for (Agent agent : agents){
            agent.updateState(this);
        }
    }



}