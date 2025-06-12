/*
 * Olivia Doherty
 * Project 4
 * 
 * 
 */


import java.awt.Color;
import java.awt.Graphics;

public class Cell {
    // Fields
    private int row;
    private int col;
    private int value;
    private boolean locked;

    // Constructors
    // initializes all values to 0 or false
    public Cell() {
        this(0, 0, 0, false);
    }
    // constructor to make a cell given a row, column, and value
    public Cell(int row, int col, int value) {
        this(row, col, value, false);
    }

    // initialize all of the Cell fields given the parameters.
    public Cell(int row, int col, int value, boolean locked) {
        this.row = row;
        this.col = col;
        this.value = value;
        this.locked = locked;
    }

    // Accessor functions
    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }

    public int getValue() {
        return this.value;
    }

    public boolean isLocked() {
        return this.locked;
    }

    // Mutator functions
    public void setValue(int newValue) {
        if (!locked) {
            this.value = newValue;
        }
    }

    public void setLocked(boolean lock) {
        this.locked = lock;
    }

    // toString function
    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public void draw(Graphics g, int x0, int y0, int scale) {
        char toDraw = (char) ('0' + getValue());
        int x = x0 + getCol() * scale; // Calculate the x position based on column and scale
        int y = y0 + getRow() * scale; // Calculate the y position based on row and scale

        // Set the color based on whether the cell is locked
        g.setColor(isLocked() ? Color.BLUE : Color.RED);

        // Draw the character at the specified position
        g.drawChars(new char[] { toDraw }, 0, 1, x, y);
    }
    

}


