/*
 * Olivia Doherty
 * Project 4
 * 
 * This Java program readz a Sudoku board from a file, solvez it, and displayz the solution.
 * To run the program, provide the filename of the Sudoku board as a command-line argument.
 */ 

// import java.util.Random;
import java.util.Stack;
// import java.awt.*;
// import java.io.IOException;


public class Sudoku {
    private Board board; // Field for the Board object
    private LandscapeDisplay ld; // Add the LandscapeDisplay field
    

    public Sudoku(int numLockedCells) {
        // Create a new Board with a specified number of randomly placed locked cells
        board = new Board(numLockedCells);
    }

    // Find the next cell to set a value for
    public Cell findNextCell() {
        for (int row = 0; row < Board.SIZE; row++) {
            for (int col = 0; col < Board.SIZE; col++) {
                Cell cell = board.get(row, col);

                if (cell.getValue() == 0) {
                    int nextValue = findNextValue(cell);
                    if (nextValue != 0) {
                        cell.setValue(nextValue);
                        return cell;
                    } else {
                        return null; // No valid value for this cell, mark as impossible solution
                    }
                }
            }
        }
        return null; // No more cells with value 0 found, the puzzle is solved
    }

    // Find the next valid value for a cell that hasn't been tried
    public int findNextValue(Cell cell) {
        int currentValue = cell.getValue();
        for (int value = currentValue + 1; value <= Board.SIZE; value++) {
            if (board.validValue(cell.getRow(), cell.getCol(), value)) {
                return value; // Found a valid value
            }
        }
        return 0; // No remaining values are valid
    }

    // Solve the Sudoku puzzle
    public boolean solve(int delay) throws InterruptedException {
        Stack<Cell> stack = new Stack<>();
    
        while (stack.size() < Board.SIZE * Board.SIZE) {
            Cell next = findNextCell();
    
            while (next == null && !stack.isEmpty()) {
                Cell popped = stack.pop();
                int nextValue = findNextValue(popped);
                popped.setValue(nextValue);
                if (popped.getValue() != 0) {
                    next = popped;
                }
            }
    
            if (next == null) {
                return false; // No more valid options, puzzle is unsolvable
            } else {
                stack.push(next);
                if (delay > 0) {
                    Thread.sleep(delay); // Add delay to control speed
                }
                if (ld != null) {
                    ld.repaint(); // Update the visual display
                }
            }
        }
    
        return true; // Puzzle is solved
    }

    // Default constructor
    public Sudoku() {
        this.board = new Board();
        ld = new LandscapeDisplay(board); // Initialize the LandscapeDisplay
    }

    // Constructor that loads a board from a file
    public Sudoku(String filename) {
        this.board = new Board(filename);
        ld = new LandscapeDisplay(board); // Initialize the LandscapeDisplay
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length != 1) {
            System.out.println("Usage: java Sudoku <filename>");
            return;
        }
    
        // Get the filename from the command line argument
        String filename = args[0];
    
        // Create a new Sudoku object from a file
        Sudoku game = new Sudoku(filename);
    
        System.out.println("Solving the puzzle...");
        game.solve(10); // Solve with a delay of 10ms
    
        System.out.println("Solved Board (from file):");
        System.out.println(game.board.toString());
    
        // Create a Sudoku object with 0 initial values
        Sudoku sudoku = new Sudoku(0);
        System.out.println("Initial Board:");
        System.out.println(sudoku.board.toString());
    
        if (sudoku.solve(10)) {
            System.out.println("Final Board (Solution):");
            System.out.println(sudoku.board.toString());
        } else {
            System.out.println("No solution found.");
        }    
}
}

