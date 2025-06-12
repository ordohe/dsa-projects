/*
file name:      Graph.java
author:        Olivia Doherty
last modified:  12/4/23
pupose: Create a graph class that will determine the distance between vertices using the edges.
*/

import java.io.*;
import java.util.*;
import java.util.PriorityQueue;
import java.util.ArrayList;

public class Graph {
    private List<Vertex> vertices;
    private List<Edge> edges;

    // Constructors
    public Graph() {
        this.vertices = new ArrayList<Vertex>();
        this.edges = new ArrayList<Edge>();
    }

    public Graph(int n, double probability) {
        this.vertices = new ArrayList<Vertex>();
        this.edges = new ArrayList<Edge>();
        Random rand = new Random();

        // Create vertices
        for (int i = 0; i < n; i++) {
            vertices.add(new Vertex());
        }

        // Create edges with given probability
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (rand.nextDouble() < probability) {
                    Edge edge = new Edge(vertices.get(i), vertices.get(j), 1.0);
                    vertices.get(i).addEdge(edge);
                    vertices.get(j).addEdge(edge);
                    edges.add(edge);
                }
            }
        }
    }

    public Graph(String filename) {
        this.vertices = new ArrayList<Vertex>();
        this.edges = new ArrayList<Edge>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            int numVertices = Integer.parseInt(br.readLine().split(": ")[1]);

            for (int i = 0; i < numVertices; i++) {
                vertices.add(new Vertex());
            }

            String line;
            while ((line = br.readLine()) != null) {
                String[] arr = line.split(",");
                int start = Integer.parseInt(arr[0]);
                int end = Integer.parseInt(arr[1]);

                Edge edge = new Edge(vertices.get(start), vertices.get(end), 1.0);
                vertices.get(start).addEdge(edge);
                vertices.get(end).addEdge(edge);
                edges.add(edge);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Methods
    public int size() {
        return vertices.size();
    }

    public Iterable<Vertex> getVertices() {
        return vertices;
    }

    public Iterable<Edge> getEdges() {
        return edges;
    }

    public Vertex addVertex() {
        Vertex v = new Vertex();
        vertices.add(v);
        return v;
    }

    public Edge addEdge(Vertex u, Vertex v, double distance) {
        Edge e = new Edge(u, v, distance);
        edges.add(e);
        u.addEdge(e);
        v.addEdge(e);
        return e;
    }

    public Edge getEdge(Vertex u, Vertex v) {
        return u.getEdgeTo(v);
    }

    public boolean remove(Vertex vertex) {
        if (!vertices.contains(vertex)) {
            return false;
        }
        List<Edge> incidentEdgesList = new ArrayList<Edge>();
        vertex.incidentEdges().forEach(incidentEdgesList::add);
        incidentEdgesList.forEach(this::remove);
    
        vertices.remove(vertex);
        return true;
    }

    public boolean remove(Edge edge) {
        if (!edges.contains(edge)) {
            return false;
        }
        edge.vertices()[0].removeEdge(edge);
        edge.vertices()[1].removeEdge(edge);
        edges.remove(edge);
        return true;
    }

    public HashMap<Vertex, Double> distanceFrom(Vertex source) {
        HashMap<Vertex, Double> distances = new HashMap<>();
        PriorityQueue<Vertex> pq = new PriorityQueue<>(new VertexComparator(distances));

        vertices.forEach(v -> {
            distances.put(v, v.equals(source) ? 0.0 : Double.POSITIVE_INFINITY);
            pq.add(v);
        });

        while (!pq.isEmpty()) {
            Vertex current = pq.poll();
            for (Edge e : current.incidentEdges()) {
                Vertex neighbor = e.other(current);
                double newDist = distances.get(current) + e.distance();
                if (newDist < distances.get(neighbor)) {
                    distances.put(neighbor, newDist);
                    pq.remove(neighbor);
                    pq.add(neighbor);
                }
            }
        }

        return distances;
    }

    // Custom Comparator class for Dijkstra's algorithm
    private class VertexComparator implements Comparator<Vertex> {
        private HashMap<Vertex, Double> distanceMap;

        public VertexComparator(HashMap<Vertex, Double> distanceMap) {
            this.distanceMap = distanceMap;
        }

        @Override
        public int compare(Vertex v1, Vertex v2) {
            return Double.compare(distanceMap.get(v1), distanceMap.get(v2));
        }
    }
}
