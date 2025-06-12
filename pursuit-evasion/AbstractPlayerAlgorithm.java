/*
file name:      AbstractPlayerAlgorithm.java
author:        Olivia Doherty
last modified:  12/4/23
pupose: Create an abstract class for players to start and determine their next move.
*/

public abstract class AbstractPlayerAlgorithm {
    protected Graph graph;
    protected Vertex currentVertex;

    public AbstractPlayerAlgorithm(Graph graph) {
        this.graph = graph;
    }

    public Graph getGraph() {
        return graph;
    }

    public Vertex getCurrentVertex() {
        return currentVertex;
    }

    public void setCurrentVertex(Vertex vertex) {
        this.currentVertex = vertex;
    }

    public abstract Vertex chooseStart();
    public abstract Vertex chooseStart(Vertex other);
    public abstract Vertex chooseNext(Vertex otherPlayer);
}

