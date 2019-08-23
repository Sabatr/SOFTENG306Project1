package graph;

/**
 * A class representing a directed edge between two vertices
 */
public class Edge {
    private String id;
    //the vertex the edge is pointing from
    private Vertex fromVertex;
    //the vertex the edge is pointing to
    private Vertex toVertex;
    //communication cost of the edge
    private int switchCost;


    public Edge(Vertex fromVertex, Vertex toVertex, int switchCost) {
        this.fromVertex = fromVertex;
        this.toVertex = toVertex;
        this.switchCost = switchCost;
        id = fromVertex.getId() + "->" + toVertex.getId();
        fromVertex.addOutgoingEdge(this);
        toVertex.addIncomingEdge(this);

    }

    public String getId() {
        return id;
    }

    public int getSwitchCost() {
        return switchCost;
    }

    public Vertex getFromVertex() {
        return fromVertex;
    }

    public Vertex getToVertex() {
        return toVertex;
    }

    @Override
    public String toString() {
        String weight = Integer.toString(switchCost);
        String output = id +  "\t[Weight=" + weight + "];";
        return output;
    }

    @Override
    public int hashCode() {
        int result = 19 * id.hashCode() * switchCost;
        return result;
    }

    @Override
    public boolean equals(Object o){
        Edge e = (Edge) o;
        return e.hashCode() == this.hashCode();
    }
}
