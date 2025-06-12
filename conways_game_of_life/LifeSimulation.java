/**
* File: LifeSimulation.Java
* Author" Olivia Doherty
* Date: 9/23/2023
*
* This file models a Conway's Game of Life simulation with visualization, image capture, and animation.
* Run using command-line arguments.
*/



public class LifeSimulation {
    public static void main(String[] args) throws InterruptedException {
        int rows = Integer.parseInt(args[0]);
        int cols = Integer.parseInt(args[1]);
        double density = Double.parseDouble(args[2]);
        int gen = Integer.parseInt(args[3]);

        Landscape scape = new Landscape(rows, cols, density);

        LandscapeDisplay display = new LandscapeDisplay(scape, 6);
                
        // Uncomment below when advance() has been finished
        for(int i=0; i < gen; i++) {
        Thread.sleep( 250 );
        scape.advance();
        display.repaint();
        display.saveImage( "data/life_frame_" + String.format( "%03d", i ) + ".png" );
        }
    }
}