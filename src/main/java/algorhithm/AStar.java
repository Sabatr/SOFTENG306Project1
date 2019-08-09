package algorhithm;

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
public class AStar  implements  Algorithm{
    private int minFullPath = Integer.MAX_VALUE;
    private boolean traversed;
    private PriorityQueue<State> candidate;
    private HashSet<String> visited;
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
     * @return
     */
    public State runAlgorithm() {
        State result = null;
        while (!candidate.isEmpty() && candidate.peek().getCostToBottomLevel() <= minFullPath) {
            State s = candidate.poll();
            for (State s1 : s.generatePossibilities()) {
                //TODO ensure toString creates a unique sorted schedule string
                if (!visited.contains(s1)) {
                    if (s1.getCostToBottomLevel() < minFullPath) {
                        candidate.add(s1);
                        if (s1.allVisited() && s1.getCostToBottomLevel() < minFullPath) {
                            minFullPath = s1.getCostToBottomLevel();
                            result = s1;
                        }
                    }
                    visited.add(s1.toString());
                }
            }

        }
        return result;
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
