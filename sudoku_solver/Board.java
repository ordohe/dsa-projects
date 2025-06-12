/*
 * Olivia Doherty
 * Project 4
 * 
 * 
 */

import java.io.*;
import java.util.Random;
import java.awt.Graphics;
import java.awt.Color;

public class Board {
     
    private Cell[][] cells; // Private field for the 2D array of Cells
    public static final int SIZE = 9; // Constant for the size of the board

    // Accessor method: Returns the number of columns
    public int getCols() {
        return SIZE;
    }

    // Accessor method: Returns the number of rows
    public int getRows() {
        return SIZE;
    }

    //Tests if the given value is valid  at the given row and column of the board
    public boolean validValue(int row, int col, int value) {
        if (value < 1 || value > SIZE) {
            return false;
        }
    
        // Check the row for duplicates
        for (int c = 0; c < SIZE; c++) {
            if (c != col && cells[row][c].getValue() == value) {
                return false;
            }
        }
    
        // Check the column for duplicates
        for (int r = 0; r < SIZE; r++) {
            if (r != row && cells[r][col].getValue() == value) {
                return false;
            }
        }
    
        // Check the local 3x3 square for duplicates
        int startRow = (row / 3) * 3;
        int startCol = (col / 3) * 3;
    
        for (int r = startRow; r < startRow + 3; r++) {
            for (int c = startCol; c < startCol + 3; c++) {
                if (r != row && c != col && cells[r][c].getValue() == value) {
                    return false;
                }
            }
        }
    
        return true;
    }

    // Method to test if the board is solved
    public boolean validSolution() {
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                int value = value(r, c);
                if (value == 0 || !validValue(r, c, value)) {
                    return false; // If any cell is empty or has an invalid value, the board is not solved.
                }
            }
        }
        return true; // All cells have valid values, and the board is solved.
    }


    // Utility method: Checks if the given row and column are valid
    private boolean isValidCell(int row, int col) {
        return row >= 0 && row < SIZE && col >= 0 && col < SIZE;
    }
    
    // Accessor method: Returns the Cell at the given location r, c
    public Cell get(int r, int c) {
        if (isValidCell(r, c)) {
            return cells[r][c];
        } else {
            return null; // Handle invalid cell access (out of bounds)
        }
    }

    // Accessor method: Returns whether the Cell at r, c is locked
    public boolean isLocked(int r, int c) {
        if (isValidCell(r, c)) {
            return cells[r][c].isLocked();
        } else {
            return false; // Handle invalid cell access (out of bounds)
        }
    }

    // Utility method: Returns the number of locked Cells on the board
    public int numLocked() {
        int count = 0;
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                if (cells[r][c].isLocked()) {
                    count++;
                }
            }
        }
        return count;
    }

    // Accessor method: Returns the value at Cell r, c
    public int value(int r, int c) {
        if (isValidCell(r, c)) {
            return cells[r][c].getValue();
        } else {
            return -1; // Handle invalid cell access (out of bounds)
        }
    }

    // Mutator method: Sets the value of the Cell at r, c
    public void set(int r, int c, int value) {
        if (isValidCell(r, c)) {
            cells[r][c].setValue(value);
        }
        // Handle invalid cell access (out of bounds) by doing nothing
    }

    // Mutator method: Sets the value and locked fields of the Cell at r, c
    public void set(int r, int c, int value, boolean locked) {
        if (isValidCell(r, c)) {
            cells[r][c].setValue(value);
            cells[r][c].setLocked(locked);
        }
        // Handle invalid cell access (out of bounds) by doing nothing
    }



    // Default constructor
    public Board() {
        // Create a 9x9 array of Cell objects and initialize them with a value of 0
        cells = new Cell[SIZE][SIZE];
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                cells[row][col] = new Cell(row, col, 0);
            }
        }
    }

    // Auxiliary constructor that takes a filename and calls the read method
    public Board(String filename) {
        this(); // Call the default constructor to initialize the grid
        read(filename); // Call the read method to load the Sudoku board from the file
    }


    // Constructor to generate a random Sudoku board with a specified number of locked cells
    public Board(int numLockedCells) {
        this(); // Call the default constructor to initialize the grid
        Random random = new Random();

        // Ensure numLockedCells is within the valid range
        numLockedCells = Math.min(numLockedCells, SIZE * SIZE);
        numLockedCells = Math.max(numLockedCells, 0);

        int lockedCount = 0;
        while (lockedCount < numLockedCells) {
            int row = random.nextInt(SIZE);
            int col = random.nextInt(SIZE);

            if (!isLocked(row, col)) {
                int value = random.nextInt(SIZE) + 1; // Generate a random value [1, 9]
                if (validValue(row, col, value)) {
                    set(row, col, value, true); // Set the value as locked
                    lockedCount++;
                }
            }
        }
    }

    private boolean finished = false; // Add the 'finished' field

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public void draw(Graphics g, int scale) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                get(i, j).draw(g, j * scale, i * scale, scale);
            }
        }

    // Draw lines to divide the board into 3x3 boxes
    g.setColor(Color.BLACK);
    for (int i = 1; i < SIZE; i++) {
        if (i % 3 == 0) {
            int lineWidth = 3; // Width of the line to separate boxes
            int x = i * scale - lineWidth / 2;
            int y = 0;
            int width = lineWidth;
            int height = SIZE * scale;
            g.fillRect(x, y, width, height);

            x = 0;
            y = i * scale - lineWidth / 2;
            width = SIZE * scale;
            height = lineWidth;
            g.fillRect(x, y, width, height);
        }
    }

    if (finished) {
        if (validSolution()) {
            g.setColor(new Color(0, 127, 0));
            g.drawChars("Hurray!".toCharArray(), 0, "Hurray!".length(), scale * 3 + 5, scale * 10 + 10);
        } else {
            g.setColor(new Color(127, 0, 0));
            g.drawChars("No solution!".toCharArray(), 0, "No Solution!".length(), scale * 3 + 5, scale * 10 + 10);
        }
    }
}


