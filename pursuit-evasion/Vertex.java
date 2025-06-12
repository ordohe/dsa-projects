/*
file name:      Vertex.java
author:        Olivia Doherty
last modified:  12/4/23
pupose:  Creates a vertex class for the players to move around on. 
*/

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;

public class Vertex {

    private List<Edge> edges;

    //initialization
    public Vertex() {
        edges = new ArrayList<>();
    }
    
    public Edge getEdgeTo(Vertex vertex) {
        for (Edge e : edges) {
            if (e.other(this) == vertex) {
                return e;
            }
        }
        return null;
    }
    
    //adds an edge
    public void addEdge(Edge edge) {
        edges.add(edge);
    }
    

    //removes an edge
    public boolean removeEdge(Edge edge) {
        return edges.remove(edge);
    }
    
    //finds neighbors
    public Iterable<Vertex> adjacentVertices() {
        LinkedList<Vertex> adjacentVertices = new LinkedList<Vertex>();
        for (Edge edge : edges) {
            Vertex other = edge.other(this);
            if (other != null) {
                adjacentVertices.add(other);
            }
        }
        return adjacentVertices;
    }
    
    
    public Iterable<Edge> incidentEdges() {
        return edges;
    }
    
}