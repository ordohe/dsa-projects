/*
 * Olivia Doherty
 * Project 4
 * 
 * This is a program to test the Cell.java file
 */

 public class CellTest {

    public static void main(String[] args) {
        testDefaultConstructor();
        testParameterizedConstructor();
        testFullConstructor();
        testSetValue();
        testSetLocked();
        testToString();
    }

    public static void testDefaultConstructor() {
        Cell cell = new Cell();
        assert cell.getRow() == 0;
        assert cell.getCol() == 0;
        assert cell.getValue() == 0;
        assert !cell.isLocked();
    }

    public static void testParameterizedConstructor() {
        Cell cell = new Cell(1, 2, 5);
        assert cell.getRow() == 1;
        assert cell.getCol() == 2;
        assert cell.getValue() == 5;
        assert !cell.isLocked();
    }

    public static void testFullConstructor() {
        Cell cell = new Cell(3, 4, 7, true);
        assert cell.getRow() == 3;
        assert cell.getCol() == 4;
        assert cell.getValue() == 7;
        assert cell.isLocked();
    }

    public static void testSetValue() {
        Cell cell = new Cell(1, 1, 3);
        cell.setValue(9);
        assert cell.getValue() == 9;
    }

    public static void testSetLocked() {
        Cell cell = new Cell(2, 2, 4);
        cell.setLocked(true);
        assert cell.isLocked();
        cell.setLocked(false);
        assert !cell.isLocked();
    }

    public static void testToString() {
        Cell cell = new Cell(0, 0, 8);
        assert cell.toString().equals("8");
    }
}
