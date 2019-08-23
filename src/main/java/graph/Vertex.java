package graph;

import java.util.ArrayList;
import java.util.List;

/**
 * A class representing the vertices
 */
public class Vertex {
    private String id;
    private int cost;
    private List<Edge> outgoingEdges;
    private List<Edge> incomingEdges;
    private List<Vertex> incomingVerticies;
    private List<Vertex> outgoingVerticies;
    int bottomLevel;
    int level;

    public List<Vertex> getOutgoingVerticies() {
        return outgoingVerticies;
    }

    // These variables are for output parsing ONLY they are not changed during the execution of the algorithm
    int startTime;
    int processorNo;

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    /**
     * gets all the vertices that are in both the given list and incoming vertices list
     * @param vList
     * @return
     */
   public List<Vertex> getCommonVertices(List<Vertex> vList){
        List<Vertex> common = new ArrayList<>(incomingVerticies);
        common.retainAll(vList);
        return common;
    }

    /**
     * set the finalised processor number that this vertex is processed on
     * @param processorNo
     */
    public void setProcessorNo(int processorNo) {
        this.processorNo = processorNo;
    }

    public int getLevel() {
        return level;
    }

   public  boolean isRoot() {
        return incomingEdges.size() == 0;
    }

    public Vertex(String id, int cost) {
        this.id = id;
        this.cost = cost;
        outgoingEdges = new ArrayList<>();
        incomingEdges = new ArrayList<>();
        incomingVerticies = new ArrayList<>();
        outgoingVerticies = new ArrayList<>();
        bottomLevel = -1;
        findLevel();
    }

    public int findLevel() {
        for (Vertex v : incomingVerticies) {
            if (v.level >= level) {
                level = v.level + 1;
            }
        }
        return level;
    }

    public List<Vertex> getIncomingVerticies() {
        return incomingVerticies;
    }

    public boolean containsIncomingVertex(Vertex v) {
        return incomingVerticies.contains(v);
    }

    /**
     * Get the edge weight for the incoming edges from vertex v to this vertex
     * @param v the vertex the edge originates from
     * @return the edge weight of the edge
     */
    public int getEdgeWeightFrom(Vertex v) {
        int cost =-1;
        for(Edge e:incomingEdges){
            if(e.getFromVertex() == v){
                cost = e.getSwitchCost();
            }
        }
        return cost;
    }


    public int getBottomLevel() {
        return bottomLevel;
    }

    /**
     * calculates the longest vertex-cost path from this vertex
     * @return
     */
    public int calculateBottomLevel() {

        dfs(this);
        return bottomLevel;
    }

    /**
     * uses depth first search to find the longest vertex cost from this vertex
     * @param currentVertex
     */
    private void dfs(Vertex currentVertex) {
        // if the bottom level of the current vertex cannot be used to calculate the bottom level
        if (currentVertex.outgoingEdges.size() == 0){
            currentVertex.bottomLevel = currentVertex.cost;
        } else {

            int highestBottomLevel = -1;

            //check bottom level of all next vertices
            for (int i = 0; i < currentVertex.outgoingEdges.size(); i++) {
                Vertex nextVertex = currentVertex.outgoingEdges.get(i).getToVertex();

                //if next bottom level is uninitialised
                if (nextVertex.bottomLevel == -1) {
                    dfs(nextVertex);
                }

                //check if next vertex is largest bottom level so far
                else if (nextVertex.bottomLevel > highestBottomLevel) {
                    highestBottomLevel = nextVertex.bottomLevel;
                }

            }

            currentVertex.bottomLevel = currentVertex.cost + highestBottomLevel;
        }
    }

    /**
     * adds an edge which comes into this vertex
     * @param edge
     */
    public void addIncomingEdge(Edge edge) {
        incomingEdges.add(edge);
        incomingVerticies.add(edge.getFromVertex());
    }

    /**
     * checks if a given list of vertices can visit this vertex
     * @param vList
     * @return
     */
    public boolean canVisit(List<Vertex> vList) {
        return vList.containsAll(incomingVerticies);
    }

    /**
     * adds an edge to the outgoing edge list
     * @param edge
     */
    public void addOutgoingEdge(Edge edge) {
        outgoingEdges.add(edge);
        outgoingVerticies.add(edge.getToVertex());
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

    /**
     * a toString() implementation used only when a processor and start time is associated with the vertex
     * @return
     */
    public String toStringSolution() {
        String output = id + "\t[Weight=" + cost + ",Start=" + startTime + ",Processor=" + processorNo +"];";
        return output;
    }

    @Override
    public String toString() {
        String output = id + "\t[Weight=" + cost + "];";
        return output;
    }

    @Override
    public int hashCode() {
        int result = 17 * id.hashCode() * outgoingEdges.hashCode() * cost;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        Vertex v = (Vertex) obj;
        return v.hashCode() == this.hashCode();
    }
}
