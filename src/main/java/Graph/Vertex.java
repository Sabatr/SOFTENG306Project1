package Graph;

import java.util.ArrayList;
import java.util.List;

public class Vertex {
    private String id;
    private int cost;
    private List<Edge> outgoingEdges;
    int bottomLevel;

    public Vertex(String id, int cost){
        this.id = id;
        this.cost = cost;
        outgoingEdges = new ArrayList<>();
    }

    //TODO calculate bottom level. //DFS but prioritise most expensive
    int calculateBottomLevel(){
        bottomLevel = -1;
        return bottomLevel;
    }


    public void addOutgoingEdge(Edge edge){
        outgoingEdges.add(edge);
    }

    public String getId() {
        return id;
    }

    public int getCost() {
        return cost;
    }

    public List<Edge> getOutgoingEdges() {
        return outgoingEdges;
    }

    @Override
    public String toString() {
        return cost + outgoingEdges.toString();
    }
}