// Read a Sudoku board from a file
public boolean read(String filename) {
    try {
        FileReader fr = new FileReader(filename);
        BufferedReader br = new BufferedReader(fr);

        int row = 0;
        String line = br.readLine();
        while (line != null && row < 9) {
            String[] arr = line.split("[ ]+");
            for (int col = 0; col < 9 && col < arr.length; col++) {
                int value = Integer.parseInt(arr[col]);
                cells[row][col].setValue(value);
            }
            line = br.readLine();
            row++;
        }

        br.close();
        return true;
    } catch (FileNotFoundException ex) {
        System.out.println("Board.read():: unable to open file " + filename);
    } catch (IOException ex) {
        System.out.println("Board.read():: error reading file " + filename);
    }

    return false;
}

// Generate a string representation of the Sudoku board
    public String toString() {
        StringBuilder boardString = new StringBuilder();
        for (int row = 0; row < 9; row++) {
            if (row > 0 && row % 3 == 0) {
                boardString.append("------+-------+------\n");
            }
            for (int col = 0; col < 9; col++) {
                if (col > 0 && col % 3 == 0) {
                    boardString.append("| ");
                }
                boardString.append(cells[row][col]);
                boardString.append(" ");
            }
            boardString.append("\n");
        }
        return boardString.toString();
    }
    

public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Board <filename>");
            return;
        }

        // Get the filename from the command line argument
        String filename = args[0];

        // Create a new Board object
        Board sudokuBoard = new Board();

        if (sudokuBoard.read(filename)) {
            System.out.println(sudokuBoard.toString());
            if (sudokuBoard.validSolution()) {
                System.out.println("The board is solved.");
            } else {
                System.out.println("The board is not solved.");
            }
        }
    }
}    

// import java.io.BufferedReader;
// import java.io.FileNotFoundException;
// import java.io.FileReader;
// import java.io.IOException;
// // import java.util.Scanner;
// import java.util.Random;
// import java.awt.Graphics;
// import java.awt.Color;

// public class Board {

//     public static final int SIZE = 9; // setting size of board

//     private Cell[][] board;
//     private boolean finished;

//     // default constructor
//     public Board(){
//         board = new Cell[SIZE][SIZE];
//         for(int i = 0; i < SIZE; i++){
//             for(int j = 0; j < SIZE; j++){
//                 board[i][j] = new Cell();
//             }
//         }
//     }

//     public boolean read(String filename) {
//       try {
//           FileReader fr = new FileReader(filename);
//           BufferedReader br = new BufferedReader(fr);
  
//           for (int row = 0; row < SIZE; row++) {
//               String line = br.readLine();
//               if (line == null || line.length() != SIZE) {
//                   System.out.println("Invalid board format in file: " + filename);
//                   br.close();
//                   return false;
//               }
  
//               for (int col = 0; col < SIZE; col++) {
//                   char cellChar = line.charAt(col);
//                   if (Character.isDigit(cellChar)) {
//                       int cellValue = Character.getNumericValue(cellChar);
//                       set(row, col, cellValue);
//                       if (cellValue != 0) {
//                           board[row][col].setLocked(true);
//                       }
//                   } else {
//                       set(row, col, 0);
//                   }
//               }
//           }
  
//           br.close();
//           return true;
//       } catch (FileNotFoundException ex) {
//           System.out.println("Board.read():: Unable to open file " + filename);
//       } catch (IOException ex) {
//           System.out.println("Board.read():: Error reading file " + filename);
//       }
  
//       return false;
//   }


//   public int getCols(){
//     return SIZE;
//   }

