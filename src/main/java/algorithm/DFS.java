package algorithm;

import graph.Graph;
import scheduler.AStarComparator;
import scheduler.State;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Stack;

/**
 * Algorithm which deals with using the A star implementation. Here, a priority queue
 * is used to ensure that nodes with least cost are placed with greatest priority followed
 * by their level.
 */
public class DFS implements Algorithm {
    private int minFullPath = Integer.MAX_VALUE;
    private boolean traversed;
    private Stack<State> candidate;
    private HashSet<State> visited;
    private Graph graph;

    public DFS(int numProcessors, Graph graph) {
        candidate = new Stack<>();
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
        while (!candidate.isEmpty()) {
            State s = candidate.pop();
            for (State s1 : s.generatePossibilities()) {
                if (!visited.contains(s1)) {
                    if (s1.getCostToBottomLevel() < minFullPath) {
                        candidate.push(s1);
                        if (s1.allVisited() && s1.getCostToBottomLevel() < minFullPath) {
                            //Prune branches
                            candidate.removeIf( (state) -> aStarComparator.compare(s1,state) < 0);
                            minFullPath = s1.getCostToBottomLevel();
                            result = s1;
                        }
                    }
                    visited.add(s1);
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

