package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class representing the entire graph with vertices and edges
 */
public class Graph {

    private int greatestCost;
    private String name;
    private int numVertices = -1;
    private HashMap<String, Vertex> vertexHashMap;
    private HashMap<String, Edge> edgeHashMap;

    public Graph(String name) {
        this.name = name;
        vertexHashMap = new HashMap<>();
        edgeHashMap = new HashMap<>();
        greatestCost = Integer.MIN_VALUE;
    }

    public String getName() {
        return name;
    }

    public void addVertex(String key, Vertex vertex) {
        vertexHashMap.put(key, vertex);
    }

    public void addEdge(String key, Edge edge) {
        edgeHashMap.put(key, edge);
    }

    public Vertex getVertex(String key) {
        return vertexHashMap.get(key);
    }

    public HashMap<String, Edge> getEdgeHashMap() {
        return edgeHashMap;
    }

    public HashMap<String, Vertex> getVertexHashMap() {
        return vertexHashMap;
    }

    public int getGreatestCost() {
        return greatestCost;
    }

    public Edge getEdge(String key) {
        return edgeHashMap.get(key);
    }


    /**
     * Get the vertices with no incoming edges
     *
     * @return the list of vertices with no incoming edges
     */
    public List<Vertex> getRoots() {
        List<Vertex> result = new ArrayList<>();
        for (Vertex vertex : vertexHashMap.values()) {
            if (vertex.isRoot()) {
                result.add(vertex);
            }
        }
        return result;
    }


    //Get the bottom level of a graph
    public int calculateBottomLevel() {
        Map.Entry<String, Vertex> entry = vertexHashMap.entrySet().iterator().next();
        // Get root vertex
        String key = entry.getKey();
        int bottomLevel = vertexHashMap.get(key).calculateBottomLevel();
        if (bottomLevel > greatestCost) {
            greatestCost = bottomLevel;
        }
        return greatestCost;
    }


    @Override
    public String toString() {
        return vertexHashMap.toString() + edgeHashMap.toString();
    }

    @Override
    public boolean equals(Object o) {
        Graph g = (Graph) o;
        return g.hashCode() == this.hashCode();
    }

    public int getNumVertices() {
        if(numVertices == -1){
            numVertices = vertexHashMap.size();
        }
        return numVertices;


    }

    @Override
    public int hashCode() {
        int result = 13 * vertexHashMap.hashCode() * edgeHashMap.hashCode();

        return result;
    }
}