//   public int getRows(){
//     return SIZE;
//   }

//   public Cell get(int row, int col){
//     return board[row][col];
//   }

//   public void set(int r, int c, int value){
//     board[r][c].setValue(value);
//   }

//   public void set(int r, int c, int value, boolean locked){
//     board[r][c].setValue(value);
//     board[r][c].setLocked(locked);
//   }

//   public boolean isLocked(int r, int c){
//     return board[r][c].isLocked();
//   }

//   public int numLocked(){
//     int numLocked = 0;
//     for(int i = 0; i < SIZE; i++){
//       for(int j = 0; j < SIZE; j++){
//         boolean locked = board[i][j].isLocked();
//         if(locked == true){
//           numLocked++;
//         }
//       }
//     }
//     return numLocked;
//   }

//   public int value(int r, int c){
//     return board[r][c].getValue();
//   }

//   public String toString(){
//     String str = "";
//     for(int i = 0; i < SIZE; i++){
//             for(int j = 0; j < SIZE; j++){
//                 str += board[i][j] + " ";
//                 if((j+1) % 3 == 0){
//                     str += " ";
//                 }
//             }
//         str += "\n";
//         if((i + 1) % 3 == 0){
//             str += "\n";
//         }
//         }
//     return str;
//   }

//   public Board(String filename){
//     this();
//     if(!read(filename)){
//       System.out.println("Error: Uable to read board from this file");
//     }
//   }

//   public Board(int numLockedCells){
//     this();

//     if(numLockedCells < 0 || numLockedCells > SIZE * SIZE){
//       System.out.println("invalid number of locked cells");
//       return;
//     }

//     Random random = new Random();

//     while(numLockedCells > 0){
//       int row = random.nextInt(SIZE);
//       int col = random.nextInt(SIZE);

//       if(!board[row][col].isLocked()){
//         int value;
//         do{
//           value = random.nextInt(SIZE) + 1;
//         } while(!validValue(row, col, value));

//         board[row][col].setValue(value);
//         board[row][col].setLocked(true);
//         numLockedCells--;
//       }
//     }
//   }

//   public boolean validValue(int row, int col, int value){
//     if(value < 1 || value > 9){
//       return false;
//     } 

//     for (int c = 0; c < getCols(); c++) {
//       if (c != col && board[row][c].getValue() == value) {
//         return false;
//       }
//     }

//     for (int r = 0; r < getRows(); r++) {
//       if (r != row && board[r][col].getValue() == value) {
//         return false;
//       }
//     }

//     int startRow = (row / 3) * 3;
//     int startCol = (col / 3) * 3;

//     // Check uniqueness in the local 3x3 square
//     for (int r = startRow; r < startRow + 3; r++) {
//         for (int c = startCol; c < startCol + 3; c++) {
//             if ((r != row || c != col) && board[r][c].getValue() == value) {
//                 return false;
//             }
//         }
//     }
//     return true;
//   }

//   public boolean validSolution(){
//     for(int i = 0; i < SIZE; i++){
//       for(int j = 0; j < SIZE; j++){
//         int value = board[i][j].getValue();

//         if(value == 0){
//           return false;
//         }

//         if(!validValue(i, j, value)){
//           return false;
//         }
//       }
//     }
//     return true;
//   }

//   public void draw(Graphics g, int scale) {
//     for (int i = 0; i < getRows(); i++) {
//         for (int j = 0; j < getCols(); j++) {
//             get(i, j).draw(g, j * scale + 5, i * scale + 10, scale);
//         }
//     }

//     if (finished) {
//         if (validSolution()) {
//             g.setColor(new Color(0, 127, 0));
//             g.drawChars("Hurray!".toCharArray(), 0, "Hurray!".length(), scale * 3 + 5, scale * 10 + 10);
//         } else {
//             g.setColor(new Color(127, 0, 0));
//             g.drawChars("No solution!".toCharArray(), 0, "No Solution!".length(), scale * 3 + 5, scale * 10 + 10);
//         }
//     }
//   }

//   public static void main(String[] args){
//     // Board board = new Board(); 
//     // System.out.println(board.toString()); 
    
//     // Scanner scanner = new Scanner(System.in); //command line argument for width, height, and radius of landscape
//     // System.out.print("Filename: ");
//     // String filename = scanner.nextLine();
//     // scanner.close();
//     // board.read(filename);

//     // System.out.println(board.getCols());
//     // System.out.println(board.getRows());
//     // System.out.println(board.get(5,1));
//     // System.out.println(board.isLocked(5,2));
//     // System.out.println(board.numLocked());
//     // System.out.println(board.value(8,1));
//     // board.set(4,1,7);
//     // System.out.println(board.value(4,1));

//     Board randomBoard = new Board(20);
//     System.out.println(randomBoard.toString());
//     }
//   }


