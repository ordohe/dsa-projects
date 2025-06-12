/*
file name:      Edge.java
author:        Olivia Doherty
last modified:  12/4/23
pupose:  Creates an edge class that will determines the connectivity between the vertices.
*/

public class Edge {
    private Vertex vertex1; // one endpoint of the edge
    private Vertex vertex2; // the other endpoint of the edge
    private double distance; // distance between the two vertices

    // constructor
    public Edge(Vertex u, Vertex v, double distance) {
        this.vertex1 = u;
        this.vertex2 = v;
        this.distance = distance;
    }

    // returns the distance of edge
    public double distance() {
        return distance;
    }

    // if vertex is one of the endpoints of this edge, returns the other endpoint
    public Vertex other(Vertex vertex) {
        if (vertex.equals(vertex1)) {
            return vertex2;
        } else if (vertex.equals(vertex2)) {
            return vertex1;
        } else {
            return null; // the provided vertex is not part of this edge
        }
    }

    // returns an array of the two vertices comprising this edge
    public Vertex[] vertices() {
        return new Vertex[]{vertex1, vertex2};
    }

    // getters for vertex1 and vertex2, if needed
    public Vertex getVertex1() {
        return vertex1;
    }

    public Vertex getVertex2() {
        return vertex2;
    }
}