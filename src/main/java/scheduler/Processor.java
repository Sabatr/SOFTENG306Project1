package scheduler;

import graph.Graph;
import graph.Vertex;

import java.util.*;

/**
 * Class to represent one CPU/processor on a schedule
 */
public class Processor implements Comparable<Processor> {
    List<ProcessorBlock> processorBlockList;
    List<Vertex> processorVertexList;

    HashMap<String, Integer> processorBlockHashMap;
    HashSet<Vertex> visited;
    Vertex startVertex;
    Graph g;

    int processorNumber;
    int boundCost;
    int startCost;

    /**
     * Setting up the fields of the Processor with initial values
     */
    Processor(int processorNumber) {
        processorBlockHashMap = new HashMap<String, Integer>();
        this.processorNumber = processorNumber;
        processorBlockList = new ArrayList<>();
        processorVertexList = new ArrayList<Vertex>();

        startCost = 0;
        boundCost = 0;
        visited = new HashSet<>();
    }

    /**
     * Create a copy a given processor
     */
    public Processor(Processor toCopy, int processorNumber) {
        this.processorNumber = processorNumber;
        processorBlockList = new ArrayList<>();
        processorVertexList = new ArrayList<>();
        processorBlockHashMap = new HashMap<String, Integer>();
        startCost = toCopy.startCost;
        boundCost = toCopy.boundCost;
        processorBlockList.addAll(toCopy.processorBlockList);
        processorVertexList.addAll(toCopy.processorVertexList);
        processorBlockHashMap = toCopy.processorBlockHashMap;
        visited = new HashSet<>(toCopy.visited);
    }

    /**
     * Gets start time by comparing all of the vertices the new vertex depends on but are not in this processor and
     * see which one out of them has the highest end time plus communication cost to the new vertex. The vertex
     * with highest will be assigned as the start time for the new vertex
     *
     * @param v
     * @param prevVertexEndTimeHashMap
     * @return
     */
    private int getStartTime(Vertex v,
                             HashMap<Vertex, Integer> prevVertexEndTimeHashMap) {
        int maxStartTime = 0;

        for (Vertex dependedVertex : v.getIncomingVerticies()) {
            if (!visited.contains(dependedVertex)) {
                int finishTimeOfDependedVertex = prevVertexEndTimeHashMap.get(dependedVertex);
                int communicationCost = v.getEdgeWeightFrom(dependedVertex);
                int possibleStartTime = communicationCost + finishTimeOfDependedVertex;

                if (possibleStartTime > maxStartTime) {
                    maxStartTime = possibleStartTime;
                }
            }
        }

        return maxStartTime;

    }

    /**
     * This method assignes the new vertex to this processor as a processor block. To decide the start time of
     * the new processor block, it is checked if there are vertices that the new vertex relies on that is not
     * in this processor. If there are, then output of the method getStartTime is assigned as its start time. But
     * if this processors finish time is greater than the new start time, the processors finish time is assigned as
     * the start time. If all dependent vertices are in this processor, than the finish time of this processor is
     * assigned as start time.
     *
     * @param v
     * @param traversed
     * @param prevVertexEndTimeHashMap
     * @return
     */
    public int addVertex(Vertex v, List<Vertex> traversed, HashMap<Vertex, Integer> prevVertexEndTimeHashMap) {
        visited.add(v);

        //Set start time init value

        //Check vertex v depends on not in this processor
        int startTime = getStartTime(v, prevVertexEndTimeHashMap);

        if (processorBlockList.size() != 0) {
            ProcessorBlock lastProcessorBlock = processorBlockList.get(processorBlockList.size() - 1);
            //Check if this processor end time is greater than potentialTimeOfStart
            startTime = Math.max(lastProcessorBlock.getEndTime(), startTime);
        }

        ProcessorBlock newProcBlock = new ProcessorBlock(v, startTime);
        processorBlockList.add(newProcBlock);
        processorVertexList.add(v);

        boundCost = startTime + v.getBottomLevel();
        return boundCost;
    }

    @Override
    public int compareTo(Processor processor) {
        int result = this.startCost - processor.startCost;
        if (result == 0) {
            String id0 = "";
            String id1 = "";
            if (processorBlockList.size() != 0) {
                id0 = processorBlockList.get(0).getV().getId();
            }
            if (processor.processorBlockList.size() != 0) {
                id1 = processor.processorBlockList.get(0).getV().getId();
            }
            result = id0.compareTo(id1);
        }
        return result;
    }

    public int getEndTime() {
        if (processorBlockList.size() > 0) {
            return processorBlockList.get(processorBlockList.size() - 1).getEndTime();
        }
        return 0;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Processor processor = (Processor) o;
        return Objects.equals(processorBlockList, processor.processorBlockList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(processorBlockList);
    }

    @Override
    public String toString() {
        return processorBlockList.toString();
    }

    public void outputFormat() {
        for (int i = 0; i < processorBlockList.size(); i++) {
            ProcessorBlock currentProcessorBlock = processorBlockList.get(i);
            Vertex currentVertex = currentProcessorBlock.getV();
            currentVertex.setStartTime(currentProcessorBlock.getStartTime());
            currentVertex.setProcessorNo(processorNumber);
        }

    }

    public List<ProcessorBlock> getProcessorBlockList() {
        return processorBlockList;
    }
}
