/**
* File: LandscapeDisplay.Java
* Author" Olivia Doherty
* Date: 9/23/2023
*
* 
*/

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class LandscapeDisplay {
    JFrame win;
    protected Landscape scape;
    private LandscapePanel canvas;
    private int gridScale; // width (and height) of each square in the grid

    public LandscapeDisplay(Landscape scape, int scale) {
        // setup the window
        this.win = new JFrame("Game of Life");
        this.win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.scape = scape;
        this.gridScale = scale;

        // create a panel in which to display the Landscape
        // put a buffer of two rows around the display grid
        this.canvas = new LandscapePanel((int) (this.scape.getCols() + 4) * this.gridScale,
                (int) (this.scape.getRows() + 4) * this.gridScale);

        // add the panel to the window, layout, and display
        this.win.add(this.canvas, BorderLayout.CENTER);
        this.win.pack();
        this.win.setVisible(true);
    }

    public void saveImage(String filename) {
        String ext = filename.substring(filename.lastIndexOf('.') + 1, filename.length());
        Component tosave = this.win.getRootPane();
        BufferedImage image = new BufferedImage(tosave.getWidth(), tosave.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        Graphics g = image.createGraphics();
        tosave.paint(g);
        g.dispose();

        try {
            ImageIO.write(image, ext, new File(filename));
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    private class LandscapePanel extends JPanel {
        public LandscapePanel(int width, int height) {
            super();
            this.setPreferredSize(new Dimension(width, height));
            this.setBackground(Color.lightGray);
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            scape.draw(g, gridScale);
        }
    }

    public void repaint() {
        this.win.repaint();
    }

    public static void main(String[] args) throws InterruptedException {
        Landscape scape = new Landscape(100, 100, .25);

        LandscapeDisplay display = new LandscapeDisplay(scape, 6);

        while (true) {
            Thread.sleep(10);
            scape.advance();
            display.repaint();
        }
    }
}
