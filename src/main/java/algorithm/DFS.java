package algorithm;

import graph.Graph;
import scheduler.AStarComparator;
import scheduler.State;
import visualisation.AlgorithmDataStorage;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Stack;

/**
 * Algorithm which uses a stack in place of a priority queue to ensure try and find the optimal
 * schedule for the problem at hand
 */
public class DFS extends AlgorithmHandler implements Algorithm  {
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
        startTimer();
        AStarComparator aStarComparator = new AStarComparator();
        State result = null;

        //Run until the priority queue is empty
        while (!candidate.isEmpty()) {
            State s = candidate.pop();

            //Generate the possibilities for state s
            for (State s1 : s.generatePossibilities()) {
                AlgorithmDataStorage.getInstance().setTotalBranches(visited.size());

                //Only try to visit if not visited before
                if (!visited.contains(s1)) {

                    // If it could be an optimal solution
                    if (s1.getCostToBottomLevel() < minFullPath) {
                        candidate.push(s1);

                        // If it is more optimal, then prune and set new optimal
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
        fireEvent(AlgorithmEvents.ALGORITHM_FINISHED,result);
        return result;
    }

}
