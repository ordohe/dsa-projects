/*
file name:      RandomPlayer.java
author:        Olivia Doherty
last modified:  12/4/23
pupose: 
*/

import java.util.ArrayList;
import java.util.Random;

public class RandomPlayer extends AbstractPlayerAlgorithm {
    private Random random;
    
    public RandomPlayer(Graph graph) {
        super(graph);
        random = new Random();
    }
    
    @Override
    public Vertex chooseStart() {
        ArrayList<Vertex> vertexList = new ArrayList<>();
        for (Vertex vertex : getGraph().getVertices()) {
            vertexList.add(vertex);
        }
        Vertex startVertex = vertexList.get(random.nextInt(vertexList.size()));
        setCurrentVertex(startVertex);
        return startVertex;
    }
    
    @Override
    public Vertex chooseStart(Vertex other) {
        return chooseStart();
    }
    
    @Override
    public Vertex chooseNext(Vertex otherPlayer) {
        ArrayList<Vertex> neighbors = new ArrayList<>();
        for (Vertex vertex : getCurrentVertex().adjacentVertices()) {
            neighbors.add(vertex);
        }
        neighbors.add(getCurrentVertex()); // Include current vertex to stay still
        Vertex next = neighbors.get(random.nextInt(neighbors.size()));
        setCurrentVertex(next);
        return next;
    }
}
