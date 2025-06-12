/**
* File: Cell.Java
* Author" Olivia Doherty
* Date: 9/23/2023
*
* This file represents one location on a regular grid. 
The Cell class stores whether or not it is alive and implement methods accordingly.
*
*/


import java.util.ArrayList;

public class Cell {

    private boolean alive;

    // Default constructor, initializes the Cell as dead
    public Cell() {
        alive = false;
    }

    // Constructor with a parameter to set the initial state
    public Cell(boolean alive) {
        this.alive = alive;
    }

    // Getter method to check if the Cell is alive
    public boolean getAlive() {
        return alive;
    }

    // Setter method to change the state of the Cell
    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    // Override toString method to represent alive state as "0" and dead state as "1"
    @Override
    public String toString() {
        return alive ? "1" : "0"; // "1" for alive, "0" for dead
    }

    // Update the cell's state based on its neighbors
    public void updateState(ArrayList<Cell> neighbors) {
        int liveNeighborCount = 0;

        // Count the number of live neighbors
        for (Cell neighbor : neighbors) {
            if (neighbor.getAlive()) {
                liveNeighborCount++;
            }
        }

        // Apply the rules to update the cell's state
        if (alive) {
            // If a live cell has 2 or 3 live neighbors, it remains alive
            if (liveNeighborCount == 2 || liveNeighborCount == 3) {
                alive = true;
            } else {
                alive = false;
            }
        } else {
            // If a dead cell has exactly 3 live neighbors, it becomes alive
            if (liveNeighborCount == 3) {
                alive = true;
            } else {
                alive = false;
            }
        }
    }
}     