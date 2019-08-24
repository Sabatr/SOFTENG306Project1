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
 * Algorithm which deals with  the problem using the A star implementation. Here, a priority queue
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

        // Run until nothing is left in the priority queue
        while (!candidate.isEmpty()) {
            State s = candidate.poll();

            // Generate possibilities for a given state
            for (State s1 : s.generatePossibilities()) {
                AlgorithmDataStorage.getInstance().setTotalBranches(totalBranches);
                totalBranches++;

                // Only visit if it hasn't been visited before
                if (!visited.contains(s1)) {

                    // Ensure heuristic allows the state to be an optimal
                    if (s1.getCostToBottomLevel() < minFullPath) {
                        candidate.add(s1);

                        // If a new optimal state is found, update result
                        if (s1.allVisited() && s1.getCostToBottomLevel() < minFullPath) {

                            // Prune branches
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

}
