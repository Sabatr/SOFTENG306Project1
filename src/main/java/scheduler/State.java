package scheduler;

import graph.Graph;
import graph.Vertex;
import graph.Edge;
import graph.VertexComparator;

import java.util.*;

/**
 * Class to represent a schedule
 */
public class State {
    List<Processor> processors;
    int currentCost;
    int currentLevel;
    int costToBottomLevel;
    HashMap<Vertex, Integer> prevVertexEndTimeHashMap;
    HashMap<Vertex,Vertex> vertexPrevVertexHashMap;
    Graph g;
    HashMap<Vertex, Integer> lastProcessorAssignedVertex;
    Vertex intAssignedVertex;
    int usedNextStateNumber = 0;
    int newAssignmentProcessor = 0;
    int newAssignmentVertexIndex = 0;
    Vertex newAssignmentVertex;
    private int countNextStateCalled = 0;
    int iterateToTraverse = 0;


    public List<Processor> getProcessors() {
        return processors;
    }

    public Vertex getInitAssignedVertex() { return intAssignedVertex; }

    public int getCurrentCost() {
        return currentCost;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public int getCostToBottomLevel() {
        return costToBottomLevel;
    }

    public Graph getG() {
        return g;
    }

    public void setCurrentCost(int cost){
        currentCost = cost;
    }

    List<Vertex> traversed;
    PriorityQueue<Vertex> toTraverse;
    int lastProcessorVertexAddedTo;
    private int prevProcessNum = -1;
    PriorityQueue<Vertex> nextToTraverse;
    List<Vertex> toTraverseList;

    /**
     * Sets up the State by assigning initial values to the fields
     * @param numProcessors
     * @param g
     */
    public State(int numProcessors, Graph g) {
        traversed = new ArrayList<>();
        processors = new ArrayList<>();
        this.g = g;
        for (int i = 0; i < numProcessors; i++) {
            processors.add(new Processor(i + 1));
        }

        toTraverseList = new ArrayList<>();
        toTraverse = new PriorityQueue<>(new VertexComparator());
        toTraverse.addAll(g.getRoots());

        nextToTraverse = toTraverse;

        currentLevel = 0;
        costToBottomLevel = g.calculateBottomLevel();
        currentCost = 0;
        prevVertexEndTimeHashMap = new HashMap<>();
        lastProcessorAssignedVertex = new HashMap<>();
    }

    /**
     * Create a copy a given state
     * @param copyState
     */
    private State(State copyState) {
        traversed = new ArrayList<>();
        traversed.addAll(copyState.traversed);

        processors = new ArrayList<>();
        this.g = copyState.g;
        for (int i = 0; i < copyState.processors.size(); i++) {
            processors.add(new Processor(copyState.processors.get(i), i + 1));
        }
        toTraverse = new PriorityQueue<>(new VertexComparator());
        toTraverse.addAll(copyState.toTraverse);

        nextToTraverse = toTraverse;


        currentLevel = copyState.currentLevel;
        costToBottomLevel = copyState.costToBottomLevel;
        prevVertexEndTimeHashMap = new HashMap<>(copyState.prevVertexEndTimeHashMap);
        lastProcessorAssignedVertex = new HashMap<>(copyState.lastProcessorAssignedVertex);
    }

    public HashMap<Vertex,Integer> getLastProcessorAssignedVertex(){
        return lastProcessorAssignedVertex;
    }

    public State addVertex(int processorNum, Vertex v) {
        // Clone state then add the new vertex. Will also have to clone the processor list and processor block
        // list within it -> reference disappears once u clone so must use int
        lastProcessorVertexAddedTo = processorNum;
        traversed.add(v);
        toTraverse.remove(v);
        //System.out.println(Arrays.toString(hasBlock.toArray()));
        // Add the vertex to processor x, at the earliest possible time.
        int boundCost = processors.get(processorNum).addVertex(v, traversed, prevVertexEndTimeHashMap);
        costToBottomLevel = Math.max(costToBottomLevel, boundCost);

        // Set the new currentCost && current level
        currentLevel = currentLevel + 1;

        int stateEndTime = 0;

        for (Processor p : processors) {

            if (stateEndTime < p.getEndTime()) {
                stateEndTime = p.getEndTime();
            }

        }

        currentCost = stateEndTime;

        // Update the toTraverseList with new vertexes to travers
        for (Edge e : v.getOutgoingEdges()) {
            Vertex toAdd = e.getToVertex();
            if (!toTraverse.contains(toAdd) && !traversed.contains(toAdd)) {
                toTraverse.add(toAdd);
            }
        }

        //TODO fix this
        // Required to check for duplicates later.
        //Collections.sort(processors);
        prevVertexEndTimeHashMap.putIfAbsent(v,currentCost);
        prevVertexEndTimeHashMap.put(v,Math.max(prevVertexEndTimeHashMap.get(v),currentCost));

        return this;
    }

    public boolean canVisit(Vertex v) {
        //Vertex / Edges to be update to have the from vertices f
        //TODO
        return v.canVisit(traversed);
    }

    public boolean allVisited() {
        //Checks if any more vertexes exist to expand
        return toTraverse.isEmpty();
    }

    public boolean isScheduleEmpty(){
        return traversed.isEmpty();
    }

    public int getCountNextStateCalled(){ return  countNextStateCalled; }
//
//    public Vertex getVertexToAddNext(){
//        if (countNextStateCalled == 1){
//
//        }
//
//    }

    public PriorityQueue<Vertex> getNextToTraverse(){return nextToTraverse; }
    public List<Vertex> getToTraverseList() {return toTraverseList; }

    public State getNextPossibleState(State currentState){
        countNextStateCalled++;
        //nextToTraverse.remove();

        if (!allVisited()) {
            int possibleNextStatesNumber = processors.size() * toTraverse.size();

            if (usedNextStateNumber < processors.size()) {
                usedNextStateNumber++;
            }

            Vertex v = null;


            List<Vertex> toTravList = new ArrayList(nextToTraverse);
            if (iterateToTraverse < toTravList.size()) {
                v = toTravList.get(iterateToTraverse);
            }
            //System.out.println(newV);

            if (countNextStateCalled == processors.size() && iterateToTraverse < toTravList.size()) {
                iterateToTraverse++;
                countNextStateCalled = 0;
            }




//            if (countNextStateCalled == processors.size()){
//                if (toTravList.size() > 1) {
//                    v = toTravList.get(1);
//                }                       countNextStateCalled = 0;
//            } else {
//                if (toTravList.size() > 0) {
//                    v = toTravList.get(0);
//                }
//            }


            if (newAssignmentProcessor < processors.size()) {
                newAssignmentProcessor = newAssignmentProcessor + 1;
            } else {
                newAssignmentProcessor = 1;
            }

            System.out.println("next " + nextToTraverse);
            if (v != null) {

                if (canVisit(v)) {

                    if (!lastProcessorAssignedVertex.containsKey(v)) {
                        lastProcessorAssignedVertex.put(v, newAssignmentProcessor - 1);
                        currentState = new State(this);
                        currentState.addVertex(newAssignmentProcessor - 1, v);
                        return currentState;
                    }
                    if (lastProcessorAssignedVertex.get(v) == processors.size()) {
                        return null;

                    } else if (lastProcessorAssignedVertex.get(v) < processors.size()) {
                        int lastProcessor = lastProcessorAssignedVertex.get(v);
                        lastProcessor = newAssignmentProcessor;
                        lastProcessorAssignedVertex.put(v, lastProcessor - 1);
                        currentState = new State(this);
                        currentState.addVertex(lastProcessor - 1, v);
                        return currentState;

                    }
                }
            }
        }


        return null;
    }

    /**
     * Generates all of the possible states
     * @return
     */
    public HashSet<State> generatePossibilities() {
        //Generates a list of possible states to visit
        HashSet<State> possibleStates = new HashSet<>();
        if (!allVisited()) {
            List<Vertex> toAddList = new ArrayList<>();
            for (Vertex v : toTraverse) {
                if (canVisit(v)) {
                    toAddList.add(v);
                    HashSet<Processor> checkedProcessors = new HashSet<>();
                    for (int i = 0; i < processors.size(); i++) {
                        State copy = new State(this);
                        Processor p = processors.get(i);
                        if(!checkedProcessors.contains(p)) {
                            checkedProcessors.add(p);
                            copy.addVertex(i, v);
                            //lastProcessorAssignedVertex.put(v,i);
                            possibleStates.add(copy);
                        }
                    }
                }
            }
            toTraverse.addAll(toAddList);
            toTraverse.removeAll(toAddList);
        }

        return possibleStates;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return Objects.equals(processors, state.processors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(processors);
    }

    //TODO return a copy of State, fpr a;; addVertex here.
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("\nCurrent Level: " + currentLevel + " Bottom Level: " + costToBottomLevel + " Current Cost: " + currentCost);
        for (int i = 0; i < processors.size(); i++) {
            Processor p = processors.get(i);
            sb.append("\nProcessor " + i + ":" + p.toString());
        }
        return sb.toString() + "\nVerticies Left:" + toTraverse;
    }
    public void outputFormat() {
        for (Processor p : processors) {
            p.outputFormat();
        }

    }

    /**
     * Ensures that a given schedule is valid by ensuring all end time for all vertices of a given
     * vertex are lower than the scheduled start time of that vertex
     * @return
     */
    public boolean isValid() {
        PriorityQueue<ProcessorBlock> pbs = new PriorityQueue<>();
        HashMap<Vertex,ProcessorBlock> vertexProcessorBlockHashMap = new HashMap<>();
        for (Processor p : processors) {
            for(ProcessorBlock pb:p.getProcessorBlockList()){
                pbs.add(pb);
                vertexProcessorBlockHashMap.put(pb.getV(),pb);
            }
        }
        for(ProcessorBlock pb: pbs){
            Vertex v = pb.getV();
            int starttime = pb.getStartTime();
            for(Vertex v1: v.getIncomingVerticies()){
                if(starttime < vertexProcessorBlockHashMap.get(v1).getEndTime()){
                    return false;
                };
            }
        }
        return true;
    }
}
