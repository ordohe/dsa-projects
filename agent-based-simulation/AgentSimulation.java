/*
 * Olivia Doherty
 * Project 3
 * 
 * This is the simulation file that runs the project
 */




public class AgentSimulation {
    public static void main(String[] args) throws InterruptedException {
        int rows = Integer.parseInt(args[0]);
        int cols = Integer.parseInt(args[1]);
        double density = Double.parseDouble(args[2]);
        int sim = Integer.parseInt(args[3]);

        Landscape scape = new Landscape(rows, cols);
        for (int i=0; i<density/2; i++){
            scape.addAgent(new AntiSocialAgent(Math.random() * scape.getWidth(), Math.random() * scape.getHeight(), 40));
        }
        for (int i=0; i<density/2; i++){
            scape.addAgent(new SocialAgent(Math.random() * scape.getWidth(), Math.random() * scape.getHeight(), 40));
        }
        // for (int i=0; i<density/5; i++){
        //     scape.addAgent(new SimonAgentExtension(Math.random() * scape.getWidth(), Math.random() * scape.getHeight(), 20));
        // }
        // for (int i=0; i<density/5; i++){
        //     scape.addAgent(new QuadrantAgent(Math.random() * scape.getWidth(), Math.random() * scape.getHeight(), 20));
        // }
        LandscapeDisplay display = new LandscapeDisplay(scape);
                
        // Uncomment below when advance() has been finished
        for(int i=0; i < sim; i++) {
        Thread.sleep( 100 );
        scape.updateAgents();
        display.repaint();
        // display.saveImage( "data/life_frame_" + String.format( "%03d", i ) + ".png" );
        }
    }
}