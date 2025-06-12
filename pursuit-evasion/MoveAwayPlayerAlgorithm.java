/*
file name:      MoveAwayPlayerAlgorithm.java
author:        Olivia Doherty
last modified:  
pupose: Creates an algorithm for the evader to escape the pursuer.
*/

import java.util.ArrayList;
import java.util.Random;


public class MoveAwayPlayerAlgorithm extends AbstractPlayerAlgorithm {

    private Random random;

    public MoveAwayPlayerAlgorithm(Graph graph) {
        super(graph);
        random = new Random();
    }

    @Override
    public Vertex chooseStart() {
        Iterable<Vertex> vertices = getGraph().getVertices();
        ArrayList<Vertex> vertexList = new ArrayList<>();
        for (Vertex v : vertices) {
            vertexList.add(v);
        }
        int index = random.nextInt(vertexList.size());
        setCurrentVertex(vertexList.get(index));
        return getCurrentVertex();
    }

    @Override
    public Vertex chooseStart(Vertex other) {
        Graph graph = getGraph();
        Iterable<Vertex> vertices = graph.getVertices();
        double maxDensity = -1;
        Vertex bestVertex = null;

        for (Vertex v : vertices) {
            if (v.equals(other)) {
                continue;
            }
            Iterable<Vertex> neighbors = v.adjacentVertices();
            int neighborCount = 0;
            for (Vertex neighbor : neighbors) {
                if (!neighbor.equals(other)) {
                    neighborCount++;
                }
            }
            if (neighborCount < 2) {
                continue;
            }
            double density = neighborCount / Math.sqrt(neighborCount - 1);
            if (density > maxDensity) {
                maxDensity = density;
                bestVertex = v;
            }
        }

        setCurrentVertex(bestVertex);
        return bestVertex;
    }

    @Override
    public Vertex chooseNext(Vertex otherPlayer) {
        Graph graph = getGraph();
        Iterable<Vertex> neighbors = getCurrentVertex().adjacentVertices();
        double maxDensity = -1;
        Vertex bestVertex = null;
        boolean otherPlayerAdjacent = false;

        for (Vertex neighbor : neighbors) {
            if (neighbor.equals(otherPlayer)) {
                otherPlayerAdjacent = true;
                continue;
            }
            Iterable<Vertex> neighborNeighbors = neighbor.adjacentVertices();
            int neighborCount = 0;
            for (Vertex neighborNeighbor : neighborNeighbors) {
                if (!neighborNeighbor.equals(otherPlayer)) {
                    neighborCount++;
                }
            }
            int density = neighborCount * neighborCount;
            if (density > maxDensity) {
                maxDensity = density;
                bestVertex = neighbor;
            }
        }

        if (otherPlayerAdjacent) {
            // If other player is adjacent, choose farthest vertex with highest density
            Iterable<Vertex> vertices = graph.getVertices();
            maxDensity = -1;
            Vertex farthestVertex = null;
            HashMap<Vertex, Double> distances = graph.distanceFrom(otherPlayer);
            for (Vertex vertex : vertices) {
                if (vertex.equals(otherPlayer)) {
                    continue;
                }
                Iterable<Vertex> vertexNeighbors = vertex.adjacentVertices();
                int vertexCount = 0;
                for (Vertex vertexNeighbor : vertexNeighbors) {
                    if (!vertexNeighbor.equals(otherPlayer)) {
                        vertexCount++;
                    }
                }
                int density = vertexCount * vertexCount;
                if (density > maxDensity) {
                    maxDensity = density;
                    farthestVertex = vertex;
                } else if (density == maxDensity && distances.get(vertex) > distances.get(farthestVertex)) {
                    farthestVertex = vertex;
                }
            }
            setCurrentVertex(farthestVertex);
            return farthestVertex;
        } else {
            // Choose vertex with highest density
            setCurrentVertex(bestVertex);
            return bestVertex;
        }
    }




}