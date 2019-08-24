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
    Graph g;

    public List<Processor> getProcessors() {
        return processors;
    }

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

    List<Vertex> traversed;
    List<Vertex> toTraverse;
    int lastProcessorVertexAddedTo;
    private int prevProcessNum = -1;

    /**
     * Sets up the State by assigning initial values to the fields
     *
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
        toTraverse = new ArrayList<>();
        toTraverse.addAll(g.getRoots());
        currentLevel = 0;
        costToBottomLevel = g.calculateBottomLevel();
        currentCost = 0;
    }

    /**
     * Create a copy a given state
     *
     * @param copyState
     */
    public State (State copyState) {
        traversed = new ArrayList<>();
        traversed.addAll(copyState.traversed);
        processors = new ArrayList<>();
        this.g = copyState.g;
        for (int i = 0; i < copyState.processors.size(); i++) {
            processors.add(new Processor(copyState.processors.get(i), i + 1));
        }
        toTraverse = new ArrayList<>();
        toTraverse.addAll(copyState.toTraverse);
        currentLevel = copyState.currentLevel;
        costToBottomLevel = copyState.costToBottomLevel;
    }

    /**
     * Generates a hashmap containing a vertex and their associated end time. Required for speeding up
     * the program later on.
     *
     * @return
     */
    public HashMap<Vertex, Integer> getPrevVertexEndTimeHashMap() {
        HashMap<Vertex, Integer> prevVertexEndTimeHashMap = new HashMap<Vertex, Integer>();
        if (traversed.size() > 0) {
            for (Processor processor : processors) {
                for (ProcessorBlock block : processor.processorBlockList) {
                    prevVertexEndTimeHashMap.put(block.getV(), block.getEndTime());
                }
            }
        }
        return prevVertexEndTimeHashMap;
    }

    /**
     * Method to help generate a stae, when assigning vertex x to processor at processorNum.
     * Checks are passed before to sensure that this vertex to be added has its incoming vertex requriements
     * fufilled.
     *
     * @param processorNum
     * @param v
     * @return
     */
    public State addVertex(int processorNum, Vertex v) {
        //Update the lists to ensure correct representation of this state.
        lastProcessorVertexAddedTo = processorNum;
        traversed.add(v);
        toTraverse.remove(v);

        // Add the vertex to processor x, at the earliest possible time.
        int boundCost = processors.get(processorNum).addVertex(v, traversed, getPrevVertexEndTimeHashMap());
        costToBottomLevel = Math.max(costToBottomLevel, boundCost);

        // Set the new currentCost && current level
        currentLevel = currentLevel + 1;

        calculateCurrentCost();
        updateToTraverse(v);

        // Sorting to ensure unique schedule to allow for checking for duplicates.
        Collections.sort(processors);

        return this;
    }

    /**
     * Updates the toTraverse list with the new states;
     * @param v
     */
    private void updateToTraverse(Vertex v){

        for (Edge e : v.getOutgoingEdges()) {
            Vertex toAdd = e.getToVertex();
            if (!toTraverse.contains(toAdd) && !traversed.contains(toAdd)) {
                toTraverse.add(toAdd);
            }
        }
    }
    /**
     * Method to update the current cost of this class.
     */
    private void calculateCurrentCost() {
        int stateEndTime = 0;
        for (Processor p : processors) {
            if (stateEndTime < p.getEndTime()) {
                stateEndTime = p.getEndTime();
            }
        }
        currentCost = stateEndTime;
    }

    /**
     * Vertex / Edges to be update to have the from vertices f
     * @param v
     * @return
     */
    public boolean canVisit(Vertex v) {
        return v.canVisit(traversed);
    }

    public boolean allVisited() {
        //Checks if any more vertexes exist to expand
        return traversed.size() == g.getNumVertices();
    }

    public List<Vertex> getToTraverse() {
        return toTraverse;
    }

    /**
     * Generates all of the possible states
     *
     * @return
     */
    public HashSet<State> generatePossibilities() {
        //Generates a list of possible states to visit
        HashSet<State> possibleStates = new HashSet<>();

        // Checks if state is final. i,e all vertices appear in the schedule.
        if (!allVisited()) {
            List<Vertex> toAddList = new ArrayList<>();

            // Go through all vertices left, and create the possible permutations of state.
            for (Vertex v : toTraverse) {
                if (canVisit(v)) {
                    toAddList.add(v);

                    // Required to check for processors which have already been added to
                    // Helps to reduce unecessary exploration of already explored places.
                    HashSet<Processor> checkedProcessors = new HashSet<>();
                    for (int i = 0; i < processors.size(); i++) {
                        Processor p = processors.get(i);
                        if (!checkedProcessors.contains(p)) {
                            State copy = new State(this);
                            checkedProcessors.add(p);
                            copy.addVertex(i, v);
                            possibleStates.add(copy);
                        }
                    }

                }
            }

            //Update lists with new information
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

    /**
     * Way of visualising the current state
     * @return
     */
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
        // Checks to see if there are still vertices to traverse
        if (toTraverse.size() > 0){
            return false;
        }

        for (Processor processor : processors){
            // Adds all of the processor blocks to a priority queue
            List<ProcessorBlock> processorBlockPriorityQueue = new ArrayList<>();
            for(ProcessorBlock processorBlock : processor.getProcessorBlockList()){
                processorBlockPriorityQueue.add(processorBlock);
                // Add processors to priority queue to order them
            }

            int costOfPrevAssignedVertex = 0;

            for (ProcessorBlock currentProcessorBlock : processorBlockPriorityQueue){
                Vertex processorBlockV = currentProcessorBlock.getV();
                int processorBlockStartTime = currentProcessorBlock.getStartTime();

                // Checking to see if there is no overlap between the previously assigned vertex and the current assigned
                if (costOfPrevAssignedVertex > processorBlockStartTime){
                    return false;
                }

                // Go through all of the vertices v depends on and see if the are scheduled correctly
                for(Vertex processorBlockV1: processorBlockV.getIncomingVerticies()){
                    for (Processor processor1 : processors){
                        if (processor1.processorVertexList.contains(processorBlockV1)){
                            // Get end time of incoming vertex
                            int endTimeIncomingV = 0;
                            for (ProcessorBlock processorBlock  : processor1.processorBlockList){
                                if (processorBlock.getV() == processorBlockV1){
                                    endTimeIncomingV = processorBlock.getEndTime();
                                    break;
                                }
                            }
                            // Check if v1, the vertices v depends on is scheduled in the same processor
                            if (processor1 == processor){
                                // Making sure incoming v is before current v
                                if (currentProcessorBlock.getStartTime() < endTimeIncomingV ){
                                    return false;
                                }

                            } else{ // Check if v1, the vertices v depends on is scheduled on a different processor
                                //  Make sure incoming v with its transferring cost is still less than current vertex
                                if (currentProcessorBlock.getStartTime() < endTimeIncomingV + processorBlockV.getEdgeWeightFrom(processorBlockV1) ){
                                    return false;
                                }
                            }
                        }
                    }
                }
                costOfPrevAssignedVertex = currentProcessorBlock.getEndTime();
            }
        }


        //Check if cost to bottom level is same as current cost
        if (costToBottomLevel != currentCost){
            return false;
        }

        return true;
    }
}
