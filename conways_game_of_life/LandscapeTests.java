/*
file name:      LandscapeTests.java
Authors:        Max Bender & Naser Al Madi
last modified:  9/18/2022

How to run:     java -ea LandscapeTests
*/


import java.util.ArrayList;

public class LandscapeTests {

    public static void landscapeTests() {

        // case 1: testing Landscape(int, int)
        {
            // set up
            Landscape l1 = new Landscape(2, 4);
            Landscape l2 = new Landscape(10, 10);

            // verify
            System.out.println("Test Case 1 - Landscape(int, int):");
            System.out.println("Landscape 1:");
            System.out.println(l1);
            System.out.println("Landscape 2:");
            System.out.println(l2);

            // test
            assert l1 != null : "Error in Landscape::Landscape(int, int)";
            assert l2 != null : "Error in Landscape::Landscape(int, int)";
        }

        // case 2: testing reset()
        {
            // set up
            Landscape l1 = new Landscape(3, 3, 0.3);

            // verify
            System.out.println("\nTest Case 2 - reset():");
            System.out.println("Original Landscape:");
            System.out.println(l1);

            // test
            l1.reset();
            System.out.println("Reset Landscape:");
            System.out.println(l1);
        }

        // case 3: testing getRows()
        {
            // set up
            Landscape l1 = new Landscape(4, 5);

            // verify
            System.out.println("\nTest Case 3 - getRows():");
            System.out.println("Number of Rows in Landscape 1: " + l1.getRows());

            // test
            assert l1.getRows() == 4 : "Error in Landscape::getRows()";
        }

        // case 4: testing getCols()
        {
            // set up
            Landscape l1 = new Landscape(5, 6);

            // verify
            System.out.println("\nTest Case 4 - getCols():");
            System.out.println("Number of Columns in Landscape 1: " + l1.getCols());

            // test
            assert l1.getCols() == 6 : "Error in Landscape::getCols()";
        }

        // case 5: testing getCell(int, int)
        {
            // set up
            Landscape l1 = new Landscape(3, 3);

            // verify
            System.out.println("\nTest Case 5 - getCell(int, int):");
            System.out.println("Cell at (1, 2) in Landscape 1: " + l1.getCell(1, 2).getAlive());

            // test
            assert !l1.getCell(1, 2).getAlive() : "Error in Landscape::getCell(int, int)";
        }

        // case 6: testing getNeighbors()
        {
            // set up
            Landscape l1 = new Landscape(3, 3);
            ArrayList<Cell> neighbors = l1.getNeighbors(1, 1);

            // verify
            System.out.println("\nTest Case 6 - getNeighbors():");
            System.out.println("Neighbors of cell (1, 1) in Landscape 1:");
            for (Cell neighbor : neighbors) {
                System.out.println(neighbor.getAlive());
            }

            // test
            assert neighbors.size() == 8 : "Error in Landscape::getNeighbors()";
        }

        // case 7: testing advance()
        {
            // set up
            Landscape l1 = new Landscape(3, 3, 0.3);

            // verify
            System.out.println("\nTest Case 7 - advance():");
            System.out.println("Original Landscape:");
            System.out.println(l1);

            // test
            l1.advance();
            System.out.println("Advanced Landscape:");
            System.out.println(l1);
        }

    }

    public static void main(String[] args) {

        landscapeTests();
    }
}
