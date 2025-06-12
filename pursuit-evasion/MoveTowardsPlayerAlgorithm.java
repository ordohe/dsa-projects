/*
file name:      MoveTowardsPlayerAlgorithm.java
author:        Olivia Doherty
last modified:  12/4/23
pupose: Creates a pursuer algorithm for a player to hunt down the other player and capture it.
*/


import java.util.Random;

public class MoveTowardsPlayerAlgorithm extends AbstractPlayerAlgorithm {
    private Random random;
    
    public MoveTowardsPlayerAlgorithm(Graph graph) {
        super(graph);
        random = new Random();
    }
    
    @Override
    public Vertex chooseStart() {
        int randomIndex = random.nextInt(getGraph().size());
        int count = 0;
        for (Vertex v : getGraph().getVertices()) {
            if (count == randomIndex) {
                setCurrentVertex(v);
                return v;
            }
            count++;
        }
        // if no vertex is found return null
        return null;
    }

    
    @Override
    public Vertex chooseStart(Vertex other) {
        // return chooseStart();
        setCurrentVertex(other);
        return other;
    }
    
    @Override
    public Vertex chooseNext(Vertex otherPlayer) {
        HashMap<Vertex, Double> distances = getGraph().distanceFrom(otherPlayer);
        double minDistance = distances.get(getCurrentVertex());
        Vertex closestVertex = getCurrentVertex();
        for (Vertex v : getCurrentVertex().adjacentVertices()) {
            double distance = distances.get(v);
            if (distance < minDistance) {
                minDistance = distance;
                closestVertex = v;
            }
        }
        setCurrentVertex(closestVertex);
        return closestVertex;
    }

}