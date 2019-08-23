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
public class DFSParallel implements Algorithm {
    private int minFullPath = Integer.MAX_VALUE;
    private boolean traversed;
    private Stack<State> candidate;
    private HashSet<State> visited;
    private Graph graph;

    private int currentThreads;
    private int MAX_THREADS;


    private AStarComparator aStarComparator;
    private State result;


    public DFSParallel(int numProcessors, Graph graph, int maxThreads) {
        candidate = new Stack<>();
        visited = new HashSet();
        this.graph = graph;
        traversed = false;
        candidate.add(new State(numProcessors, graph));
        aStarComparator = new AStarComparator();
        MAX_THREADS = maxThreads;
    }


    public DFSParallel(int numProcessors, Graph graph) {
        candidate = new Stack<>();
        visited = new HashSet();
        this.graph = graph;
        traversed = false;
        candidate.add(new State(numProcessors, graph));
        aStarComparator = new AStarComparator();
        MAX_THREADS = 4;
    }

    /**
     * Runs the algorithm
     *
     * @return
     */
    public State runAlgorithm() {
        while (!candidate.isEmpty()) {

            if (currentThreads < MAX_THREADS ) {
                currentThreads++;
                new DFSThread().start();
                iterate();
            } else {
                iterate();
            }
        }

        return result ;
    }

    private synchronized void stackPush(State s){
        candidate.push(s);
    }

    private synchronized void stackRemove(State s){
       // candidate.removeIf((state) -> aStarComparator.compare(s, state) < 0);
    }

    private synchronized State stackPop(){
        State s = candidate.pop();
        return s;
    }

    private synchronized void setResult(State s){
        result = s;
    }

    public void iterate(){
        State s = stackPop();
        for (State s1 : s.generatePossibilities()) {
            if (!visited.contains(s1)) {
                if (s1.getCostToBottomLevel() < minFullPath) {
                    //candidate.push(s1);
                    stackPush(s1);
                    if (s1.allVisited() && s1.getCostToBottomLevel() < minFullPath) {
                        //Prune branches
                        stackRemove(s1);
                        //candidate.removeIf((state) -> aStarComparator.compare(s1, state) < 0);
                        minFullPath = s1.getCostToBottomLevel();
                        setResult(s1);
                    }
                }
                visited.add(s1);
            }
        }
    }

    private class DFSThread extends Thread{
        @Override
        public void run() {
            runAlgorithm();
        }
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

