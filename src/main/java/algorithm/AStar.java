package algorithm;

import graph.Graph;
import graph.Vertex;
import scheduler.AStarComparator;
import scheduler.Processor;
import scheduler.State;
import visualisation.AlgorithmDataStorage;
import visualisation.processor.listeners.ObservableAlgorithm;
import visualisation.processor.listeners.SchedulerListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Algorithm which deals with using the A star implementation. Here, a priority queue
 * is used to ensure that nodes with least cost are placed with greatest priority followed
 * by their level.
 */
public class AStar extends AlgorithmHandler implements  Algorithm {
    private int minFullPath = Integer.MAX_VALUE;
    private boolean traversed;
    private PriorityQueue<State> candidate;
    private HashSet<State> visited;
    private Graph graph;
    private State finalState;
    private int numberOfProcessors;
    private int prunedBranches;
    private int totalBranches;

    public AStar(int numProcessors, Graph graph) {
        super();
        totalBranches = 1;
        this.numberOfProcessors = numProcessors;
        candidate = new PriorityQueue<>(new AStarComparator());
        visited = new HashSet();
        this.graph = graph;
        traversed = false;
        candidate.add(new State(numberOfProcessors, graph));
    }

    /**
     * Runs the algorithm
     *
     * @return
     */
    public State runAlgorithm() {
        startTimer();
        finalState = null;
        AStarComparator aStarComparator = new AStarComparator();
        State result = null;
        while (!candidate.isEmpty()) {

            State s = candidate.poll();
            for (State s1 : generatePossibilities(s)) {
                AlgorithmDataStorage.getInstance().setTotalBranches(totalBranches);
                totalBranches++;
                if (!visited.contains(s1)) {
                    if (s1.getCostToBottomLevel() < minFullPath) {
                        candidate.add(s1);
                        if (s1.allVisited() && s1.getCostToBottomLevel() < minFullPath) {
                            //Prune branches
                            minFullPath = s1.getCostToBottomLevel();
                            finalState = s1;
                            AlgorithmDataStorage.getInstance().setPrunedBranches(
                                    AlgorithmDataStorage.getInstance().getDetails().getBranchesPruned()
                                            +minFullPath);

                        }
                    }
                    visited.add(s1);
                }
            }
        }
        fireEvent(AlgorithmEvents.ALGORITHM_FINISHED,finalState);
        return finalState;
    }
    /**
     * Generates all of the possible states
     * @return
     */
    public HashSet<State> generatePossibilities(State state) {
        int duplicates = 0;
        //Generates a list of possible states to visit

        HashSet<State> possibleStates = new HashSet<>();
        if (!state.allVisited()) {
            List<Vertex> toAddList = new ArrayList<>();
            for (Vertex v : state.getToTraverse()) {
                if (state.canVisit(v)) {
                    toAddList.add(v);
                    HashSet<Processor> checkedProcessors = new HashSet<>();
                    for (int i = 0; i < state.getProcessors().size(); i++) {
                        State copy = new State(state);
                        Processor p = state.getProcessors().get(i);
                        if(!checkedProcessors.contains(p)) {
                            checkedProcessors.add(p);
                            copy.addVertex(i, v);
                            possibleStates.add(copy);
                        }else{
                            // Duplicate branches count found here.
                            duplicates++;
                        }
                    }
                }
            }
            state.getToTraverse().addAll(toAddList);
            state.getToTraverse().removeAll(toAddList);
        }

        return possibleStates;

    }
    //Todo implement this class.
    /*
    Initialise MinFullPath to integer.Maxint
    Add the initial State(Empty, VisitedList(root),CandidateList(roots' children),currentCost) to the
    Priority Queue
    While the priorityQueue is not empty:
        Generate the possibilities involving all nodes in the candidate list
        If we have traversed all nodes and cost is less than the minFullPathCost:
            Add the possibilities onto the priority queue
        Else:
            Add the possibilities onto the priority queue
        Pop off the priority queue
        if current is full state and cheaper than minFullPath:
            replace minFullPath
            For all states in priority queue:
                If cost is less than the minFullPathCost:
                    Remove it from the priority queue
    done
    Select the State with cheapest DFS cost
     */
}
