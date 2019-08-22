package algorithm;

import graph.Graph;
import scheduler.AStarComparator;
import scheduler.State;

import java.util.HashSet;
import java.util.PriorityQueue;

/**
 * Algorithm which deals with using the A star implementation. Here, a priority queue
 * is used to ensure that nodes with least cost are placed with greatest priority followed
 * by their level.
 */
public class AStar implements Algorithm {
    private int minFullPath = Integer.MAX_VALUE;
    private boolean traversed;
    private PriorityQueue<State> candidate;
    private HashSet<State> visited;
    private Graph graph;

    public AStar(int numProcessors, Graph graph) {
        candidate = new PriorityQueue<>(new AStarComparator());
        visited = new HashSet();
        this.graph = graph;
        traversed = false;
        candidate.add(new State(numProcessors, graph));
    }

    /**
     * Runs the algorithm
     *
     * @return
     */
    public State runAlgorithm() {
        AStarComparator aStarComparator = new AStarComparator();
        State result = null;

        //Run until all states in candidate are finished
        while (!candidate.isEmpty()) {
            State s = candidate.poll();
            for (State s1 : s.generatePossibilities()) {

                //Only visit states which haven't previously been visited, which are still lower
                // than the current best solution
                if (!visited.contains(s1) && s1.getCostToBottomLevel() < minFullPath) {
                    candidate.add(s1);

                    // Check if it is a complete schedule I.E all vertices of the graph have been
                    // incorporated
                    if (s1.allVisited()) {
                        //Prune branches
                        candidate.removeIf((state) -> aStarComparator.compare(s1, state) < 0);
                        minFullPath = s1.getCostToBottomLevel();
                        result = s1;
                    }
                }
                visited.add(s1);
            }

        }
        return result;
    }

}
