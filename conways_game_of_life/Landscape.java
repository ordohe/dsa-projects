/**
* File: Landscape.Java
* Author" Olivia Doherty
* Date: 9/23/2023
*
* The Landscape class represents a 2D grid of Cell objects and provides methods
* for managing and interacting with the grid.
*
*/

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Landscape {
    private Cell[][] grid;
    private int numRows;
    private int numCols;

    public Landscape(int rows, int cols) {
        numRows = rows;
        numCols = cols;
        grid = new Cell[numRows][numCols];
        initializeGrid();
    }

    public Landscape(int rows, int cols, double chance) {
        numRows = rows;
        numCols = cols;
        grid = new Cell[numRows][numCols];
        initializeGridWithChance(chance);
    }
    public void draw(Graphics g, int gridScale) {
        // Implement the logic to draw the cells based on their state.
        // You'll need to iterate through the grid of cells and draw them.
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                Cell cell = grid[row][col];
                int x = col * gridScale; // X-coordinate for drawing
                int y = row * gridScale; // Y-coordinate for drawing

                // Determine the color based on whether the cell is alive or dead
                if (cell.getAlive()) {
                    g.setColor(Color.BLACK); // Set color for live cells
                } else {
                    g.setColor(Color.WHITE); // Set color for dead cells
                }

                // Draw a rectangle (cell) at the specified position
                g.fillRect(x, y, gridScale, gridScale);
            }
        }
    }

    private void initializeGridWithChance(double chance) {
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                if (Math.random() < chance) {
                    grid[row][col] = new Cell(true);
                } else {
                    grid[row][col] = new Cell(false);
                }
            }
        }
    }

    private void initializeGrid() {
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                grid[row][col] = new Cell(false);
            }
        }
    }

    public void reset() {
        initializeGridWithChance(0.5); // Change the chance as needed
    }

    public int getRows() {
        return numRows;
    }

    public int getCols() {
        return numCols;
    }

    public Cell getCell(int row, int col) {
        return grid[row][col];
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                result.append(grid[row][col].getAlive() ? "X" : ".");
            }
            result.append("\n");
        }
        return result.toString();
    }

    public ArrayList<Cell> getNeighbors(int row, int col) {
        ArrayList<Cell> neighbors = new ArrayList<>();

        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                int neighborRow = row + dx;
                int neighborCol = col + dy;

                if (neighborRow >= 0 && neighborRow < numRows && neighborCol >= 0 && neighborCol < numCols
                        && !(dx == 0 && dy == 0)) {
                    neighbors.add(grid[neighborRow][neighborCol]);
                }
            }
        }

        return neighbors;
    }

    public void advance() {
        // Create a new grid to store the next state of the cells
        Cell[][] newGrid = new Cell[numRows][numCols];

        // Iterate over each cell in the current grid
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                Cell cell = grid[row][col];
                ArrayList<Cell> neighbors = getNeighbors(row, col);

                // Count the number of live neighbors
                int liveNeighborCount = 0;
                for (Cell neighbor : neighbors) {
                    if (neighbor.getAlive()) {
                        liveNeighborCount++;
                    }
                }

                // Apply Conway's Game of Life rules to determine the next state of the cell
                boolean newAliveState = false;
                if (cell.getAlive() && (liveNeighborCount == 2 || liveNeighborCount == 3)) {
                    newAliveState = true;
                } else if (!cell.getAlive() && liveNeighborCount == 3) {
                    newAliveState = true;
                }

                // Update the cell in the new grid with the new state
                newGrid[row][col] = new Cell(newAliveState);
            }
        }

        // Assign the temporary grid back to the original grid
        grid = newGrid;
    }

    public static void main(String[] args) {
        Landscape landscape = new Landscape(5, 5, 0.5);
        System.out.println("Initial Landscape:");
        System.out.println(landscape.toString());

        // Advance the landscape (simulate one step)
        landscape.advance();
        System.out.println("Landscape after one step:");
        System.out.println(landscape.toString());
    }

}

class Cell {
    private boolean alive;

    public Cell(boolean alive) {
        this.alive = alive;
    }

    public boolean getAlive() {
        return alive;
    }

}

